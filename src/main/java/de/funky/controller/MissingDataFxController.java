package de.funky.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MissingDataFxController implements Initializable {

    @FXML
    public Pane missingdatapane;
    @FXML
    public Label missingdatalabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Just for the lulz!");
    }

    public void setMatchingColors(String bg, String t) {
        missingdatapane.styleProperty().set(bg);
        missingdatalabel.setTextFill(Color.web(t));
        System.out.println("Just for the lulz!");
    }
}
