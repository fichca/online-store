package by.dt.orderservice.converter;

import by.dt.orderservice.dto.CreateOrderDTO;
import by.dt.orderservice.dto.CreateTemporaryOrderDTO;
import by.dt.orderservice.dto.CreateWithoutAuthOrderDTO;
import by.dt.orderservice.dto.request.CreateOrderRequest;
import by.dt.orderservice.dto.request.CreateWithoutAuthOrderRequest;
import by.dt.orderservice.dto.response.OrderResponse;
import by.dt.orderservice.entity.Status;
import by.dt.orderservice.entity.StoreOrder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderResponse toOrderResponse(StoreOrder order) {
        return OrderResponse.builder()
                .id(order.getId())
                .clientId(order.getClient())
                .description(order.getDescription())
                .status(order.getStatus().toString())
                .build();
    }

    public static List<OrderResponse> toOrderResponse(List<StoreOrder> orders) {
        return orders.stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());
    }

    public static CreateOrderDTO toCreateOrderDTO(CreateOrderRequest request) {
        return CreateOrderDTO.builder()
                .clientId(request.getClientId())
                .description(request.getDescription())
                .status(Status.valueOf(request.getStatus()))
                .build();
    }

    public static CreateWithoutAuthOrderDTO toCreateOrderDTO(CreateWithoutAuthOrderRequest request) {
        return CreateWithoutAuthOrderDTO.builder()
                .clientId(request.getClientId())
                .email(request.getEmail())
                .description(request.getDescription())
                .status(Status.valueOf(request.getStatus()))
                .build();
    }

    public static StoreOrder toOrder(CreateOrderDTO createOrder) {
        return StoreOrder.builder()
                .client(createOrder.getClientId())
                .description(createOrder.getDescription())
                .status(createOrder.getStatus())
                .build();
    }

    public static StoreOrder toOrder(CreateTemporaryOrderDTO createOrder) {
        return StoreOrder.builder()
                .id(createOrder.getTemporaryId())
                .client(createOrder.getClientId())
                .description(createOrder.getDescription())
                .status(createOrder.getStatus())
                .build();
    }

    public static CreateOrderDTO toCreateOrderDTO(CreateWithoutAuthOrderDTO createOrder) {
        return CreateOrderDTO.builder()
                .clientId(createOrder.getClientId())
                .description(createOrder.getDescription())
                .status(createOrder.getStatus())
                .build();

    }

    public static CreateTemporaryOrderDTO toCreateTemporaryOrderDTO(CreateWithoutAuthOrderDTO createOrder) {
        return CreateTemporaryOrderDTO.builder()
                .clientId(-1L)
                .status(createOrder.getStatus())
                .description(createOrder.getDescription())
                .build();
    }
}
