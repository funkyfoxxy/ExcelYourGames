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
  private Pane mainpane;
  @FXML
  private ChoiceBox<String> checkDark;
  @FXML
  private Label headerLabel;
  @FXML
  private Button buttonsavepath;
  @FXML
  private TextField fieldFilePath;
  @FXML
  private Button createXlsx;
  @FXML
  private Button informationSteamId;
  @FXML
  private Button informationWebId;
  @FXML
  private Label creator;

  public enum Colors {
    black, white, green, yellow, blue, orange, red, pink, purple, grey
  }

  private int usedBgColorFromEnum = 1;
  private int usedTextColorFromEnum = 0;
  private static String steamId;
  private static String webApi;
  private static String filePath;
  private static boolean containsError;
  private static final int IMAGESIZE = 16;
  private static final int NUMBEROFCOLORS = 10;

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
   * Locale to retrieve system preferences and information.
   */
  private final Locale locale = Locale.getDefault();

  /**
   * Generating the ResourceBundle to retrieve the
   * fitting MessageBundle out of resources.
   */
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", locale);

  private final ObservableList<String> variousModes = FXCollections
          .observableArrayList(res.getString("black"),
                  res.getString("white"),
                  res.getString("surprise"));

  /**
   * Getter for the index that will be used from the ObservableList.
   *
   * @return index as int
   */
  public int getUsedBgColorFromEnum() {
    return usedBgColorFromEnum;
  }

  /**
   * Setter for the index that will be used from the ObservableList.
   *
   * @param newUsedBgColorFromEnum new index as int
   */
  public void setUsedBgColorFromEnum(final int newUsedBgColorFromEnum) {
    this.usedBgColorFromEnum = newUsedBgColorFromEnum;
  }

  /**
   * Getter for the index that will be used from the ObservableList.
   *
   * @return index as int
   */
  public int getUsedTextColorFromEnum() {
    return usedTextColorFromEnum;
  }

  /**
   * Setter for the index that will be used from the ObservableList.
   *
   * @param newUsedTextColorFromEnum new index as int
   */
  public void setUsedTextColorFromEnum(final int newUsedTextColorFromEnum) {
    this.usedTextColorFromEnum = newUsedTextColorFromEnum;
  }

  /**
   * Getter for the users steamId.
   *
   * @return steamId as String
   */
  public static String getSteamId() {
    return steamId;
  }

  /**
   * Setter for the users steamId.
   *
   * @param newSteamId new SteamId as String
   */
  public static void setSteamId(final String newSteamId) {
    MainFxController.steamId = newSteamId;
  }

  /**
   * Getter for the users webApiKey.
   *
   * @return webApi as String
   */
  public static String getWebApi() {
    return webApi;
  }

  /**
   * Setter for the users webApi.
   *
   * @param newWebApi new webApiKey as String
   */
  public static void setWebApi(final String newWebApi) {
    MainFxController.webApi = newWebApi;
  }

  /**
   * Getter for the filePath that the user selected.
   *
   * @return filePath as a String
   */
  public static String getFilePath() {
    return filePath;
  }

  /**
   * Setter for the filePath that he user selected.
   *
   * @param newFilePath new filePath as String
   */
  public static void setFilePath(final String newFilePath) {
    MainFxController.filePath = newFilePath;
  }

  /**
   * Returns if there is any error and therefore the process
   * of creating workbook should be cancelled or not.
   *
   * @return containsError as boolean
   */
  public static boolean isContainsError() {
    return containsError;
  }

  /**
   * Sets the value of the boolean if there are errors in the
   * process of creating the workbook or requesting data from the
   * gameclient.
   *
   * @param newContainsError new containsError as boolean
   */
  public static void setContainsError(final boolean newContainsError) {
    MainFxController.containsError = newContainsError;
  }

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
    setButtonImages(Colors.values()[0].name());

    createXlsx.setText(res.getString("create"));
    headerLabel.setText(res.getString("header"));

    fieldSteamId.setPromptText(res.getString("fieldInfoId"));
    fieldWebApi.setPromptText(res.getString("fieldInfoApi"));
    fieldFilePath.setPromptText(res.getString("pathInfo"));

    checkDark.setItems(variousModes);
    checkDark.setValue(res.getString("defaultColor"));

    checkDark.getSelectionModel().selectedIndexProperty()
        .addListener((observableValue, number, t1) -> {
          if (t1.equals(0)) {
            setUsedBgColorFromEnum(0);
            setUsedTextColorFromEnum(1);
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[0].name());
            headerLabel.setTextFill(Color.web(Colors.values()[1].name()));
            creator.setTextFill(Color.web(Colors.values()[1].name()));
            setButtonImages(Colors.values()[1].name());
          } else if (t1.equals(1)) {
            setUsedBgColorFromEnum(1);
            setUsedTextColorFromEnum(0);
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[1].name());
            headerLabel.setTextFill(Color.web(Colors.values()[0].name()));
            creator.setTextFill(Color.web(Colors.values()[0].name()));
            setButtonImages(Colors.values()[0].name());
          } else if (t1.equals(2)) {
            Random random = new Random();
            int randomNumber = random.nextInt(NUMBEROFCOLORS);
            setUsedBgColorFromEnum(randomNumber);
            mainpane.styleProperty().set("-fx-background-color: "
                  + Colors.values()[randomNumber].name());
            int secondRandomNumber = random.nextInt(NUMBEROFCOLORS);
            if (secondRandomNumber == randomNumber
                    && secondRandomNumber < (NUMBEROFCOLORS - 1)) {
              secondRandomNumber++;
            }
            setUsedTextColorFromEnum(secondRandomNumber);
            headerLabel.setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            creator.setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            setButtonImages(Colors.values()[0].name());
          }
        });
  }

  private void setButtonImages(final String name) {
    Image infoImageSteamId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoSteamIdImageView = new ImageView(infoImageSteamId);
    infoSteamIdImageView.setFitWidth(IMAGESIZE);
    infoSteamIdImageView.setFitHeight(IMAGESIZE);
    informationSteamId.setGraphic(infoSteamIdImageView);
    Image infoImageWebId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoWebIdImageView = new ImageView(infoImageWebId);
    infoWebIdImageView.setFitWidth(IMAGESIZE);
    infoWebIdImageView.setFitHeight(IMAGESIZE);
    informationWebId.setGraphic(infoWebIdImageView);
    Image image = new Image(getClass()
            .getResourceAsStream("/images/folder_black.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(IMAGESIZE);
    imageView.setFitHeight(IMAGESIZE);
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
                  + Colors.values()[getUsedBgColorFromEnum()].name(),
                  Colors.values()[getUsedTextColorFromEnum()].name());
          break;
        case "steamwebapifx":
          SteamWebApiFxController swaController = fxmlLoader.getController();
          swaController.setMatchingColors("-fx-background-color: "
                  + Colors.values()[getUsedBgColorFromEnum()].name(),
                  Colors.values()[getUsedTextColorFromEnum()].name());
          break;
        case "createdatafx":
          CreateDataFxController smfController = fxmlLoader.getController();
          smfController.setMatchingColors("-fx-background-color: "
                            + Colors.values()[getUsedBgColorFromEnum()].name(),
                            Colors.values()[getUsedTextColorFromEnum()].name());
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
      fieldFilePath.setText(selectedDirectory.getAbsolutePath());
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
        steamGames.createSteamGamesExcel(getSteamId(), getWebApi());
        setContainsError(false);
      }
      //      if(checkIfGogRequested()){}
      ExcelWorkbook.writingOfWorkbook();
      fieldSteamId.setText("");
      fieldWebApi.setText("");
      fieldFilePath.setText("");
      openInfo(actionEvent);
    } catch (IOException e) {
      setContainsError(true);
      openInfo(actionEvent);
    }
  }

  private boolean checkIfSteamRequested() {
    setSteamId(fieldSteamId.getText());
    setWebApi(fieldWebApi.getText());
    setFilePath(fieldFilePath.getText());
    return getSteamId().length() == STEAMIDLENGTH
                && !getWebApi().isEmpty()
                && !getFilePath().isEmpty();
  }

  //    private boolean checkIfGogRequested() {
  //        return false;
  //    }

}
