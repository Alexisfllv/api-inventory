package hub.com.apiinventory.mapper;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    // to response
    SupplierDTOResponse toResponse(Supplier supplier);

    // to entity
    Supplier toEntity(SupplierDTORequest supplierDTORequest);

}
