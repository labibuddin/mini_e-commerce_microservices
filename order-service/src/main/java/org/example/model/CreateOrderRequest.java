package org.example.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
@Builder
public class CreateOrderRequest {

    @NotBlank
    private String productId;

    @NotBlank
    private int quantity;

}
