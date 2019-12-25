package rs.ac.uns.ftn.authentication_service.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

	private String username;
	private List<String> payments;
}
