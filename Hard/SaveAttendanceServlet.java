package com.example.webapp;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/SaveAttendance")
public class SaveAttendanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] studentIds = request.getParameterValues("studentIds");
        Date today = new Date(System.currentTimeMillis());

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)");

            for (String idStr : studentIds) {
                int studentId = Integer.parseInt(idStr);
                String status = request.getParameter("status_" + studentId);

                ps.setInt(1, studentId);
                ps.setDate(2, today);
                ps.setString(3, status);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("attendance.jsp"); // Redirect back to form after saving
    }
}
