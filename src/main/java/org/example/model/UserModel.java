package org.example.model;

public class UserModel {
    private final String login;
    private final String password;
    private final String email;

    public UserModel(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
