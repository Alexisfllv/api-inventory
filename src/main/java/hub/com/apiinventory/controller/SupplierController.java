package hub.com.apiinventory.controller;

import hub.com.apiinventory.dto.SupplierDTORequest;
import hub.com.apiinventory.dto.SupplierDTOResponse;
import hub.com.apiinventory.service.SupplierService;
import hub.com.apiinventory.util.apiresponse.GenericResponse;
import hub.com.apiinventory.util.apiresponse.StatusApi;
import hub.com.apiinventory.util.page.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<GenericResponse<SupplierDTOResponse>>> getByEmailGet(@PathVariable String email){
        return supplierService.getByEmail(email)
                .map(supplierDTO ->
                        ResponseEntity.status(HttpStatus.OK)
                                .body(new GenericResponse<>(StatusApi.SUCCESS, supplierDTO))
                );
    }

    @GetMapping("/search/name")
    public Mono<ResponseEntity<GenericResponse<List<SupplierDTOResponse>>>> getByNameGet(@RequestParam String name) {
        return supplierService.searchByName(name)
                .collectList()
                .map(list -> ResponseEntity.status(HttpStatus.OK)
                        .body(new GenericResponse<>(StatusApi.SUCCESS, list)));
    }

    // POST
    @PostMapping()
    public Mono<ResponseEntity<GenericResponse<SupplierDTOResponse>>> saveSupplierPost(@Valid @RequestBody SupplierDTORequest request){
        return supplierService.saveSupplier(request)
                .map(supplierDTOResponse ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(new GenericResponse<>(StatusApi.CREATED, supplierDTOResponse))
                );
    }

    // PUT
    @PutMapping("/{id}")
    public Mono<ResponseEntity<GenericResponse<SupplierDTOResponse>>> updateSupplierPut(@PathVariable Long id, @Valid @RequestBody SupplierDTORequest request){
        return supplierService.updateSupplier(id,request)
                .map(updatedSupplier ->
                        ResponseEntity
                                .status(HttpStatus.OK)
                                .body(new GenericResponse<>(StatusApi.UPDATED, updatedSupplier))
                );
    }


    // DELETE
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<GenericResponse<Void>>> deleteSupplierDelete(@PathVariable Long id){
        return supplierService.deleteSupplier(id)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }


}
