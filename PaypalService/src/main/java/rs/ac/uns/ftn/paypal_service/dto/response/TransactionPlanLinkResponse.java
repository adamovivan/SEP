package rs.ac.uns.ftn.paypal_service.dto.response;

import lombok.Data;

@Data
public class TransactionPlanLinkResponse {
	private Boolean success;
	private String url;

}