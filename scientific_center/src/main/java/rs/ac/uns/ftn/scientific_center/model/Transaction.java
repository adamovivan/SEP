package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    private Long id;
    private LocalDateTime dateTime;
    @OneToOne
    private Membership membership;
}
