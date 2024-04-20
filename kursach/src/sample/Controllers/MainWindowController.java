package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DataBaseHandler;
import sample.Order;
import sample.User;

import java.util.Arrays;
import java.util.List;
import java.sql.SQLException;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane buttonsPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button mainButton;

    @FXML
    private AnchorPane mainButtonPane;

    @FXML
    private AnchorPane mainPage;

    @FXML
    private Button orderButton;

    @FXML
    private AnchorPane orderButtonPane;

    @FXML
    private AnchorPane orderPage;

    @FXML
    private Text orderText;

    @FXML
    private Text orderText1;

    @FXML
    private TableColumn<Order, String> order_carColumn;

    @FXML
    private Button order_clearButton;

    @FXML
    private TableColumn<Order, Integer> order_idColumn;

    @FXML
    private TableView<Order> order_ordersTable;

    @FXML
    private TableColumn<Order, String> order_problemColumn;

    @FXML
    private Button order_sendButton;

    @FXML
    private TableColumn<Order, String> order_statusColumn;

    @FXML
    private TextField order_textCarModel;

    @FXML
    private TextField order_textCarNumber;

    @FXML
    private DatePicker order_textDate;

    @FXML
    private TextArea order_textProblem;

    @FXML
    private ChoiceBox<String> order_textTime;

    @FXML
    private Text welcomeText;


    @FXML
    void hideButtonsPane(MouseEvent event) {
        //new ButtonsPaneAnimation(buttonsPane,"toleft").play();
        buttonsPane.setLayoutX(-300);
    }

    @FXML
    void showButtonsPane(MouseEvent event) {
        //new ButtonsPaneAnimation(buttonsPane,"toright").play();
        buttonsPane.setLayoutX(0);
    }

    public static User current_user;
    public static ObservableList<String> times = FXCollections.observableArrayList(Arrays.asList("09:00", "12:00", "15:00", "18:00", "21:00"));


    @FXML
    void initialize() {
        initSettings();
        // #383e81
        // #3f458f

        welcomeText.setText(String.format("Добро пожаловать,\n%s!\nВы вошли как %s.",current_user.getFullName(), current_user.getRole()));
        exitButton.setOnAction(event -> onExitButtonClick());
        mainButton.setOnAction(event -> onMainButtonClick());
        orderButton.setOnAction(event -> { try { onOrderButtonClick(); } catch (SQLException throwables) { throwables.printStackTrace(); } });

        order_clearButton.setOnAction(event -> order_onClearButtonClick());
        order_sendButton.setOnAction(event -> { try { order_onSendButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });

        order_idColumn.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        order_carColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("carModel"));
        order_problemColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("problemType"));
        order_statusColumn.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));

        assert buttonsPane != null : "fx:id=\"buttonsPane\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert mainButton != null : "fx:id=\"mainButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert mainButtonPane != null : "fx:id=\"mainButtonPane\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert mainPage != null : "fx:id=\"mainPage\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert orderButton != null : "fx:id=\"orderButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert orderButtonPane != null : "fx:id=\"orderButtonPane\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert orderPage != null : "fx:id=\"orderPage\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert orderText != null : "fx:id=\"orderText\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert orderText1 != null : "fx:id=\"orderText1\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_carColumn != null : "fx:id=\"order_carColumn\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_clearButton != null : "fx:id=\"order_clearButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_idColumn != null : "fx:id=\"order_idColumn\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_ordersTable != null : "fx:id=\"order_ordersTable\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_problemColumn != null : "fx:id=\"order_problemColumn\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_sendButton != null : "fx:id=\"order_sendButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_statusColumn != null : "fx:id=\"order_statusColumn\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_textCarModel != null : "fx:id=\"order_textCarModel\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_textCarNumber != null : "fx:id=\"order_textCarNumber\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_textDate != null : "fx:id=\"order_textDate\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_textProblem != null : "fx:id=\"order_textProblem\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert order_textTime != null : "fx:id=\"order_textTime\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert welcomeText != null : "fx:id=\"welcomeText\" was not injected: check your FXML file 'mainWindow.fxml'.";

    }

    // Методы управления --------------------------------------------------

    public void initSettings(){
        onMainButtonClick();
        current_user = LoginWindowController.current_user;
        buttonsPane.setLayoutX(-300);
    }

    public void hideAllPages(){
        mainPage.setVisible(false);
        orderPage.setVisible(false);
    }

    public void recolorAllButtons(){
        mainButtonPane.setStyle("-fx-background-color: #3f458f;");
        orderButtonPane.setStyle("-fx-background-color: #3f458f;");
    }

    public void onExitButtonClick(){
        current_user = null;
        openNewScene("/sample/Templates/loginWindow.fxml");
    }

    public void onMainButtonClick(){
        hideAllPages();
        recolorAllButtons();
        mainPage.setVisible(true);
        mainButtonPane.setStyle("-fx-background-color: #383e81;");

    }

    public void onOrderButtonClick() throws SQLException {
        hideAllPages();
        recolorAllButtons();
        orderPage.setVisible(true);
        orderButtonPane.setStyle("-fx-background-color: #383e81;");
        order_init();
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

    // ORDER PAGE ---------------------------------------------------------

    public void order_init() throws SQLException {
        order_onClearButtonClick();
        order_tableUpdate();
    }

    public void order_tableUpdate() throws SQLException {
        List<Order> orders = new DataBaseHandler().getOrdersByUserId(current_user.getId());
        ObservableList<Order> data = FXCollections.observableArrayList(orders);
        order_ordersTable.setItems(data);
    }

    public void order_onClearButtonClick(){
        order_textCarModel.setText(null);
        order_textCarNumber.setText(null);
        order_textProblem.setText(null);
        order_textDate.setValue(null);
        order_textTime.setItems(times);
        order_textTime.setValue("Выберите время");

    }

    public void order_onSendButtonClick() throws SQLException {
        // Создание "сообщения"
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        // Проверки
        try {
            String carModel = order_textCarModel.getText();
            String carNumber = order_textCarNumber.getText();
            String problem = order_textProblem.getText();
            String date = order_textDate.getValue().toString();
            String time = order_textTime.getValue();

            // 1 - Не выбрано время
            if(time.equals("Выберите время")){
                alert.setContentText("Не выбрано время!");
                alert.showAndWait();
                return;
            }
            // 2 - Свободно время
            if (new DataBaseHandler().isDateTimeInTable(date, time)) {
                alert.setContentText("Выбранное время занято!\nПопробуйте другое.");
                alert.showAndWait();
                return;
            }


            alert.setContentText("Заявка создана! Ожидайте рассмотрения");
            alert.showAndWait();
            Order order = new Order(current_user.getId(),carModel,carNumber,problem,date,time,"Рассматривается");
            new DataBaseHandler().addOrder(order);

            order_tableUpdate();

            }

        // 3 - Заполнена заявка
        catch (NullPointerException e) {
            alert.setContentText("Заполните заявку полностью!");
            alert.showAndWait();
            return;
        }
    }


    // MISC ---------------------------------------------------------------
}
