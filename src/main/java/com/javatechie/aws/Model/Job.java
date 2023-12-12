package com.javatechie.aws.Model;

import com.javatechie.aws.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_generator")
    private Long id;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "name", nullable = false)
    private String Name;

    @Column(name = "estimatedCost")
    private Long estimatedCost;

    @Column(name = "totalCost")
    private Long totalCost;

    @Column(name = "status")
    private Status status;

    @Column(name = "completedAt")
    private Date completedAt = new Date();

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "customer_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;



    public Long getId() {
        return id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Long estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}