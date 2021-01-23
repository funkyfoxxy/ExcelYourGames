package de.funky.backend;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class SteamWebApiFxController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("And now I am here!");
    }

    public void openBrowser(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop()
                    .browse(new URI("https://steamidfinder.com/"));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

}
