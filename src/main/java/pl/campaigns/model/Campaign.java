package pl.campaigns.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Campaign {

    private Long id;
    private String campaignName;
    private String keywords;
    private BigDecimal bidAmount;
    private BigDecimal campaignFund;
    private CampaignStatus status;
    private String town;
    private double radius;
}
