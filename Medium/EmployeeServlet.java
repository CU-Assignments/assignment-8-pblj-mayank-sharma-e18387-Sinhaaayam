package com.example.webapp;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            if (idParam != null && !idParam.isEmpty()) {
                // Search by ID
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
                ps.setInt(1, Integer.parseInt(idParam));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                    ));
                }
            } else {
                // List all employees
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

                while (rs.next()) {
                    employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // HTML Output
        out.println("<html><body>");
        out.println("<h2>Employee List</h2>");
        out.println("<form method='get' action='employees'>");
        out.println("Search by ID: <input type='text' name='id'/> <input type='submit' value='Search'/>");
        out.println("</form><br>");

        if (employees.isEmpty()) {
            out.println("<p>No employee found.</p>");
        } else {
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>");
            for (Employee emp : employees) {
                out.println("<tr>");
                out.println("<td>" + emp.getId() + "</td>");
                out.println("<td>" + emp.getName() + "</td>");
                out.println("<td>" + emp.getDepartment() + "</td>");
                out.println("<td>" + emp.getSalary() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</body></html>");
    }
}
