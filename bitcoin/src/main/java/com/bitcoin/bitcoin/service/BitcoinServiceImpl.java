package com.bitcoin.bitcoin.service;

import java.util.Date;
import java.util.UUID;

import com.bitcoin.bitcoin.dto.*;
import com.bitcoin.bitcoin.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bitcoin.bitcoin.exception.BadRequestException;
import com.bitcoin.bitcoin.exception.BitcoinUserNotExistException;
import com.bitcoin.bitcoin.exception.OrderNotExistException;
import com.bitcoin.bitcoin.repository.OrderRepository;
import com.bitcoin.bitcoin.repository.UserBitcoinRepository;

@Service
public class BitcoinServiceImpl implements BitcoinService {

	private static final Logger logger = LoggerFactory.getLogger(BitcoinService.class);
	
	@Autowired
	private UserBitcoinRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	private String address = "https://localhost:";

	@Override
	public ResponseUrlDto pay(PaymentDto pdt, String username) {
		// TODO Auto-generated method stub
		Merchant u = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new BitcoinUserNotExistException("User with that username does not exist!"));

		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + u.getToken());
		System.out.println(u.getToken());

		String randomToken = UUID.randomUUID().toString();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("order_id", "nc-1");
		map.add("price_amount", Double.toString(pdt.getTotalPrice()));
		map.add("price_currency", Currency.USD.toString());
		map.add("receive_currency", Currency.USD.toString());
		map.add("cancel_url", this.address + "4202/cancel/" + randomToken);
		map.add("success_url", this.address + "4202/success/" + randomToken);
		map.add("token", randomToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseUrlDto returnResponse = new ResponseUrlDto();
		try {
			ResponseEntity<PaymentResponseDto> response = rest
					.postForEntity("https://api-sandbox.coingate.com/v2/orders", request, PaymentResponseDto.class);
			System.out.println("obratio se api-u");
			Order o = convertToOrder(response.getBody(), username, "", randomToken);
			o.setOrderId(pdt.getOrderId());
			o.setCallbackUrl(pdt.getCallbackUrl());
			this.orderRepository.save(o);
			returnResponse.setUrl(response.getBody().getPayment_url());
			returnResponse.setSuccess(true);
		
		}catch(HttpStatusCodeException e) {
			System.out.println("ERROR WHILE CALLING APPLICATION!");
			System.out.println(e.getMessage());
			returnResponse.setUrl("");
			returnResponse.setSuccess(false);
			logger.error("Redirect url for bitcoin payment to user account " + username + " was"
					+ " not created.");
		}

		return returnResponse;
	}

	private Order convertToOrder(PaymentResponseDto body, String username, String callbackUrl, String randomToken) {
		// TODO Auto-generated method stub
		return new Order(body.getId().toString(), username, new Date(), null,
				Double.parseDouble(body.getPrice_amount()), Currency.valueOf(body.getPrice_currency()),
				TransactionStatus.CREATED, NotificationState.NOT_NOTIFIED, callbackUrl, randomToken);
	}

	@Override
	public void saveMerchant(Merchant m) {
		// TODO Auto-generated method stub
		Merchant find = this.userRepository.findByUsername(m.getUsername()).orElse(null);

		if (find != null) {
			if (find.getToken().equals("")) {
				find.setToken("");
			} else {
				find.setToken(m.getToken());
			}
		} else {
			if (m.getToken().equals("")) {
				throw new BadRequestException("Some of the input fields can`t be empty!");
			}
			find = new Merchant();
			find.setUsername(m.getUsername());
			find.setToken(m.getToken());
		}
		try {
			this.userRepository.save(find);
			logger.info("Merchant id for user " + m.getUsername() + "was succesfully saved.");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Merchant id for user " + m.getUsername() + "was not saved.");
		}
		
	}

	@Override
	public String completePayment(String token) {
		// TODO Auto-generated method stub
		Order order = this.orderRepository.findByRandomToken(token)
				.orElseThrow(() -> new OrderNotExistException("Order with that token does not exist!"));

		OrderResponseDto ordto = new OrderResponseDto();
		System.out.println(order.getState());
		//if (order.getState().equals(PaymentState.PAID) || order.getState() == PaymentState.CANCELED) {
		if (order.getTransactionStatus().equals(TransactionStatus.SUCCESS) || order.getTransactionStatus() == TransactionStatus.FAILED) {
			// throw new BadRequestException("Payment is already processed!");
			System.out.println("paid is it");
			ordto.setMessage("Payment is already processed!");
			ordto.setSuccess(false);
			return ordto.getMessage();
		}

		Merchant m = this.userRepository.findByUsername(order.getUsername())
				.orElseThrow(() -> new BitcoinUserNotExistException("Merchant with that username does not exist!"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + m.getToken());
		RestTemplate rest = new RestTemplate();

		try {
			ResponseEntity<PaymentResponseDto> response = rest.exchange(
					"https://api-sandbox.coingate.com/v2/orders/" + order.getPaymentId(), HttpMethod.GET,
					new HttpEntity<Object>(headers), PaymentResponseDto.class);

			if (response.getBody().getStatus().equals("paid")) {
				//order.setState(PaymentState.PAID);
				order.setTransactionStatus(TransactionStatus.SUCCESS);
				order.setUpdateTime(new Date());
				this.orderRepository.save(order);
			}
			ordto.setMessage("Payment is success processed!");
			ordto.setSuccess(true);
		} catch (HttpStatusCodeException e) {
			System.out.println("Error while checking bitcoin order!");
			ordto.setMessage("Error while checking bitcoin order!");
			ordto.setSuccess(false);

			logger.info("Error while checking bitcoin order!");
		
		}
		logger.info("Successfully created bitcoin order!");
		//obavestimo naucnu centralu
		notify(order);
		return ordto.getMessage();
	}

	private SimpleResponseDTO notify(Order order){
		PaymentNotificationDto paymentNotificationDto = new PaymentNotificationDto(order.getOrderId(), order.getTransactionStatus());

		SimpleResponseDTO simpleResponseDTO = restTemplate.postForObject(order.getCallbackUrl(), paymentNotificationDto, SimpleResponseDTO.class);

		if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
			order.setNotification(NotificationState.NOTIFIED);
			orderRepository.save(order);
		}

		return new SimpleResponseDTO("Success.");
	}

	@Override
	public String cancelPayment(String token) {
		// TODO Auto-generated method stub
		Order order = this.orderRepository.findByRandomToken(token)
				.orElseThrow(() -> new OrderNotExistException("Order with that token does not exist!"));
		OrderResponseDto ordto = new OrderResponseDto();
		//if (order.getState() == PaymentState.PAID || order.getState() == PaymentState.CANCELED) {
		if (order.getTransactionStatus() == TransactionStatus.SUCCESS || order.getTransactionStatus() == TransactionStatus.FAILED) {
			// throw new BadRequestException("Payment is already processed!");
			ordto.setMessage("Payment is already processed!");
			ordto.setSuccess(false);
			return ordto.getMessage();
		}

		Merchant m = this.userRepository.findByUsername(order.getUsername())
				.orElseThrow(() -> new BitcoinUserNotExistException("Merchant with that username does not exist!"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Token " + m.getToken());
		RestTemplate rest = new RestTemplate();

		try {
			ResponseEntity<PaymentResponseDto> response = rest.exchange(
					"https://api-sandbox.coingate.com/v2/orders/" + order.getPaymentId(), HttpMethod.GET,
					new HttpEntity<Object>(headers), PaymentResponseDto.class);

			if (!response.getBody().getStatus().equals("paid")) {
				//order.setState(PaymentState.CANCELED);
				order.setTransactionStatus(TransactionStatus.FAILED);
				order.setUpdateTime(new Date());
				this.orderRepository.save(order);
				ordto.setMessage("Payment is success processed!");
				ordto.setSuccess(true);
			}
		} catch (HttpStatusCodeException e) {
			System.out.println("Error while checking bitcoin order!");
			ordto.setMessage("Error while checking bitcoin order!");
			ordto.setSuccess(false);

			logger.info("Error while checking bitcoin order!");
		}
		logger.info("Successfully canceled bitcoin order!");
		notify(order);
		return ordto.getMessage();

	}

}
