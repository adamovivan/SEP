package rs.ac.uns.ftn.bank.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String merchantId;
    private String merchantPassword;
    @OneToOne
    private Business business;
}
