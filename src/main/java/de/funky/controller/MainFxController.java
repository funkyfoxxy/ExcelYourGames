package de.funky.controller;

import de.funky.backend.ExcelInGamesMain;
import de.funky.gameclients.SteamGames;

import java.io.IOException;
import java.io.File;
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
import javafx.scene.control.*;
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

    public enum Colors{
        black, white, green, yellow, blue, orange, red, pink, purple, grey
    }

    public int usedBgColorFromEnum = 1;
    public int usedTColorFromEnum = 0;
    public String steamId;
    public String webApi;

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

    Locale locale = Locale.getDefault();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    ObservableList<String> variousModes = FXCollections
            .observableArrayList(r.getString("black"), r.getString("white"), r.getString("surprise"));

    /**
     * Has to be implemented because of the Initializable.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        create_xlsx.setText(r.getString("create"));
        information_steamid.setText(r.getString("shortInfo"));
        information_webid.setText(r.getString("shortInfo"));
        fieldSteamId.setPromptText(r.getString("fieldInfoId"));
        fieldWebApi.setPromptText(r.getString("fieldInfoApi"));
        field_file_path.setPromptText(r.getString("pathInfo"));
        buttonsavepath.setText(r.getString("choosePath"));
        headerLabel.setText(r.getString("header"));
        check_dark.setItems(variousModes);
        check_dark.setValue(r.getString("defaultColor"));

        check_dark.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> {
                    if (t1.equals(0)) {
                        usedBgColorFromEnum = 0;
                        usedTColorFromEnum = 1;
                        mainpane.styleProperty().set("-fx-background-color: " + Colors.values()[0].name());
                        headerLabel.setTextFill(Color.web(Colors.values()[1].name()));
                    } else if (t1.equals(1)) {
                        usedBgColorFromEnum = 1;
                        usedTColorFromEnum = 0;
                        mainpane.styleProperty().set("-fx-background-color: " + Colors.values()[1].name());
                        headerLabel.setTextFill(Color.web(Colors.values()[0].name()));
                    } else if (t1.equals(2)) {
                        Random random = new Random();
                        int randomNumber = random.nextInt(10);
                        usedBgColorFromEnum = randomNumber;
                        mainpane.styleProperty().set("-fx-background-color: " + Colors.values()[randomNumber].name());
                        int secondRandomNumber = random.nextInt(10);
                        if(secondRandomNumber == randomNumber && secondRandomNumber < 9){
                            secondRandomNumber++;
                        }
                        usedTColorFromEnum = secondRandomNumber;
                        headerLabel.setTextFill(Color.web(Colors.values()[secondRandomNumber].name()));
                    }
                });
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        if(calledId.equals("steamidinfofx") || calledId.equals("steamwebapifx") || (calledId.equals("missingdatafx") && steamId.length() != STEAMIDLENGTH)){
            fxmlLoader.setLocation(getClass()
                    .getClassLoader()
                    .getResource("fxml/" + calledId + ".fxml"));
        } else {
            fxmlLoader.setLocation(getClass()
                    .getClassLoader()
                    .getResource("fxml/successmessage.fxml"));
        }

        Parent root = fxmlLoader.load();

        switch (calledId) {
            case "steamidinfofx":
                SteamIdInfoFxController steamIdInfoFxController = fxmlLoader.getController();
                steamIdInfoFxController.setMatchingColors("-fx-background-color: " + Colors.values()[usedBgColorFromEnum].name(), Colors.values()[usedTColorFromEnum].name());
                break;
            case "steamwebapifx":
                SteamWebApiFxController steamWebApiFxController = fxmlLoader.getController();
                steamWebApiFxController.setMatchingColors("-fx-background-color: " + Colors.values()[usedBgColorFromEnum].name(), Colors.values()[usedTColorFromEnum].name());
                break;
            case "missingdatafx":
                if (steamId.length() != STEAMIDLENGTH) {
                    MissingDataFxController missingDataFxController = fxmlLoader.getController();
                    missingDataFxController.setMatchingColors("-fx-background-color: " + Colors.values()[usedBgColorFromEnum].name(), Colors.values()[usedTColorFromEnum].name());
                    break;
                }
            default:
                SuccessMessageFxController successMessageFxController = fxmlLoader.getController();
                successMessageFxController.setMatchingColors("-fx-background-color: " + Colors.values()[usedBgColorFromEnum].name(), Colors.values()[usedTColorFromEnum].name());
        }

        stage.setScene(new Scene(root));
        stage.setTitle(r.getString("info"));
        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(
                ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                stage.hide();
            }
        });
        stage.show();
    }

    public void openDirectory(final ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        buttonsavepath.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(ExcelInGamesMain.getStage());
            File defaultDirectory = new File("c:/");
            directoryChooser.setInitialDirectory(defaultDirectory);
            field_file_path.setText(selectedDirectory.getAbsolutePath());
        });
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
        steamId = fieldSteamId.getText();
        webApi = fieldWebApi.getText();
        String filePath = field_file_path.getText();
        if (steamId.length() == STEAMIDLENGTH && !webApi.isEmpty()) {
            SteamGames steamGames = new SteamGames();
            steamGames.createSteamGamesExcel(steamId, webApi, filePath);
            fieldSteamId.setText("");
            fieldWebApi.setText("");
            field_file_path.setText("");
        }
        openInfo(actionEvent);
    }

}
