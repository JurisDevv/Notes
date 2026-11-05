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
import javax.servlet.http.HttpSession;
import at.favre.lib.crypto.bcrypt.BCrypt;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {


        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            System.out.println("Login attempt: " + username);

            if (username == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String sql = "SELECT id, password_hash FROM users WHERE username = ?";

            try (Connection conn = Database.getConn();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        String passwordFromDb = rs.getString("password_hash");

                        System.out.println("User found in database");

                        boolean valid = BCrypt.verifyer()
                                .verify(password.toCharArray(), passwordFromDb)
                                .verified;

                        System.out.println("Password valid: " + valid);

                        if (valid) {
                            int userId = rs.getInt("id");

                            HttpSession session = req.getSession();
                            session.setAttribute("userId", userId);

                            resp.setStatus(HttpServletResponse.SC_OK);
                            resp.getWriter().write("success");
                        } else {
                            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            resp.getWriter().write("Invalid username or password");
                        }
                    } else {
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.getWriter().write("Invalid username or password");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}