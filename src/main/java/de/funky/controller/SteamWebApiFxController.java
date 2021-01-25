package de.funky.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class SteamWebApiFxController implements Initializable {

    @FXML
    public VBox steamwebapipane;
    @FXML
    public Label steamwebapilabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("And now I am here!");
    }

    public void openBrowser(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop()
                    .browse(new URI("https://steamidfinder.com/"));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    public void setMatchingColors(String bg, String t) {
        steamwebapipane.styleProperty().set(bg);
        steamwebapilabel.setTextFill(Color.web(t));
    }
}
