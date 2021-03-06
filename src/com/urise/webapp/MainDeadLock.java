package com.urise.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        final Account client1 = new Account(1000);
        final Account client2 = new Account(2000);

        new Thread(() -> {
            System.out.println("Waiting " + client1);
            transfer(client1, client2, 500);
        }).start();

        new Thread(() -> {
            System.out.println("Waiting " + client2);
            transfer(client2, client1, 300);
        }).start();
    }

    static void transfer(Account a1, Account a2, int amount) {
        if (a1.getBalance() < amount) {
            throw new IllegalArgumentException();
        }
        synchronized (a1) {
            System.out.println("Holding " + a1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (a2) {
                System.out.println("Holding " + a2);
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

    @Override
    public String toString() {
        return "Account " + balance;
    }
}