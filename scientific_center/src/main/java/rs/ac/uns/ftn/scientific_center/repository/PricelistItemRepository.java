package rs.ac.uns.ftn.scientific_center.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;

@Repository
public interface PricelistItemRepository extends JpaRepository<PricelistItem, Long> {
    PricelistItem findByMagazineId(Long magazineId);
}
