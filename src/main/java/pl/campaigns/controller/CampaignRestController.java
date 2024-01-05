package pl.campaigns.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<Campaign> addCampaign(@Valid @RequestBody Campaign campaign) {
        Campaign savedCampaign = campaignService.addCampaign(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @Valid @RequestBody Campaign campaignDetails) {
        Campaign updatedCampaign = campaignService.editCampaign(id, campaignDetails);
        return ResponseEntity.ok(updatedCampaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Campaign> deleteCampaign (@PathVariable Long id) {
        Campaign deletedCampaign = campaignService.deleteCampaignById(id);
        return ResponseEntity.ok(deletedCampaign);
    }
}
