package sample;

public class Car {
    private int id;
    private int userId;
    private String carModel;
    private String carNumber;

    public Car(){}

    public Car(int userId,String carModel, String carNumber){
        this.userId = userId;
        this.carModel = carModel;
        this.carNumber = carNumber;
    }
    public Car(int id, int userId, String carModel, String carNumber) {
        this.id = id;
        this.userId = userId;
        this.carModel = carModel;
        this.carNumber = carNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
