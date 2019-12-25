package rs.ac.uns.ftn.paypal_service.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity
public class PaypalPayment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(nullable=false,length=100)
	private String paymentId;
	
	@Column(nullable=false,length=100)
	private String paymentSecret;

	//videti sa sa ovim urlovima.. da li ce svaki user imati drugi url? (za sad nulable=true)
	@Column(nullable=true)
	private String successUrl;

	@Column(nullable=true)
	private String cancelUrl;
	
	@Column(nullable=true)
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentSecret() {
		return paymentSecret;
	}

	public void setPaymentSecret(String paymentSecret) {
		this.paymentSecret = paymentSecret;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
