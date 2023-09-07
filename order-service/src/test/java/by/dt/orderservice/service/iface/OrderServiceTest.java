package by.dt.orderservice.service.iface;

import by.dt.api.scope.dto.requst.CreateRequest;
import by.dt.api.scope.dto.response.ClientResponse;
import by.dt.api.scope.feignclient.ClientServiceApiClient;
import by.dt.orderservice.dto.CreateOrderDTO;
import by.dt.orderservice.dto.CreateWithoutAuthOrderDTO;
import by.dt.orderservice.entity.Status;
import by.dt.orderservice.entity.StoreOrder;
import by.dt.orderservice.exception.ClientIdNotFoundException;
import by.dt.orderservice.exception.OrderNotFoundException;
import by.dt.orderservice.repo.OrderRepo;
import by.dt.orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = OrderServiceImpl.class)
class OrderServiceTest {

    @Autowired
    private OrderService service;
    @MockBean
    private OrderRepo orderRepoMock;
    @MockBean
    private ClientServiceApiClient clientServiceApiMock;
    @Captor
    private ArgumentCaptor<StoreOrder> orderArgumentCaptor;


    @Test
    void testCreateWithoutAuth_clientId_IS_NULL_AND__email_IS_EXIST() {
        String email = "test";
        Status status = Status.OPENED;
        String description = "test description";
        CreateWithoutAuthOrderDTO orderDTO = CreateWithoutAuthOrderDTO.builder()
                .email(email)
                .description(description)
                .status(status)
                .build();
        long clientId = 21L;
        ClientResponse clientResponse = ClientResponse.builder()
                .id(clientId)
                .email(email)
                .build();
        when(clientServiceApiMock.getByEmail(orderDTO.getEmail()))
                .thenReturn(Optional.of(clientResponse));

        service.createWithoutAuth(orderDTO);

        verify(orderRepoMock).save(orderArgumentCaptor.capture());
        assertEquals(clientId, orderArgumentCaptor.getValue().getClient());
        assertEquals(status, orderArgumentCaptor.getValue().getStatus());
        assertEquals(description, orderArgumentCaptor.getValue().getDescription());
    }

    @Test
    void testCreateWithoutAuth_clientId_IS_NULL_AND_email_IS_NOT_EXIST() {
        String email = "test";
        Status status = Status.OPENED;
        String description = "test description";
        CreateWithoutAuthOrderDTO orderDTO = CreateWithoutAuthOrderDTO.builder()
                .email(email)
                .description(description)
                .status(status)
                .build();
        long clientId = 21L;
        ClientResponse clientResponse = ClientResponse.builder()
                .id(clientId)
                .email(email)
                .build();
        when(clientServiceApiMock.getByEmail(orderDTO.getEmail()))
                .thenReturn(Optional.empty());
        when(clientServiceApiMock.create(new CreateRequest(email)))
                .thenReturn(clientResponse);

        service.createWithoutAuth(orderDTO);

        verify(orderRepoMock).save(orderArgumentCaptor.capture());
        assertEquals(clientId, orderArgumentCaptor.getValue().getClient());
        assertEquals(status, orderArgumentCaptor.getValue().getStatus());
        assertEquals(description, orderArgumentCaptor.getValue().getDescription());
    }

    @Test
    void testGetOrderById() {
        long orderId = 1L;
        StoreOrder mockOrder = new StoreOrder();
        when(orderRepoMock.findById(orderId)).thenReturn(Optional.of(mockOrder));

        StoreOrder result = service.get(orderId);

        assertNotNull(result);
        assertEquals(mockOrder, result);
    }

    @Test
    void testGetOrderByIdNotFound() {
        long orderId = 1L;
        when(orderRepoMock.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> service.get(orderId));
    }

    @Test
    void testGetOrdersByClientId() {
        long clientId = 1L;
        List<StoreOrder> mockOrders = new ArrayList<>();
        when(orderRepoMock.findAllByClient(clientId)).thenReturn(mockOrders);

        List<StoreOrder> result = service.getByClientId(clientId);

        assertNotNull(result);
        assertEquals(mockOrders, result);
    }

    @Test
    void testCreateOrderWithNonExistentClientId() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(1L, "test", Status.OPENED);
        when(clientServiceApiMock.exist(anyLong()))
                .thenReturn(false);

        assertThrows(ClientIdNotFoundException.class, () -> service.create(createOrderDTO));
    }


}