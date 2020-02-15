package rs.ac.uns.ftn.authentication_service.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

    private String companySecretId;



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
}
