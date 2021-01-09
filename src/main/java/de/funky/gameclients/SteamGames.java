package de.funky.gameclients;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * SteamGames-Class to retrieve every owned game on the steam platform.
 */
public class SteamGames {

  //todo: connect to UI where user provides their own data
  /**
   * The personalSteamWebAPIKey that every user has to create for themself.
   * The key can be created at "https://steamcommunity.com/dev/apikey".
   */
  private String personalSteamWebApiKey = "";

  /**
   * The personalSteamID that every user can check out on his profile.
   * It is usually located at the settings or can be found here
   * "https://steamidfinder.com/".
   */
  private String personalSteamId = "";

  /** Constant of value 60. */
  private static final int DIVISOR = 60;

  /** Constant of value 15. */
  private static final int FONTSIZE = 15;

  /**
   * Getter for personalSteamWebApiKey.
   *
   * @return personalSteamWebApiKey
   */
  public String getPersonalSteamWebApiKey() {
    return this.personalSteamWebApiKey;
  }

  /**
   * Getter for personalSteamId.
   *
   * @return personalSteamId
   */
  public String getPersonalSteamId() {
    return this.personalSteamId;
  }

  /**
   * Getter to divide minutes into hours.
   *
   * @return 60
   */
  public int getDivisor() {
    return DIVISOR;
  }

  /**
   * Getter to retrieve the chosen fontsize.
   *
   * @return fontsize
   */
  public int getFontsize() {
    return FONTSIZE;
  }

  /**
   * Using the public Steam-API to call for every owned game. Setting styles
   * and fonts for the XSSFWorkbook in particular for the XSSFCells. The
   * created list is sorted by the most played games on top and the least
   * on the bottom.
   */
  public SteamGames() {
    try {
      URL url = new URL("http://api.steampowered.com/"
              + "IPlayerService/GetOwnedGames/v0001/?key="
              + getPersonalSteamWebApiKey()
              + "&steamid="
              + getPersonalSteamId()
              + "&include_appinfo=1");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      BufferedReader in = new BufferedReader(
              new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }

      in.close();
      con.disconnect();

      JSONObject json = new JSONObject(response.toString());
      JSONArray allGames = json.getJSONObject("response")
              .getJSONArray("games");

      String[][] gamesAndTime = new String[allGames.length()][2];

      for (int i = 0; i < allGames.length(); i++) {
        String gameName = allGames.getJSONObject(i).getString("name");
        String gameTime = String.valueOf((allGames.getJSONObject(i)
                .getInt("playtime_forever") / getDivisor()));
        gamesAndTime[i][0] = gameName;
        gamesAndTime[i][1] = gameTime;
      }

      Arrays.sort(gamesAndTime, Collections.reverseOrder(
              Comparator.comparingInt(o -> Integer.parseInt(o[1]))));

      //todo: connect to UI where user provides their own path
      //todo: outsource the creation of the workbook and the sheets
      // (no need to have it here). Create the content of the/
      // workbook in the specific classes (to be coded) and
      // bring the content into one class or the main for
      // Workbook creation
      XSSFWorkbook workbook = new XSSFWorkbook();
      XSSFFont headerFont = workbook.createFont();
      headerFont.setBold(true);
      headerFont.setFontHeightInPoints((short) getFontsize());
      headerFont.setFontName("Times New Roman");
      headerFont.setUnderline((byte) 1);
      XSSFCellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setAlignment(HorizontalAlignment.CENTER);
      headerStyle.setFont(headerFont);

      XSSFCellStyle columnStyle = workbook.createCellStyle();
      columnStyle.setAlignment(HorizontalAlignment.CENTER);

      XSSFSheet sheet = workbook.createSheet("Steam");
      Row row = sheet.createRow(0);
      Cell cell = row.createCell(0);
      cell.setCellStyle(headerStyle);
      Cell cell1 = row.createCell(1);
      cell1.setCellStyle(headerStyle);
      cell.setCellValue("Title of the Game");
      cell1.setCellValue("Time you played");
      int rowCount = 1;

      for (int i = 0; i < allGames.length(); i++) {
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell1 = row.createCell(1);
        cell1.setCellStyle(columnStyle);
        cell.setCellValue(gamesAndTime[i][0]);
        cell1.setCellValue(Integer.parseInt(gamesAndTime[i][1]));
      }

      sheet.autoSizeColumn(0);
      sheet.autoSizeColumn(1);
      workbook.write(new FileOutputStream(
              "C:/Users/Alex/Desktop/AllGamesAllPlatforms.xlsx"));
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
