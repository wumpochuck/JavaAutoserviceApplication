package sample.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Car;
import sample.Order;

public class EmployerWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text carsChoicedCarID;

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
    private ChoiceBox<?> ordersChoiceBoxSetStatus;

    @FXML
    private Text ordersChoicedOrderText;

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

    private String current_window = "Orders";

    @FXML
    void initialize() {

        exitButton.setOnAction(event -> onExitButtonClick());
        switcherTablesButton.setOnAction(event -> onSwitcherTablesButtonClick());

        orderTableInit();

        assert carsChoicedCarID != null : "fx:id=\"carsChoicedCarID\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsColumnCarModel != null : "fx:id=\"carsColumnCarModel\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsColumnCarNumber != null : "fx:id=\"carsColumnCarNumber\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsColumnID != null : "fx:id=\"carsColumnID\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsColumnUserID != null : "fx:id=\"carsColumnUserID\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsDeleteCarButton != null : "fx:id=\"carsDeleteCarButton\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsPane != null : "fx:id=\"carsPane\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsPieSchema != null : "fx:id=\"carsPieSchema\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert carsTable != null : "fx:id=\"carsTable\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert orderColumnStatus != null : "fx:id=\"orderColumnStatus\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersAddCarButton != null : "fx:id=\"ordersAddCarButton\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersApplyStatusButton != null : "fx:id=\"ordersApplyStatusButton\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersChoiceBoxSetStatus != null : "fx:id=\"ordersChoiceBoxSetStatus\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersChoicedOrderText != null : "fx:id=\"ordersChoicedOrderText\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnCarModel != null : "fx:id=\"ordersColumnCarModel\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnCarNumber != null : "fx:id=\"ordersColumnCarNumber\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnDate != null : "fx:id=\"ordersColumnDate\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnID != null : "fx:id=\"ordersColumnID\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnProblems != null : "fx:id=\"ordersColumnProblems\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnTime != null : "fx:id=\"ordersColumnTime\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersColumnUserID != null : "fx:id=\"ordersColumnUserID\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersPane != null : "fx:id=\"ordersPane\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert ordersTable != null : "fx:id=\"ordersTable\" was not injected: check your FXML file 'employerWindow.fxml'.";
        assert switcherTablesButton != null : "fx:id=\"switcherTablesButton\" was not injected: check your FXML file 'employerWindow.fxml'.";

    }

    // УПРАВЛЕНИЕ -----------------------------------------------------------

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

    // ORDERS --------------------------------------------------------------

    public void orderTableInit(){
        ordersColumnID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
        ordersColumnUserID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("userid"));
        ordersColumnCarModel.setCellValueFactory(new PropertyValueFactory<Order,String>("carModel"));
        ordersColumnCarNumber.setCellValueFactory(new PropertyValueFactory<Order,String>("carNumber"));
        ordersColumnProblems.setCellValueFactory(new PropertyValueFactory<Order,String>("problemType"));
        ordersColumnDate.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
        ordersColumnTime.setCellValueFactory(new PropertyValueFactory<Order,String>("time"));
        orderColumnStatus.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
    }

    public void orderTableUpdate(){

    }

    // CARS ----------------------------------------------------------------



}
