package pl.campaigns.dto;

import pl.campaigns.model.CampaignStatus;

import java.math.BigDecimal;

public record CampaignDto(Long id,
                          String campaignName,
                          String keywords,
                          BigDecimal bidAmount,
                          BigDecimal campaignFund,
                          CampaignStatus status,
                          String town,
                          double radius) {
}
