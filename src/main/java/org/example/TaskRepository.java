package org.example;

import java.sql.*;

public class TaskRepository {

    public int addTask(String task, String text, int priority,
                       int status, int userId) {

        String sql = """
        INSERT INTO "NApp" (task, text, priority, status, user_id)
        VALUES (?, ?, ?, ?, ?)
        RETURNING id
        """;

        try (Connection conn = Database.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, task);
            ps.setString(2, text);
            ps.setInt(3, priority);
            ps.setInt(4, status);
            ps.setInt(5, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void getTasks(int userId) {
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
                String task = rs.getString("task");
                String text = rs.getString("text");
                int priority = rs.getInt("priority");
                int status = rs.getInt("status");

                System.out.println(task);
                System.out.println(text);
                System.out.println(priority);
                System.out.println(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id, int userId) {
        try{
            Connection conn = Database.getConn();

            String sql = "DELETE FROM \"NApp\"\n" +
                    "WHERE id = ? AND user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2,userId );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(int id, int status) {
        try{
            Connection conn = Database.getConn();

            String sql = "UPDATE \"NApp\" SET status = ? WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


}
