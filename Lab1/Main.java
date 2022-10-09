import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static final int numberOfThreads = 100;
    public static final int numberOfTransactions = 1000;

    public static void main(String[] args) {

        Bank bank = new Bank();
        LocalTime startTime = LocalTime.now();
        List<Thread> threadList = new ArrayList<>();

        System.out.println("App started");

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    bank.runTransaction(numberOfTransactions);
                }
            });

            threadList.add(thread);
        }

        threadList.forEach(thread -> thread.start());
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        LocalTime endTime = LocalTime.now();

        System.out.println(
                "Started at: " + startTime + "\n" +
                        "Ended at: " + endTime
        );
        System.out.println("Execution finished.");
    }
}
