package model;

public abstract class Task implements Runnable{
    protected Matrix firstMatrix;
    protected Matrix secondMatrix;
    protected Matrix resultMatrix;

    public Task(Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
    }

    public Matrix getFirstMatrix() {
        return firstMatrix;
    }

    public void setFirstMatrix(Matrix firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    public Matrix getSecondMatrix() {
        return secondMatrix;
    }

    public void setSecondMatrix(Matrix secondMatrix) {
        this.secondMatrix = secondMatrix;
    }

    public Matrix getResultMatrix() {
        return resultMatrix;
    }

    public void setResultMatrix(Matrix resultMatrix) {
        this.resultMatrix = resultMatrix;
    }
}
