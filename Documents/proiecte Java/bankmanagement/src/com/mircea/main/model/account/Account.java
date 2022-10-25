package com.mircea.main.model.account;

import java.text.DecimalFormat;

public abstract class Account {

    private String id;
    private String name;
    private double balance;

    public Account(String id, String name, double balance) {
        checkParameter(id);
        checkParameter(name);
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Account(Account source) {
        this.id = source.id;
        this.name = source.name;
        this.balance = source.balance;
    }

    public abstract boolean deposit(double amount);

    public abstract boolean withdraw(double amount);

    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(amount));
    }

    protected double charges(double balance, double charge) {
        return balance -= charge;
    }
    public abstract Account clone();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        checkParameter(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkParameter(name);
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return (getClass().getSimpleName()) + "    " +
                "\t" + id + "" +
                "\t" + name + "" +
                "\t$" + balance + "";
    }

    protected void checkParameter(String parameter) {
        if (parameter == null || parameter.isBlank()) {
            throw new IllegalArgumentException("One of the parameters is null or blank!");
        }
    }
}
