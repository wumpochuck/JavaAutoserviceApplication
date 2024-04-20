package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler {
    Connection connection;

    public DataBaseHandler(){
        try {
            this.connection = getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String urlConnection = "jdbc:mysql://127.0.0.1:3306/Autoservice";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(urlConnection, "root", "a32gDF%b23");
        return connection;
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "login VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL," +
                    "name VARCHAR(50)," +
                    "surname VARCHAR(50)," +
                    "role VARCHAR(50) NOT NULL" +
                    ")";
            statement.executeUpdate(createTableQuery);
            System.out.println("createUsersTable(): Table created/consist!");
        }
    }

    public void createOrdersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS orders (" +
                    "idorder INT AUTO_INCREMENT PRIMARY KEY," +
                    "iduser INT," +
                    "carmodel VARCHAR(50)," +
                    "carnumber VARCHAR(50)," +
                    "problemtype VARCHAR(150)," +
                    "date VARCHAR(50)," +
                    "time VARCHAR(50)," +
                    "status VARCHAR(50)" +
                    ")";
            statement.executeUpdate(createTableQuery);
            System.out.println("createOrdersTable(): Table created/consist!");
        }
    }

    public void addUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO Users (login, password, name, surname, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getRole());

            preparedStatement.executeUpdate();
            System.out.println("addUser(): User added successfully.");
        }
    }

    public User getUserByLogin(String login) throws SQLException {
        String selectQuery = "SELECT * FROM Users WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String role = resultSet.getString("role");

                    return new User(id, login, password, name, surname, role);
                }
            }
        }
        return null; // Если пользователь с таким логином не найден
    }

    public boolean isDateTimeInTable(String date, String time) throws SQLException {
        String selectDateTimeQuery = "SELECT COUNT(*) AS count FROM orders WHERE date = ? AND time = ?";
        int count = 0;

        try (PreparedStatement statement = connection.prepareStatement(selectDateTimeQuery)) {
            statement.setString(1, date);
            statement.setString(2, time);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }

        return count > 0;
    }

    public void addOrder(Order order) throws SQLException {
        String insertQuery = "INSERT INTO orders (iduser, carmodel, carnumber, problemtype, date, time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, order.getCarModel());
            preparedStatement.setString(3, order.getCarNumber());
            preparedStatement.setString(4, order.getProblemType());
            preparedStatement.setString(5, order.getDate());
            preparedStatement.setString(6, order.getTime());
            preparedStatement.setString(7, order.getStatus());

            preparedStatement.executeUpdate();
            System.out.println("insertOrder(): Order added successfully.");
        }
    }

    // Метод вытаскивает
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT idorder, carmodel, problemtype, status FROM orders WHERE iduser = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("idorder"));
                    order.setCarModel(resultSet.getString("carmodel"));
                    order.setProblemType(resultSet.getString("problemtype"));
                    order.setStatus(resultSet.getString("status"));
                    // Добавляем заказ в список
                    orders.add(order);
                }
            }
        }
        return orders;
    }
}
