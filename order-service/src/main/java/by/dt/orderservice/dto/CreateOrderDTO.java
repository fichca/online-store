package by.dt.orderservice.dto;

import by.dt.orderservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {
    @NotNull
    private Long clientId;
    private String description;
    @NotNull
    private Status status;
}
