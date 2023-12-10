package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.OrderRepository;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    OrderRepository orderRepository;


    public ResponseEntity<Iterable<Order>> getAllOrders() {
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<List<Order>> getAllOrdersByJobId(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }
        List<Order> orders = orderRepository.findByJobId(jobId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<List<Order>> getAllOrdersByStatus(ShipmentStatus status) {

        List<Order> orders = orderRepository.findByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    public ResponseEntity<Order> createOrder(Long jobId, Order newOrder) {
        Order order = jobRepository.findById(jobId).map(job -> {
            newOrder.setJob(job);
            return orderRepository.save(newOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Job with id = " + jobId));

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    public ResponseEntity<Order> updateOrder(Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderId " + id + "not found"));

        if (updatedOrder.getMerchant() != null) order.setMerchant(updatedOrder.getMerchant());
        if (updatedOrder.getStatus() != null) order.setStatus(updatedOrder.getStatus());
        if (updatedOrder.getCost() != null) order.setCost(updatedOrder.getCost());
        if (updatedOrder.getWebsite() != null) order.setWebsite(updatedOrder.getWebsite());
        if (updatedOrder.getDescription() != null) order.setDescription(updatedOrder.getDescription());
        if (updatedOrder.getCompletedAt() != null) order.setCompletedAt(updatedOrder.getCompletedAt());
        if (updatedOrder.getJob() != null) order.setJob(updatedOrder.getJob());
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }


    public ResponseEntity<HttpStatus> deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<List<Order>> deleteAllOrdersOfJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Not found Job with id = " + jobId);
        }

        orderRepository.deleteByJobId(jobId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
