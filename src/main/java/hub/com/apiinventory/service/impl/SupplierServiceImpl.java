package hub.com.apiinventory.service.impl;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.service.SupplierService;
import hub.com.apiinventory.service.domain.SupplierServiceDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierServiceDomain supplierServiceDomain;
    private final SupplierMapper supplierMapper;

    @Override
    public Mono<SupplierDTOResponse> getById(Long id) {
        return supplierServiceDomain.findByIdOrError(id)
                .map(supplierMapper::toResponse);
    }
}
