<%@ page import="java.sql.*" %>
<%@ page import="com.example.webapp.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Attendance</title>
</head>
<body>
    <h2>Enter Attendance</h2>
    <form action="SaveAttendance" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Student Name</th>
                <th>Status</th>
            </tr>
            <%
                Connection conn = null;
                try {
                    conn = DBConnection.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                    while (rs.next()) {
                        int studentId = rs.getInt("id");
                        String name = rs.getString("name");
            %>
            <tr>
                <td><%= name %></td>
                <td>
                    <input type="hidden" name="studentIds" value="<%= studentId %>">
                    <select name="status_<%= studentId %>">
                        <option value="Present">Present</option>
                        <option value="Absent">Absent</option>
                    </select>
                </td>
            </tr>
            <% 
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) conn.close();
                }
            %>
        </table><br>
        <input type="submit" value="Submit Attendance">
    </form>
</body>
</html>
