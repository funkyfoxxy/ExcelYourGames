package de.funky.backend;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * main class for the whole project.
 */
public class ExcelInGamesMain extends Application {

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
   * Stage of the main window.
   */
  private static Stage stage;

  /**
   * Getter for the main stage.
   *
   * @return stage
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * Setter for the main stage.
   *
   * @param newStage Setting a new main stage
   */
  public static void setStage(final Stage newStage) {
    ExcelInGamesMain.stage = newStage;
  }



  /**
   * Start of the GUI for user input and output.
   *
   * @param newStage the parent stage of everything
   */
  @Override
  public void start(final Stage newStage) {
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(
              getClass().getClassLoader().getResource("fxml/mainfx.fxml")));
      root.setStyle("-fx-background-color: white");
      newStage.setTitle(res.getString("title"));
      newStage.setScene(new Scene(root));
      setStage(newStage);
      newStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Main method for launching the GUI and the Api requests.
   *
   * @param args the args
   */
  public static void main(final String[] args) {
    launch(args);
  }
}
