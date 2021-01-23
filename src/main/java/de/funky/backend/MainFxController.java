package de.funky.backend;

import de.funky.gameclients.SteamGames;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

    ObservableList<String> variousModes = FXCollections
            .observableArrayList("Black", "White", "Surprise");

    /**
     * Has to be implemented because of the Initializable.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        check_dark.setItems(variousModes);
        check_dark.setValue("White");

        check_dark.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, t1) -> {
                    if (t1.equals(0)) {
                        mainpane.styleProperty().set("-fx-text-base-color: green; -fx-background-color: black");
                    } else if (t1.equals(1)) {
                        mainpane.styleProperty().set("-fx-background-color: white");
                    } else if (t1.equals(2)) {
                        mainpane.styleProperty().set("-fx-background-color: yellow");
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

}
