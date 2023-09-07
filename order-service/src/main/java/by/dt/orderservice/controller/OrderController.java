package by.dt.orderservice.controller;

import by.dt.orderservice.converter.OrderConverter;
import by.dt.orderservice.dto.CreateOrderDTO;
import by.dt.orderservice.dto.CreateWithoutAuthOrderDTO;
import by.dt.orderservice.dto.request.CreateOrderRequest;
import by.dt.orderservice.dto.request.CreateWithoutAuthOrderRequest;
import by.dt.orderservice.dto.response.OrderResponse;
import by.dt.orderservice.entity.Status;
import by.dt.orderservice.service.iface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController implements BaseController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable long id) {
        return OrderConverter.toOrderResponse(orderService.get(id));
    }

    @GetMapping
    public List<OrderResponse> getByClientId(@RequestParam long clientId) {
        return OrderConverter
                .toOrderResponse(orderService.getByClientId(clientId));
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        CreateOrderDTO createOrder = OrderConverter.toCreateOrderDTO(request);
        return OrderConverter
                .toOrderResponse(orderService.create(createOrder));
    }

    @PatchMapping("{id}/status")
    public OrderResponse updateStatus(@RequestParam String status, @PathVariable long id) {
        return OrderConverter
                .toOrderResponse(orderService.updateStatus(Status.valueOf(status), id));
    }

    @PostMapping("/unauthorized")
    public OrderResponse createWithoutAuth(@RequestBody CreateWithoutAuthOrderRequest request) {
        CreateWithoutAuthOrderDTO createOrder = OrderConverter.toCreateOrderDTO(request);
        return OrderConverter
                .toOrderResponse(orderService.createWithoutAuth(createOrder));
    }

    @PatchMapping("{id}/client")
    public OrderResponse updateClientId(@RequestParam long clientId, @PathVariable long id) {
        return OrderConverter
                .toOrderResponse(orderService.updateClientId(clientId, id));
    }

}
