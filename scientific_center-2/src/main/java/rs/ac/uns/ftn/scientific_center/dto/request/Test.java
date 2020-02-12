package rs.ac.uns.ftn.scientific_center.dto.request;

import lombok.Data;

@Data
public class Test {
    private Double totalPrice;
    private String username;

    @Override
    public String toString() {
        return "Test{" +
                "totalPrice=" + totalPrice +
                ", username='" + username + '\'' +
                '}';
    }
}
