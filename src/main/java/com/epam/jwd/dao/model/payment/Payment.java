package com.epam.jwd.dao.model.payment;

import com.epam.jwd.dao.model.Entity;

import java.util.Date;
import java.util.Objects;

public class Payment extends Entity<Integer> {
    private Integer userId;
    private String destinationAddress;
    private Integer price;
    private Boolean committed;
    private Date time;
    private String name;

    public Payment() {}

    public Payment(Integer userId, String destinationAddress, Integer price, Boolean committed, Date time, String name) {
        this.userId = userId;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.committed = committed;
        this.time = time;
        this.name = name;
    }

    public Payment(Integer id, Integer userId, String destinationAddress, Integer price, Boolean committed, Date time, String name) {
        this.userId = userId;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.committed = committed;
        this.time = time;
        this.name = name;
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(userId, payment.getUserId())
                && Objects.equals(destinationAddress, payment.getDestinationAddress())
                && Objects.equals(price, payment.getPrice())
                && Objects.equals(committed, payment.isCommitted())
                && Objects.equals(time, payment.getTime())
                && Objects.equals(name, payment.getName())
                && Objects.equals(id, payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, destinationAddress, price, committed, time, name, id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", accountId=" + userId +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", price=" + price +
                ", committed=" + committed +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
