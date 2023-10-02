package Demo.CRUDoperations.download;

import Demo.CRUDoperations.entity.Order;
import Demo.CRUDoperations.repository.OrderItemRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelDownload {

    public static String OrderDetails[] = {"id", "taxAmount", "nonTaxAmount", "status", "customerId", "customerName", "customerEmail", "createdDate", "updatedDate"};
    public static String orderSheet = "orderDetails";

    public static ByteArrayInputStream orderDownload(List<Order> orders) throws IOException {
        OrderItemRepository orderItemRepository = null;

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(orderSheet);

            Row row = sheet.createRow(0);
            for (int i = 0; i < OrderDetails.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(OrderDetails[i]);
            }

            int rowIndex = 1;
            for (Order o : orders) {
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(o.getId());
                row1.createCell(1).setCellValue(String.valueOf(orderItemRepository.findByOrderId(o.getId())));
                row1.createCell(2).setCellValue(o.getTaxAmount());
                row1.createCell(3).setCellValue(o.getNonTaxAmount());
                row1.createCell(4).setCellValue(String.valueOf(o.getStatus()));
                row1.createCell(5).setCellValue(String.valueOf(o.getCustomer()));
                row1.createCell(6).setCellValue(o.getCustomer().getId());
                row1.createCell(7).setCellValue(o.getCustomer().getName());
                row1.createCell(8).setCellValue(o.getCustomer().getEmail());
                row1.createCell(9).setCellValue(o.getCreatedDate());
                row1.createCell(10).setCellValue(o.getUpdatedDate());
            }


            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }
}
