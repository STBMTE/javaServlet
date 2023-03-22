package org.example.repository;

import org.example.model.UserModel;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, UserModel> users = new HashMap<>();

    public UserModel getUserByLogin(String login) {
        return users.get(login);
    }

    public boolean addUser(UserModel user) {
        String login = user.getLogin();
        if (users.containsKey(login)) {
            return false;
        } else {
            users.put(login, user);
            return true;
        }
    }

    public UserModel getUserFromCookie(Cookie[] cookies) {
        UserModel user = null;
        String login = null;
        String email = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    login = cookie.getValue();
                } else if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
            if (login != null && email != null && password != null) {
                user = new UserModel(login, password, email);
            }
        }
        return user;
    }
}
