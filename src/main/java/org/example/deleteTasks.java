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

@WebServlet("/deleteTasks")
public class deleteTasks extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException{
        int id = Integer.parseInt(req.getParameter("id"));

        TaskRepository repository = new TaskRepository();
        repository.deleteTask(id);
    }
}
