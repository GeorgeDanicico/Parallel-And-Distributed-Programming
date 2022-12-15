import models.Consumer;
import models.Producer;
import models.SafeBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> vector1 = new ArrayList<>();
        List<Integer> vector2 = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            vector1.add(1);
            vector2.add(i);
        }

        SafeBuffer safeBuffer = new SafeBuffer();
        Producer producer = new Producer(vector1, vector2, safeBuffer);
        Consumer consumer = new Consumer(safeBuffer);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        producerThread.start();

        try {
            consumerThread.join();
            producerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}