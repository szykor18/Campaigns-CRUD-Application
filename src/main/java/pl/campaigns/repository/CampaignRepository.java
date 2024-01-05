package pl.campaigns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.campaigns.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
