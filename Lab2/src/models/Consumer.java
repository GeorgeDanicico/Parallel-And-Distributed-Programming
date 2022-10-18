package models;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class Consumer implements Runnable{
    int sum;
    private final SafeBuffer buffer;

    public Consumer(SafeBuffer buffer) {
        this.buffer = buffer;
        sum = 0;
    }

    @Override
    public void run() {
        Integer number = 0;
        while (number != null) {
            sum += number;
            number = buffer.pop();
        }

        System.out.println("The scalar product of the vectors is: " + sum);
    }
}
