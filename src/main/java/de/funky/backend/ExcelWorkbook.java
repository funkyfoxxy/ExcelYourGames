package de.funky.backend;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWorkbook {

    public static XSSFWorkbook workbook;
    private XSSFSheet steamSheet;
    private XSSFCellStyle headerStyle;
    private XSSFCellStyle columnStyle;

    public ExcelWorkbook() {
    }

    public static void setWorkbook(XSSFWorkbook xssfWorkbook) {
        workbook = xssfWorkbook;
    }

    public static XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public XSSFSheet getSteamSheet() {
        return this.steamSheet;
    }

    public XSSFCellStyle getHeaderStyle() {
        return this.headerStyle;
    }

    public XSSFCellStyle getColumnStyle() {
        return this.columnStyle;
    }

    public void creationOfWorkbook() {
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

    public void creationOfSteamSheet(JSONArray allGames, String[][] gamesAndTime) throws IOException {
        if(workbook == null){
            creationOfWorkbook();
        }
        steamSheet = getWorkbook().createSheet("Steam");
        Row row = steamSheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        Cell cell1 = row.createCell(1);
        cell1.setCellStyle(headerStyle);
        cell.setCellValue("Title of the Game");
        cell1.setCellValue("Time you played");
        int rowCount = 1;
        for (int i = 0; i < allGames.length(); i++) {
            row = steamSheet.createRow(rowCount++);
            cell = row.createCell(0);
            cell1 = row.createCell(1);
            cell1.setCellStyle(columnStyle);
            cell.setCellValue(gamesAndTime[i][0]);
            cell1.setCellValue(Integer.parseInt(gamesAndTime[i][1]));
        }
        steamSheet.autoSizeColumn(0);
        steamSheet.autoSizeColumn(1);

        //todo: this has to leave this method because it can only be written when every data has been acquired.
        writingOfWorkbook();
    }

    //todo: connect to UI where user provides their own file path
    public void writingOfWorkbook() throws IOException {
        workbook.write(new FileOutputStream(
                "C:/Users/Alex/Desktop/AllGamesPlatforms.xlsx"));
        workbook.close();
    }
}
