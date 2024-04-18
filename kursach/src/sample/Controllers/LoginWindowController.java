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

    public static User current_user = new User();

    @FXML
    void initialize() {
        switchRegistrationButton.setOnAction(event -> {onSwitchRegistrationButtonClick(LoginPane, RegistrationPane);});

        backButton.setOnAction(event -> {onBackButtonClick(LoginPane, RegistrationPane);});

        loginButton.setOnAction(event -> {
            try {
                onLoginButtonClick(loginButton);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        registerButton.setOnAction(event -> {
            try {
                onRegisterButtonClick();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void onSwitchRegistrationButtonClick(AnchorPane loginPane, AnchorPane registrationPane) {
        loginPane.setVisible(false);
        registrationPane.setVisible(true);
    }

    public void onBackButtonClick(AnchorPane loginPane, AnchorPane registrationPane){
        loginPane.setVisible(true);
        registrationPane.setVisible(false);
    }

    public void onLoginButtonClick(Button loginButton) throws SQLException {
        // Создание "сообщения"
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        // Логика проверки пароля

        String login = loginField1.getText();
        String password = passwordField1.getText();

        // 1й этап проверки - Неправильное имя пользователя
        if(new DataBaseHandler().getUserByLogin(login) == null){
            alert.setContentText("Такого пользователя не существует!");
            alert.showAndWait();
            return;
        }
        User DataBaseUser = new DataBaseHandler().getUserByLogin(login);
        if(DataBaseUser.getPassword() != password){                         // ошибка тут
            PlayShakeAnimation();
            return;
        }
        alert.setContentText("Вы успешно вошли!");
        alert.showAndWait();



        //current_user.setLogin(loginField1.getText());
        //openNewScene("/sample/Templates/mainWindow.fxml");
    }

    public void onRegisterButtonClick() throws SQLException {

        // Создание "сообщения"
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        // Логика проверки регистрации
        String login = loginField2.getText();
        String password = passwordField2.getText();
        String passwordRepeat = passwordField3.getText();

        // 1й этап проверки - 2 пароля введены верно
        if(!password.equals(passwordRepeat)){
            alert.setContentText("Пароли должны совпадать!");


        }else{
            current_user.setLogin(login);
            current_user.setPassword(password);
            current_user.setRole("guest");
            current_user.setName(null);
            current_user.setSurname(null);
            new DataBaseHandler().addUser(current_user);

            alert.setContentText("Вы успешно зарегистрировались!");
        }
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
