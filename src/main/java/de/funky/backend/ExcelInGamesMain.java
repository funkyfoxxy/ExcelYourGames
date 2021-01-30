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

  private final Locale locale = Locale.getDefault();
  private final String language = locale.getLanguage();
  private final String country = locale.getCountry();
  private final Locale loc = new Locale(language, country);
  private final ResourceBundle res = ResourceBundle
          .getBundle("MessageBundle", loc);

  private static Stage stage;

  public static Stage getStage() {
    return stage;
  }

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
   * main method for launching the GUI and the Api requests.
   *
   * @param args the args
   */
  public static void main(final String[] args) {
    launch(args);
  }
}
