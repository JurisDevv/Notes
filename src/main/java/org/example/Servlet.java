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

        // 1. Проверка авторизации
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 2. Получение userId из сессии
        int userId = (int) session.getAttribute("userId");

        // 3. Получение параметров запроса
        String name = request.getParameter("name");
        String text = request.getParameter("text");
        String priorityParam = request.getParameter("priority");
        String statusParam = request.getParameter("status");

        // 4. Проверка названия задачи
        if (name == null || name.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 5. Проверка числовых параметров
        int priority;
        int status;

        try {
            priority = Integer.parseInt(priorityParam);
            status = Integer.parseInt(statusParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 6. Проверка допустимых значений
        if (priority < 1 || priority > 3 || status < 1 || status > 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 7. Добавление задачи
        TaskRepository taskRepository = new TaskRepository();

        int taskId = taskRepository.addTask(
                name, text, priority, status, userId
        );

        // 8. Проверка результата
        if (taskId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // 9. Возвращение ID созданной задачи
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().print(taskId);
    }

