package hub.com.apiinventory.service.domain;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.exception.ResourceNotFoundException;
import hub.com.apiinventory.mapper.SupplierMapper;
import hub.com.apiinventory.nums.ExceptionMessages;
import hub.com.apiinventory.repo.SupplierRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceDomainTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceDomain supplierServiceDomain;

    @Nested
    @DisplayName("Test findByIdOrError")
    class findByIdOrErrorTest{
        @Test
        @DisplayName("Test findByIdOrError Success")
        void findByIdOrErrorSuccess(){
            // Arrange
            Supplier supplier = new Supplier(1L,"name Test","email_test@gmail.com","+51 9827364352");
            Long idExist = 1L;

            when(supplierRepository.findById(idExist)).thenReturn(Mono.just(supplier));
            // Act & Assert
            StepVerifier.create(supplierServiceDomain.findByIdOrError(idExist))
                    .expectNextMatches(s ->
                            s.getId().equals(idExist)
                            && s.getName().equals("name Test")
                            && s.getEmail().equals("email_test@gmail.com")
                            && s.getPhone().equals("+51 9827364352")
                    )
                    .verifyComplete();

            // Verify
            verify(supplierRepository,times(1)).findById(idExist);
        }

        @Test
        @DisplayName("Test findByIdOrError Fail")
        void findByIdOrErrorFail(){
            // Arrange
            when(supplierRepository.findById(1L)).thenReturn(Mono.empty());

            // Act & Assert
            StepVerifier.create(supplierServiceDomain.findByIdOrError(1L))
                    .expectErrorMatches(throwable ->
                            throwable instanceof ResourceNotFoundException &&
                                    throwable.getMessage().equals(ExceptionMessages.RESOURCE_NOT_FOUND_ERROR.message() + "1")
                    )
                    .verify();

            verify(supplierRepository, times(1)).findById(1L);
        }
    }

    @Nested
    @DisplayName("Test saveSupplier")
    class shouldSaveSupplierSuccess{

        @Test
        @DisplayName("Test saveSupplier Success")
        void saveSupplierSuccess(){
            // Arrange
            Supplier supplier = new Supplier(null,"name Test","email_test@gmail.com","+51 9827364352");
            Supplier supplierSaved = supplier;
            supplierSaved.setId(1L);

            SupplierDTOResponse supplierDTOResponse = new SupplierDTOResponse(1L,"name Test","email_test@gmail.com","+51 9827364352");

            when(supplierRepository.save(supplier)).thenReturn(Mono.just(supplierSaved));
            when(supplierMapper.toResponse(supplierSaved)).thenReturn(supplierDTOResponse);
            // Act & Assert
            StepVerifier.create(supplierServiceDomain.saveSupplier(supplier))
                    .expectNext(supplierDTOResponse)
                    .verifyComplete();

            // InOrder verify
            InOrder inOrder = inOrder(supplierRepository,supplierMapper);
            inOrder.verify(supplierRepository).save(supplier);
            inOrder.verify(supplierMapper).toResponse(supplierSaved);
            inOrder.verifyNoMoreInteractions();
        }
    }
}
