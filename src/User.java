package Model;

import java.util.ArrayList;

public class User {
    private String lastName;
    private String firstName;
    private String login;
    private int key;
    private String gender;
    private String pwd;

    public User() {

    }

    public User(String lastName, String firstName, String login, int key, String gender, String pwd) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.key = key;
        this.gender = gender;
        this.pwd = pwd;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String toString() {
        return "User{" + "lastName=" + lastName + ", firstName=" + firstName + ", login=" + login + ", key=" + key + ", gender=" + gender + ", pwd=" + pwd + "}";
    }
}
