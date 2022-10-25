package com.mircea.main.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Transaction {

     public enum Type {
         DEPOSIT, WITHDRAW
     }

     private Type type;
    private String id;
    private double amount;
    private long timeStamp;

    public Transaction(Type type , long timeStamp, String id, double amount) {
        checkParameter(id);
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be smaller or equal to 0");
        }
        this.type = type;
        this.timeStamp = timeStamp;
        this.id = id;
        this.amount = amount;

    }

    public Transaction(Transaction source) {
        this.type = source.type;
        this.timeStamp = source.timeStamp;
        this.id = source.id;
        this.amount = source.amount;
    }

    public String returnDate(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        checkParameter(id);
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be smaller or equal to 0");
        }
        this.amount = amount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    private void checkParameter(String parameter) {
        if (parameter == null || parameter.isBlank()) {
            throw new IllegalArgumentException(parameter + " is null or blank");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Transaction that = (Transaction) o;
        return this.type.equals(that.type) &&
                this.timeStamp == that.timeStamp &&
                this.id.equals(that.id) &&
                this.amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, timeStamp, id, amount);
    }

    @Override
    public String toString() {
        return (type) + " " +
                "\t" + returnDate(timeStamp) + "" +
                "\t" + id + "" +
                "\t$" + amount + "";
    }
}
