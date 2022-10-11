import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int THREADS_NUMBER = 10;
    public static final int TRANSACTIONS_NUMBER = 100;
    public static final int CONSISTENCY_CHECK_DELAY = 20;
    public static final int CONSISTENCY_CHECK_NUMBER = 2;

    public static void main(String[] args) {

        Bank bank = new Bank();
        LocalTime startTime = LocalTime.now();
        List<Thread> threadList = new ArrayList<>();

        Thread consistencyCheckThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                while (counter < CONSISTENCY_CHECK_NUMBER) {
                    try {
                        Thread.sleep(CONSISTENCY_CHECK_DELAY);
                        bank.audit();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    counter ++;
                }
            }
        });

        System.out.println("App started");

        for (int i = 0; i < THREADS_NUMBER; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    bank.runTransaction(TRANSACTIONS_NUMBER);
                }
            });

            threadList.add(thread);
        }

        consistencyCheckThread.start();
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
