package tasks;

import model.Matrix;
import model.Task;

public class KthConsecutiveTask extends Task {
    private int taskNumber;
    private int noOfElements;
    private int kValue;

    public KthConsecutiveTask(Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix, int taskNumber, int noOfElements, int taskCount) {
        super(firstMatrix, secondMatrix, resultMatrix);
        this.taskNumber = taskNumber;
        this.noOfElements = noOfElements;
        this.kValue = taskCount;
    }

    @Override
    public void run() {
        int row = taskNumber / resultMatrix.getColumns();
        int column = taskNumber % resultMatrix.getColumns();

        for (int i = 0; i < noOfElements; i++) {

            int resultValue = firstMatrix.computeElementOfProduct(secondMatrix, row, column);
            this.resultMatrix.setMatrixElement(row, column, resultValue);

            row = row + ((column + kValue) / resultMatrix.getColumns());
            column = (column + kValue) % resultMatrix.getColumns();
        }
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public int getNoOfElements() {
        return noOfElements;
    }

    public void setNoOfElements(int noOfElements) {
        this.noOfElements = noOfElements;
    }
}
