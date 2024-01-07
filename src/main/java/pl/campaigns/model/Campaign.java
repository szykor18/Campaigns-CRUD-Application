package pl.campaigns.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "{campaignName.not.null}")
    private String campaignName;
    @NotNull(message = "{keywords.not.null}")
    private String keywords;
    @NotNull(message = "{bidAmount.not.null}")
    @Min(value = 1, message = "{bidAmount.min} {value}")
    private BigDecimal bidAmount;
    @NotNull(message = "{campaignFund.not.null}")
    private BigDecimal campaignFund;
    @NotNull(message = "{status.not.null}")
    private CampaignStatus status;
    private String town;
    @NotNull(message = "{radius.not.null}")
    private double radius;

}
