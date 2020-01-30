package rs.ac.uns.ftn.paypal_service.dto.response;

import java.util.List;

import lombok.Data;
import rs.ac.uns.ftn.paypal_service.model.SubscriptionPlan;

@Data
public class SubscriptionPlanResponse {
	
	List<SubscriptionPlan> plans ;

}
