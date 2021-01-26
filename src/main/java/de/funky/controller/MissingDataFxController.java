package de.funky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MissingDataFxController implements Initializable {

    @FXML
    public Pane missingdatapane;
    @FXML
    public Label missingdatalabel;

    String language = "de";
    String country = "DE";
    Locale l = new Locale(language,country);
    ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        missingdatalabel.setText(r.getString("error"));
    }

    public void setMatchingColors(String bg, String t) {
        missingdatapane.styleProperty().set(bg);
        missingdatalabel.setTextFill(Color.web(t));
    }
}
