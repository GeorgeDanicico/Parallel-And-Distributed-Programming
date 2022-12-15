package flcd.logic;

import flcd.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class HamiltonianFinder {
    private static final int THREADS_COUNT = Runtime.getRuntime().availableProcessors();
    private Graph graph;

    public HamiltonianFinder(Graph graph) {
        this.graph = graph;
    }

    public String findPath() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
        List<Integer> path = new ArrayList<>();
        new SearchCycle(graph, 0, path).search(0);

        for(int i = 0; i < graph.getNodes().size(); i++) {
            executorService.submit(new SearchCycle(graph, i, path));
        }
        executorService.shutdown();
        executorService.awaitTermination(50, TimeUnit.SECONDS);

        if (path.size() == graph.getNodes().size() + 1) {
            return path.toString();
        }
        else {
            return "There are no hamiltonian cycles.";
        }
    }

}
