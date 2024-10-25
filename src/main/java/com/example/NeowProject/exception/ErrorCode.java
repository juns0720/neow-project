package com.example.NeowProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),

    FILE_SAVE_FAILED("Failed to save JSON file", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
