package rs.ac.uns.ftn.authentication_service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MerchantDTO {

    private Long id;

    private String companySecretId;

    private String username;

    @NotNull(message = "Company name can't be null!")
    @Size(min = 1, message = "Company name can't be null")
    private String companyName;

    private String phoneNumber;

    private String password;

    public MerchantDTO() {}

    public MerchantDTO(Long id, String username,String companyName,
                       String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
