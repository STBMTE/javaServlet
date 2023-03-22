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

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (login != null && password != null && email != null) {
            UserModel user = new UserModel(login, password, email);
            if (localdatabase.userRepository.addUser(user)) {
                resp.addCookie(new Cookie("login", user.getLogin()));
                resp.addCookie(new Cookie("email", user.getEmail()));
                resp.addCookie(new Cookie("password", user.getPassword()));
                resp.sendRedirect("./");
            } else {
                resp.sendRedirect("./register");
            }
        }
    }
}
