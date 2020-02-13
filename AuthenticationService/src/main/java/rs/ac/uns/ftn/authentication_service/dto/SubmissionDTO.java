package rs.ac.uns.ftn.authentication_service.dto;

import rs.ac.uns.ftn.authentication_service.model.SubmissionState;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubmissionDTO {

    private Long id;

    private String email;

    //@NotNull(message = "Company name can't be null!")
    @Size(min = 1, message = "Company name can't be null")
    private String companyName;

   // @NotNull(message = "Organization can't be null!")
    @Size(min = 1, message = "Organization can't be null")
    private String organization;

    //@NotNull(message = "Location can't be null!")
    @Size(min = 1, message = "Location can't be null")
    private String location;

   // @NotNull(message = "State can't be null!")
    @Size(min = 1, message = "State can't be null")
    private String stateLocation;

    private String country;

   // @NotNull(message = "Usage can't be null!")
    @Size(min = 1, message = "Usage can't be null")
    private String usage;


    private String phoneNumber;


    private String password;

    private SubmissionState state;

    public SubmissionDTO() {}

    public SubmissionDTO(Long id, String email, String companyName, String organization,
                         String location, String stateLocation, String country, String usage,
                         String phoneNumber, String password, SubmissionState state) {
        this.id = id;
        this.email = email;
        this.companyName = companyName;
        this.organization = organization;
        this.location = location;
        this.stateLocation = stateLocation;
        this.country = country;
        this.usage = usage;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStateLocation() {
        return stateLocation;
    }

    public void setStateLocation(String stateLocation) {
        this.stateLocation = stateLocation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
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

    public SubmissionState getState() {
        return state;
    }

    public void setState(SubmissionState state) {
        this.state = state;
    }
}
