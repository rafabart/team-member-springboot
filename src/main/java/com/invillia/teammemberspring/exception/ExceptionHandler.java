package com.invillia.teammemberspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author s2it_pandrade
 * @version : $<br/>
 * : $
 * @since 24/10/19 16:54
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ActionNotPermitedException.class)
    public void handleNotFound(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
