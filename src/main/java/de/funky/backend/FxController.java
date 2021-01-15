package de.funky.backend;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import de.funky.gameclients.SteamGames;
import javafx.event.ActionEvent;
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

  public TextField field_steamid;
  public TextField field_webapi;

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
  }

  public void openInfo(ActionEvent actionEvent) throws IOException {
    String calledId = ((Control)actionEvent.getSource()).getId();
    Stage stage = new Stage();
    if(calledId.equals("info_steamid")){
      Parent root = FXMLLoader.load(
              Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/steamidinfo.fxml")));
      stage.setScene(new Scene(root));
      stage.setTitle("Steam ID Information");
    } else {
      Parent root = FXMLLoader.load(
              Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/steamwebapi.fxml")));
      stage.setScene(new Scene(root));
      stage.setTitle("SteamWebAPIKey Information");
    }
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(
            ((Node)actionEvent.getSource()).getScene().getWindow() );
    stage.show();
  }

  public void createWorkbook(ActionEvent actionEvent) throws IOException {
    String steamId = field_steamid.getText();
    //System.out.println("Länge: " + steamId.length());
    String webApi = field_webapi.getText();
    //System.out.println("Webapi ist leer: " + webApi.isEmpty());
    if(steamId.length() == 17 && !webApi.isEmpty()){
      SteamGames steamGames = new SteamGames();
      steamGames.requestSteamGames(steamId, webApi);
    } else {
      Stage stage = new Stage();
      Parent root = FXMLLoader.load(
              Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/missingdata.fxml")));
      stage.setScene(new Scene(root));
      stage.setTitle("Error!");
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(
              ((Node)actionEvent.getSource()).getScene().getWindow() );
      stage.show();
    }
  }

  public void openBrowser(ActionEvent actionEvent) {
    String calledId = ((Control)actionEvent.getSource()).getId();
    if(calledId.equals("id_finder")){
      try {
        Desktop.getDesktop().browse(new URI("https://steamidfinder.com/"));
      } catch (IOException | URISyntaxException e1) {
        e1.printStackTrace();
      }
    } else if(calledId.equals("webapikey_finder")){
      try {
        Desktop.getDesktop().browse(new URI("https://steamcommunity.com/dev/apikey"));
      } catch (IOException | URISyntaxException e1) {
        e1.printStackTrace();
      }
    }
  }
}
