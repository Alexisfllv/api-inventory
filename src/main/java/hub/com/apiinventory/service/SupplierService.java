package hub.com.apiinventory.service;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.util.page.PageResponse;
import reactor.core.publisher.Mono;

public interface SupplierService {
    // GET
    Mono<SupplierDTOResponse> getById(Long id);
    Mono<PageResponse<SupplierDTOResponse>> findAllPage(int page, int size);

    // POST
    Mono<SupplierDTOResponse> saveSupplier(SupplierDTORequest request);

    // PUT
    Mono<SupplierDTOResponse> updateSupplier(Long id, SupplierDTORequest request);

    // DELETE
    Mono<Void> deleteSupplier(Long id);
}
