package com.mircea.main.model.account;

public class Savings extends Account {

    private static final double FEE = 5.00;

    public Savings(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Savings(Account source) {
        super(source);
    }

    @Override
    public boolean deposit(double amount) {
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        double balance = this.getBalance();
        balance -= FEE + amount;
        this.setBalance(round(balance));
        return true;

    }

    @Override
    public Account clone() {
        return new Savings(this);
    }

}
