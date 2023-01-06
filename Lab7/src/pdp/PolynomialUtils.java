package pdp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialUtils {
    public static Polynomial getResult(Object[] polynomials){
        int size = ((Polynomial) polynomials[0]).getDegree();
        Polynomial result = new Polynomial(size + 1);
        for (Object polynomial: polynomials) {
            result = Polynomial.add(result, (Polynomial) polynomial);
        }

        return result;
    }

    public static Polynomial add(Polynomial p1, Polynomial p2){
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        //Add the 2 polynomials
        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
        }

        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2){
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
        }

        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }

        int i = coefficients.size() - 1;
        while (coefficients.get(i) == 0 && i > 0) {
            coefficients.remove(i);
            i--;
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial addZerosCoefficients(Polynomial p, int offset) {
        List<Integer> coefficients = IntStream.range(0, offset).mapToObj(i -> 0).collect(Collectors.toList());
        coefficients.addAll(p.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial regularSequential(Polynomial p1, Polynomial p2){
        Polynomial result = new Polynomial(p1.getDegree() + p2.getDegree() + 1);

        for (int i = 0; i < p1.getCoefficients().size(); i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                int newIndex = i + j;
                int newVal = p1.getCoefficients().get(i) * p2.getCoefficients().get(j) + result.getCoefficients().get(newIndex);
                result.getCoefficients().set(newIndex, newVal);
            }
        }

        return  result;
    }

    public static Polynomial karatsubaSequential(Polynomial p1, Polynomial p2) {
        if (p1.getDegree() <= 2 && p2.getDegree() <= 2) {
            return regularSequential(p1, p2);
        }

        int len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        Polynomial leftP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        Polynomial rightP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getCoefficients().size()));
        Polynomial leftP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        Polynomial rightP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getCoefficients().size()));

        Polynomial result1 = karatsubaSequential(leftP1, leftP2);
        Polynomial result2 = karatsubaSequential(rightP1, rightP2);
        Polynomial result3 = karatsubaSequential(add(leftP1, rightP1), add(leftP2, rightP2));

        Polynomial f1 = addZerosCoefficients(
                subtract(subtract(result3, result2), result1), len);
        Polynomial f2 = addZerosCoefficients(result2, 2 * len);

        return add(add(f1, f2), result1);
    }

    public static Polynomial multiplySequence(Polynomial p1, Polynomial p2, int start, int end) {
        Polynomial result = new Polynomial(2 * p1.getDegree() + 1);

        for (int i = start; i < end; i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                result.getCoefficients().set(i + j, result.getCoefficients().get(i + j) + p1.getCoefficients().get(i) * p2.getCoefficients().get(j));
            }
        }

        return result;
    }
}
