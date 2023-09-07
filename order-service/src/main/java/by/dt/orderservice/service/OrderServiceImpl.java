package by.dt.orderservice.service;

import by.dt.api.scope.dto.requst.CreateRequest;
import by.dt.api.scope.dto.response.ClientResponse;
import by.dt.api.scope.feignclient.ClientServiceApiClient;
import by.dt.orderservice.converter.OrderConverter;
import by.dt.orderservice.dto.CreateOrderDTO;
import by.dt.orderservice.dto.CreateTemporaryOrderDTO;
import by.dt.orderservice.dto.CreateWithoutAuthOrderDTO;
import by.dt.orderservice.entity.Status;
import by.dt.orderservice.entity.StoreOrder;
import by.dt.orderservice.exception.ClientIdNotFoundException;
import by.dt.orderservice.exception.OrderNotFoundException;
import by.dt.orderservice.repo.OrderRepo;
import by.dt.orderservice.service.iface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo repository;
    private final Map<Long, CreateTemporaryOrderDTO> withoutAuthRepository = new HashMap<>();
    private final ClientServiceApiClient clientServiceApi;
    private int TEMPORARY_ID = 0;

    @Override
    public StoreOrder get(long id) {
        Optional<StoreOrder> storeOrder = getOptional(id);
        if (storeOrder.isPresent()) {
            return storeOrder.get();
        } else if (withoutAuthRepository.containsKey(id)) {
            return OrderConverter.toOrder(withoutAuthRepository.get(id));
        }
        throw new OrderNotFoundException("Order not found");
    }

    private Optional<StoreOrder> getOptional(long id) {
        return repository
                .findById(id);
    }

    @Override
    public List<StoreOrder> getByClientId(long clientId) {
        return repository.findAllByClient(clientId);
    }

    @Override
    public StoreOrder create(CreateOrderDTO createOrder) {
        if (!clientServiceApi.exist(createOrder.getClientId())) {
            throw new ClientIdNotFoundException();
        }
        return save(createOrder);
    }

    private StoreOrder save(CreateOrderDTO createOrder) {
        return repository.save(OrderConverter.toOrder(createOrder));
    }


    @Override
    public StoreOrder updateStatus(Status status, long id) {
        StoreOrder order = get(id);
        if (withoutAuthRepository.containsKey(id)) {
            return updateTemporaryOrderStatus(id, status);
        } else {
            order.setStatus(status);
            return repository.save(order);
        }
    }

    @Override
    public StoreOrder createWithoutAuth(CreateWithoutAuthOrderDTO createOrder) {

        if (createOrder.getClientId() != null) {
            return create(OrderConverter.toCreateOrderDTO(createOrder));
        }

        if (createOrder.getEmail() != null) {
            return create(createOrder);
        }

        return createTemporaryOrder(createOrder);
    }

    @Override
    public StoreOrder updateClientId(long clientId, long id) {
        CreateOrderDTO orderDTO = withoutAuthRepository.get(clientId);
        if (orderDTO == null)
            throw new OrderNotFoundException("Order not found!");

        StoreOrder order = create(orderDTO);
        withoutAuthRepository.remove(id);
        return order;
    }

    private StoreOrder create(CreateWithoutAuthOrderDTO createOrder) {
        Optional<ClientResponse> client = clientServiceApi.getByEmail(createOrder.getEmail());
        if (client.isPresent()) {
            createOrder.setClientId(client.get().getId());
        } else {
            ClientResponse clientResponse = clientServiceApi.create(new CreateRequest(createOrder.getEmail()));
            createOrder.setClientId(clientResponse.getId());
        }
        return save(OrderConverter.toCreateOrderDTO(createOrder));
    }

    private StoreOrder updateTemporaryOrderStatus(long id, Status status) {
        CreateTemporaryOrderDTO order = withoutAuthRepository.get(id);
        order.setStatus(status);
        return OrderConverter.toOrder(order);
    }

    private StoreOrder createTemporaryOrder(CreateWithoutAuthOrderDTO createOrder) {
        CreateTemporaryOrderDTO order = OrderConverter.toCreateTemporaryOrderDTO(createOrder);
        order.setTemporaryId(generateId());
        withoutAuthRepository.put(order.getTemporaryId(), order);
        return OrderConverter.toOrder(order);
    }

    private int generateId() {
        return ++TEMPORARY_ID;
    }
}
