package pl.campaigns.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.campaigns.model.Campaign;
import pl.campaigns.service.CampaignService;

@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
public class CampaignRestController {

    private final CampaignService campaignService;
    @GetMapping
    public ResponseEntity<Campaign> findAllCampaigns() {

    }

    @PostMapping
    public ResponseEntity<Campaign> addCampaign(@RequestBody Campaign campaign) {

    }

    @PutMapping
    public ResponseEntity<Campaign> updateCampaign(@RequestBody Campaign campaign) {

    }

    @PatchMapping
    public ResponseEntity<Campaign> updatePartlyCampaign(@RequestBody Campaign campaign) {

    }

    @DeleteMapping
    public ResponseEntity<Campaign> deleteCampaign (@RequestBody Campaign campaign) {

    }


}
