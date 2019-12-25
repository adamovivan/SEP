package rs.ac.uns.ftn.bank_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bank_service.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
        Merchant findByUsername(String username);
}
