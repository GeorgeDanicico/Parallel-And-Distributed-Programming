package exam;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SubjectNo2P3 {
    public static ExecutorService executorService;

    public static int nonrecursiveSum(int[] a, int[] b, int startIndex, int endIndex) {
        int sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += (a[i] * b[i]);
        }
        return sum;
    }

    public static int recursiveSum(int[] a, int[] b, int startIndex, int endIndex, int nrThreads) throws ExecutionException, InterruptedException {

        int midIndex = (startIndex + endIndex) / 2;

        if (nrThreads <= 1) {
            return nonrecursiveSum(a, b, startIndex, endIndex);
        }
        Future<Integer> sum1 = executorService.submit(() -> recursiveSum(a, b, startIndex, midIndex, nrThreads / 2));
        int s2 = recursiveSum(a, b, midIndex, endIndex, nrThreads / 2);

        return sum1.get() + s2;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int nThreads = 10;
        executorService = Executors.newFixedThreadPool(nThreads);

        int[] a = IntStream.range(1, 6).toArray();
        int[] b = IntStream.range(1, 6).toArray();

        System.out.println(recursiveSum(a, b, 0, 5, nThreads));

    }
}
