package tasks;

import model.Matrix;
import model.Task;

public class ColumnConsecutiveTask extends Task {
    private int row;
    private int column;
    private int noOfElements;

    public ColumnConsecutiveTask(Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix,
                              int row, int column, int noOfElements) {
        super(firstMatrix, secondMatrix, resultMatrix);
        this.row = row;
        this.column = column;
        this.noOfElements = noOfElements;
    }


    @Override
    public void run() {
        for (int i = 0; i < noOfElements; i++) {
            int resultValue = firstMatrix.computeElementOfProduct(secondMatrix, row, column);
            this.resultMatrix.setMatrixElement(row, column, resultValue);

            column = column + (row + 1) / resultMatrix.getRows();
            row = (row + 1) % resultMatrix.getRows();
        }
    }
}
