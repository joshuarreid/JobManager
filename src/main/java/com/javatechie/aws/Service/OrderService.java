package com.javatechie.aws.Service;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.javatechie.aws.DAO.JobRepository;
import com.javatechie.aws.DAO.OrderRepository;
import com.javatechie.aws.Model.Order;
import com.javatechie.aws.common.ShipmentStatus;
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


    public ResponseEntity<Iterable<Order>> getAllOrders() {
        try {
            Iterable<Order> orders = new ArrayList<>();
            orders = orderRepository.findAll();
            logger.info(orders.toString());
            if (!orders.iterator().hasNext()) {
                logger.info("No Orders Found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("All Orders Successfully Fetched");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Order>> getAllOrdersByJobId(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            List<Order> orders = new ArrayList<>();
            orders = orderRepository.findByJobId(jobId);
            if (orders.isEmpty()) {
                logger.info("No Order Found for JobId: " + jobId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(orders.size() + " Orders Successfully Found for JobId: " + jobId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Order>> getAllOrdersByStatus(ShipmentStatus status) {
        try {
            List<Order> orders = new ArrayList<>();
            orders = orderRepository.findByStatus(status);
            if (orders.isEmpty()) {
                logger.info("No Order Found with Status: " + status.toString());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info(orders.size() + " Orders Successfully Found with Status: " + status.toString());
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Order> createOrder(Long jobId, Order newOrder) {
        try {
            Order order = jobRepository.findById(jobId).map(job -> {
                newOrder.setJob(job);
                return orderRepository.save(newOrder);
            }).orElseThrow(() -> new ResourceNotFoundException("Job does not exist: id=" + jobId));
            logger.info(order);
            logger.info("Order Successfully Created for JobId: " + jobId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Order> updateOrder(Long id, Order updatedOrder) {
        try {
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
            logger.info("Order Successfully Updated");
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<HttpStatus> deleteOrder(Long id) {
        try {
            orderRepository.deleteById(id);
            logger.info("Order Successfully Deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Order>> deleteAllOrdersOfJob(Long jobId) {
        try {
            if (!jobRepository.existsById(jobId)) {
                throw new ResourceNotFoundException("Job does not exist: id=" + jobId);
            }
            orderRepository.deleteByJobId(jobId);
            logger.info("All Orders Successfully Deleted for JobId: " + jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
