package sample;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String role;

    public User(){}

    public User(int id, String login, String password, String name, String surname, String role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    // Геттеры

    public int getId(){
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        if (name == null) return "";
        return name;
    }

    public String getSurname() {
        if (surname == null) return "";
        return surname;
    }

    public String getRole(){
        return role;
    }

    public String getFullName(){
        if (!name.equals("") && !surname.equals("")){
            return surname + " " + name;
        } else return login;
    }

    // Сеттеры

    public void setId(int id){
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRole(String role){
        this.role = role;
    }
}
