package com.example.NeowProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND("JSON file not found", HttpStatus.NOT_FOUND),
    RELIC_NOT_FOUND("Relic not found", HttpStatus.NOT_FOUND),
    GAME_NOT_FOUND("Game not found", HttpStatus.NOT_FOUND),
    CARD_NOT_FOUND("Card not found", HttpStatus.NOT_FOUND),

    FILE_SAVE_FAILED("Failed to save JSON file", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_READ_FAILED("Failed to read JSON file", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_DELETE_FAILED("Failed to delete JSON file", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
