package de.funky.gameclients;

import de.funky.backend.ExcelWorkbook;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
  private final String personalSteamWebApiKey = "";

  //todo: connect to UI where user provides their own data
  /**
   * The personalSteamID that every user can check out on his profile.
   * It is usually located at the settings or can be found here
   * "https://steamidfinder.com/".
   */
  private final String personalSteamId = "";

  /** Constant of value 60. */
  private static final int DIVISOR = 60;

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

      //todo: if there is no workbook existing, create one and pass that with the data
      if(ExcelWorkbook.workbook == null){
        ExcelWorkbook workbook = new ExcelWorkbook();
        workbook.creationOfSteamSheet(allGames, gamesAndTime);
      //todo: if there is a workbook just pass the data
      }
//      else {
//
//      }


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
