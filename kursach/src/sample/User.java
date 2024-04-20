package sample;

public class User {
    private int _id;
    private String _login;
    private String _password;
    private String _name;
    private String _surname;
    private String _role;

    public User(){}

    public User(int id, String login, String password, String name, String surname, String role){
        _id = id;
        _login = login;
        _password = password;
        _name = name;
        _surname = surname;
        _role = role;
    }

    // Геттеры

    public int getId(){
        return _id;
    }

    public String getLogin() {
        return _login;
    }

    public String getPassword() {
        return _password;
    }

    public String getName() {
        if(_name == null) return "";
        return _name;
    }

    public String getSurname() {
        if(_surname == null) return "";
        return _surname;
    }

    public String getRole(){
        return _role;
    }

    public String getFullName(){
        if(!_name.equals("") && !_surname.equals("")){
            return _surname + " " + _name;
        } else return _login;
    }
    // Сеттеры

    public void setId(int id){
        _id = id;
    }

    public void setLogin(String login) {
        //if (login == null){
        //    _login = "123";
        //} else {
        _login = login;
        //}
    }

    public void setPassword(String password) {
        _password = password;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setSurname(String surname) {
        _surname = surname;
    }

    public void setRole(String role){
        _role = role;
    }
}
