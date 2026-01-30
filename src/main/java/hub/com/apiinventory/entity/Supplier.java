package hub.com.apiinventory.entity;

import hub.com.apiinventory.util.auditing.BaseEntityReactive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("suppliers")
public class Supplier extends BaseEntityReactive {

    @Id
    private Long id;
    private String name;
    private String email;
    private String phone;
}
