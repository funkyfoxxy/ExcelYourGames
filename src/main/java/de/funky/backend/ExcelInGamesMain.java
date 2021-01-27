package de.funky.backend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * main class for the whole project.
 */
public class ExcelInGamesMain extends Application {

  Locale locale = Locale.getDefault();
  String language = locale.getLanguage();
  String country = locale.getCountry();
  Locale l = new Locale(language,country);
  ResourceBundle r = ResourceBundle.getBundle("MessageBundle", l);

  private static Stage stage;

  public static Stage getStage() {
    return stage;
  }

  public static void setStage(Stage stage) {
    ExcelInGamesMain.stage = stage;
  }



  /**
   * Start of the GUI for user input and output.
   *
   * @param stage the final stage
   */
  @Override
  public void start(Stage stage) {
    try{
      Parent root = FXMLLoader.load(Objects.requireNonNull(
              getClass().getClassLoader().getResource("fxml/mainfx.fxml")));
      root.setStyle("-fx-background-color: white");
      stage.setTitle(r.getString("title"));
      stage.setScene(new Scene(root));
      setStage(stage);
      stage.show();
    } catch (Exception e){
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
