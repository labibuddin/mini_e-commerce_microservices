package org.user.userservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
@Builder
public class UpdateProductQuantity {

    @NotBlank
    private String productId;

    @NotBlank
    private int stock;
}
