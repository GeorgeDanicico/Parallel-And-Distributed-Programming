package algorithms.karatsuba;

import models.Polynomial;
import utils.PolynomialOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelKaratsuba {
    private static final int THREADS_COUNT = 4;
    private static final int MAX_DEPTH = 4;

    public static Polynomial multiply(Polynomial p1, Polynomial p2, int currentDepth) throws InterruptedException, ExecutionException {
        if (currentDepth > MAX_DEPTH) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return SequentialKaratsuba.multiply(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));
        Polynomial lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);
        List<CompletableFuture<Polynomial>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> {
            try {
                return multiply(lowP1, lowP2, currentDepth + 1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, executor));
        futures.add(CompletableFuture.supplyAsync(() -> {
            try {
                return multiply(highP1, highP2, currentDepth + 1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, executor));
        futures.add(CompletableFuture.supplyAsync(() -> {
            try {
                return multiply(PolynomialOperations.add(lowP1, highP1), PolynomialOperations
                        .add(lowP2, highP2), currentDepth + 1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, executor));

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();

        Polynomial result1 = futures.get(0).join();
        Polynomial result2 = futures.get(1).join();
        Polynomial result3 = futures.get(2).join();

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);

        //calculate the final result
        Polynomial f1 = PolynomialOperations.addZerosCoefficients(result2, 2 * len);
        Polynomial f2 = PolynomialOperations.addZerosCoefficients(PolynomialOperations.subtract(PolynomialOperations.subtract(result3, result2), result1), len);
        return PolynomialOperations.add(PolynomialOperations.add(f1, f2), result1);
    }
}
