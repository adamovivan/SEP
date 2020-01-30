package com.bitcoin.bitcoin.dto;

import com.bitcoin.bitcoin.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotificationDto {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
