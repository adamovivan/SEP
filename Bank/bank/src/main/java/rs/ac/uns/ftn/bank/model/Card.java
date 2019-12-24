package rs.ac.uns.ftn.bank.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CardBrand cardBrand;
    private String pan;
    private String cvv;
    private String cardholderName;
    private LocalDate expiryDate;
    private Boolean blocked;
    @OneToOne
    private BankAccount bankAccount;
}
