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

/**
 * Controller for the steamidinfofx.fxml.
 */
public class SteamIdInfoFxController implements Initializable {

  /**
   * Pane that is used to contain the label and link.
   */
  @FXML
  private VBox steamidinfopane;

  /**
   * Label that is used to show the info about the users SteamId.
   */
  @FXML
  private Label steamidinfolabel;

  /**
   * Hyperlink to make it easier for the user to find his SteamId.
   */
  @FXML
  private Hyperlink idfinderlink;

  /**
   * Locale to retrieve system preferences and information.
   */
  private final Locale locale = Locale.getDefault();

  /**
   * Generating the ResourceBundle to retrieve the
   * fitting MessageBundle out of resources.
   */
  private final ResourceBundle res = ResourceBundle
            .getBundle("MessageBundle", locale);

  /**
   * Setting the information text message for the user about
   * the steamId.
   *
   * @param url given URL
   * @param resourceBundle given ResourceBundle
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
    steamidinfolabel.setText(res.getString("steamIdInfoLabel"));
    idfinderlink.setText(res.getString("idfinderlink"));
  }

  /**
   * Opens the default Browser of the user's system.
   */
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
    steamidinfopane.styleProperty().set(bg);
    steamidinfolabel.setTextFill(Color.web(t));
  }
}
