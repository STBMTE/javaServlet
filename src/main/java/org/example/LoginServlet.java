package org.example;

import org.example.model.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserModel user = localdatabase.userRepository.getUserFromCookie(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null) {
            UserModel user = localdatabase.userRepository.getUserByLogin(login);
            if (user != null && user.getPassword().equals(password)) {
                resp.addCookie(new Cookie("login", user.getLogin()));
                resp.addCookie(new Cookie("email", user.getEmail()));
                resp.addCookie(new Cookie("password", user.getPassword()));
                resp.sendRedirect("./");
            } else {
                resp.sendRedirect("./login");
            }
        }
    }
}
