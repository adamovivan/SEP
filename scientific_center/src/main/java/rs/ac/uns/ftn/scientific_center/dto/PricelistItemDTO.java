package rs.ac.uns.ftn.scientific_center.dto;


import lombok.Data;

@Data
public class PricelistItemDTO {
    private Long id;
    private Double price;
    private MembershipDTO membership;
}
