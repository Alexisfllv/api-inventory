package hub.com.apiinventory.service;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import reactor.core.publisher.Mono;

public interface SupplierService {
    // GET
    Mono<SupplierDTOResponse> getById(Long id);

}
