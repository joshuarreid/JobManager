package com.javatechie.aws.Controller;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.OrderRepository;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.Service.OrderService;
import com.javatechie.aws.common.ShipmentStatus;
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

    @GetMapping("/order")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/job/{jobId}/orders")
    public ResponseEntity<List<Order>> getAllOrdersByJobId(@PathVariable(value = "jobId") Long jobId) {
        return orderService.getAllOrdersByJobId(jobId);
    }

    @GetMapping("/order/{status}")
    public ResponseEntity<List<Order>> getAllOrdersByStatus(@PathVariable(value = "status") ShipmentStatus status) {
        return orderService.getAllOrdersByStatus(status);
    }

    @PostMapping("/job/{jobId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable(value = "jobId") Long jobId,
                                         @RequestBody Order newOrder) {
        return orderService.createOrder(jobId, newOrder);
    }


    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }

    @DeleteMapping("/job/{jobId}/orders")
    public ResponseEntity<List<Order>> deleteAllOrdersOfJob(@PathVariable(value = "jobId") Long jobId) {
        return orderService.deleteAllOrdersOfJob(jobId);
    }





}
