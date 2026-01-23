package hub.com.apiinventory.dto;

public record SupplierDTOResponse(
        Long id,
        String name,
        String email,
        String phone
) { }
