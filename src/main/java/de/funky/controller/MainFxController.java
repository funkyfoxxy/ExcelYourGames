package de.funky.controller;

import de.funky.backend.ExcelInGamesMain;
import de.funky.backend.ExcelWorkbook;
import de.funky.gameclients.SteamGames;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for JavaFX and the mainfx.fxml.
 */
public class MainFxController implements Initializable {

  @FXML
  public Pane mainpane;
  @FXML
  public ChoiceBox<String> check_dark;
  @FXML
  public Label headerLabel;
  @FXML
  public Button buttonsavepath;
  @FXML
  public TextField field_file_path;
  @FXML
  public Button create_xlsx;
  @FXML
  public Button information_steamid;
  @FXML
  public Button information_webid;
  @FXML
  public Label creator;

  public enum Colors {
    black, white, green, yellow, blue, orange, red, pink, purple, grey
  }

  public int usedBgColorFromEnum = 1;
  public int usedTColorFromEnum = 0;
  public static String steamId;
  public static String webApi;
  public static String filePath;
  public static boolean containsError;

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

  private final Locale locale = Locale.getDefault();
  private final String language = locale.getLanguage();
  private final String country = locale.getCountry();
  private final Locale loc = new Locale(language, country);
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", loc);

  ObservableList<String> variousModes = FXCollections
          .observableArrayList(res.getString("black"),
                  res.getString("white"),
                  res.getString("surprise"));

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
    setButtonImages(Colors.values()[0].name());

    create_xlsx.setText(res.getString("create"));
    headerLabel.setText(res.getString("header"));

    fieldSteamId.setPromptText(res.getString("fieldInfoId"));
    fieldWebApi.setPromptText(res.getString("fieldInfoApi"));
    field_file_path.setPromptText(res.getString("pathInfo"));

    check_dark.setItems(variousModes);
    check_dark.setValue(res.getString("defaultColor"));

    check_dark.getSelectionModel().selectedIndexProperty()
        .addListener((observableValue, number, t1) -> {
          if (t1.equals(0)) {
            usedBgColorFromEnum = 0;
            usedTColorFromEnum = 1;
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[0].name());
            headerLabel.setTextFill(Color.web(Colors.values()[1].name()));
            creator.setTextFill(Color.web(Colors.values()[1].name()));
            setButtonImages(Colors.values()[1].name());
          } else if (t1.equals(1)) {
            usedBgColorFromEnum = 1;
            usedTColorFromEnum = 0;
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[1].name());
            headerLabel.setTextFill(Color.web(Colors.values()[0].name()));
            creator.setTextFill(Color.web(Colors.values()[0].name()));
            setButtonImages(Colors.values()[0].name());
          } else if (t1.equals(2)) {
            Random random = new Random();
            int randomNumber = random.nextInt(10);
            usedBgColorFromEnum = randomNumber;
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[randomNumber].name());
            int secondRandomNumber = random.nextInt(10);
            if (secondRandomNumber == randomNumber && secondRandomNumber < 9) {
              secondRandomNumber++;
            }
            usedTColorFromEnum = secondRandomNumber;
            headerLabel.setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            creator.setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            setButtonImages(Colors.values()[0].name());
          }
        });
  }

  private void setButtonImages(String name) {
    Image infoImageSteamId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoSteamIdImageView = new ImageView(infoImageSteamId);
    infoSteamIdImageView.setFitWidth(16);
    infoSteamIdImageView.setFitHeight(16);
    information_steamid.setGraphic(infoSteamIdImageView);
    Image infoImageWebId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoWebIdImageView = new ImageView(infoImageWebId);
    infoWebIdImageView.setFitWidth(16);
    infoWebIdImageView.setFitHeight(16);
    information_webid.setGraphic(infoWebIdImageView);
    Image image = new Image(getClass()
            .getResourceAsStream("/images/folder_black.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(16);
    imageView.setFitHeight(16);
    buttonsavepath.setGraphic(imageView);
  }

  /**
   * Creating new modals with the content of the matching fxml-file.
   * The id's of the pressed buttons do have the same names as the
   * corresponding fxml-file.
   *
   * @param actionEvent pressed button
   */
  public void openInfo(final ActionEvent actionEvent) {
    try {
      String calledId = ((Control) actionEvent.getSource()).getId();
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getClassLoader()
          .getResource("fxml/" + calledId + ".fxml"));

      Parent root = fxmlLoader.load();

      switch (calledId) {
        case "steamidinfofx":
          SteamIdInfoFxController siiController = fxmlLoader.getController();
          siiController.setMatchingColors("-fx-background-color: "
                  + Colors.values()[usedBgColorFromEnum].name(),
                  Colors.values()[usedTColorFromEnum].name());
          break;
        case "steamwebapifx":
          SteamWebApiFxController swaController = fxmlLoader.getController();
          swaController.setMatchingColors("-fx-background-color: "
                  + Colors.values()[usedBgColorFromEnum].name(),
                  Colors.values()[usedTColorFromEnum].name());
          break;
        case "createdatafx":
          CreateDataFxController smfController = fxmlLoader.getController();
          smfController.setMatchingColors("-fx-background-color: "
                            + Colors.values()[usedBgColorFromEnum].name(),
                            Colors.values()[usedTColorFromEnum].name());
          break;
        default:
          System.out.println("Default!");
      }

      stage.setScene(new Scene(root));
      stage.setTitle(res.getString("info"));
      stage.initModality(Modality.WINDOW_MODAL);
      //    stage.initStyle(StageStyle.UNDECORATED);
      stage.initOwner(
              ((Node) actionEvent.getSource()).getScene().getWindow());
      stage.focusedProperty()
               .addListener((obs, wasFocused, isNowFocused) -> {
                 if (!isNowFocused) {
                   stage.hide();
                 }
               });
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void openDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser
            .showDialog(ExcelInGamesMain.getStage());
    if (selectedDirectory != null) {
      field_file_path.setText(selectedDirectory.getAbsolutePath());
    }
  }

  /**
   * Connected with the "Create .xlsx" button from the GUI, this
   * function will provide the data for every gameclient and starts
   * the process of creating and writing the gameclients data into
   * a newly created workbook.
   *
   * @param actionEvent pressed button
   */
  public void createWorkbook(final ActionEvent actionEvent) {
    try {
      if (checkIfSteamRequested()) {
        SteamGames steamGames = new SteamGames();
        steamGames.createSteamGamesExcel(steamId, webApi);
        containsError = false;
      }
      //      if(checkIfGogRequested()){}
      ExcelWorkbook.writingOfWorkbook();
      fieldSteamId.setText("");
      fieldWebApi.setText("");
      field_file_path.setText("");
      openInfo(actionEvent);
    } catch (IOException e) {
      containsError = true;
      openInfo(actionEvent);
    }
  }

  private boolean checkIfSteamRequested() {
    steamId = fieldSteamId.getText();
    webApi = fieldWebApi.getText();
    filePath = field_file_path.getText();
    return steamId.length() == STEAMIDLENGTH
                && !webApi.isEmpty()
                && !filePath.isEmpty();
  }

  //    private boolean checkIfGogRequested() {
  //        return false;
  //    }

}
