package org.aston.task.servlet;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TagServletTest {
    TagServlet tagServlet = new TagServlet();

    Gson gson = new Gson();

    @Test
    void doDeleteUserBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "id");

        Assertions.assertThrows(BadRequestException.class, () -> tagServlet.doDelete(req, resp));
    }
}
