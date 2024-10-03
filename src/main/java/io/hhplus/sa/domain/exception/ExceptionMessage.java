package io.hhplus.sa.domain.exception;

public enum ExceptionMessage {
    ENTITY_IS_NULL("entity is null");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
