import algorithms.classic.Parallel;
import algorithms.classic.Sequential;
import algorithms.karatsuba.ParallelKaratsuba;
import algorithms.karatsuba.SequentialKaratsuba;
import models.Algorithm;
import models.Execution;
import models.Polynomial;

import java.util.concurrent.ExecutionException;

import static models.Algorithm.KARATSUBA;
import static models.Execution.PARALLEL;
import static models.Execution.SEQUENTIAL;

public class Main {
    private static final Execution METHOD = PARALLEL;
    private static final Algorithm ALGORITHM = KARATSUBA;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Polynomial p1 = new Polynomial(5);
        System.out.println("p1=" + p1);
        Polynomial p2 = new Polynomial(5);
        System.out.println("p2=" + p2);
        long startTime = System.nanoTime();
        run(p1, p2);
        long stopTime = System.nanoTime();
        double totalTime = ((double) stopTime - (double) startTime) / 1_000_000_000.0;
        System.out.println("Elapsed running time: " + totalTime + "s");
    }

    private static void run(Polynomial polynomial1, Polynomial polynomial2) throws ExecutionException, InterruptedException {
        Polynomial result;
        if (METHOD.equals(SEQUENTIAL)) {
            if (ALGORITHM.equals(Algorithm.CLASSIC)) {
                result = Sequential.multiply(polynomial1, polynomial2);
            } else {
                result = SequentialKaratsuba.multiply(polynomial1, polynomial2);
            }
        } else {
            if (ALGORITHM.equals(Algorithm.CLASSIC)) {
                result = Parallel.multiply(polynomial1, polynomial2);
            } else {
                result = ParallelKaratsuba.multiply(polynomial1, polynomial2, 1);
            }
        }
        System.out.println("p1*p2=" + result.toString());

    }
}