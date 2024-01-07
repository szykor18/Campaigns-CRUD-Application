package pl.campaign.apivalidationerror;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.campaign.BaseIntegrationTest;
import pl.campaigns.controller.error.ApiValidationResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationErrorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_bad_request_and_validation_message_when_null_in_campaign_save_request() throws Exception {
        //given && when
        ResultActions performPostCampaigns = mockMvc.perform(post("/campaigns")
                .content("""
                        {
                           "keywords": "keywords",
                           "bidAmount": 0.5,
                           "campaignFund": 2,
                           "town": "town",
                           "radius": 3.1
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        MvcResult mvcResult = performPostCampaigns.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationResponse response = objectMapper.readValue(json, ApiValidationResponse.class);
        assertThat(response.messages()).containsExactlyInAnyOrder(
                "bid amount must equals or be greater than 1",
                "status must not be null", "campaign name must not be null");
    }
}
