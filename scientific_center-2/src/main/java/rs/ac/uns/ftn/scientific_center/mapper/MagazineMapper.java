package rs.ac.uns.ftn.scientific_center.mapper;


import org.mapstruct.Mapper;
import rs.ac.uns.ftn.scientific_center.dto.MagazineDTO;
import rs.ac.uns.ftn.scientific_center.model.Magazine;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagazineMapper {
    MagazineDTO magazineToMagazineDTO(Magazine magazine);
    List<MagazineDTO> magazinesToMagazineDTOs(List<Magazine> magazines);
}
