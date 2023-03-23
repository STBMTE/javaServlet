package org.example.repository;

import org.example.model.UserModel;

import javax.servlet.http.Cookie;
import java.sql.*;

public class UserRepositorySql {
    private Connection connectionNow;

    public boolean addUser(UserModel user) {
        try {
            PreparedStatement st = getConnection().prepareStatement(
                    "INSERT INTO users (login, password, email) VALUES (?, ?, ?)");
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public UserModel getUserByLogin(String login) {
        try {
            PreparedStatement st = getConnection().prepareStatement("SELECT login, password, email FROM users WHERE login = ?");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new UserModel(
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"));
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public UserModel getUserFromCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    return this.getUserByLogin(cookie.getValue());
                }
            }
        }
        return null;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connectionNow != null && !connectionNow.isClosed()) {
            return connectionNow;
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        connectionNow = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", "12345");
        return connectionNow;
    }
}
