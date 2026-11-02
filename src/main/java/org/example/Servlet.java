package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String name = request.getParameter("name");
        String text = request.getParameter("text");
        String priorityParam = request.getParameter("priority");
        String statusParam = request.getParameter("status");

        int priority = "3".equals(priorityParam) ? 3 :
                "2".equals(priorityParam) ? 2 : 1;

        int status = "3".equals(statusParam) ? 3 :
                "2".equals(statusParam) ? 2 : 1;

        TaskRepository taskRepository = new TaskRepository();

        int taskId = taskRepository.addTask(name, text, priority, status, userId);

        response.getWriter().print(taskId);
    }
}
