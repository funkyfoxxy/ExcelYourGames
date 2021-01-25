package de.funky.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SuccessMessageFxController implements Initializable {

    @FXML
    public Pane successpane;
    @FXML
    public Label successlabel;

    public String bgColor;
    public String tColor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("Hier ist die SuccessMessageInitialisierung");
//        successpane.styleProperty().set(bgColor);
//        successlabel.setTextFill(Color.web(tColor));
    }

    public void showSuccessMessage(ActionEvent actionEvent, String bg, String t) throws IOException {
        bgColor = bg;
        tColor = t;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource("fxml/successmessage.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("Information");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
//        setMatchingColors(bg, t);
    }

    public void setMatchingColors(String s, String t) {
        successpane.styleProperty().set(s);
        successlabel.setTextFill(Color.web(t));
    }
}
