package hub.com.apiinventory.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("suppliers")
public class Supplier {

    @Id
    private Long id;
    private String name;
    private String email;
    private String phone;
}
