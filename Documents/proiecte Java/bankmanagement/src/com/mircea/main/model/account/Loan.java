package com.mircea.main.model.account;

public class Loan extends Account {

    private final double DEBT_LIMIT = 10000;
    private final double INTEREST_RATE = 2d / 100d;

    public Loan(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Loan(Account source) {
        super(source);
    }

    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return false;
        }
        double balance = this.getBalance();
        balance -= amount;
        this.setBalance(round(balance));
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        double balance = this.getBalance();
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return false;
        }
        if ((balance + amount) > DEBT_LIMIT) {
            System.out.println("Cannot withdraw money, debt exceeds " + DEBT_LIMIT + "$");
            return false;
        }
        amount = charges(amount, INTEREST_RATE);
        balance += amount;
        this.setBalance(round(balance));
        return true;
    }

    @Override
    protected double charges(double balance, double charge) {
        double interest = balance * charge;
        balance += interest;
        return balance;
    }

    @Override
    public Account clone() {
        return new Loan(this);
    }
}
