package hub.com.apiinventory.controller;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.service.SupplierService;
import hub.com.apiinventory.util.apiresponse.GenericResponse;
import hub.com.apiinventory.util.apiresponse.StatusApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    // GET
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GenericResponse<SupplierDTOResponse>>> getByIdGet(@PathVariable Long id){
        return supplierService.getById(id)
                .map(supplierDTO ->
                        ResponseEntity.ok(new GenericResponse<>(StatusApi.SUCCESS, supplierDTO))
                );
    }


}
