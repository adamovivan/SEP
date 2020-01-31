package rs.ac.uns.ftn.scientific_center.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AgreementTransaction {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String orderId;
    
    private LocalDateTime times;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    private Long magazineId;
    
    private String magazineIssn;
    
}