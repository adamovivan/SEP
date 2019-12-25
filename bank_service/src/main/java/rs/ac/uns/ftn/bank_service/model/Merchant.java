package rs.ac.uns.ftn.bank_service.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Merchant {
    @Id
    private Long id;
    private String username;
    private String merchantId;
    private String merchantPassword;
}
