import de.funky.gameclients.SteamGames;
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
   * Start of the GUI for user input and output.
   *
   * @param stage the final stage
   * @throws Exception if the loading failed
   */
  @Override
  public void start(final Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass()
              .getResource("fxml/mainfx.fxml"));
    stage.setTitle("Excel All Games");
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * main method for launching the GUI and the Api requests.
   *
   * @param args the args
   */
  public static void main(final String[] args) {
    //new SteamGames();
    launch(args);
  }
}
