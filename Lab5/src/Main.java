import algorithms.classic.Parallel;
import algorithms.classic.Sequential;
import algorithms.karatsuba.ParallelKaratsuba;
import algorithms.karatsuba.SequentialKaratsuba;
import models.Algorithm;
import models.Execution;
import models.Polynomial;

import java.util.concurrent.ExecutionException;

import static models.Algorithm.CLASSIC;
import static models.Algorithm.KARATSUBA;
import static models.Execution.PARALLEL;
import static models.Execution.SEQUENTIAL;

public class Main {
    private static final Execution METHOD = PARALLEL;
    private static final Algorithm ALGORITHM = CLASSIC;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Polynomial p1 = new Polynomial(100);
        Polynomial p2 = new Polynomial(100);
        System.out.println("P1(x) =" + p1);
        System.out.println("P2(x) =" + p2);

        long startTime = System.currentTimeMillis();
        execute(p1, p2);
        long stopTime = System.currentTimeMillis();
        double totalTime = ((double) stopTime - (double) startTime);
        System.out.println("Elapsed running time: " + totalTime + "ms");
    }

    private static void execute(Polynomial polynomial1, Polynomial polynomial2) throws ExecutionException, InterruptedException {
        Polynomial result = null;
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
        System.out.println("P1(x) * P2(x) = " + result.toString());
    }
}