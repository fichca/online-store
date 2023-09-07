package by.dt.orderservice.dto;

import by.dt.orderservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateWithoutAuthOrderDTO {
    private Long clientId;
    private String email;
    private String description;
    @NotNull
    private Status status;
}
