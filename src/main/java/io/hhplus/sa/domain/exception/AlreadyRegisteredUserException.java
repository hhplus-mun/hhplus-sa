package io.hhplus.sa.domain.exception;

public class AlreadyRegisteredUserException extends RuntimeException {
    public AlreadyRegisteredUserException(String message) {
        super(message);
    }
}