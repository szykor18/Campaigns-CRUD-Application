package pl.campaigns;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.campaigns.model.Campaign;
import pl.campaigns.model.CampaignNotFoundException;
import pl.campaigns.model.CampaignStatus;
import pl.campaigns.service.CampaignService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CampaignServiceTest {
    private final CampaignService campaignService = new CampaignService(new InMemoryCampaignRepositoryTestImpl());
    @Test
    public void should_save_to_database_when_user_add_to_database() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .campaignFund(new BigDecimal(1))
                .bidAmount(new BigDecimal(2))
                .radius(3.4)
                .town("town")
                .status(CampaignStatus.ON)
                .build();
        //when
        campaignService.addCampaign(campaign);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        assertThat(campaigns).containsExactly(Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .campaignFund(new BigDecimal(1))
                .bidAmount(new BigDecimal(2))
                .radius(3.4)
                .town("town")
                .status(CampaignStatus.ON)
                .build());
    }
    @Test
    public void should_find_all_campaigns_after_user_added_two_campaigns() {
        //given
        Campaign campaign1 = Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .campaignFund(new BigDecimal(1))
                .bidAmount(new BigDecimal(2))
                .radius(3.4)
                .town("town")
                .status(CampaignStatus.ON)
                .build();
        Campaign campaign2 = Campaign.builder()
                .id(2L)
                .campaignName("name2")
                .keywords("keywords2")
                .campaignFund(new BigDecimal(2))
                .bidAmount(new BigDecimal(3))
                .radius(4.2)
                .town("town2")
                .status(CampaignStatus.OFF)
                .build();
        //when
        campaignService.addCampaign(campaign1);
        campaignService.addCampaign(campaign2);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        Assertions.assertAll(
                () -> assertThat(campaigns).hasSize(2),
                () -> assertThat(campaigns).containsExactlyInAnyOrder(
                        Campaign.builder()
                                .id(1L)
                                .campaignName("name")
                                .keywords("keywords")
                                .campaignFund(new BigDecimal(1))
                                .bidAmount(new BigDecimal(2))
                                .radius(3.4)
                                .town("town")
                                .status(CampaignStatus.ON)
                                .build(),
                        Campaign.builder()
                                .id(2L)
                                .campaignName("name2")
                                .keywords("keywords2")
                                .campaignFund(new BigDecimal(2))
                                .bidAmount(new BigDecimal(3))
                                .radius(4.2)
                                .town("town2")
                                .status(CampaignStatus.OFF)
                                .build()));
    }
    @Test
    public void should_return_deleted_campaign_and_return_empty_list_if_user_deleted_campaign() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .campaignFund(new BigDecimal(1))
                .bidAmount(new BigDecimal(2))
                .radius(3.4)
                .town("town")
                .status(CampaignStatus.ON)
                .build();
        //when
        campaignService.addCampaign(campaign);
        Campaign deletedCampaign = campaignService.deleteCampaignById(1L);
        //then
        Assertions.assertAll(
                () -> assertThat(campaignService.findAllCampaigns()).hasSize(0),
                () -> assertThat(deletedCampaign).isEqualTo(Campaign.builder()
                        .id(1L)
                        .campaignName("name")
                        .keywords("keywords")
                        .campaignFund(new BigDecimal(1))
                        .bidAmount(new BigDecimal(2))
                        .radius(3.4)
                        .town("town")
                        .status(CampaignStatus.ON)
                        .build()));
    }

    @Test
    public void should_return_edited_task_when_user_edited_existing_task() {
        //given
        Campaign campaign1 = Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .campaignFund(new BigDecimal(1))
                .bidAmount(new BigDecimal(2))
                .radius(3.4)
                .town("town")
                .status(CampaignStatus.ON)
                .build();
        Campaign campaign2 = Campaign.builder()
                .id(1L)
                .campaignName("name2")
                .keywords("keywords2")
                .campaignFund(new BigDecimal(2))
                .bidAmount(new BigDecimal(3))
                .radius(4.2)
                .town("town2")
                .status(CampaignStatus.OFF)
                .build();
        //when
        campaignService.addCampaign(campaign1);
        campaignService.editCampaign(1L, campaign2);
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        //then
        Assertions.assertAll(
                () -> assertThat(campaigns).hasSize(1),
                () -> assertThat(campaigns).containsExactlyInAnyOrder(Campaign.builder()
                        .id(1L)
                        .campaignName("name2")
                        .keywords("keywords2")
                        .campaignFund(new BigDecimal(2))
                        .bidAmount(new BigDecimal(3))
                        .radius(4.2)
                        .town("town2")
                        .status(CampaignStatus.OFF)
                        .build()));
    }

    @Test
    public void should_throw_campaign_not_found_exception_when_campaign_with_given_id_not_exists() {
        //given
        Campaign campaign = Campaign.builder()
                .id(1L)
                .campaignName("name2")
                .keywords("keywords2")
                .campaignFund(new BigDecimal(2))
                .bidAmount(new BigDecimal(3))
                .radius(4.2)
                .town("town2")
                .status(CampaignStatus.OFF)
                .build();
        //when
        Throwable throwable = catchThrowable(() -> campaignService.editCampaign(10L, campaign));
        //then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(CampaignNotFoundException.class)
                .hasMessage("Campaign with id '10' not found");
    }
}
