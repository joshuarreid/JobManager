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
@CrossOrigin
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @GetMapping("/order")
    public ResponseEntity<Object> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/job/{jobId}/orders")
    public ResponseEntity<Object> getAllOrdersByJobId(@PathVariable(value = "jobId") Long jobId) {
        return orderService.getAllOrdersByJobId(jobId);
    }

    @GetMapping("/order/{status}")
    public ResponseEntity<Object> getAllOrdersByStatus(@PathVariable(value = "status") ShipmentStatus status) {
        return orderService.getAllOrdersByStatus(status);
    }

    @PostMapping("/job/{jobId}/orders")
    public ResponseEntity<Object> createOrder(@PathVariable(value = "jobId") Long jobId,
                                         @RequestBody Order newOrder) {
        return orderService.createOrder(jobId, newOrder);
    }


    @PutMapping("/order/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }

    @DeleteMapping("/job/{jobId}/orders")
    public ResponseEntity<Object> deleteAllOrdersOfJob(@PathVariable(value = "jobId") Long jobId) {
        return orderService.deleteAllOrdersOfJob(jobId);
    }





}
