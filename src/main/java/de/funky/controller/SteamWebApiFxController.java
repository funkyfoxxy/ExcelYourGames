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
  private VBox steamwebapipane;
  @FXML
  private Label steamwebapilabel;
  @FXML
  private Hyperlink webapikeyfinderlink;

  private final Locale locale = Locale.getDefault();
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", locale);

  /**
   * Setting the information text message for the user about
   * the webApiKey.
   *
   * @param url given URL
   * @param resourceBundle given ResourceBundle
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
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

  /**
   * Setting the background color and the font color based on
   * the colors that have been used in the parent or root window.
   *
   * @param bg background color
   * @param t text color
   */
  public void setMatchingColors(final String bg, final String t) {
    steamwebapipane.styleProperty().set(bg);
    steamwebapilabel.setTextFill(Color.web(t));
  }
}
