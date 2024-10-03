package io.hhplus.sa.application.registration;

public class RegistrationCommand {
    public record Register (
            Long lectureId,
            Long lectureItemId,
            Long userId
    ) {}

    public record History (
            long userId
    ) {}
}
