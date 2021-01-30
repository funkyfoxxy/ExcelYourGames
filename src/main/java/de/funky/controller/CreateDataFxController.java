package de.funky.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CreateDataFxController implements Initializable {

  @FXML
  public Pane createdatapane;
  @FXML
  public Label createdatalabel;

  private final Locale locale = Locale.getDefault();
  private final String language = locale.getLanguage();
  private final String country = locale.getCountry();
  private final Locale loc = new Locale(language, country);
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", loc);

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (MainFxController.steamId.length() == 17
                && MainFxController.webApi.length() != 0
                && MainFxController.filePath.length() != 0
                && !MainFxController.containsError) {
      createdatalabel.setText(res.getString("success"));
    } else {
      createdatalabel.setText(res.getString("error"));
    }
  }

  public void setMatchingColors(String bg, String t) {
    createdatapane.styleProperty().set(bg);
    createdatalabel.setTextFill(Color.web(t));
  }
}
