package rs.ac.uns.ftn.bank2.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String organizationName;
    private String companyRegistrationNumber;
    private String purpose;
    @OneToOne
    private Merchant merchant;
    @OneToOne
    private BankAccount bankAccount;
}
