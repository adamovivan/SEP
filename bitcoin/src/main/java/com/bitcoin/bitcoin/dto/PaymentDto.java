package com.bitcoin.bitcoin.dto;

import java.io.Serializable;
import java.util.Date;

import com.bitcoin.bitcoin.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
	private String username;
	private String orderId;
	@Positive
	private Double totalPrice;
	private String callbackUrl;
}
