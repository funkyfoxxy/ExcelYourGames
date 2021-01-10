package de.funky.backend;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for JavaFX and the mainfx.fxml.
 */
public class FxController implements Initializable {

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {

  }

  public void openInfoSteam(ActionEvent actionEvent) throws IOException {
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/steamidinfo.fxml")));
    stage.setScene(new Scene(root));
    stage.setTitle("Steam ID Information");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(
            ((Node)actionEvent.getSource()).getScene().getWindow() );
    stage.show();
  }

  public void openInfoWeb(ActionEvent actionEvent) throws IOException {
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/steamwebapi.fxml")));
    stage.setScene(new Scene(root));
    stage.setTitle("SteamWebAPIKey Information");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(
            ((Node)actionEvent.getSource()).getScene().getWindow() );
    stage.show();
  }

  public void createWorkbook(ActionEvent actionEvent) {
  }
}
