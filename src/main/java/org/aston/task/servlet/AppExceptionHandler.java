package org.aston.task.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.exceptions.NotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AppExceptionHandler")
public class AppExceptionHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request
                .getAttribute("jakarta.servlet.error.exception");

        response.setContentType("text/html");
        PrintWriter responseWriter = response.getWriter();

        if (throwable.getClass() == NotFoundException.class) {
            responseWriter.write(throwable.getMessage());
            response.setStatus(404);
        } else if (throwable.getClass() == BadRequestException.class) {
            responseWriter.write(throwable.getMessage());
            response.setStatus(400);
        }

        responseWriter.close();
    }
}
