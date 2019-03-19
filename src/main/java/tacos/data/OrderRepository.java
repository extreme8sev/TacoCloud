package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Order;
import tacos.model.User;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user);
}