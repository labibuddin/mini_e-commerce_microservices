package org.user.userservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    @Size(max = 100) // Valid untuk String
    private String name;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to zero") // Hanya @Min cukup untuk validasi angka
    private double price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be at least 0") // Sama seperti price, hanya gunakan @Min untuk validasi angka
    private int stock;
}
