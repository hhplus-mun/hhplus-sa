package io.hhplus.sa.domain.exception;

public class ActiveUserException extends RuntimeException {
    public ActiveUserException(String message) {
        super(message);
    }
}
