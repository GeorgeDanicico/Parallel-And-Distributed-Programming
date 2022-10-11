import models.Account;
import models.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Bank {
    private static final String FILE_NAME = "accounts.csv";
    private static final int ACCOUNTS_NO = 500;
    private static final String DELIMITER = " ";

    private List<Account> bankAccounts;
    private static AtomicInteger transactionIdentifier = new AtomicInteger(1);
    private List<Lock> locks = new ArrayList<>();
    private Lock bankLock = new ReentrantLock();

    private void readBankAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] info = line.split(DELIMITER);
                Account account = new Account(
                        Integer.parseInt(info[0]),
                        Integer.parseInt(info[1])
                );

                this.bankAccounts.add(account);
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occured while importing the accounts");
        }
    }

    public Bank() {
        bankAccounts = new ArrayList<>();
        this.readBankAccounts();

        for (int i = 0; i < ACCOUNTS_NO; i++) {
            locks.add(new ReentrantLock());
        }
    }

    public void runTransaction(int numberOfTransactions) {

        for (int i = 0; i < numberOfTransactions; i++) {
            Random random = new Random();

            int senderIndex = random.nextInt(0, ACCOUNTS_NO);
            int receiverIndex = random.nextInt(0, ACCOUNTS_NO);
            while (senderIndex == receiverIndex) {
                receiverIndex = random.nextInt(0, ACCOUNTS_NO);
            }

            if (senderIndex > receiverIndex) {
                locks.get(receiverIndex).lock();
                locks.get(senderIndex).lock();
            } else {
                locks.get(senderIndex).lock();
                locks.get(receiverIndex).lock();
            }

            Account sender = bankAccounts.get(senderIndex);
            Account receiver = bankAccounts.get(receiverIndex);
            int senderBalance = sender.getBalance();
      
            if (senderBalance == 0) {
                System.out.println("Transaction between " + sender.getId() + " and " + receiver.getId() + " failed due to insufficient funds.");
            } else {

                int randomAmount = random.nextInt(0, senderBalance + 1);
                Log log = new Log(transactionIdentifier.getAndIncrement(), sender, receiver, randomAmount);

                sender.addNewLog(log);
                receiver.addNewLog(log);

                sender.decreaseBalance(randomAmount);
                receiver.increaseBalance(randomAmount);

                System.out.println("Transaction #" + log.getId() + " between " + sender.getId() + " and " + receiver.getId() +
                        " completed successfully.");
            }

            if (senderIndex > receiverIndex) {
                locks.get(senderIndex).unlock();
                locks.get(receiverIndex).unlock();
            } else {
                locks.get(receiverIndex).unlock();
                locks.get(senderIndex).unlock();

            }
        }
    }

    public void consistencyCheck() {
        for (Account account : bankAccounts) {
            List<Log> logs = account.getLogs();

            int accountCurrentBalance = account.getBalance();

            for (Log log : logs) {
                if (log.getSender() == account) {
                    accountCurrentBalance += log.getAmount();
                } else {
                    accountCurrentBalance -= log.getAmount();
                }
            }

            if (accountCurrentBalance != account.getInitialBalance()) {

                throw new RuntimeException("Inconsistency detected!");
            }
        }
    }

    public void audit() {
        locks.forEach(Lock::lock);
//        bankLock.lock();
        System.out.println("Consistency check started.");
        consistencyCheck();
        System.out.println("Consistency check finished.");
//        bankLock.unlock();
        locks.forEach(Lock::unlock);
    }

    public List<Account> getBankAccounts() {
        return this.bankAccounts;
    }
}
