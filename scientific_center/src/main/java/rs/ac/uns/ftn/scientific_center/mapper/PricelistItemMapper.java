package rs.ac.uns.ftn.scientific_center.mapper;


import org.mapstruct.Mapper;
import rs.ac.uns.ftn.scientific_center.dto.PricelistItemDTO;
import rs.ac.uns.ftn.scientific_center.model.PricelistItem;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PricelistItemMapper {
    Set<PricelistItemDTO> pricelistItemsToPricelistItemDTOs(Set<PricelistItem> pricelistItems);
}
