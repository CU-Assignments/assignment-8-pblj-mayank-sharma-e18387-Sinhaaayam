package com.example.webapp;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (USERNAME.equals(enteredUsername) && PASSWORD.equals(enteredPassword)) {
            out.println("<h2>Welcome, " + enteredUsername + "!</h2>");
        } else {
            out.println("<h2>Login Failed. Invalid username or password.</h2>");
        }
    }
}
