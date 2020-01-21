package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.RoleName;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private Long id;
    private RoleName roleName;
}
