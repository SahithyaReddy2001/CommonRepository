package Demo.CRUDoperations.download;

import Demo.CRUDoperations.entity.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelDownload {
    public static String OrderDetails[] = {"id", "productId", "taxAmount", "nonTaxAmount", "status"};
    public static String orderSheet = "orderDetails";

    public static ByteArrayInputStream orderDownload(List<Order> orders) throws IOException {
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
                //row1.createCell(1).setCellValue(o.getProduct().getId());
                row1.createCell(2).setCellValue(o.getTaxAmount());
                row1.createCell(3).setCellValue(o.getNonTaxAmount());
                row1.createCell(4).setCellValue(String.valueOf(o.getStatus()));
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
