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
@Table(name = "contractors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractors_generator")
    private Long id;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private int phone;

    @Column(name = "address")
    private String address;

    @Column(name = "payRate")
    private Long payRate;

    @Column(name = "hoursWorked")
    private Long hoursWorked;

    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "labor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Labor labor;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPayRate() {
        return payRate;
    }

    public void setPayRate(Long payRate) {
        this.payRate = payRate;
    }

    public Long getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Long hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Labor getLabor() {
        return labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }
}