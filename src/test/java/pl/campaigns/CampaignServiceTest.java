package pl.campaigns;

import org.junit.jupiter.api.Assertions;
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
        Campaign campaign = new Campaign(1L, "name", "1",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "1", 3.4);
        //when
        campaignService.addCampaign(campaign);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        assertThat(campaigns).containsExactly(new Campaign(1L, "name", "1",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "1", 3.4));
    }
    @Test
    public void should_find_all_campaigns_after_user_added_two_campaigns() {
        //given
        Campaign campaign1 = new Campaign(1L, "name1", "1",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "1", 3.4);
        Campaign campaign2 = new Campaign(2L, "name2", "2",
                new BigDecimal(2), new BigDecimal(3),
                CampaignStatus.OFF, "2", 3.5);
        //when
        campaignService.addCampaign(campaign1);
        campaignService.addCampaign(campaign2);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        Assertions.assertAll(
                () -> assertThat(campaigns).hasSize(2),
                () -> assertThat(campaigns).containsExactlyInAnyOrder(
                        new Campaign(1L, "name1", "1",
                                new BigDecimal(1), new BigDecimal(2),
                                CampaignStatus.ON, "1", 3.4),
                        new Campaign(2L, "name2", "2",
                                new BigDecimal(2), new BigDecimal(3),
                                CampaignStatus.OFF, "2", 3.5))
        );
    }
    @Test
    public void should_return_deleted_campaign_and_return_empty_list_if_user_deleted_campaign() {
        //given
        Campaign campaign = new Campaign(1L, "name1", "1",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "1", 3.4);
        //when
        campaignService.addCampaign(campaign);
        Campaign deletedCampaign = campaignService.deleteCampaign(campaign);
        //then
        Assertions.assertAll(
                () -> assertThat(campaignService.findAllCampaigns()).hasSize(0),
                () -> assertThat(deletedCampaign).isEqualTo(new Campaign(1L, "name1", "1",
                        new BigDecimal(1), new BigDecimal(2),
                        CampaignStatus.ON, "1", 3.4))

        );
    }

    @Test
    public void should_return_edited_task_when_user_edited_existing_task() {
        //given
        Campaign campaign = new Campaign(1L, "name", "1",
                new BigDecimal(1), new BigDecimal(2),
                CampaignStatus.ON, "1", 3.4);
        Campaign campaignToUpdate = new Campaign(1L, "name2", "2",
                new BigDecimal(8), new BigDecimal(3),
                CampaignStatus.ON, "2", 4.2);
        //when
        campaignService.addCampaign(campaign);
        campaignService.editCampaign(1L, campaignToUpdate);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        Assertions.assertAll(
                () -> assertThat(campaigns).hasSize(1),
                () -> assertThat(campaigns).containsExactlyInAnyOrder(new Campaign(1L, "name2", "2",
                        new BigDecimal(8), new BigDecimal(3),
                        CampaignStatus.ON, "2", 4.2))
        );
    }
}
