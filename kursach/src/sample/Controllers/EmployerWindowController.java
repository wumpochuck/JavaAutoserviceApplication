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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Car;
import sample.DataBaseHandler;
import sample.Order;

public class EmployerWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Car, String> carsColumnCarModel;

    @FXML
    private TableColumn<Car, String> carsColumnCarNumber;

    @FXML
    private TableColumn<Car, Integer> carsColumnID;

    @FXML
    private TableColumn<Car, Integer> carsColumnUserID;

    @FXML
    private Button carsDeleteCarButton;

    @FXML
    private AnchorPane carsPane;

    @FXML
    private PieChart carsPieSchema;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Order, String> orderColumnStatus;

    @FXML
    private Button ordersAddCarButton;

    @FXML
    private Button ordersApplyStatusButton;

    @FXML
    private ChoiceBox<String> ordersChoiceBoxSetStatus;

    @FXML
    private TableColumn<Order, String> ordersColumnCarModel;

    @FXML
    private TableColumn<Order, String> ordersColumnCarNumber;

    @FXML
    private TableColumn<Order, String> ordersColumnDate;

    @FXML
    private TableColumn<Order, Integer> ordersColumnID;

    @FXML
    private TableColumn<Order, String> ordersColumnProblems;

    @FXML
    private TableColumn<Order, String> ordersColumnTime;

    @FXML
    private TableColumn<Order, Integer> ordersColumnUserID;

    @FXML
    private AnchorPane ordersPane;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private Button switcherTablesButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField ordersSearchText;

    private String current_window = "Orders";
    private static ObservableList<String> statuses = FXCollections.observableArrayList(
            Arrays.asList("Рассматривается","Принята","В работе", "Выполнена"));

    @FXML
    void initialize() {
        hideAllPages();
        try { onUpdateButtonClick(); } catch (SQLException throwables) { throwables.printStackTrace(); }
        ordersPane.setVisible(true);

        exitButton.setOnAction(event -> onExitButtonClick());
        switcherTablesButton.setOnAction(event -> onSwitcherTablesButtonClick());
        updateButton.setOnAction(event -> { try { onUpdateButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });
        ordersApplyStatusButton.setOnAction(event -> { try { onOrderApplyStatusButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });
        ordersAddCarButton.setOnAction(event -> { try { onOrderAddCarButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });
        carsDeleteCarButton.setOnAction(event -> { try { onCarsDeleteButtonClick(); } catch (SQLException e) { e.printStackTrace(); } });

        orderTableInit();
        carTableInit();
    }

    // CONTROLS --------------------------------------------------------------------------------------------------------

    public void hideAllPages(){
        carsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    public void onSwitcherTablesButtonClick(){
        hideAllPages();
        switch (current_window){
            case("Orders"):{
                carsPane.setVisible(true);
                current_window = "Cars";
                switcherTablesButton.setText("Автомобили");
                return;
            }
            case("Cars"):{
                ordersPane.setVisible(true);
                current_window = "Orders";
                switcherTablesButton.setText("Заявки");
                return;
            }
        }
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

    public void onUpdateButtonClick() throws SQLException { // При нажатии на кнопку "Обновить"
        ordertableUpdate(); // Вызывается метод обновления таблицы заказов
        carTableUpdate();
    }

    // ORDERS ----------------------------------------------------------------------------------------------------------

    public void orderTableInit(){
        ordersColumnID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        ordersColumnUserID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("userId"));
        ordersColumnCarModel.setCellValueFactory(new PropertyValueFactory<Order,String>("carModel"));
        ordersColumnCarNumber.setCellValueFactory(new PropertyValueFactory<Order,String>("carNumber"));
        ordersColumnProblems.setCellValueFactory(new PropertyValueFactory<Order,String>("problemType"));
        ordersColumnDate.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
        ordersColumnTime.setCellValueFactory(new PropertyValueFactory<Order,String>("time"));
        orderColumnStatus.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));

        ordersChoiceBoxSetStatus.setItems(statuses);
    }

    public void ordertableUpdate() throws SQLException {
        String searchText = ordersSearchText.getText(); // Считывается текст марки автомобиля для поиска
        List<Order> orders;

        if(searchText.equals(null) || searchText.equals("")) { // В случае, когда считанный текст пуст
            orders = new DataBaseHandler().getAllOrders(); // Создается список всех заказов из БД
        } else{ // Иначе
            orders = new DataBaseHandler().getOrderByCarModel(searchText); // Создается список только заказов с соотв. маркой
        }
        ObservableList<Order> data = FXCollections.observableArrayList(orders); // Список преобразуется в коллекцию
        ordersTable.setItems(data); // Коллекция заполняет таблицу в GUI
    }

    public void onOrderApplyStatusButtonClick() throws SQLException {
        Order order = ordersTable.getSelectionModel().getSelectedItem();
        String new_status = ordersChoiceBoxSetStatus.getValue().toString();
        new DataBaseHandler().updateOrderStatusById(order.getId(),new_status);

        ordertableUpdate();
    }

    public void onOrderAddCarButtonClick() throws SQLException {
        // Создание "сообщения"
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        Order order = ordersTable.getSelectionModel().getSelectedItem();
        Car new_car = new Car(order.getUserId(),order.getCarModel(),order.getCarNumber());
        new DataBaseHandler().addCar(new_car);

        alert.setContentText("Автомобиль успешно перенесён!");
        alert.showAndWait();

        carTableUpdate();
    }

    // CARS ------------------------------------------------------------------------------------------------------------

    public void carTableInit(){
        carsColumnID.setCellValueFactory(new PropertyValueFactory<Car,Integer>("id"));
        carsColumnUserID.setCellValueFactory(new PropertyValueFactory<Car,Integer>("userId"));
        carsColumnCarModel.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));
        carsColumnCarNumber.setCellValueFactory(new PropertyValueFactory<Car, String>("carNumber"));
    }

    public void carTableUpdate() throws SQLException {
        List<Car> cars = new DataBaseHandler().getAllCars();
        ObservableList<Car> data1 = FXCollections.observableArrayList(cars);
        carsTable.setItems(data1);

        pieChartUpdate();
    }

    public void pieChartUpdate() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = new DataBaseHandler().getCarDataForPieChart();
        carsPieSchema.setData(pieChartData);
    }

    public void onCarsDeleteButtonClick() throws SQLException {
        Car car = carsTable.getSelectionModel().getSelectedItem();
        new DataBaseHandler().deleteCar(car.getId());

        carTableUpdate();
    }
}
