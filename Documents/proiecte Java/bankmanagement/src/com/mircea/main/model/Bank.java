package com.mircea.main.model;

import com.mircea.main.model.account.Account;
import com.mircea.main.model.account.Chequing;
import com.mircea.main.model.account.impl.Taxable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {



    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    private void withdrawTransaction(Transaction transaction) {

        if (!(transaction.getType().equals(Transaction.Type.WITHDRAW))) {
            throw new IllegalArgumentException("Cannot execute withdrawal, wrong transaction type");
        }
        Account account = getAccount(transaction.getId());
        if(account.withdraw(transaction.getAmount())) {
            this.transactions.add(transaction);
        }

    }

    private void depositTransaction(Transaction transaction) {

        if (!(transaction.getType().equals(Transaction.Type.DEPOSIT))) {
            throw new IllegalArgumentException("Cannot execute deposit, wrong transaction type");
        }
        Account account = getAccount(transaction.getId());
        if(account.deposit(transaction.getAmount())) {
            this.transactions.add(transaction);
        }
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Cannot process a null transaction!");
        }
        if (transaction.getType().equals(Transaction.Type.WITHDRAW)) {
            withdrawTransaction(transaction);
        } else if (transaction.getType().equals(Transaction.Type.DEPOSIT)){
            depositTransaction(transaction);

        } else {
            throw new IllegalArgumentException("Something went wrong!");
        }

    }

    private double getIncome(Taxable account) {
        Transaction[] transactions = getTransactions(((Chequing) account).getId());
        return Arrays.stream(transactions)
                .mapToDouble((transaction) -> {
                    switch (transaction.getType()) {
                        case WITHDRAW: return  -transaction.getAmount();
                        case DEPOSIT: return transaction.getAmount();
                        default: return 0;
                    }
                }).sum();
    }

    public void deductTaxes() {
        for (Account account : accounts) {
            if (Taxable.class.isAssignableFrom(account.getClass())) {
                Taxable taxable = (Taxable) account;
                taxable.tax(getIncome(taxable));
            }
        }
    }


    public void addAccount(Account account) {
        if (account == null) {
            System.out.println("Cannot add null object");
            return;
        }
        this.accounts.add(account.clone());
    }

    private void addTransaction(Transaction transaction) {
        if (transaction == null) {
            System.out.println("Cannot add null transaction");
            return;
        }
        this.transactions.add(transaction);
    }

    public Transaction[] getTransactions(String accountId) {
        List<Transaction> list =  this.transactions.stream()
                .filter((element) -> element.getId().equals(accountId))
                .collect(Collectors.toList());
        return list.toArray(new Transaction[list.size()]);
    }

    public Account getAccount(String id) {
        return this.accounts.stream()
                .filter((element) -> element.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
