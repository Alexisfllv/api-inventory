package hub.com.apiinventory.repo;

import hub.com.apiinventory.entity.Supplier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SupplierRepository extends ReactiveCrudRepository<Supplier, Long> {
}
