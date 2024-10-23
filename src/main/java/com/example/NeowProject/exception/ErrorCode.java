package com.example.NeowProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
