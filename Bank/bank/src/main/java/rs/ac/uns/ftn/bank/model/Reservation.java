package rs.ac.uns.ftn.bank.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String paymentId;
    @ManyToOne
    private BankAccount bankAccount;
    private Double amount;
    private Boolean active;

    public Reservation(){
        this.active = true;
    }
}
