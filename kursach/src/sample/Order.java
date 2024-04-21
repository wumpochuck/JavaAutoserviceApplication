package sample;

public class Order {
    private int id;
    private int userId;
    private String carModel;
    private String carNumber;
    private String problemType;
    private String date;
    private String time;
    private String status;

    public Order(){}

    public Order(int userId, String carModel, String carNumber, String problemType, String date, String time, String status) {
        this.userId = userId;
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.problemType = problemType;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Order(int id, int userId, String carModel, String carNumber, String problemType, String date, String time, String status) {
        this.id = id;
        this.userId = userId;
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.problemType = problemType;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getProblemType() {
        return problemType;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
