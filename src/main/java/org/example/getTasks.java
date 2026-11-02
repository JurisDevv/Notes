package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/getTasks")
public class getTasks extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int userId = (int) session.getAttribute("userId");
        StringBuilder result = new StringBuilder();

        try {
            Connection conn = Database.getConn();

            String sql = """
                SELECT id, task, text, priority, status
                FROM "NApp"
                WHERE user_id = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String task = rs.getString("task");
                String text = rs.getString("text");
                int priority = rs.getInt("priority");
                int status = rs.getInt("status");

                result.append(id).append("|")
                        .append(task).append("|")
                        .append(text).append("|")
                        .append(priority).append("|")
                        .append(status).append("\n");
            }

            resp.setContentType("text/plain; charset=UTF-8");
            resp.getWriter().write(result.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
