package org.aston.task.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {

    }

    public BadRequestException(String s) {
        super(s);
    }
}
