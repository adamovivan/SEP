package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<RoleDTO> roles;
    private String email;
    private String city;
    private String country;
    private String title;
    private Boolean emailVerified;
}
