import models.Consumer;
import models.Producer;
import models.SafeBuffer;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> vector1 = Arrays.asList(4, 3, 2, 6, 4, 7, 4);
        List<Integer> vector2 = Arrays.asList(5, 3, 2, 7, 9, 11, 1);

        SafeBuffer safeBuffer = new SafeBuffer();
        Producer producer = new Producer(vector1, vector2, safeBuffer);
        Consumer consumer = new Consumer(safeBuffer);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        consumerThread.start();
        producerThread.start();
    }

}