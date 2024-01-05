package pl.campaigns;

import org.junit.jupiter.api.Test;
import pl.campaigns.model.Campaign;
import pl.campaigns.model.CampaignStatus;
import pl.campaigns.service.CampaignService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CampaignServiceTest {
    private final CampaignService campaignService = new CampaignService(new InMemoryCampaignRepositoryTestImpl());
    @Test
    public void should_save_to_database_when_user_add_to_database() {
        //given
        Campaign campaign = new Campaign(1L, "name", "",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "", 3.4);
        //when
        campaignService.addCampaign(campaign);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        assertThat(campaigns).containsExactly(campaign);
    }


}
