package org.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
@Builder
public class CreateProductRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    private double price;

    @NotBlank
    private int stock;
}
