package hub.com.apiinventory.service.impl;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.exception.ResourceNotFoundException;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.nums.ExceptionMessages;
import hub.com.apiinventory.repo.SupplierRepository;
import hub.com.apiinventory.service.SupplierService;
import hub.com.apiinventory.service.domain.SupplierServiceDomain;
import hub.com.apiinventory.util.page.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierServiceDomain supplierServiceDomain;
    private final SupplierMapper supplierMapper;

    // uso directo no reutilizable
    private final SupplierRepository supplierRepository;

    // GET
    @Override
    public Mono<SupplierDTOResponse> getById(Long id) {
        return supplierServiceDomain.findByIdOrError(id)
                .map(supplierMapper::toResponse);
    }

    @Override
    public Mono<PageResponse<SupplierDTOResponse>> findAllPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Mono<List<SupplierDTOResponse>> contentMono =
                supplierRepository.findAllBy(pageable)
                        .map(supplierMapper::toResponse)
                        .collectList();

        Mono<Long> totalElementsMono = supplierRepository.count();

        return Mono.zip(contentMono, totalElementsMono)
                .map(tuple -> {
                    long totalElements = tuple.getT2();
                    int totalPages = (int) Math.ceil((double) totalElements / size);

                    return new PageResponse<>(
                            tuple.getT1(),
                            page,
                            size,
                            totalElements,
                            totalPages
                    );
                });
    }

    @Override
    public Mono<SupplierDTOResponse> getByEmail(String email) {
        return supplierRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                ExceptionMessages.RESOURCE_NOT_FOUND_ERROR.message() + email
                        )
                ))
                .map(supplierMapper::toResponse);
    }

    // POST
    @Override
    public Mono<SupplierDTOResponse> saveSupplier(SupplierDTORequest request) {
        Supplier supplier = supplierMapper.toEntity(request);
        // save
        return supplierServiceDomain.saveSupplier(supplier);

    }

    // PUT
    @Override
    public Mono<SupplierDTOResponse> updateSupplier(Long id, SupplierDTORequest request) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND_ERROR.message()+ id)))
                .map(exist -> {
                    exist.setName(request.name());
                    exist.setEmail(request.email());
                    exist.setPhone(request.phone());
                    return exist;
                })
                .flatMap(supplierRepository::save)
                .map(supplierMapper::toResponse);
    }

    // DELETE
    @Override
    public Mono<Void> deleteSupplier(Long id) {
        return supplierServiceDomain.findByIdOrError(id)
                .flatMap(sup -> supplierRepository.delete(sup));
    }
}
