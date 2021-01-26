package de.funky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SuccessMessageFxController implements Initializable {

    @FXML
    public Pane successpane;
    @FXML
    public Label successlabel;

    Locale locale = Locale.getDefault();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successlabel.setText(r.getString("success"));
    }

    public void setMatchingColors(String bg, String t) {
        successpane.styleProperty().set(bg);
        successlabel.setTextFill(Color.web(t));
    }
}
