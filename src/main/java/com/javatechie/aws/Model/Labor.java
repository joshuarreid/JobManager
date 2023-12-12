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
@Table(name = "labors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Labor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labors_generator")
    private Long id;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "totalHours")
    private Long totalHours;

    @Column(name = "totalCost")
    private Long totalCost;

    @Column(name = "status")
    private Status status;

    @Column(name = "completedAt")
    private Date completedAt = new Date();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Job job;

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Long totalHours) {
        this.totalHours = totalHours;
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}