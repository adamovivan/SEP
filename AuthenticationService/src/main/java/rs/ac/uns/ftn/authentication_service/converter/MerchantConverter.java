package rs.ac.uns.ftn.authentication_service.converter;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.authentication_service.dto.MerchantDTO;
import rs.ac.uns.ftn.authentication_service.model.Client;

@Component
public class MerchantConverter {

    public Client convert(MerchantDTO mdto) {

        return new Client(
            null,
            mdto.getCompanyName(),
            mdto.getPhoneNumber(),
            mdto.getPassword()
        );
    }

    public MerchantDTO convert(Client user) {

        return new MerchantDTO(
            user.getId(),
            user.getUsername(),
            user.getCompanyName(),
            user.getPhoneNumber(),
            null
        );
    }

}
