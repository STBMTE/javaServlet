package org.example.repository;

import org.example.HibernateConfig;
import org.example.model.UserModel;
import org.hibernate.Session;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements UserReposit {
    private final Map<String, UserModel> users = new HashMap<>();

    public boolean addUser(UserModel user) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public UserModel getUserByLogin(String login) {
        UserModel user = null;
        try (Session session = HibernateConfig.getSession()) {
            user = session.byNaturalId(UserModel.class).using("login", login).load();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return user;
        }
        return user;
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
}
