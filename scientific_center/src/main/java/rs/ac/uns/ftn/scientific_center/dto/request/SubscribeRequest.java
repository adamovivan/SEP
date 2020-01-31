package rs.ac.uns.ftn.scientific_center.dto.request;

import lombok.Data;

@Data
public class SubscribeRequest {

	private Long magazineId;
	private String magazineIssn;
	
}
