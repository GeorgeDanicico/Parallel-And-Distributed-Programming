package models;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class Producer implements Runnable{
    private List<Integer> vector1;
    private List<Integer> vector2;
    private final SafeBuffer buffer;

    public Producer(List<Integer> vector1, List<Integer> vector2, SafeBuffer buffer) {
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        IntStream.range(0,vector1.size())
                .forEach(i -> {
                    buffer.add(vector1.get(i) * vector2.get(i));
                });
        buffer.close();

    }
}
