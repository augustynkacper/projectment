package com.projectment.rest.body.request;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
