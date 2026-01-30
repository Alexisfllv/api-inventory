package hub.com.apiinventory.repo;

import hub.com.apiinventory.entity.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SupplierRepository extends ReactiveCrudRepository<Supplier, Long> {
    // pageable
    Flux<Supplier> findAllBy(Pageable pageable);
    Mono<Long> count();

    // find x email
    Mono<Supplier> findByEmail(String email);

    // find like name
    Flux<Supplier> findByNameContainingIgnoreCase(String name);


}
