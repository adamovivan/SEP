package rs.ac.uns.ftn.scientific_center.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreementRequest {
	private String type;
	private String email;
	private String orderId;
	private String callbackUrl;
}
