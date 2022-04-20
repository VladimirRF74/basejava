package com.urise.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        final Account client1 = new Account(1000);
        final Account client2 = new Account(2000);

        new Thread(() -> transfer(client1, client2, 500)).start();

        new Thread(() -> transfer(client2, client1, 300)).start();
    }

    static void transfer(Account a1, Account a2, int amount) {
        if (a1.getBalance() < amount) {
            throw new IllegalArgumentException();
        }
        synchronized (a1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (a2) {
                a1.withdraw(amount);
                a2.deposit(amount);
            }
        }
    }
}

class Account {
    int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    void withdraw(int amount) {
        balance -= amount;
    }

    void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}