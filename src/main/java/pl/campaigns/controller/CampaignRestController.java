package pl.campaigns.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.campaigns.model.Campaign;
import pl.campaigns.service.CampaignService;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
public class CampaignRestController {

    private final CampaignService campaignService;
    @GetMapping
    public ResponseEntity<List<Campaign>> findAllCampaigns() {
        List<Campaign> campaigns = campaignService.findAllCampaigns();
        return ResponseEntity.ok(campaigns);
    }

    @PostMapping
    public ResponseEntity<Campaign> addCampaign(@RequestBody Campaign campaign) {
        Campaign savedCampaign = campaignService.addCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
    }

    @PutMapping
    public ResponseEntity<Campaign> updateCampaign(@RequestBody Campaign campaign) {
        Campaign updatedCampaign = campaignService.editCampaign(campaign.getId(), campaign);
        return ResponseEntity.ok(updatedCampaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Campaign> deleteCampaign (@PathVariable Long id) {
        Campaign deletedCampaign = campaignService.deleteCampaignById(id);
        return ResponseEntity.ok(deletedCampaign);
    }
}
