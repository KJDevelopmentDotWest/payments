package com.epam.jwd.repository.model.payment;

import com.epam.jwd.repository.model.Entity;

import java.util.Date;

public class Payment extends Entity<Integer> {
    private Integer accountId;
    private String destinationAddress;
    private int price;
    private boolean committed;
    private Date time;
    private String name;

    public Payment() {}

    public Payment(Integer userId, String destinationAddress, int price, boolean committed, Date time, String name) {
        this.accountId = userId;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
