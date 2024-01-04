package pl.campaigns.service;

import lombok.AllArgsConstructor;
import pl.campaigns.model.Campaign;
import pl.campaigns.repository.CampaignRepository;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public Campaign addCampaign(Campaign campaign) {
        return new Campaign();
    }

    public List<Campaign> findAllCampaigns() {
        return Collections.emptyList();
    }

    public Campaign findCampaignById(Long id) {
        return new Campaign();
    }

    public Campaign deleteCampaign(Campaign campaign) {
        return new Campaign();
    }
}
