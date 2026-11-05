package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.favre.lib.crypto.bcrypt.BCrypt;


@WebServlet("/regServlet")
public class regServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

        try{
            Connection conn = Database.getConn();

            String name = req.getParameter("username");
            String password = req.getParameter("password");
            String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

            System.out.println("Registration started");
            System.out.println("Username: " + name);

            boolean validUsername = name != null &&
                    name.matches("[a-zA-Z0-9_]{3,20}");

            boolean validPassword = password != null &&
                    password.matches("[a-zA-Z0-9]{8,}");

            System.out.println("Username valid: " + validUsername);
            System.out.println("Password valid: " + validPassword);

            if (!validUsername) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Username must contain 3-20 English letters, numbers or _");
                return;
            }

            if (!validPassword) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Password must contain at least 8 characters");
                return;
            }

            String passwordHash = BCrypt.withDefaults()
                    .hashToString(12, password.toCharArray());

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, passwordHash);

            System.out.println("Executing INSERT...");

            int rows = ps.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Registration successful");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }

    }

}
