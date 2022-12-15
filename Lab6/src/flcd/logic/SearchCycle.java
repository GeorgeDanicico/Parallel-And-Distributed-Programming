package flcd.logic;

import flcd.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SearchCycle implements Runnable {
    private Graph graph;
    private int start;
    private static AtomicBoolean found = new AtomicBoolean(false);
    //    private List<Integer> possiblePath;
    private List<Integer> path;
    private static Lock lock = new ReentrantLock();
    private List<Boolean> visited;
    private List<Integer> validPath;

    public SearchCycle(Graph _graph, int _start, List<Integer> validPath) {
        this.graph = _graph;
        this.start = _start;
        this.validPath = validPath;
        path = new ArrayList<>(_graph.getNodes().size());
        visited = new ArrayList<>(_graph.getNodes().size());

        for (int i = 0; i < _graph.getNodes().size(); i++) {
            visited.add(false);
        }
    }

    private void hamiltonianCycleFound() {
        this.lock.lock();
        this.path.add(this.start);
        this.found.set(true);
        this.validPath.clear();
        this.validPath.addAll(this.path);
        this.lock.unlock();
    }

    public void search(int node) {
        if (found.get()) {
            return;
        }

        path.add(node);
        visited.set(node, true);

        if (path.size() == graph.getNodes().size() && graph.nodeHasNeighbour(node, start)) {
            this.hamiltonianCycleFound();
        } else {
            for (int neighbour : this.graph.edgesFrom(node)) {
                if (!this.visited.get(neighbour)) {
                    this.search(neighbour);
                    if (found.get() == false) {
                        path.remove((Integer) neighbour);
                        visited.set(neighbour, false);
                    }
//                    return;
                }
            }
        }
    }

    @Override
    public void run() {
        search(start);
    }
}
