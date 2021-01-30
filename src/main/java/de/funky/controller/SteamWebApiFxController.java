package de.funky.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SteamWebApiFxController implements Initializable {

  @FXML
  public VBox steamwebapipane;
  @FXML
  public Label steamwebapilabel;
  @FXML
  public Hyperlink webapikeyfinderlink;

  private final Locale locale = Locale.getDefault();
  private final String language = locale.getLanguage();
  private final String country = locale.getCountry();
  private final Locale loc = new Locale(language, country);
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", loc);

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    steamwebapilabel.setText(res.getString("steamWebInfoLabel"));
    webapikeyfinderlink.setText(res.getString("webapikeyfinderlink"));
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
