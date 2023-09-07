package by.dt.orderservice.repo;

import by.dt.orderservice.entity.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<StoreOrder, Long> {
    List<StoreOrder> findAllByClient(long clientId);
}
