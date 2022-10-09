package models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private int balance;
    private List<Log> logs;
    private final int initialBalance;

    public Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
        this.logs = new ArrayList<>();
        initialBalance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void addNewLog(Log log) {
        this.logs.add(log);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public void increaseBalance(int receivedMoney) {
        this.balance += receivedMoney;
    }

    public void decreaseBalance(int sentMoney) {
        this.balance -= sentMoney;
    }

    public int getInitialBalance() {
        return this.initialBalance;
    }
}
