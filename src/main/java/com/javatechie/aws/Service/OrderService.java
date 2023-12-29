package com.javatechie.aws.Service;

import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.OrderRepository;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
import com.javatechie.aws.common.exception.ResourceNotFoundException;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    OrderRepository orderRepository;

    private static final Logger logger = LogManager.getLogger(OrderService.class);


    public ResponseEntity<Object> getAllOrders() {
        Iterable<Order> orders = new ArrayList<>();
        orders = orderRepository.findAll();
        logger.info(orders);
        return ResponseHandler.generateResponse(orders);
    }


    public ResponseEntity<Object> getAllOrdersByJobId(Long jobId) {
        List<Order> orders = new ArrayList<>();
        orders = orderRepository.findByJobId(jobId);
        logger.info(orders);
        return ResponseHandler.generateResponse(orders);
    }

    public ResponseEntity<Object> getAllOrdersByStatus(ShipmentStatus status) {
        List<Order> orders = new ArrayList<>();
        orders = orderRepository.findByStatus(status);
        logger.info(orders);
        return ResponseHandler.generateResponse(orders);
    }


    public ResponseEntity<Object> createOrder(Long jobId, Order newOrder) {
        Order order = jobRepository.findById(jobId).map(job -> {
            newOrder.setJob(job);
            return orderRepository.save(newOrder);
        }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
        logger.info(order);
        return ResponseHandler.generateResponse(order);
    }


    public ResponseEntity<Object> updateOrder(Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist: id=" + id));

        order.setMerchant(updatedOrder.getMerchant());
        order.setStatus(updatedOrder.getStatus());
        order.setCost(updatedOrder.getCost());
        order.setWebsite(updatedOrder.getWebsite());
        order.setDescription(updatedOrder.getDescription());
        order.setCompletedAt(updatedOrder.getCompletedAt());
        order.setJob(updatedOrder.getJob());
        orderRepository.save(order);
        logger.info(order);
        return ResponseHandler.generateResponse(order);
    }


    public ResponseEntity<Object> deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteAllOrdersOfJob(Long jobId) {
        orderRepository.deleteByJobId(jobId);
        return ResponseHandler.generateResponse(HttpStatus.OK);
    }


}
