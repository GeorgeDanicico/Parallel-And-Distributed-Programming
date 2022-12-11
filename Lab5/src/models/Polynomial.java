package models;

import utils.PolynomialOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Polynomial {
    private List<Integer> coefficients;
    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    private List<Integer> addZeroCoefficients(List<Integer> coefficients, int offset) {
        List<Integer> newCoefficients = IntStream.range(0, offset).mapToObj(i -> 0).collect(Collectors.toList());
        newCoefficients.addAll(coefficients);
        return newCoefficients;
    }

    public Polynomial(List<Integer> coefficients, int degree) {

        this.coefficients = addZeroCoefficients(coefficients, degree - coefficients.size() + 1);
        this.degree = degree;
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.generateCoefficients(degree);
    }

    private void generateCoefficients(int degree) {
        coefficients = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < degree; i++) {
            coefficients.add(r.nextInt(10));
        }
        coefficients.add(r.nextInt(10) + 1);
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public int getDegree() {
        return degree;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(coefficients.get(degree)).append("x ^ ").append(degree);
        for (var i = degree - 1; i > 0; --i) {
            if (coefficients.get(i) != 0)
                builder.append(" + ").append(coefficients.get(i)).append("x ^ ").append(i);
        }
        if (coefficients.get(0) != 0)
            builder.append(" + ").append(coefficients.get(0));
        return builder.toString();
    }
}
