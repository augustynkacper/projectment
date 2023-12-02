package com.projectment.rest.body.request;

public record LoginRequest(
        String email,
        String password
) {
}