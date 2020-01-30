package rs.ac.uns.ftn.paypal_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.paypal_service.model.SubscriptionPlan;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long>{
	List<SubscriptionPlan> findByUsername(String username);
	
}
