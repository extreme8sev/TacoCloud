package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Order;
import tacos.model.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}