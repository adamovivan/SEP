package rs.ac.uns.ftn.scientific_center.dto;

import lombok.Data;

import java.util.Set;

@Data
public class EditorialBoardDTO {
    private Long id;
    private UserDTO mainEditor;
    private Set<UserDTO> scientificFieldEditors;
}
