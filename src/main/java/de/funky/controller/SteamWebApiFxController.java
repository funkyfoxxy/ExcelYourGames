package de.funky.controller;

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

public class SteamWebApiFxController implements Initializable {

    @FXML
    public VBox steamwebapipane;
    @FXML
    public Label steamwebapilabel;

    Locale locale = Locale.getDefault();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        steamwebapilabel.setText(r.getString("steamWebInfoLabel"));
    }

    public void openBrowser() {
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
