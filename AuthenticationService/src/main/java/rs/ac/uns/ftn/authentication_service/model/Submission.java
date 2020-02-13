package rs.ac.uns.ftn.authentication_service.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String stateLocation;

    @Column(nullable = false)
    private String country;

   // @Column(nullable = false)
    private String usage;

   // @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubmissionState state;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(length=100000)
    private byte[] certificateSigningRequest;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(length=100000)
    private byte[] certificate;

    public Submission() {}

    public Submission(String email, String companyName, String organization, String location, String stateLocation,
                      String country, String usage, String phoneNumber, String password, SubmissionState state) {
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

    public byte[] getCertificateSigningRequest() {
        return certificateSigningRequest;
    }

    public void setCertificateSigningRequest(byte[] certificateSigningRequest) {
        this.certificateSigningRequest = certificateSigningRequest;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }
}
