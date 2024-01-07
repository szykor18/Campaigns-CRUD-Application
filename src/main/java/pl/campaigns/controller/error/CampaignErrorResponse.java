package pl.campaigns.controller.error;

import org.springframework.http.HttpStatus;

public record CampaignErrorResponse(String message, HttpStatus status) {
}
