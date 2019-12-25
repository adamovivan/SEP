package rs.ac.uns.ftn.bank.mapper;

import org.mapstruct.Mapper;
import rs.ac.uns.ftn.bank.dto.PaymentRequestDTO;
import rs.ac.uns.ftn.bank.model.PaymentRequest;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {
    PaymentRequest paymentRequestDTOtoPaymentRequest(PaymentRequestDTO paymentRequestDTO);
}
