package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DataBaseHandler;
import sample.Order;
import sample.User;

public class AdminWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addDataButton;

    @FXML
    private Button clearDataButton;

    @FXML
    private Button deleteDataButton;

    @FXML
    private Button editDataButton;

    @FXML
    private Button exitButton;

    @FXML
    private ChoiceBox<String> textChoiceBoxRole;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TableColumn<User, Integer> userColumnID;

    @FXML
    private TableColumn<User, String> userColumnLogin;

    @FXML
    private TableColumn<User, String> userColumnName;

    @FXML
    private TableColumn<User, String> userColumnPassword;

    @FXML
    private TableColumn<User, String> userColumnRole;

    @FXML
    private TableColumn<User, String> userColumnSurname;

    @FXML
    private TableView<User> userTable;

    private static ObservableList<String> roles = FXCollections.observableArrayList(Arrays.asList("guest","employer","admin"));

    @FXML
    void initialize() {

        usersInit();

        try{ usersTableUpdate(); } catch (SQLException e){ e.printStackTrace(); }

        exitButton.setOnAction(event -> onExitButtonClick());
        addDataButton.setOnAction(event -> onAddUserButtonClick());
        editDataButton.setOnAction(event -> onEditUserButtonClick());
        deleteDataButton.setOnAction(event -> onDeleteUserButtonClick());
        clearDataButton.setOnAction(event -> onClearDataButtonClick());

        assert addDataButton != null : "fx:id=\"addDataButton\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert deleteDataButton != null : "fx:id=\"deleteDataButton\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert editDataButton != null : "fx:id=\"editDataButton\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert textChoiceBoxRole != null : "fx:id=\"textChoiceBoxRole\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert textFieldLogin != null : "fx:id=\"textFieldLogin\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert textFieldName != null : "fx:id=\"textFieldName\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert textFieldPassword != null : "fx:id=\"textFieldPassword\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert textFieldSurname != null : "fx:id=\"textFieldSurname\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnID != null : "fx:id=\"userColumnID\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnLogin != null : "fx:id=\"userColumnLogin\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnName != null : "fx:id=\"userColumnName\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnPassword != null : "fx:id=\"userColumnPassword\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnRole != null : "fx:id=\"userColumnRole\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userColumnSurname != null : "fx:id=\"userColumnSurname\" was not injected: check your FXML file 'adminWindow.fxml'.";
        assert userTable != null : "fx:id=\"userTable\" was not injected: check your FXML file 'adminWindow.fxml'.";

    }

    public void usersInit(){
        userColumnID.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        userColumnLogin.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        userColumnPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        userColumnName.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        userColumnSurname.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        userColumnRole.setCellValueFactory(new PropertyValueFactory<User,String>("role"));

        textChoiceBoxRole.setItems(roles);
        textChoiceBoxRole.setValue("guest");
    }

    public void usersTableUpdate() throws SQLException {
        List<User> users = new DataBaseHandler().getAllUsers();
        ObservableList<User> data = FXCollections.observableArrayList(users);
        userTable.setItems(data);
    }

    public void onExitButtonClick(){
        openNewScene("/sample/Templates/mainWindow.fxml");
    }

    public void openNewScene(String window) {
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
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

    public void onAddUserButtonClick(){
        // Создание "сообщения"
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String role = textChoiceBoxRole.getValue().toString();

        if (login.equals("") || password.equals("")){
            alert.setContentText("Логин и пароль обязательны!");
            alert.showAndWait();
            return;
        }

        User new_user = new User();
        new_user.setLogin(login);
        new_user.setPassword(password);
        new_user.setName(name);
        new_user.setSurname(surname);
        new_user.setRole(role);

        try {
            new DataBaseHandler().addUser(new_user);
            usersTableUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void onEditUserButtonClick(){
        // Создание "сообщения"
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        User chosen_user = userTable.getSelectionModel().getSelectedItem();
        if(chosen_user == null) {
            alert.setContentText("Пользователь не выбран!");
            alert.showAndWait();
            return;
        }

        String login = textFieldLogin.getText();
        if(login.equals("")) login = chosen_user.getLogin();
        String password = textFieldPassword.getText();
        if(password.equals("")) password = chosen_user.getPassword();
        String name = textFieldName.getText();
        if(name.equals("")) name = chosen_user.getName();
        String surname = textFieldSurname.getText();
        if(surname.equals("")) surname = chosen_user.getSurname();
        String role = textChoiceBoxRole.getValue().toString();
        try {
            new DataBaseHandler().updateUserById(new User(chosen_user.getId(),login,password,name,surname,role));
            usersTableUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void onDeleteUserButtonClick(){
        User chosen_user = userTable.getSelectionModel().getSelectedItem();
        try {
            new DataBaseHandler().deleteUserByLogin(chosen_user.getLogin());
            usersTableUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void onClearDataButtonClick(){
        textFieldLogin.setText("");
        textFieldPassword.setText("");
        textFieldName.setText("");
        textFieldSurname.setText("");
        textChoiceBoxRole.setValue("guest");
    }
}
