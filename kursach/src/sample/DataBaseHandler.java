package sample;

import java.sql.*;

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
            System.out.printf("createUsersTable(): Таблица создана/существует");
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

}
