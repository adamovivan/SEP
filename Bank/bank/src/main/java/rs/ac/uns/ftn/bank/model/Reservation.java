package rs.ac.uns.ftn.bank.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BankAccount bankAccount;
    private Double amount;
    private Boolean active;
    @OneToOne
    private Transaction transaction;

    public Reservation(){
        this.active = true;
    }
}
