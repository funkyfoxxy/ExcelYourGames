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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * SteamGames-Class to retrieve every owned game on the steam platform.
 */
public class SteamGames {

  /**
   * Constant of value 60.
   */
  private static final int DIVISOR = 60;
  /**
   * JSONObject containing every information from REST request.
   */
  private JSONObject jsonFile;
  /**
   * JSONArray in which every block is one game.
   */
  private JSONArray allGames;
  /**
   * 2D String array that is used to save the name
   * and also the played time of every game.
   */
  private String[][] allGamesAndTimes;

  /**
   * Getter to divide minutes into hours.
   *
   * @return 60
   */
  public int getDivisor() {
    return DIVISOR;
  }

  /**
   * Setter for the JSONObject that contains every information.
   *
   * @param newJsonFile created from requestSteamGames()
   */
  public void setJsonFile(final JSONObject newJsonFile) {
    this.jsonFile = newJsonFile;
  }

  /**
   * Getter of the JSONObject with every information.
   *
   * @return jsonFile
   */
  public JSONObject getJsonFile() {
    return jsonFile;
  }

  /**
   * Getter of the JSONArray with every game and playtime.
   *
   * @return allGames
   */
  public JSONArray getAllGames() {
    return allGames;
  }

  /**
   * Setter of the JSONArray that contains every game and playtime.
   *
   * @param newAllGames created from createSortedArrayOfSteamGames()
   */
  public void setAllGames(final JSONArray newAllGames) {
    this.allGames = newAllGames;
  }

  /**
   * Getter of the 2D String array that has every game with
   * its corresponding playtime listed in chronological order.
   *
   * @return allGamesAndTimes
   */
  public String[][] getAllGamesAndTimes() {
    return allGamesAndTimes;
  }

  /**
   * Setter of the 2D array that has every game with its
   * corresponding playtime listed in chronological order.
   *
   * @param newAllGamesAndTimes created from createSortedArrayOfSteamGames()
   */
  public void setAllGamesAndTimes(final String[][] newAllGamesAndTimes) {
    this.allGamesAndTimes = newAllGamesAndTimes;
  }

  /**
   * Function that is starting the request and the creation of the sheet
   * or workbook that is to be written with every game and playtime from
   * all the connected gameclients.
   *
   * @param steamId entered from user in GUI
   * @param steamWebApi entered from user in GUI
   * @throws IOException requestSteamGames() is using HttpURLConnection
   */
  public void createSteamGamesExcel(final String steamId,
                                    final String steamWebApi)
          throws IOException {
    int returnedCode = requestSteamGames(steamId, steamWebApi);
    if (returnedCode == 200) {
      createSortedArrayOfSteamGames();
      writeSteamGamesInWorkbook();
    }
  }

  /**
   * Function that is calling a GET to the steam REST-API and
   * requests every game that is owned by the user, if the
   * correct login data has been provided.
   *
   * @param steamId given steamId from user
   * @param steamWebApi given steamWebApi from user
   */
  public int requestSteamGames(final String steamId,
                                final String steamWebApi) {
    try {
      URL url = new URL("http://api.steampowered.com/"
              + "IPlayerService/GetOwnedGames/v0001/?key="
              + steamWebApi
              + "&steamid="
              + steamId
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

      int code = con.getResponseCode();

      System.out.println("ResponseCode: " + code);

      in.close();
      con.disconnect();

      setJsonFile(new JSONObject(response.toString()));
      return code;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Method to create out of the JSONObject that contains every
   * information, a 2D array that is ordered in a chronological
   * way with every game and its corresponding play time.
   */
  public void createSortedArrayOfSteamGames() {
    setAllGames(getJsonFile().getJSONObject("response")
              .getJSONArray("games"));

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

    setAllGamesAndTimes(gamesAndTime);
  }

  /**
   * Method that is used to write the processed data into the already
   * existing workbook or is used to create a new workbook and then
   * write every processed data into this newly created workbook.
   *
   * @throws IOException workbook is using FileOutputStream()
   */
  public void writeSteamGamesInWorkbook() {
    ExcelWorkbook.creationOfSteamSheet(getAllGames(), getAllGamesAndTimes());
  }
}
