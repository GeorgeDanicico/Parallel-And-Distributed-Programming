import model.Matrix;
import model.Task;
import tasks.ColumnConsecutiveTask;
import tasks.KthConsecutiveTask;
import tasks.RowConsecutiveTask;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int MATRIX_ROWS = 1000;
    private static final int MATRIX_COLUMNS = 1000;
    private static final int THREAD_COUNT = 4;
    private static final String PATTERN_FORMAT = "dd.MM.yyyy";


    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(Matrix.generateRandomMatrix(MATRIX_ROWS, MATRIX_COLUMNS), MATRIX_ROWS, MATRIX_COLUMNS);
        Matrix matrix2 = new Matrix(Matrix.generateRandomMatrix(MATRIX_ROWS, MATRIX_COLUMNS), MATRIX_ROWS, MATRIX_COLUMNS);
        Matrix resultMatrix = new Matrix(Matrix.generateRandomMatrix(MATRIX_ROWS, MATRIX_COLUMNS), MATRIX_ROWS, MATRIX_COLUMNS);

        runTaskOne(matrix1, matrix2, resultMatrix);
        runTaskTwo(matrix1, matrix2, resultMatrix);
        runTaskThree(matrix1, matrix2, resultMatrix);

        // Method 1.b) Consecutive Row Elements with thread pool.
    }

    public static void runTaskOne(Matrix matrix1, Matrix matrix2, Matrix resultMatrix) {
        // Get tasks
        List<Task> tasks = generateTasksForFirstApproach(matrix1, matrix2, resultMatrix);
        List<Thread> threadList = new ArrayList<>();
        // Method 1.a. Consecutive Row Elements with a thread for each task;
        for (Task task : tasks) {
            threadList.add(new Thread(task));
        }

        Instant startTime = Instant.now();
        threadList.forEach(Thread::start);
        Instant endTime = Instant.now();
        System.out.println("Consecutive Row Elements, threads approach, started at " + startTime +
                " and ended at: " + endTime);

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        startTime = Instant.now();
        tasks.forEach(executorService::submit);
        endTime = Instant.now();
        System.out.println("Consecutive Row Elements, thread pool approach, started at " + startTime +
                " and ended at: " + endTime + " : \n" + "\n");

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Method 1.b) Consecutive Row Elements with thread pool.
    }

    public static void runTaskTwo(Matrix matrix1, Matrix matrix2, Matrix resultMatrix) {
        // Get tasks
        List<Task> tasks = generateTasksForSecondApproach(matrix1, matrix2, resultMatrix);
        List<Thread> threadList = new ArrayList<>();
        // Method 1.a. Consecutive Row Elements with a thread for each task;
        for (Task task : tasks) {
            threadList.add(new Thread(task));
        }

        Instant startTime = Instant.now();
        threadList.forEach(Thread::start);
        Instant endTime = Instant.now();
        System.out.println("Consecutive Columns Elements, threads approach, started at " + startTime +
                " and ended at: " + endTime + " : \n" );

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        startTime = Instant.now();
        tasks.forEach(executorService::submit);
        endTime = Instant.now();
        System.out.println("Consecutive Columns Elements, thread pool approach, started at " + startTime +
                " and ended at: " + endTime);

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Method 1.b) Consecutive Row Elements with thread pool.
    }

    public static void runTaskThree(Matrix matrix1, Matrix matrix2, Matrix resultMatrix) {
        // Get tasks
        List<Task> tasks = generateTasksForThirdApproach(matrix1, matrix2, resultMatrix);
        List<Thread> threadList = new ArrayList<>();
        // Method 1.a. Consecutive Row Elements with a thread for each task;
        for (Task task : tasks) {
            threadList.add(new Thread(task));
        }

        Instant startTime = Instant.now();
        threadList.forEach(Thread::start);
        Instant endTime = Instant.now();
        System.out.println("Consecutive Kth Elements, threads approach, started at " + startTime +
                " and ended at: " + endTime + " : \n");

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        startTime = Instant.now();
        tasks.forEach(executorService::submit);
        endTime = Instant.now();
        System.out.println("Consecutive Kth Elements, thread pool approach, started at " + startTime +
                " and ended at: " + endTime );

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Method 1.b) Consecutive Row Elements with thread pool.
    }

    public static List<Task> generateTasksForFirstApproach(Matrix matrix1, Matrix matrix2, Matrix resultMatrix){
        int totalElements = MATRIX_ROWS * MATRIX_COLUMNS;
        int averageOperationPerformed = totalElements / THREAD_COUNT;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int startingRow = i * averageOperationPerformed / resultMatrix.getColumns();
            int startingColumn = i * averageOperationPerformed % resultMatrix.getColumns();
            if(i == THREAD_COUNT - 1) {
                // this occurs because the final thread may have less/more operations to perform.
                averageOperationPerformed = resultMatrix.getRows() * resultMatrix.getColumns() - i * averageOperationPerformed;
            }
            tasks.add(new RowConsecutiveTask(matrix1, matrix2, resultMatrix, startingRow, startingColumn, averageOperationPerformed));
        }

        return tasks;
    }

    public static List<Task> generateTasksForSecondApproach(Matrix matrix1, Matrix matrix2, Matrix resultMatrix){
        int totalElements = MATRIX_ROWS * MATRIX_COLUMNS;
        int averageOperationPerformed = totalElements / THREAD_COUNT;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int startingRow = i * averageOperationPerformed / resultMatrix.getRows();
            int startingColumn = i * averageOperationPerformed % resultMatrix.getRows();
            if(i == THREAD_COUNT - 1) {
                // this occurs because the final thread may have less/more operations to perform.
                averageOperationPerformed = resultMatrix.getRows() * resultMatrix.getColumns() - i * averageOperationPerformed;
            }
            tasks.add(new ColumnConsecutiveTask(matrix1, matrix2, resultMatrix, startingRow, startingColumn, averageOperationPerformed));
        }

        return tasks;
    }

    public static List<Task> generateTasksForThirdApproach(Matrix matrix1, Matrix matrix2, Matrix resultMatrix){
        int totalElements = MATRIX_ROWS * MATRIX_COLUMNS;
        int averageOperationPerformed = totalElements / THREAD_COUNT;
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            averageOperationPerformed += ((totalElements - averageOperationPerformed * THREAD_COUNT) % THREAD_COUNT);
            tasks.add(new KthConsecutiveTask(matrix1, matrix2, resultMatrix, i, averageOperationPerformed, THREAD_COUNT));
        }

        return tasks;
    }
}