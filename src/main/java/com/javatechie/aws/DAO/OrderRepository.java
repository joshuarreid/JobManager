package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByJobId(Long jobId);

    List<Order> findByStatus(ShipmentStatus status);

    @Transactional
    void deleteByJobId(long id);
}
