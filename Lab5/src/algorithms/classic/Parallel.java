package algorithms.classic;

import models.Polynomial;
import models.Task;
import utils.PolynomialOperations;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parallel {
    private static final int THREADS_COUNT = 4;

    public static Polynomial multiply(Polynomial p1, Polynomial p2) throws InterruptedException {
        int sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;

        Polynomial result = PolynomialOperations.generatePolynomialWithZeroCoefficients(sizeOfResultCoefficientList);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS_COUNT);
        int step = sizeOfResultCoefficientList / THREADS_COUNT == 0 ? 1 : sizeOfResultCoefficientList / THREADS_COUNT;

        for (int i = 0; i < sizeOfResultCoefficientList; i += step) {
            int end = i + step;
            Task task = new Task(i, end, p1, p2, result);
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        return result;
    }
}
