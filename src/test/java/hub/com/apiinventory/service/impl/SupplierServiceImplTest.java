package hub.com.apiinventory.service.impl;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.service.domain.SupplierServiceDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {

    @Mock
    private SupplierServiceDomain supplierServiceDomain;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierServiceImpl;

    @Test
    @DisplayName("Test getById GET")
    void testgetByIdSuccess() {
        // Arrange
        Long id = 1L;
        Supplier supplier = new Supplier(1L,"name","email@gmail.com","123456789");
        SupplierDTOResponse dto = new SupplierDTOResponse(1L,"name","email@gmail.com","123456789");

        when(supplierServiceDomain.findByIdOrError(id))
                .thenReturn(Mono.just(supplier));
        when(supplierMapper.toResponse(supplier))
                .thenReturn(dto);

        // Act
        Mono<SupplierDTOResponse> result = supplierServiceImpl.getById(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();
    }
}
