package pl.campaign.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.campaign.BaseIntegrationTest;
import pl.campaigns.model.Campaign;
import pl.campaigns.model.CampaignStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWantToCRUDCampaignsIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_correct_statuses_and_endpoints_and_database_should_work() throws Exception {
    //step 1: user want to add some campaign.
        //given && when
        ResultActions performAddCampaign = mockMvc.perform(post("/campaigns")
                .content("""
                        {
                           "campaignName": "name",
                           "keywords": "keywords",
                           "bidAmount": 1,
                           "campaignFund": 2,
                           "status": "ON",
                           "town": "town",
                           "radius": 3.4
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResultAdd = performAddCampaign.andExpect(status().isCreated()).andReturn();
        String jsonAddedCampaign = mvcResultAdd.getResponse().getContentAsString();
        Campaign addedCampaign = objectMapper.readValue(jsonAddedCampaign, Campaign.class);
        //then
        assertThat(addedCampaign).isEqualTo(Campaign.builder()
                .id(1L)
                .campaignName("name")
                .keywords("keywords")
                .bidAmount(new BigDecimal("1"))
                .campaignFund(new BigDecimal("2"))
                .status(CampaignStatus.ON)
                .town("town")
                .radius(3.4)
                .build());


    //step 2: User made a mistake and want to update previous campaign he added.
        //given && when
        ResultActions performUpdateCampaign = mockMvc.perform(put("/campaigns/1")
                .content("""
                        {
                           "campaignName": "name2",
                           "keywords": "keywords2",
                           "bidAmount": 3,
                           "campaignFund": 4,
                           "status": "OFF",
                           "town": "town2",
                           "radius": 4.2
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResultUpdate = performUpdateCampaign.andExpect(status().isOk()).andReturn();
        String jsonUpdateCampaign = mvcResultUpdate.getResponse().getContentAsString();
        Campaign updatedCampaign = objectMapper.readValue(jsonUpdateCampaign, Campaign.class);
        //then
        assertThat(updatedCampaign).isEqualTo(Campaign.builder()
                .id(1L)
                .campaignName("name2")
                .keywords("keywords2")
                .bidAmount(new BigDecimal("3"))
                .campaignFund(new BigDecimal("4"))
                .status(CampaignStatus.OFF)
                .town("town2")
                .radius(4.2)
                .build());


    //step 3: User want see to all campaigns and system returns one correct campaign.
        //given && when
        ResultActions performGetAll = mockMvc.perform(get("/campaigns")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcGetAllResult = performGetAll.andExpect(status().isOk()).andReturn();
        String jsonAllCampaigns = mvcGetAllResult.getResponse().getContentAsString();
        List<Campaign> allCampaigns = objectMapper.readValue(jsonAllCampaigns, new TypeReference<>() {});
        //then
        assertThat(allCampaigns).hasSize(1);
        assertThat(allCampaigns).containsExactlyInAnyOrder(Campaign.builder()
                .id(1L)
                .campaignName("name2")
                .keywords("keywords2")
                .bidAmount(new BigDecimal("3.00"))
                .campaignFund(new BigDecimal("4.00"))
                .status(CampaignStatus.OFF)
                .town("town2")
                .radius(4.2)
                .build());


    //step 4: User want to delete campaign
        // given && when
        ResultActions performDeleteMovie = mockMvc.perform(delete("/campaigns/1")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcDeleteResult = performDeleteMovie.andExpect(status().isOk()).andReturn();
        String jsonDeleteCampaign = mvcDeleteResult.getResponse().getContentAsString();
        Campaign deletedCampaign = objectMapper.readValue(jsonDeleteCampaign, Campaign.class);
        //then
        assertThat(deletedCampaign).isEqualTo(Campaign.builder()
                .id(1L)
                .campaignName("name2")
                .keywords("keywords2")
                .bidAmount(new BigDecimal("3.00"))
                .campaignFund(new BigDecimal("4.00"))
                .status(CampaignStatus.OFF)
                .town("town2")
                .radius(4.2)
                .build());
    }
}
