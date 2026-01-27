package hub.com.apiinventory.service.impl;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.repo.SupplierRepository;
import hub.com.apiinventory.service.domain.SupplierServiceDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {

    @Mock
    private SupplierServiceDomain supplierServiceDomain;

    @Mock
    private SupplierMapper supplierMapper;

    @Mock
    private SupplierRepository supplierRepository;

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

    @Nested
    @DisplayName("findAllPage")
    class findAllPage {
        @Test
        @DisplayName("Test findAllPage Success")
        void findAllPage_shouldReturnPagedResponse() {
            // Arrange
            int page = 0;
            int size = 2;

            Supplier supplier1 = new Supplier();
            Supplier supplier2 = new Supplier();
            SupplierDTOResponse dto1 = new SupplierDTOResponse(1L,"names1","emails1@gmail.com","+51 123456789");
            SupplierDTOResponse dto2 = new SupplierDTOResponse(2L,"names2","emails2@gmail.com","+51 987654321");

            when(supplierRepository.findAllBy(any(Pageable.class))).thenReturn(Flux.just(supplier1,supplier2));
            when(supplierRepository.count()).thenReturn(Mono.just(5L));
            when(supplierMapper.toResponse(supplier1)).thenReturn(dto1);
            when(supplierMapper.toResponse(supplier2)).thenReturn(dto2);

            // Act Assert
            StepVerifier.create(supplierServiceImpl.findAllPage(page, size))
                    .assertNext(pageResponse -> {
                        assertEquals(2,pageResponse.content().size());
                        assertEquals(page,pageResponse.page());
                        assertEquals(size,pageResponse.size());
                        assertEquals(5L,pageResponse.totalElements());
                        assertEquals(3,pageResponse.totalPages());
                    })
                    .verifyComplete();
        }
        @Test
        @DisplayName("Test findAll Page Fail")
        void findAllPage_shouldReturnError() {
            // Arrange
            when(supplierRepository.findAllBy(any(Pageable.class)))
                    .thenReturn(Flux.error(new RuntimeException("DB error")));
            when(supplierRepository.count())
                    .thenReturn(Mono.just(0L));

            // Act Assert
            StepVerifier.create(supplierServiceImpl.findAllPage(0, 10))
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Test
    @DisplayName("saveSupplier")
    void saveSupplierSucces(){
        // Arrange
        SupplierDTORequest supplierRequest = new SupplierDTORequest("name","email@gmail.com","123456789");
        Supplier supplier = new Supplier(null,"name","email@gmail.com","123456789");
        SupplierDTOResponse supplierResponse = new SupplierDTOResponse(1L,"name","email@gmail.com","123456789");
        when(supplierMapper.toEntity(supplierRequest)).thenReturn(supplier);
        when(supplierServiceDomain.saveSupplier(supplier)).thenReturn(Mono.just(supplierResponse));
        // Act & Assert
        StepVerifier.create(supplierServiceImpl.saveSupplier(supplierRequest))
                .expectNext(supplierResponse)
                .verifyComplete();

        // InOrder Verify
        InOrder inOrder = Mockito.inOrder(supplierMapper,supplierServiceDomain);
        inOrder.verify(supplierMapper).toEntity(supplierRequest);
        inOrder.verify(supplierServiceDomain).saveSupplier(supplier);
    }
}
