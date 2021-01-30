package de.funky.backend;

import de.funky.controller.MainFxController;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;

public class ExcelWorkbook {

  public static XSSFWorkbook workbook;
  private static XSSFSheet steamSheet;
  private static XSSFCellStyle headerStyle;
  private static XSSFCellStyle columnStyle;

  public ExcelWorkbook() {
  }

  public static void setWorkbook(XSSFWorkbook xssfWorkbook) {
    workbook = xssfWorkbook;
  }

  public static XSSFWorkbook getWorkbook() {
    return workbook;
  }

  public XSSFSheet getSteamSheet() {
    return steamSheet;
  }

  public static XSSFCellStyle getHeaderStyle() {
    return headerStyle;
  }

  public static XSSFCellStyle getColumnStyle() {
    return columnStyle;
  }

  public static void creationOfWorkbook() {
    workbook = new XSSFWorkbook();
    XSSFFont headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 15);
    headerFont.setFontName("Times New Roman");
    headerFont.setUnderline((byte) 1);
    headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setFont(headerFont);
    columnStyle = workbook.createCellStyle();
    columnStyle.setAlignment(HorizontalAlignment.CENTER);
  }

  public static void creationOfSteamSheet(JSONArray allGames,
                                         String[][] gamesAndTime) {
    if (workbook == null) {
      creationOfWorkbook();
      steamSheet = getWorkbook().createSheet("Steam");
    }
    Row row = steamSheet.createRow(0);
    Cell cell = row.createCell(0);
    cell.setCellStyle(getHeaderStyle());
    Cell cell1 = row.createCell(1);
    cell1.setCellStyle(getHeaderStyle());
    cell.setCellValue("Title of the Game");
    cell1.setCellValue("Time you played");
    int rowCount = 1;
    for (int i = 0; i < allGames.length(); i++) {
      row = steamSheet.createRow(rowCount++);
      cell = row.createCell(0);
      cell1 = row.createCell(1);
      cell1.setCellStyle(getColumnStyle());
      cell.setCellValue(gamesAndTime[i][0]);
      cell1.setCellValue(Integer.parseInt(gamesAndTime[i][1]));
    }
    steamSheet.autoSizeColumn(0);
    steamSheet.autoSizeColumn(1);
  }

  //    public void creationOfGogSheet(){}

  public static void writingOfWorkbook() throws IOException {
    workbook.write(new FileOutputStream(MainFxController
                .filePath + "/AllGamesPlatforms.xlsx"));
    workbook.close();
  }
}
