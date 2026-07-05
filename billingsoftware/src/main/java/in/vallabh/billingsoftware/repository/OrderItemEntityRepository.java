package in.vallabh.billingsoftware.repository;

import in.vallabh.billingsoftware.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long> {
}
