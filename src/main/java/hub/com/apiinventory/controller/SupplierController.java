package hub.com.apiinventory.controller;

import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.service.SupplierService;
import hub.com.apiinventory.util.apiresponse.GenericResponse;
import hub.com.apiinventory.util.apiresponse.StatusApi;
import hub.com.apiinventory.util.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/page")
    public Mono<GenericResponse<PageResponse<SupplierDTOResponse>>> pageListSupplierGet(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        return supplierService.findAllPage(page, size)
                .map(paged ->
                        new GenericResponse<>(StatusApi.SUCCESS, paged)
                );
    }


}
