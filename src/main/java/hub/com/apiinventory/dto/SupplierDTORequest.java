package hub.com.apiinventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierDTORequest (
        @Schema(description = "Supplier name.", example = "Edwar", required = true)
        @NotBlank(message = "{field.required}")
        @Size(min = 2, max = 255, message = "{field.size.range}")
         String name,

        @Schema(description = "Supplier email.", example = "Edwar@gmail.com", required = true)
        @NotBlank(message = "{field.required}")
        @Size(min = 2, max = 255, message = "{field.size.range}")
        @Email(message = "{field.email.invalid}")
         String email,

        @Schema(description = "Supplier name.", example = "+51 987654321", required = true)
        @NotBlank(message = "{field.required}")
        @Size(min = 2, max = 50, message = "{field.size.range}")
         String phone
){ }
