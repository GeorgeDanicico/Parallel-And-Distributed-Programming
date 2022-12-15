package model;

import java.util.Random;

public class Matrix {
    private final int rows;
    private final int columns;
    private int[][] mat;

    public Matrix(int[][] mat, int row, int column) {
        this.mat = mat;
        this.rows = row;
        this.columns = column;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getElement(int row, int column) {
        return this.mat[row][column];
    }

    public int[][] getMatrix() {
        return mat;
    }

    public int computeElementOfProduct(Matrix secondMatrix, int row, int column) {
        int result = 0;

        for (int i = 0; i < this.rows; i++) {
            result += (this.mat[row][i] * secondMatrix.getElement(i, column));
        }

        return result;
    }

    public void setMatrixElement(int row, int column, int element) {
        this.mat[row][column] = element;
    }

    public static int[][] generateRandomMatrix(int row, int column) {
        int[][] matrix = new int[row][column];
        Random random = new Random();

        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = 1;
            }
        }

        return matrix;
    }

    public static int[][] generateEmptyMatrix(int row, int column) {
        int[][] matrix = new int[row][column];

        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Matrix: \n");
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < columns; j++) {
                stringBuilder.append(mat[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
