package rs.ac.uns.ftn.bank.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bank.model.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByMerchantIdAndMerchantPassword(String merchantId, String merchantPassword);
}
