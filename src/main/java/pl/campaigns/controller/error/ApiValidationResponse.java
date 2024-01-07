package pl.campaigns.controller.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationResponse(List<String> messages, HttpStatus status) {
}
