package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.ProductService;
import Demo.CRUDoperations.service.kafkaservice.ConsumerService;
import Demo.CRUDoperations.service.kafkaservice.ProducerService;
import Demo.CRUDoperations.service.mailservice.MailService;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    MailService mailService;

    @Autowired
    KafkaTemplate<String, ProductRequest> kafkaTemplate;


    @Autowired
    ProducerService producerService;

   /* public ApiResponse fileData(MultipartFile file)throws IOException {
        InputStream inputStream=file.getInputStream();
        //FileInputStream file1=new FileInputStream(inputStream);
        //file1.
        BufferedReader s=new BufferedReader(new InputStreamReader(inputStream));
        List<Product> li=s.lines().map(n->{
            String[] n1=n.split(",");
            Product product=new Product(n1[0],Float.parseFloat(n1[1]),Float.parseFloat(n1[2]));
            product.setStatus(Status.ACTIVE);
            productRepository.save(product);
            return product;
        }).collect(Collectors.toList());
        System.out.println(li);
        return new ApiResponse(HttpStatus.CREATED.value(),li,"All products are inserted in data base",true);
    }*/



    public ByteArrayInputStream getFile() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = workbook.createSheet("products");
        Row row = xssfSheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("Id");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("name");
        Cell cell2 = row.createCell(2);
        cell2.setCellValue("Price");
        Cell cell3 = row.createCell(3);
        cell3.setCellValue("Tax");
        Cell cell4 = row.createCell(4);
        cell4.setCellValue("Status");
        List products = (List) getProducts().getData();
        for (int i = 1; i < products.size(); i++) {
            ProductResponse product = (ProductResponse) products.get(i);
            row = xssfSheet.createRow(i);
            cell0 = row.createCell(0);
            cell0.setCellValue(product.getId());
            cell1 = row.createCell(1);
            cell1.setCellValue(product.getName());
            cell2 = row.createCell(2);
            cell2.setCellValue(product.getPrice());
            cell3 = row.createCell(3);
            cell3.setCellValue(product.getTax());
            cell4 = row.createCell(4);
            cell4.setCellValue(String.valueOf(product.getStatus()));

        }

        ByteArrayOutputStream data = new ByteArrayOutputStream();
        workbook.write(data);
        return new ByteArrayInputStream(data.toByteArray());

    }

    public ApiResponse fileData(MultipartFile file) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
        //xssfWorkbook.getName();
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Iterator<Row> rowIterator = xssfSheet.rowIterator();
        List<Product> products = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //row.getCell(0);
            int cellcount = 0;
            Product product = new Product();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cellcount) {
                    case 0:
                        product.setName(cell.getStringCellValue());
                        break;
                    case 1:
                        product.setPrice((float) cell.getNumericCellValue());
                        break;
                    case 2:
                        product.setTax((float) cell.getNumericCellValue());
                        break;
                }
                cellcount++;

            }
            product.setStatus(Status.ACTIVE);
            products.add(product);
        }
        products = productRepository.saveAll(products);
        return new ApiResponse(HttpStatus.CREATED.value(), products, "All products are inserted", true);
    }

    public ApiResponse getProducts() {
        List<ProductResponse> productResponses = productRepository.findByStatus()
                .stream().map(ProductResponse::new).collect(Collectors.toList());
        return new ApiResponse(HttpStatus.OK.value(), productResponses, HttpStatus.OK.getReasonPhrase(), true);
    }

    public ApiResponse deleteProduct(int id) {

        Optional<Product> optionalProduct = Optional.of(getProductById(id));
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus(Status.INACTIVE);
            productRepository.save(product);
            return getProducts();
        }

        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase(), false);
    }

    public ApiResponse getProduct(int id) {
        Product product = getProductById(id);
        ProductResponse productResponse = new ProductResponse(product);
        return new ApiResponse(HttpStatus.OK.value(), productResponse, HttpStatus.OK.getReasonPhrase(), Boolean.TRUE);
    }

    public ApiResponse consumer(ProductRequest productRequest) {

        kafkaTemplate.send("Products", productRequest);
        return new ApiResponse(HttpStatus.CREATED.value(), productRequest, HttpStatus.CREATED.getReasonPhrase(), true);
    }

    @KafkaListener(topics = "Products", groupId = "Insert_products")
    public void upsert(ProductRequest productRequest) {
        Optional<Product> optionalProduct = Optional.empty();
        if (!ObjectUtils.isEmpty(productRequest.getId())) {
            optionalProduct = Optional.of(getProductById(productRequest.id));
        }
        Product product = optionalProduct.orElse(new Product());
        System.out.println(product);
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setTax(productRequest.getTax());
        product.setStatus(Status.ACTIVE);
        System.out.println(product);
        //product.getCreatedDate()==null?product.setCreatedDate(LocalDateTime.now()):product.setUpdatedDate(LocalDateTime.now());
        ProductResponse productResponse = new ProductResponse(productRepository.save(product));
        producerService.productConformation(productResponse);
        //return new ApiResponse(HttpStatus.CREATED.value(), productResponse, HttpStatus.CREATED.getReasonPhrase(), true);
    }

   /* public ApiResponse upsert(ProductRequest productRequest) {


        Optional<Product> optionalProduct = Optional.empty();
        if (!ObjectUtils.isEmpty(productRequest.getId())) {
            optionalProduct = Optional.of(getProductById(productRequest.id));
        }

        Product product = optionalProduct.orElse(new Product());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setTax(productRequest.getTax());
        product.setStatus(Status.ACTIVE);
        ProductResponse productResponse = new ProductResponse(productRepository.save(product));
        mailService.sendMail(productResponse);
        return new ApiResponse(HttpStatus.CREATED.value(), productResponse, HttpStatus.CREATED.getReasonPhrase(), true);
    }*/


    public ApiResponse getProductsByOffset(int offset, int limit) {
        List<ProductResponse> products;
        if (offset <= 0 || limit < 1) {
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Entered page or page_size is less than 1", false);
        }
        products = productRepository.findAll(PageRequest.of(offset - 1, limit)).stream().map(n -> new ProductResponse(n)).collect(Collectors.toList());
        String message = "TotalPages=" + (productRepository.findAll().size() / limit + 1);
        return new ApiResponse(HttpStatus.OK.value(), products, message, true);
    }


    public ApiResponse getProductOrders(int id) {
        ApiResponse apiResponse=new ApiResponse(HttpStatus.OK.value(), productRepository.getByProductId(id), "All product orders", true);
        System.out.println(apiResponse.getData());
        return apiResponse;
    }


    public ApiResponse sortByName(String sort) {
        List<ProductResponse> products;
        products = productRepository.findAll(Sort.by(Sort.Direction.DESC, sort)).stream().map(n -> new ProductResponse(n)).collect(Collectors.toList());
        return new ApiResponse(HttpStatus.OK.value(), products, "sorted order by " + sort, true);
    }

    public ApiResponse forEmailReport(String email){
        mailService.sendReport(email);
        return new ApiResponse(HttpStatus.OK.value(), "report.csv","Report is send to "+email,true);

    }

    private Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            throw new NoSuchElementException("Inavlid product id");
        return optionalProduct.get();
    }

}
