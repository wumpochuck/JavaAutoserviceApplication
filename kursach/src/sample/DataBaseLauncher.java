package sample;

import java.sql.SQLException;

public class DataBaseLauncher {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DataBaseHandler dbc = new DataBaseHandler();

        dbc.createUsersTable();
        dbc.createOrdersTable();
        dbc.createCarTable();
        //dbc.insertTwentyUsers();
        //dbc.insertTwentyOrders();
    }
}
