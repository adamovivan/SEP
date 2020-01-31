package rs.ac.uns.ftn.authentication_service.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreementUrlRequest {
	String username;
	String planID;
	String orderId;
	String callbackUrl;
}
