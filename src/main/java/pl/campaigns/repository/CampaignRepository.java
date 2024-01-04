package pl.campaigns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.campaigns.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
