package com.bitcoin.bitcoin.model;

import lombok.Data;

import java.util.Date;

import javax.persistence.*;

@Data
@Entity
@Table(name="order_tab")
public class Order {
	
		@Id
		@GeneratedValue(strategy= GenerationType.AUTO)
		private Long id; 
		
		@Column(nullable=false)
		private String paymentId; 
		
		@Column(nullable=false)
		private String username; 
		
		@Column(nullable=false)
		private Date createTime;

	@Column(nullable=false)
	private String orderId;

	@Column
		private Date updateTime;
		
		@Column(nullable=false)
		private double amount;

	@Enumerated(EnumType.STRING)
		@Column(nullable=false)
		private Currency currency;

	@Enumerated(EnumType.STRING)
		private PaymentState state;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;

	@Enumerated(EnumType.STRING)
		@Column(nullable=false)
		private NotificationState notification; 
		
		@Column(nullable=false)
		private String callbackUrl;
		
		@Column(nullable=false, unique=true)
		private String randomToken;
		
		public Order() {
			
		}

		public Order(String paymentId, String username, Date createTime, Date updateTime, double amount,
				Currency currency, TransactionStatus transactionStatus, NotificationState notification, String callbackUrl,
				String randomToken) {
			this();
			this.paymentId = paymentId;
			this.username = username;
			this.createTime = createTime;
			this.updateTime = updateTime;
			this.amount = amount;
			this.currency = currency;
			this.transactionStatus = transactionStatus;
			this.notification = notification;
			this.callbackUrl = callbackUrl;
			this.randomToken = randomToken;
		}

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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public Currency getCurrency() {
			return currency;
		}

		public void setCurrency(Currency currency) {
			this.currency = currency;
		}

		public PaymentState getState() {
			return state;
		}

		public void setState(PaymentState state) {
			this.state = state;
		}

		public NotificationState getNotification() {
			return notification;
		}

		public void setNotification(NotificationState notification) {
			this.notification = notification;
		}

		public String getCallbackUrl() {
			return callbackUrl;
		}

		public void setCallbackUrl(String callbackUrl) {
			this.callbackUrl = callbackUrl;
		}

		public String getRandomToken() {
			return randomToken;
		}

		public void setRandomToken(String randomToken) {
			this.randomToken = randomToken;
		}
		
		
		
		
}
