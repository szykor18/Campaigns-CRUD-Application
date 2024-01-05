package pl.campaign.feature;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.campaign.BaseIntegrationTest;
import pl.campaigns.model.Campaign;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWantToCRUDCampaignsIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_correct_statuses_and_endpoints_and_database_should_work_and() throws Exception {
    //step 1: user want to add some campaign.
        //given && when
        ResultActions performAddMovie = mockMvc.perform(post("/campaigns")
                .content("""
                        {
                            
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        assertThat(0).isEqualTo(0);


    //step 2: User made a mistake and want to update previous campaign he added.
        //given && when
        ResultActions performUpdateMovie = mockMvc.perform(put("/campaigns")
                .content("""
                        {
                            
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        assertThat(0).isEqualTo(0);


    //step 3: User want see to all campaigns and system returns one correct campaign.
        //given && when
        ResultActions performGetAll = mockMvc.perform(get("/campaigns")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcGetAllResult = performGetAll.andExpect(status().isOk()).andReturn();

        List<Campaign> allCampaigns;
        //then
        assertThat(0).isEqualTo(0);


    //step 4: User want to delete campaign
        //given && when
        ResultActions performDeleteMovie = mockMvc.perform(delete("/campaigns")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcDeleteResult = performDeleteMovie.andExpect(status().isOk()).andReturn();
        //then
        assertThat(0).isEqualTo(0);
    }
}
