package by.dt.orderservice.service.iface;

import by.dt.orderservice.dto.CreateOrderDTO;
import by.dt.orderservice.dto.CreateWithoutAuthOrderDTO;
import by.dt.orderservice.entity.Status;
import by.dt.orderservice.entity.StoreOrder;

import java.util.List;

public interface OrderService {

    StoreOrder get(long id);

    List<StoreOrder> getByClientId(long clientId);

    StoreOrder create(CreateOrderDTO createOrder);

    StoreOrder updateStatus(Status status, long id);

    StoreOrder createWithoutAuth(CreateWithoutAuthOrderDTO createOrder);

    StoreOrder updateClientId(long clientId, long id);
}
