package pl.campaigns.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String campaignName;
    @NotNull
    private String keywords;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal bidAmount;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal campaignFund;
    @NotNull
    private CampaignStatus status;
    private String town;
    @NotNull
    private double radius;
}
