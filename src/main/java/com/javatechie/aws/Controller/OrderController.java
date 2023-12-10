package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.OrderRepository;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.OrderService;
import com.javatechie.aws.common.ShipmentStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @GetMapping("/order")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        logger.info("Fetching All Orders...");
        return orderService.getAllOrders();
    }

    @GetMapping("/job/{jobId}/orders")
    public ResponseEntity<List<Order>> getAllOrdersByJobId(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Fetching Orders for jobId: " + jobId);
        return orderService.getAllOrdersByJobId(jobId);
    }

    @GetMapping("/order/{status}")
    public ResponseEntity<List<Order>> getAllOrdersByStatus(@PathVariable(value = "status") ShipmentStatus status) {
        logger.info("Fetching All Orders with status: " + status.toString());
        return orderService.getAllOrdersByStatus(status);
    }

    @PostMapping("/job/{jobId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable(value = "jobId") Long jobId,
                                         @RequestBody Order newOrder) {
        logger.info("Creating Order...");
        logger.info(newOrder.toString());
        return orderService.createOrder(jobId, newOrder);
    }


    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        logger.info("Updating OrderId: " + id);
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        logger.info("Deleting OrderId: " + id);
        return orderService.deleteOrder(id);
    }

    @DeleteMapping("/job/{jobId}/orders")
    public ResponseEntity<List<Order>> deleteAllOrdersOfJob(@PathVariable(value = "jobId") Long jobId) {
        logger.info("Deleting All Orders for JobId: " + jobId);
        return orderService.deleteAllOrdersOfJob(jobId);
    }





}
