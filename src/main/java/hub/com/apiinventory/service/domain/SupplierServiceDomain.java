package hub.com.apiinventory.service.domain;

import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.repo.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierServiceDomain {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    // findByIdOrError
    public Mono<Supplier> findByIdOrError(Long id){
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Supplier with id %s not found", id))));
    }


}
