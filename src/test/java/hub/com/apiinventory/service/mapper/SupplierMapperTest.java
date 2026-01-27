package hub.com.apiinventory.service.mapper;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.entity.Supplier;
import hub.com.apiinventory.mapper.SupplierMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierMapperTest {

    private SupplierMapper supplierMapper;

    @BeforeEach
    void setUp() {
        supplierMapper = Mappers.getMapper(SupplierMapper.class);
    }

    @Nested
    @DisplayName("Tests mapped response <- entity")
    class toResponseTests{

        @Test
        @DisplayName("Should map response <- entity")
        void toResponseTestCorrectly(){
            // Arrange
            Supplier supplier = new Supplier(1L,"name","email@gmail.com","+51 9229282");

            // Act
            SupplierDTOResponse result = supplierMapper.toResponse(supplier);

            // Assert
            assertAll(
                    () -> assertEquals(1L, result.id()),
                    () -> assertEquals("name",result.name()),
                    () -> assertEquals("email@gmail.com",result.email()),
                    () -> assertEquals("+51 9229282",result.phone())
            );
        }

        @Test
        @DisplayName("Should map response <- entity null")
        void toResponseTestNull(){
            // Act
            SupplierDTOResponse result = supplierMapper.toResponse(null);

            // Assert
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Tests mapped entity <- request")
    class toEntityTests{
        @Test
        @DisplayName("Should map entity <- request")
        void toEntityTestCorrectly(){
            // Arrange
            SupplierDTORequest request = new SupplierDTORequest("name","email@gmail.com","+51 9229282");
            // Act
            Supplier result = supplierMapper.toEntity(request);
            // Assert
            assertAll(
                    () -> assertEquals("name",result.getName()),
                    () -> assertEquals("email@gmail.com", result.getEmail()),
                    () -> assertEquals("+51 9229282",result.getPhone())
            );

        }

        @Test
        @DisplayName("Should map entity <- request null")
        void toEntityTestNull(){
            // Act
            Supplier result = supplierMapper.toEntity(null);

            // Assert
            assertNull(result);
        }
    }
}
