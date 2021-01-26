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
import java.util.Locale;
import java.util.ResourceBundle;

public class SteamIdInfoFxController implements Initializable {

    @FXML
    public VBox steamidinfopane;
    @FXML
    public Label steamidinfolabel;

    Locale locale = Locale.getDefault();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        steamidinfolabel.setText(r.getString("steamIdInfoLabel"));
    }

    public void openBrowser(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop()
                    .browse(new URI("https://steamidfinder.com/"));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    public void setMatchingColors(String bg, String t){
        steamidinfopane.styleProperty().set(bg);
        steamidinfolabel.setTextFill(Color.web(t));
    }
}
