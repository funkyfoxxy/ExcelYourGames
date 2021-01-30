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

/**
 * Responsible for gathering every gameclients information
 * and with this information creating the workbook that
 * will be written to the user's system.
 */
public class ExcelWorkbook {

  /**
   * One and only workbook that will be created
   * and filled with the various gameclient sheets.
   */
  private static XSSFWorkbook workbook;

  /**
   * Sheet for the Steam gameclient.
   */
  private static XSSFSheet steamSheet;

  /**
   * Style of the header inside the sheet.
   */
  private static XSSFCellStyle headerStyle;

  /**
   * Style of the column inside the sheet.
   */
  private static XSSFCellStyle columnStyle;

  /**
   * Size of the font.
   */
  private static final int FONTHEIGHTINPOINTS = 15;

  /**
   * Setter of workbook.
   *
   * @param xssfWorkbook new or other workbook
   */
  public static void setWorkbook(final XSSFWorkbook xssfWorkbook) {
    workbook = xssfWorkbook;
  }

  /**
   * Getter of workbook.
   *
   * @return the one and only workbook
   */
  public static XSSFWorkbook getWorkbook() {
    return workbook;
  }

  /**
   * Getter of the Steam sheet.
   *
   * @return the one existing Steam sheet
   */
  public XSSFSheet getSteamSheet() {
    return steamSheet;
  }

  /**
   * Getter of the headerStyle.
   *
   * @return defined headerStyle
   */
  public static XSSFCellStyle getHeaderStyle() {
    return headerStyle;
  }

  /**
   * Getter of the columnStyle.
   *
   * @return defined columnStyle
   */
  public static XSSFCellStyle getColumnStyle() {
    return columnStyle;
  }

  /**
   * Creation of the workbook. Also the most important
   * settings for the workbook will be set as following:
   * the headerStyle, the columenStyle and also the
   * headerFont with fontsizes.
   */
  public static void creationOfWorkbook() {
    workbook = new XSSFWorkbook();
    XSSFFont headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) FONTHEIGHTINPOINTS);
    headerFont.setFontName("Times New Roman");
    headerFont.setUnderline((byte) 1);
    headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setFont(headerFont);
    columnStyle = workbook.createCellStyle();
    columnStyle.setAlignment(HorizontalAlignment.CENTER);
  }

  /**
   * Creating the Steam sheet. Cells and rows will be added
   * based on the numbers from the parameters. Also the header
   * and the columensizes will be set.
   *
   * @param allGames every game with every information
   * @param gamesAndTime every game sorted by gametime
   */
  public static void creationOfSteamSheet(final JSONArray allGames,
                                         final String[][] gamesAndTime) {
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

  //  /**
  //   * For the future use if the RestAPI from gog is working again.
  //   */
  //  public void creationOfGogSheet(){
  //  }

  /**
   * Writing and closing the workbook.
   *
   * @throws IOException if FileOutputStream fails
   */
  public static void writingOfWorkbook() throws IOException {
    workbook.write(new FileOutputStream(MainFxController
                .getFilePath() + "/AllGamesPlatforms.xlsx"));
    workbook.close();
  }
}
