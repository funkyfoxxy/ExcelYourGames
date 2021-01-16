package de.funky.backend;

import de.funky.gameclients.SteamGames;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for JavaFX and the mainfx.fxml.
 */
public class FxController implements Initializable {

  /**
   * Connects the textfield of the personal
   * SteamId of the GUI with the controller.
   */
  @FXML
  private TextField fieldSteamId;
  /**
   * Connects the textfield of the personal
   * SteamWebApiKey of the GUI with the
   * controller.
   */
  @FXML
  private TextField fieldWebApi;
  /**
   * The personal SteamId will always be 17 characters long.
   */
  private static final int STEAMIDLENGTH = 17;

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
  }

  /**
   * Creating new modals with the content of the matching fxml-file.
   * The id's of the pressed buttons do have the same names as the
   * corresponding fxml-file.
   *
   * @param actionEvent pressed button
   * @throws IOException if the object is null this throws an IOException
   */
  public void openInfo(final ActionEvent actionEvent) throws IOException {
    String calledId = ((Control) actionEvent.getSource()).getId();
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(
              Objects.requireNonNull(getClass()
                      .getClassLoader()
                      .getResource("fxml/" + calledId + ".fxml")));
    stage.setScene(new Scene(root));
    stage.setTitle("Information");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(
            ((Node) actionEvent.getSource()).getScene().getWindow());
    stage.show();
  }

  /**
   * Connected with the "Create .xlsx" button from the GUI, this
   * function will provide the data for every gameclient and starts
   * the process of creating and writing the gameclients data into
   * a newly created workbook.
   *
   * @param actionEvent pressed button
   * @throws IOException createSteamGamesExcel is using an HttpURLConnection
   */
  public void createWorkbook(final ActionEvent actionEvent) throws IOException {
    String steamId = fieldSteamId.getText();
    String webApi = fieldWebApi.getText();
    if (steamId.length() == STEAMIDLENGTH && !webApi.isEmpty()) {
      SteamGames steamGames = new SteamGames();
      steamGames.createSteamGamesExcel(steamId, webApi);
      fieldSteamId.setText("");
      fieldWebApi.setText("");
    } else {
      openInfo(actionEvent);
    }
  }

  /**
   * Connected to the two info-buttons in the GUI, this
   * function will open the default browser of the user
   * with the given URLs.
   *
   * @param actionEvent pressed button
   */
  public void openBrowser(final ActionEvent actionEvent) {
    String calledId = ((Control) actionEvent.getSource()).getId();
    if (calledId.equals("id_finder")) {
      try {
        Desktop.getDesktop()
                .browse(new URI("https://steamidfinder.com/"));
      } catch (IOException | URISyntaxException e1) {
        e1.printStackTrace();
      }
    } else if (calledId.equals("webapikey_finder")) {
      try {
        Desktop.getDesktop()
                .browse(new URI("https://steamcommunity.com/dev/apikey"));
      } catch (IOException | URISyntaxException e1) {
        e1.printStackTrace();
      }
    }
  }
}
