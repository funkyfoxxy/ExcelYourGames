package de.funky.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Controller for the createdatafx.fxml. It will show the matching
 * message based on the inputs made by the user and therefore the
 * result of the REST-API request and the success or failure to
 * write the data into the workbook.
 */
public class CreateDataFxController implements Initializable {

  /**
   * Final int to represent the length of a steamid.
   */
  private static final int STEAMIDLENGTH = 17;

  /**
   * Window to show the success or error message inside a label.
   */
  @FXML
  private Pane createdatapane;

  /**
   * Label that is inside the createdatapane that
   * contains the message.
   */
  @FXML
  private Label createdatalabel;

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
   * Setting the correct text message for the user. If everything worked
   * fine the success message will be displayed, else the error message.
   *
   * @param url given URL
   * @param resourceBundle given ResourceBundle
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {
    if (MainFxController.getSteamId().length() == STEAMIDLENGTH
                && MainFxController.getWebApi().length() != 0
                && MainFxController.getFilePath().length() != 0
                && !MainFxController.isContainsError()) {
      createdatalabel.setText(res.getString("success"));
    } else {
      createdatalabel.setText(res.getString("error"));
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
    createdatapane.styleProperty().set(bg);
    createdatalabel.setTextFill(Color.web(t));
  }
}
