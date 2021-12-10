package com.epam.jwd.service.dto.paymentdto;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.Date;
import java.util.Objects;

public class PaymentDto extends AbstractDto<Integer> {
    private Integer userId;
    private String destinationAddress;
    private Long price;
    private Boolean committed;
    private Date time;
    private String name;

    /**
     *
     * @param userId id of payment creator
     * @param destinationAddress address of payment
     * @param price price of payment
     * @param committed is payment committed
     * @param time transaction time of payment
     * @param name name of payment
     */
    public PaymentDto(Integer userId, String destinationAddress, Long price, Boolean committed, Date time, String name) {
        this.userId = userId;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.committed = committed;
        this.time = time;
        this.name = name;
    }

    /**
     *
     * @param id id of payment
     * @param userId id of payment creator
     * @param destinationAddress address of payment
     * @param price price of payment
     * @param committed is payment committed
     * @param time transaction time of payment
     * @param name name of payment
     */
    public PaymentDto(Integer id, Integer userId, String destinationAddress, Long price, Boolean committed, Date time, String name) {
        this.userId = userId;
        this.destinationAddress = destinationAddress;
        this.price = price;
        this.committed = committed;
        this.time = time;
        this.name = name;
        this.id = id;
    }

    /**
     *
     * @return id of credit card owner
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId owner id to be set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return address of payment
     */
    public String getDestinationAddress() {
        return destinationAddress;
    }

    /**
     *
     * @param destinationAddress address to be set
     */
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     *
     * @return price of payment
     */
    public Long getPrice() {
        return price;
    }

    /**
     *
     * @param price price to be set
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     *
     * @return true if payment already committed, false otherwise
     */
    public Boolean getCommitted() {
        return committed;
    }

    /**
     *
     * @param committed true to set state to committed, false otherwise
     */
    public void setCommitted(Boolean committed) {
        this.committed = committed;
    }

    /**
     *
     * @return transaction time of payment, null if payment wasn't committed yet
     */
    public Date getTime() {
        return time;
    }

    /**
     *
     * @param time time to be set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     *
     * @return name of credit card
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto paymentDTO = (PaymentDto) o;
        return Objects.equals(userId, paymentDTO.getUserId())
                && Objects.equals(destinationAddress, paymentDTO.getDestinationAddress())
                && Objects.equals(price, paymentDTO.getPrice())
                && Objects.equals(committed, paymentDTO.getCommitted())
                && Objects.equals(time, paymentDTO.getTime())
                && Objects.equals(name, paymentDTO.getName())
                && Objects.equals(id, paymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, destinationAddress, price, committed, time, name, id);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
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
