package com.epam.jwd.repository.model.payment;

import com.epam.jwd.repository.model.Entity;

import java.util.Date;

public class Payment extends Entity<Integer> {
    private Integer userId;
    private String destinationAddress;
    private int price;
    private boolean committed;
    private Date time;
    private String name;

    public Payment(Integer id) {
        super(id);
    }

    public Payment(Integer id, Integer userId, String destinationAddress, int price, boolean committed, Date time, String name) {
        super(id);
        this.userId = userId;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.committed = committed;
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
