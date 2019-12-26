package com.bitcoin.bitcoin.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.bitcoin.bitcoin.dto.PaymentDto;
import com.bitcoin.bitcoin.dto.PaymentResponseDto;
import com.bitcoin.bitcoin.dto.ResponseUrlDto;
import com.bitcoin.bitcoin.exception.BadRequestException;
import com.bitcoin.bitcoin.exception.BitcoinUserNotExistException;
import com.bitcoin.bitcoin.exception.OrderNotExistException;
import com.bitcoin.bitcoin.model.Merchant;
import com.bitcoin.bitcoin.model.Currency;
import com.bitcoin.bitcoin.model.NotificationState;
import com.bitcoin.bitcoin.model.Order;
import com.bitcoin.bitcoin.model.PaymentState;
import com.bitcoin.bitcoin.repository.OrderRepository;
import com.bitcoin.bitcoin.repository.UserBitcoinRepository;

@Service
public class BitcoinServiceImpl implements BitcoinService{

	
	@Autowired
	private UserBitcoinRepository userRepository; 
	@Autowired
	private OrderRepository orderRepository;
	
	private String address = "http://localhost:";
	
	
	@Override
	public ResponseUrlDto pay(PaymentDto pdt, String username) {
		// TODO Auto-generated method stub
		Merchant u = this.userRepository.findByUsername(username).orElseThrow(() -> new BitcoinUserNotExistException("User with that username does not exist!"));
		
		
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + u.getToken());
		System.out.println(u.getToken());
		
		String randomToken = UUID.randomUUID().toString();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("order_id", "nc-1");
		map.add("price_amount",  Double.toString(pdt.getTotalPrice()));
		map.add("price_currency", Currency.USD.toString());
		map.add("receive_currency", Currency.USD.toString());
		map.add("cancel_url", this.address + "4202/cancel/" + randomToken);
		map.add("success_url", this.address + "4202/success/" + randomToken);
		map.add("token", randomToken);
		
		
		HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<MultiValueMap<String,String>>(map, headers);
		ResponseUrlDto returnResponse = new ResponseUrlDto();
		try {
			ResponseEntity<PaymentResponseDto> response = rest.postForEntity("https://api-sandbox.coingate.com/v2/orders", request, PaymentResponseDto.class);
			System.out.println("obratio se api-u");
			Order o = convertToOrder(response.getBody(), username, "", randomToken);
			this.orderRepository.save(o);
			returnResponse.setUrl(response.getBody().getPayment_url());
			returnResponse.setSuccess(true);
		}catch(HttpStatusCodeException e) {
			System.out.println("ERROR WHILE CALLING APPLICATION!");
			System.out.println(e.getMessage());
			returnResponse.setUrl("");
			returnResponse.setSuccess(false);
		}
		
		
		
		return returnResponse;
	}


	private Order convertToOrder(PaymentResponseDto body, String username, String callbackUrl, String randomToken) {
		// TODO Auto-generated method stub
		return new Order(body.getId().toString(), username, new Date(), null, Double.parseDouble(body.getPrice_amount()), Currency.valueOf(body.getPrice_currency()),
				PaymentState.NEW, NotificationState.NOT_NOTIFIED, callbackUrl, randomToken);
	}


	@Override
	public void saveMerchant(Merchant m) {
		// TODO Auto-generated method stub
		Merchant find = this.userRepository.findByUsername(m.getUsername()).orElse(null);
		
		if (find != null) {
			if (find.getToken().equals("")) {
				find.setToken("");
			}else {
				find.setToken(m.getToken());
			}
		}else {
			if(m.getToken().equals("")){
				throw new BadRequestException("Some of the input fields can`t be empty!");
			}
			find = new Merchant();
			find.setUsername(m.getUsername());
			find.setToken(m.getToken());
		}
		this.userRepository.save(find);
	}


	@Override
	public String completePayment(String token) {
		// TODO Auto-generated method stub
		Order order = this.orderRepository.findByRandomToken(token).orElseThrow(() -> new OrderNotExistException("Order with that token does not exist!"));
		
		if (order.getState() == PaymentState.PAID || order.getState() == PaymentState.CANCELED) {
			throw new BadRequestException("Payment is already processed!");
		}
		
		Merchant m = this.userRepository.findByUsername(order.getUsername()).orElseThrow(()-> new BitcoinUserNotExistException("Merchant with that username does not exist!"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + m.getToken());
		RestTemplate rest = new RestTemplate();
		
		try {
			ResponseEntity<PaymentResponseDto> response = rest.exchange("https://api-sandbox.coingate.com/v2/orders/"+order.getPaymentId(), HttpMethod.GET,
					new HttpEntity<Object>(headers), PaymentResponseDto.class);
			
			if(response.getBody().getStatus().equals("paid")) {
				order.setState(PaymentState.PAID);
				order.setUpdateTime(new Date());
				this.orderRepository.save(order);
			}
		}catch(HttpStatusCodeException e)
		{
			System.out.println("Error while checking bitcoin order!");
		}
		return this.address + "4202/bitcoin/details" + order.getId();
	}


	@Override
	public String cancelPayment(String token) {
		// TODO Auto-generated method stub
Order order = this.orderRepository.findByRandomToken(token).orElseThrow(() -> new OrderNotExistException("Order with that token does not exist!"));
		
		if (order.getState() == PaymentState.PAID || order.getState() == PaymentState.CANCELED) {
			throw new BadRequestException("Payment is already processed!");
		}
		
		Merchant m = this.userRepository.findByUsername(order.getUsername()).orElseThrow(()-> new BitcoinUserNotExistException("Merchant with that username does not exist!"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + m.getToken());
		RestTemplate rest = new RestTemplate();
		
		try {
			ResponseEntity<PaymentResponseDto> response = rest.exchange("https://api-sandbox.coingate.com/v2/orders/"+order.getPaymentId(), HttpMethod.GET,
					new HttpEntity<Object>(headers), PaymentResponseDto.class);
			
			if(!response.getBody().getStatus().equals("paid")) {
				order.setState(PaymentState.CANCELED);
				order.setUpdateTime(new Date());
				this.orderRepository.save(order);
			}
		}catch(HttpStatusCodeException e)
		{
			System.out.println("Error while checking bitcoin order!");
		}
		return this.address + "4202/bitcoin/details" + order.getId();
	}
	
	
	
	

}
