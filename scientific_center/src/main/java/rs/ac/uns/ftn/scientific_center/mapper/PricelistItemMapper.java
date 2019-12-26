package rs.ac.uns.ftn.scientific_center.mapper;


import org.mapstruct.Mapper;
import rs.ac.uns.ftn.scientific_center.dto.PricelistItemDTO;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;

@Mapper(componentModel = "spring")
public interface PricelistItemMapper {
    PricelistItemDTO pricelistItemToPricelistItemDTO(PricelistItem pricelistItem);
}
