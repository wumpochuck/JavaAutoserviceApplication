package sample.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sample.User;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text welcomeText;

    public static User current_user = LoginWindowController.current_user;

    @FXML
    void initialize() {
        welcomeText.setText(String.format("Добро пожаловать,\n%s!",current_user.getLogin()));
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'mainWindow.fxml'.";

    }

}
