package de.funky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateDataFxController implements Initializable {

    @FXML
    public Pane createdatapane;
    @FXML
    public Label createdatalabel;

    Locale locale = Locale.getDefault();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(MainFxController.steamId.length() == 17 && MainFxController.webApi.length() != 0 && MainFxController.filePath.length() != 0 && !MainFxController.containsError){
            createdatalabel.setText(r.getString("success"));
        } else {
            createdatalabel.setText(r.getString("error"));
        }
    }

    public void setMatchingColors(String bg, String t) {
        createdatapane.styleProperty().set(bg);
        createdatalabel.setTextFill(Color.web(t));
    }
}
