package org.example.repository;

import org.example.model.UserModel;

import javax.servlet.http.Cookie;

public interface UserReposit {
    boolean addUser(UserModel user);

    UserModel getUserByLogin(String login);

    UserModel getUserFromCookie(Cookie[] cookies);
}
