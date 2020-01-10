package rs.ac.uns.ftn.bank2.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Client client;
    @OneToOne
    private Card card;
    private Double balance;
    @OneToOne
    private Business business;
    @OneToMany
    private List<Reservation> reservations;
}
