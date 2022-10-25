package com.mircea.main;

import com.mircea.main.model.Bank;
import com.mircea.main.model.Transaction;
import com.mircea.main.model.account.Account;
import com.mircea.main.model.account.Chequing;
import com.mircea.main.model.account.Savings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static final String ACCOUNTS_FILE = "src/com/mircea/main/data/accounts.txt";
    private static final String TRANSACTIONS_FILE = "src/com/mircea/main/data/transactions.txt";

    static Bank bank = new Bank();


    public static void main(String[] args) {

        try {
            List<Account> accounts = returnAccounts(ACCOUNTS_FILE);
            loadAccounts(accounts);

            List<Transaction> transactions = returnTransactions(TRANSACTIONS_FILE);
            runTransactions(transactions);
            bank.deductTaxes();
            for (Account account : accounts) {
                System.out.println("\n\t\t\t\t\t ACCOUNT\n\n\t"+account+"\n\n");
                transactionHistory(account.getId());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Account createObject(String[] values) {
        try {

            Object object = Class.forName("com.mircea.main.model.account." + values[0])
                    .getConstructor(String.class, String.class, double.class)
                    .newInstance(values[1], values[2], Double.parseDouble(values[3]));
            return (Account) object;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Account> returnAccounts(String fileName) throws FileNotFoundException {
        FileInputStream fins = new FileInputStream(fileName);
        List<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(fins);

        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] values = line.split(",");

            Account account = createObject(values);
            accounts.add(account);
        }
        sc.close();
        return accounts;
    }

    public static void loadAccounts(List<Account> accounts) {
        accounts.stream()
                .forEach(account -> bank.addAccount(account));
    }

    public static List<Transaction> returnTransactions (String fileName) throws FileNotFoundException {
        FileInputStream fins = new FileInputStream(fileName);
        Scanner sc = new Scanner(fins);
        List<Transaction> transactions = new ArrayList<>();

        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] values = line.split(",");

            Transaction transaction = new Transaction(Transaction.Type.valueOf(values[1]), Long.parseLong(values[0]),
                    values[2], Double.parseDouble(values[3]));

            transactions.add(transaction);
        }
        sc.close();
       transactions = transactions.stream().sorted(Comparator.comparing(transaction -> transaction.getTimeStamp()))
                .collect(Collectors.toList());
       return transactions;
    }

    public static void runTransactions(List<Transaction> transactions) {
        transactions.stream()
                .forEach(transaction -> bank.executeTransaction(transaction));
    }

    public static void transactionHistory(String id) {
        Account account = bank.getAccount(id);
        Transaction[] transactions = bank.getTransactions(id);
        System.out.println("\t\t\t\tACCOUNT\n\t");
        System.out.println(account);
        System.out.println("\t\t\t\t   TRANSACTION HISTORY\n\t");
        for (Transaction transaction : transactions) {
            System.out.println("\t" + transaction +"\n");
            wait(300);
        }

        System.out.println("\n\t\t\t\t"+ "After TAX"+ "\n");
        bank.deductTaxes();
        System.out.println("\t" + account + "\n\n\n\n");

    }

    public static void wait(int miliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(miliSeconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
