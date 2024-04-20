package sample;

public class Order {
    private int _id;
    private int _userid;
    private String _carModel;
    private String _carNumber;
    private String _problemType;
    private String _date;
    private String _time;
    private String _status;

    public Order(){}

    public Order(int userid, String carModel, String carNumber, String problemtype, String date, String time, String status) {
        _userid = userid;
        _carModel = carModel;
        _carNumber = carNumber;
        _problemType = problemtype;
        _date = date;
        _time = time;
        _status = status;
    }

    public Order(int id, int userid, String carModel, String carNumber, String problemtype, String date, String time, String status) {
        _id = id;
        _userid = userid;
        _carModel = carModel;
        _carNumber = carNumber;
        _problemType = problemtype;
        _date = date;
        _time = time;
        _status = status;
    }

    // Геттеры
    public int getId() {
        return _id;
    }

    public int getUserId() {
        return _userid;
    }

    public String getCarModel() {
        return _carModel;
    }

    public String getCarNumber() {
        return _carNumber;
    }

    public String getProblemType() {
        return _problemType;
    }

    public String getDate() {
        return _date;
    }

    public String getTime() {
        return _time;
    }

    public String getStatus() {
        return _status;
    }

    // Сеттеры
    public void setId(int id) {
        _id = id;
    }

    public void setUserId(int userid) {
        _userid = userid;
    }

    public void setCarModel(String carModel) {
        _carModel = carModel;
    }

    public void setCarNumber(String carNumber) {
        _carNumber = carNumber;
    }

    public void setProblemType(String problemtype) {
        _problemType = problemtype;
    }

    public void setDate(String date) {
        _date = date;
    }

    public void setTime(String time) {
        _time = time;
    }

    public void setStatus(String status) {
        _status = status;
    }
}
