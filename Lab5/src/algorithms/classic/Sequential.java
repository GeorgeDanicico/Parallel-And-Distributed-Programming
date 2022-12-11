package algorithms.classic;

import models.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class Sequential {
    public static Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2) {
        List<Integer> coefficients = new ArrayList<>();

        for(int i = 0; i <= polynomial1.getDegree() + polynomial2.getDegree(); ++i)
            coefficients.add(0);

        Polynomial result = new Polynomial(coefficients);
        for (int i = 0; i < polynomial1.getDegree() + 1; i++) {
            for (int j = 0; j < polynomial2.getDegree() + 1; j++) {
                int newValue = result.getCoefficients().get(i + j)
                        + polynomial1.getCoefficients().get(i) * polynomial2.getCoefficients().get(j);
                result.getCoefficients().set(i + j, newValue);
            }
        }
        return result;
    }
}
