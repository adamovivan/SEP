package rs.ac.uns.ftn.paypal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;

@Repository
public interface PaymentRepository extends JpaRepository<PaypalPayment, Long> {
    PaypalPayment findByUsername(String username);
}
