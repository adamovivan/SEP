package rs.ac.uns.ftn.paypal_service.dto.response;

import com.paypal.api.payments.Agreement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteAgreementResponse {

	public Boolean success;
	public Agreement agreement;
}
