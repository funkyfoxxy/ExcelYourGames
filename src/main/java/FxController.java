import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Controller for JavaFX and the mainfx.fxml.
 */
public class FxController implements Initializable {

  /**
   * Label for testing purpose.
   */
  @FXML
  private Label theLabel;

  /**
   * handleButton method for testing purpose.
   *
   * @param actionEvent this actionEvent
   */
  @FXML
  public void handleButton(final ActionEvent actionEvent) {
    theLabel.setText("Hello World!");
  }

  /**
   * Has to be implemented because of the Initializable.
   */
  @Override
  public void initialize(final URL url, final ResourceBundle resourceBundle) {

  }
}
