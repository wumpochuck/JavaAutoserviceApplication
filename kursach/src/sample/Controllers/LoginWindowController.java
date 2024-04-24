package sample.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Animations.ShakeAnimation;
import sample.Animations.SwitchLogRegAnimation;
import sample.DataBaseHandler;
import sample.User;

public class LoginWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private AnchorPane RegistrationPane;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField1;

    @FXML
    private TextField loginField2;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private PasswordField passwordField3;

    @FXML
    private Button registerButton;

    @FXML
    private Button switchRegistrationButton;

    public static User current_user;

    @FXML
    void initialize() {
        initSettings();
        current_user = new User();

        switchRegistrationButton.setOnAction(event -> {onSwitchToRegistration();});
        backButton.setOnAction(event -> {onSwitchToLogin();});
        loginButton.setOnAction(event -> { try { onLoginButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });
        registerButton.setOnAction(event -> { try { onRegisterButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });
    }

    private void initSettings(){
        LoginPane.setVisible(true);
        LoginPane.setDisable(false);
        RegistrationPane.setVisible(true);
        RegistrationPane.setDisable(true);
    }

    private void onSwitchToRegistration() { SwitchLogRegAnimation.switchToRegistration(LoginPane, RegistrationPane); }

    private void onSwitchToLogin() {
        SwitchLogRegAnimation.switchToLogin(LoginPane, RegistrationPane);
    }

    public void onLoginButtonClick() throws SQLException {
        // Создание "сообщения"
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        String login = loginField1.getText();
        String password = passwordField1.getText();

        // Если пользователя нету
        if(new DataBaseHandler().getUserByLogin(login) == null){
            alert.setContentText("Такого пользователя не существует!");
            alert.showAndWait();
            return;
        }
        User DataBaseUser = new DataBaseHandler().getUserByLogin(login);
        // Если пароль не совпадает
        if(!password.equals(DataBaseUser.getPassword())){
            PlayShakeAnimation();
            return;
        }

        current_user.setId(DataBaseUser.getId());
        current_user.setLogin(DataBaseUser.getLogin());
        current_user.setPassword(DataBaseUser.getPassword());
        current_user.setRole(DataBaseUser.getRole());
        current_user.setName(DataBaseUser.getName());
        current_user.setSurname(DataBaseUser.getSurname());

        openNewScene("/sample/Templates/mainWindow.fxml");
    }

    public void onRegisterButtonClick() throws SQLException {
        // Создание "сообщения"
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        String login = loginField2.getText();
        String password = passwordField2.getText();
        String passwordRepeat = passwordField3.getText();

        // Если пользователь существует
        if(new DataBaseHandler().getUserByLogin(login) != null){
            alert.setContentText("Такой пользователь уже существует!");
            alert.showAndWait();
            return;
        }
        // Если пароли не совпадают
        if(!password.equals(passwordRepeat)){
            alert.setContentText("Пароли должны совпадать!");
            alert.showAndWait();
            return;
        }
        current_user.setLogin(login);
        current_user.setPassword(password);
        current_user.setRole("guest");
        current_user.setName(null);
        current_user.setSurname(null);
        new DataBaseHandler().addUser(current_user);

        alert.setContentText("Вы успешно зарегистрировались!");
        alert.showAndWait();
    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void PlayShakeAnimation(){
        new ShakeAnimation(passwordField1).play();
    }

}
