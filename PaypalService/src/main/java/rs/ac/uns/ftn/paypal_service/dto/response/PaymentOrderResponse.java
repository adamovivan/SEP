package rs.ac.uns.ftn.paypal_service.dto.response;

import lombok.Data;

@Data
public class PaymentOrderResponse {
    private Boolean success;
    private String url;
    
    
    
	public PaymentOrderResponse(Boolean success, String url) {
		super();
		this.success = success;
		this.url = url;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
