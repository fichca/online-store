package by.dt.orderservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CreateTemporaryOrderDTO extends CreateOrderDTO {
    private long temporaryId;
}
