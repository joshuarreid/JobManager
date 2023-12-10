package com.javatechie.aws.DAO;

import com.javatechie.aws.Model.Labor;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
import com.javatechie.aws.common.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface LaborRepository extends CrudRepository<Labor,Long> {

    List<Labor> findByJobId(Long jobId);

    List<Labor> findByStatus(Status status);

    @Transactional
    void deleteByJobId(long id);
}
