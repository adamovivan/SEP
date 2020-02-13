package rs.ac.uns.ftn.authentication_service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MerchantDTO {

    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
