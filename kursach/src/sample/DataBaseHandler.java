package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

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

    // Создание таблицы Users
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

    // Создание таблицы Orders
    public void createOrdersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS orders (" +
                    "idorder INT AUTO_INCREMENT PRIMARY KEY," +
                    "userid INT," +
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

    // Создание таблицы Cars
    public void createCarTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cars (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "userId INT," +
                    "carModel VARCHAR(255)," +
                    "carNumber VARCHAR(20)" +
                    ")";
            statement.executeUpdate(createTableQuery);
            System.out.println("createCarTable(): Table created/consist!");
        }
    }

    // Добавление User
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

    // Добавление Order
    public void addOrder(Order order) throws SQLException {
        String insertQuery = "INSERT INTO orders (userid, carmodel, carnumber, problemtype, date, time, status) " +
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

    public List<Order> getOrderByCarModel(String carModel) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE carmodel = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
             statement.setString(1, carModel);
             try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("idorder"));
                    order.setUserId(resultSet.getInt("userid"));
                    order.setCarModel(resultSet.getString("carmodel"));
                    order.setCarNumber(resultSet.getString("carnumber"));
                    order.setProblemType(resultSet.getString("problemtype"));
                    order.setDate(resultSet.getString("date"));
                    order.setTime(resultSet.getString("time"));
                    order.setStatus(resultSet.getString("status"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT idorder, carmodel, problemtype, status FROM orders WHERE userid = ?";
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

    public void updateUserById(int userId, String newName, String newSurname) throws SQLException {
        String updateQuery = "UPDATE users SET name = ?, surname = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newSurname);
            preparedStatement.setInt(3, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("updateUserById(): User details updated successfully!");
            } else {
                System.out.println("updateUserById(): No user found with the given ID.");
            }
        }
    }

    public void updateOrderStatusById(int orderId, String newStatus) throws SQLException {
        String updateQuery = "UPDATE orders SET status = ? WHERE idorder = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, orderId);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("updateOrderStatusById(): User details updated successfully!");
            } else {
                System.out.println("updateOrderStatusById(): No user found with the given ID.");
            }
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("idorder"));
                order.setUserId(resultSet.getInt("userid"));
                order.setCarModel(resultSet.getString("carmodel"));
                order.setCarNumber(resultSet.getString("carnumber"));
                order.setProblemType(resultSet.getString("problemtype"));
                order.setDate(resultSet.getString("date"));
                order.setTime(resultSet.getString("time"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }

    public void addCar(Car car) throws SQLException {
        String insertQuery = "INSERT INTO cars (userId, carModel, carNumber) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, car.getUserId());
            preparedStatement.setString(2, car.getCarModel());
            preparedStatement.setString(3, car.getCarNumber());

            preparedStatement.executeUpdate();
            System.out.println("addCar(): Car added successfully.");
        }
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setUserId(resultSet.getInt("userId"));
                car.setCarModel(resultSet.getString("carModel"));
                car.setCarNumber(resultSet.getString("carNumber"));
                cars.add(car);
            }
        }
        return cars;
    }

    public ObservableList<PieChart.Data> getCarDataForPieChart() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Получение данных о количестве машин по каждой марке из базы данных
        String query = "SELECT carModel, COUNT(*) as count FROM cars GROUP BY carModel";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String carModel = resultSet.getString("carModel");
                int count = resultSet.getInt("count");
                pieChartData.add(new PieChart.Data(carModel, count));
            }
        }
        return pieChartData;
    }

    public void deleteCar(int carId) throws SQLException {
        String deleteQuery = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, carId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("deleteCar(): Car deleted successfully.");
            } else {
                System.out.println("deleteCar(): No car found with the given ID.");
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Обработка результатов запроса
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String role = resultSet.getString("role");

                // Создание объекта User и добавление его в список
                User user = new User(id, login, password, name, surname, role);
                userList.add(user);
            }
        }

        return userList;
    }

    public void deleteUserByLogin(String login) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE login = ?")) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            System.out.println("User with login '" + login + "' has been deleted successfully.");
        }
    }

    public void updateUserById(User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET login = ?, password = ?, name = ?, surname = ?, role = ? WHERE id = ?")) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("User with ID '" + user.getId() + "' has been updated successfully.");
        }
    }

    /*
    public void insertTwentyUsers() throws SQLException {
        String insertQuery = "INSERT INTO users (login, password, name, surname, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            String[] logins = {"user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10", "user11", "user12", "user13", "user14", "user15", "user16", "user17", "user18", "user19", "user20"};
            String[] passwords = {"password1", "password2", "password3", "password4", "password5", "password6", "password7", "password8", "password9", "password10", "password11", "password12", "password13", "password14", "password15", "password16", "password17", "password18", "password19", "password20"};
            String[] names = {"Иван", "Мария", "Александр", "Елена", "Дмитрий", "Анна", "Сергей", "Ольга", "Николай", "Екатерина", "Андрей", "Татьяна", "Алексей", "Виктория", "Артем", "Евгения", "Игорь", "Наталья", "Станислав", "Анастасия"};
            String[] surnames = {"Петров", "Иванова", "Смирнов", "Кузнецова", "Попов", "Соколова", "Лебедев", "Козлова", "Новиков", "Морозова", "Павлов", "Волкова", "Семенов", "Федорова", "Морозов", "Васильева", "Петров", "Соловьёва", "Волков", "Виноградова"};
            String[] roles = {"guest", "guest", "guest", "guest", "employer", "guest", "guest", "guest", "admin", "guest", "guest", "guest", "guest", "guest", "guest", "guest", "guest", "guest", "guest", "guest"};

            for (int i = 0; i < 20; i++) {
                preparedStatement.setString(1, logins[i]);
                preparedStatement.setString(2, passwords[i]);
                preparedStatement.setString(3, names[i]);
                preparedStatement.setString(4, surnames[i]);
                preparedStatement.setString(5, roles[i]);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            System.out.println("Twenty users inserted successfully.");
        }
    }
    */

    /*
    public void insertTwentyOrders() throws SQLException {
        String insertQuery = "INSERT INTO orders (userid, carmodel, carnumber, problemtype, date, time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            int[] userIds = {5, 6, 7, 8, 9, 10, 11, 12, 5, 6, 7, 8}; // UserIDs от 5 до 12 и повторение для некоторых пользователей
            String[] carModels = {"Toyota", "Honda", "Ford", "BMW", "Mercedes", "Volkswagen", "Toyota", "Honda", "Audi", "Lexus", "Toyota", "Honda"};
            String[] carNumbers = {"AB1234", "CD5678", "EF91011", "GH121314", "IJ151617", "KL181920", "AB1234", "CD5678", "MN212223", "OP242526", "AB1234", "CD5678"};
            String[] problemTypes = {"Проблема с двигателем", "Проблема с тормозами", "Проблема с трансмиссией", "Проблема с подвеской", "Проблема с электрикой", "Проблема с кондиционером", "Проблема с двигателем", "Проблема с тормозами", "Проблема с выхлопной системой", "Проблема с топливной системой", "Проблема с двигателем", "Проблема с тормозами"};
            String[] dates = {"2024-04-15", "2024-04-16", "2024-04-17", "2024-04-18", "2024-04-19", "2024-04-20", "2024-04-21", "2024-04-22", "2024-04-23", "2024-04-24", "2024-04-25", "2024-04-26"};
            String[] times = {"09:00", "12:00", "15:00", "18:00", "21:00"};
            String statuses = "Рассматривается";

            for (int i = 0; i < 12; i++) {
                preparedStatement.setInt(1, userIds[i]);
                preparedStatement.setString(2, carModels[i]);
                preparedStatement.setString(3, carNumbers[i]);
                preparedStatement.setString(4, problemTypes[i]);
                preparedStatement.setString(5, dates[i % 6]); // Использование модуля для циклического прохода по массиву дат
                preparedStatement.setString(6, times[i % 5]); // Использование модуля для циклического прохода по массиву времени
                preparedStatement.setString(7, statuses); // Использование модуля для циклического прохода по массиву статусов
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            System.out.println("Twenty orders inserted successfully.");
        }
    }
    */
}
