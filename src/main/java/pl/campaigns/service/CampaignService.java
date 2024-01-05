package pl.campaigns.service;

import lombok.AllArgsConstructor;
import pl.campaigns.model.Campaign;
import pl.campaigns.repository.CampaignRepository;
import java.util.List;

@AllArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public Campaign addCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public List<Campaign> findAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign findCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
    }

    public Campaign deleteCampaign(Campaign campaign) {
        campaignRepository.delete(campaign);
        return campaign;
    }

    public Campaign editCampaign(Long id, Campaign campaign) {
        Campaign existingCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        Campaign updatedCampaign = updateExistingCampaignToNewOne(existingCampaign, campaign);
        return updatedCampaign;
    }

    private Campaign updateExistingCampaignToNewOne(Campaign existingCampaign, Campaign campaign) {
        existingCampaign.setCampaignName(campaign.getCampaignName());
        existingCampaign.setCampaignFund(campaign.getCampaignFund());
        existingCampaign.setKeywords(campaign.getKeywords());
        existingCampaign.setTown(campaign.getTown());
        existingCampaign.setStatus(campaign.getStatus());
        existingCampaign.setBidAmount(campaign.getBidAmount());
        existingCampaign.setRadius(campaign.getRadius());
        return existingCampaign;
    }
}
