package com.mircea.main.model.account;

import com.mircea.main.model.account.impl.Taxable;

public class Chequing extends Account implements Taxable {

    private final double OVER_DRAFT_TAX = 5.50d;
    private static final double TAXABLE_AMOUNT = 3000d;
    private final double TAX = 15d / 100d;
    private static final int OVER_DRAFT_AMOUNT = 200;

    public Chequing(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Chequing(Chequing source) {
        super(source);
    }

    @Override
    public boolean deposit(double amount) {
        double balance = this.getBalance();
        if (amount <= 0) {
            System.out.println("Cannot deposit invalid amounts!");
            return false;
        }
//        if (amount > TAXABLE_AMOUNT) {
//            System.out.println("Your account income exceeds " + TAXABLE_AMOUNT + " and will be taxed: " + TAX * 100 + "%");
//            amount -= taxable(amount);
//        }
        balance += amount;
        this.setBalance(round(balance));
        System.out.println(amount + " was deposited successfully!");
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        double balance = this.getBalance();
        if (amount <= 0) {
            System.out.println("Cannot withdraw invalid amounts!");
            return false;
        } else if (amount > balance) {
            if ((amount - balance) > OVER_DRAFT_AMOUNT) {
                return false;
            }
            System.out.println("Your are trying to withdraw an amount bigger than your balance!");
            System.out.println("This operation will cost an additional: " + OVER_DRAFT_TAX + "$");
            balance -= OVER_DRAFT_TAX;
            balance -= amount;
            System.out.println("Amount withdrew successfully!");
            System.out.println("Current balance: " + balance);
        } else {
            balance -= amount;
        }
        System.out.println(amount + " was withdrew successfully!");
        this.setBalance(round(balance));
        return true;
    }

    @Override
    public Account clone() {
        return new Chequing(this);
    }


    @Override
    public void tax(double income) {

        double tax =  Math.max(0, income - TAXABLE_AMOUNT) * TAX;
        super.setBalance(  super.round(super.getBalance() - tax));
    }
}
