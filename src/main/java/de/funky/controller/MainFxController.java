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

  /**
   * Showing every label and button.
   */
  @FXML
  private Pane mainpane;

  /**
   * Offers the option to choose between various colors.
   */
  @FXML
  private ChoiceBox<String> checkDark;

  /**
   * The header of the window.
   */
  @FXML
  private Label headerLabel;

  /**
   * Opening the DirectoryChooser.
   */
  @FXML
  private Button buttonsavepath;

  /**
   * Showing the user selected filePath.
   */
  @FXML
  private TextField fieldFilePath;

  /**
   * Triggers the creation of workbook.
   */
  @FXML
  private Button createXlsx;

  /**
   * Gives information about the users SteamId.
   */
  @FXML
  private Button informationSteamId;

  /**
   * Gives information about the users SteamWebApiKey.
   */
  @FXML
  private Button informationWebId;

  /**
   * Informs about the creator and the version of this program.
   */
  @FXML
  private Label creator;

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
   * Index that is chosen from the enum as the background color.
   */
  private int usedBgColorFromEnum = 1;

  /**
   * Index that is chosen from the enum as the background color.
   */
  private int usedTextColorFromEnum = 0;

  /**
   * SteamId given by the user.
   */
  private static String steamId;

  /**
   * WebApiKey given by the user.
   */
  private static String webApi;

  /**
   * Filepath chosen by the user.
   */
  private static String filePath;

  /**
   * If an error occured somewhere this will stop further processing.
   */
  private static boolean containsError;

  /**
   * General size of the imageheight and -width.
   */
  private static final int IMAGESIZE = 16;

  /**
   * Number of colors that are present inside the enum.
   */
  private static final int NUMBEROFCOLORS = 10;

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

  /**
   * ObservableList of Strings that is used to create the
   * ChoiceBox content.
   */
  private final ObservableList<String> variousModes = FXCollections
          .observableArrayList(res.getString("black"),
                  res.getString("white"),
                  res.getString("surprise"));

  /**
   * Enum to list every color that can be used as background or text.
   */
  public enum Colors {
      /**
       * List of 10 colors to be used..
       */
      black, white, green, yellow, blue, orange, red, pink, purple, grey
  }

  /**
   * Getter for the mainpane.
   *
   * @return mainpane
   */
  public Pane getMainpane() {
    return mainpane;
  }

  //  /**
  //   * Setter for the mainpane.
  //   *
  //   * @param newMainpane specified new pane
  //   */
  //  public void setMainpane(final Pane newMainpane) {
  //    this.mainpane = newMainpane;
  //  }

  /**
   * Getter for the ChoiceBox.
   *
   * @return checkDark ChoiceBox
   */
  public ChoiceBox<String> getCheckDark() {
    return checkDark;
  }

  //  /**
  //   * Setter for the ChoiceBox.
  //   *
  //   * @param newCheckDark specified new ChoiceBox
  //   */
  //  public void setCheckDark(final ChoiceBox<String> newCheckDark) {
  //    this.checkDark = newCheckDark;
  //  }

  /**
   * Getter for the headerlabel.
   *
   * @return headerlabel
   */
  public Label getHeaderLabel() {
    return headerLabel;
  }

  //  /**
  //   * Setter for the headerlabel.
  //   *
  //   * @param newHeaderLabel specified new label
  //   */
  //  public void setHeaderLabel(final Label newHeaderLabel) {
  //    this.headerLabel = newHeaderLabel;
  //  }

  /**
   * Getter for the button that lets you choose the filepath.
   *
   * @return buttonsavepath as button
   */
  public Button getButtonsavepath() {
    return buttonsavepath;
  }

  //  /**
  //   * Setter for the button that lets you choose the filepath.
  //   *
  //   * @param newButtonsavepath specified new button
  //   */
  //  public void setButtonsavepath(final Button newButtonsavepath) {
  //    this.buttonsavepath = newButtonsavepath;
  //  }

  /**
   * Getter for the filepath that the user entered.
   *
   * @return fieldFilePath as TextField
   */
  public TextField getFieldFilePath() {
    return fieldFilePath;
  }

  //  /**
  //   * Setter for the filepath that the user entered.
  //   *
  //   * @param newFieldFilePath specified new TextField
  //   */
  //  public void setFieldFilePath(final TextField newFieldFilePath) {
  //    this.fieldFilePath = newFieldFilePath;
  //  }

  /**
   * Getter for the button that starts the creation of the workbook.
   *
   * @return createXlsx as Button
   */
  public Button getCreateXlsx() {
    return createXlsx;
  }

  //  /**
  //   * Setter for the button that starts the creation of the workbook.
  //   *
  //   * @param newCreateXlsx specified new Button
  //   */
  //  public void setCreateXlsx(final Button newCreateXlsx) {
  //    this.createXlsx = newCreateXlsx;
  //  }

  /**
   * Getter for the button that gives the user info about the SteamId.
   *
   * @return informationSteamId as Button
   */
  public Button getInformationSteamId() {
    return informationSteamId;
  }

  //  /**
  //   * Setter for the button that gives the user info about the SteamId.
  //   *
  //   * @param newInformationSteamId specified new Button
  //   */
  //  public void setInformationSteamId(final Button newInformationSteamId) {
  //    this.informationSteamId = newInformationSteamId;
  //  }

  /**
   * Getter for the button that gives the user info about the SteamWebApiKey.
   *
   * @return informationWebId as Button
   */
  public Button getInformationWebId() {
    return informationWebId;
  }

  //  /**
  //   * Setter for the button that gives the user info
  //   * about the SteamWebApiKey.
  //   *
  //   * @param newInformationWebId specified new Button
  //   */
  //  public void setInformationWebId(final Button newInformationWebId) {
  //    this.informationWebId = newInformationWebId;
  //  }

  /**
   * Getter for the Label that informs about the creator of this program.
   *
   * @return creator as String
   */
  public Label getCreator() {
    return creator;
  }

  //  /**
  //   * Setter for the Label that informs about the creator of this program.
  //   *
  //   * @param newCreator specified new Label
  //   */
  //  public void setCreator(final Label newCreator) {
  //    this.creator = newCreator;
  //  }

  /**
   * Getter for the TextField in which the user had given
   * the personal SteamId.
   *
   * @return fieldSteamId as String
   */
  public TextField getFieldSteamId() {
    return fieldSteamId;
  }

  //  /**
  //     * Setter for the TextField in which the user had given
  //     * the personal SteamId.
  //     *
  //     * @param newFieldSteamId specified new TextField
  //     */
  //  public void setFieldSteamId(final TextField newFieldSteamId) {
  //    this.fieldSteamId = newFieldSteamId;
  //  }

  /**
     * Getter for the TextField in which the user had given
     * the personal SteamWebApiKey.
     *
     * @return fieldWebApi as TextField
     */
  public TextField getFieldWebApi() {
    return fieldWebApi;
  }

  //  /**
  //     * Setter for the TextField in which the user had given
  //     * the personal SteamWebApiKey.
  //     *
  //     * @param newFieldWebApi specified new TextField
  //     */
  //  public void setFieldWebApi(final TextField newFieldWebApi) {
  //    this.fieldWebApi = newFieldWebApi;
  //  }

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
   * Initialization of the contents of the labels, colors and buttons.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
    setButtonImages(Colors.values()[0].name());

    getCreateXlsx().setText(res.getString("create"));
    getHeaderLabel().setText(res.getString("header"));

    getFieldSteamId().setPromptText(res.getString("fieldInfoId"));
    getFieldWebApi().setPromptText(res.getString("fieldInfoApi"));
    getFieldFilePath().setPromptText(res.getString("pathInfo"));

    getCheckDark().setItems(variousModes);
    getCheckDark().setValue(res.getString("defaultColor"));

    getCheckDark().getSelectionModel().selectedIndexProperty()
        .addListener((observableValue, number, t1) -> {
          if (t1.equals(0)) {
            setUsedBgColorFromEnum(0);
            setUsedTextColorFromEnum(1);
            getMainpane().styleProperty().set("-fx-background-color: "
                  + Colors.values()[0].name());
            getHeaderLabel().setTextFill(Color.web(Colors.values()[1].name()));
            getCreator().setTextFill(Color.web(Colors.values()[1].name()));
            setButtonImages(Colors.values()[1].name());
          } else if (t1.equals(1)) {
            setUsedBgColorFromEnum(1);
            setUsedTextColorFromEnum(0);
            getMainpane().styleProperty().set("-fx-background-color: "
                  + Colors.values()[1].name());
            getHeaderLabel().setTextFill(Color.web(Colors.values()[0].name()));
            getCreator().setTextFill(Color.web(Colors.values()[0].name()));
            setButtonImages(Colors.values()[0].name());
          } else if (t1.equals(2)) {
            Random random = new Random();
            int randomNumber = random.nextInt(NUMBEROFCOLORS);
            setUsedBgColorFromEnum(randomNumber);
            getMainpane().styleProperty().set("-fx-background-color: "
                  + Colors.values()[randomNumber].name());
            int secondRandomNumber = random.nextInt(NUMBEROFCOLORS);
            if (secondRandomNumber == randomNumber
                    && secondRandomNumber < (NUMBEROFCOLORS - 1)) {
              secondRandomNumber++;
            }
            setUsedTextColorFromEnum(secondRandomNumber);
            getHeaderLabel().setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            getCreator().setTextFill(Color.web(
                  Colors.values()[secondRandomNumber].name()));
            setButtonImages(Colors.values()[0].name());
          }
        });
  }

  /**
   * Setting the folder and the info image for every button on mainpane.
   *
   * @param name name of the file that has to be used
   */
  private void setButtonImages(final String name) {
    Image infoImageSteamId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoSteamIdImageView = new ImageView(infoImageSteamId);
    infoSteamIdImageView.setFitWidth(IMAGESIZE);
    infoSteamIdImageView.setFitHeight(IMAGESIZE);
    getInformationSteamId().setGraphic(infoSteamIdImageView);
    Image infoImageWebId = new Image(getClass()
            .getResourceAsStream("/images/info_" + name + ".png"));
    ImageView infoWebIdImageView = new ImageView(infoImageWebId);
    infoWebIdImageView.setFitWidth(IMAGESIZE);
    infoWebIdImageView.setFitHeight(IMAGESIZE);
    getInformationWebId().setGraphic(infoWebIdImageView);
    Image image = new Image(getClass()
            .getResourceAsStream("/images/folder_black.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(IMAGESIZE);
    imageView.setFitHeight(IMAGESIZE);
    getButtonsavepath().setGraphic(imageView);
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
          break;
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

  /**
   * The buttonsavepath Button opens the system own DirectoryChooser.
   */
  public void openDirectory() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File selectedDirectory = directoryChooser
            .showDialog(ExcelInGamesMain.getStage());
    if (selectedDirectory != null) {
      getFieldFilePath().setText(selectedDirectory.getAbsolutePath());
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
      getFieldSteamId().setText("");
      getFieldWebApi().setText("");
      getFieldFilePath().setText("");
      openInfo(actionEvent);
    } catch (IOException e) {
      setContainsError(true);
      openInfo(actionEvent);
    }
  }

  /**
   * Checking if the user wants his steam games to be included.
   *
   * @return boolean
   */
  private boolean checkIfSteamRequested() {
    setSteamId(getFieldSteamId().getText());
    setWebApi(getFieldWebApi().getText());
    setFilePath(getFieldFilePath().getText());
    return getSteamId().length() == STEAMIDLENGTH
                && !getWebApi().isEmpty()
                && !getFilePath().isEmpty();
  }

  //  /**
  //   * For future use if the REST-API from gog is working again.
  //   *
  //   * @return boolean
  //   */
  //  private boolean checkIfGogRequested() {
  //    return false;
  //  }
}
