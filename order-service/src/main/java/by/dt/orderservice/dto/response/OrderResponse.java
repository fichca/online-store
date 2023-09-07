package by.dt.orderservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private long id;
    private long clientId;
    private String description;
    private String status;
}
