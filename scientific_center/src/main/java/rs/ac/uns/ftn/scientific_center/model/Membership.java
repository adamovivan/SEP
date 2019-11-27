package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Membership {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    @ManyToOne
    private Magazine magazine;
    @ManyToOne
    private Article article;
    @OneToOne
    private User user;
}
