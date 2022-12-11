package algorithms.karatsuba;

import algorithms.classic.Sequential;
import models.Polynomial;
import utils.PolynomialOperations;

public class SequentialKaratsuba {
    public static Polynomial multiply(Polynomial p1, Polynomial p2) throws InterruptedException {
        if (p1.getDegree() <= 2 && p2.getDegree() <= 2) {
            return Sequential.multiply(p1,p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial leftP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial rightP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));
        Polynomial leftP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial rightP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        Polynomial result1 = multiply(leftP1, leftP2);
        Polynomial result2 = multiply(rightP1, rightP2);
        Polynomial result3 = multiply(PolynomialOperations.add(leftP1, rightP1), PolynomialOperations.add(leftP2, rightP2));

        Polynomial f1 = PolynomialOperations.addZerosCoefficients(
                PolynomialOperations.subtract(PolynomialOperations.subtract(result3, result2), result1), len);
        Polynomial f2 = PolynomialOperations.addZerosCoefficients(result2, 2 * len);

        return PolynomialOperations.add(
                PolynomialOperations.add(f1, f2), result1);
    }
}
