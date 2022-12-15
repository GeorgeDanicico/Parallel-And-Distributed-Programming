package flcd.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {

    private List<Integer> nodes;
    private List<List<Integer>> edges;

    private Graph(List<Integer> _nodes, List<List<Integer>> _edges){
        nodes = _nodes;
        edges = _edges;
    }

    public static Graph provideGraph(String file) {
        try {
            var bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int nodesCount = Integer.parseInt(line);
            List<Integer> nodes = new ArrayList<>();
            List<List<Integer>> edges = new ArrayList<>();
            for (int i = 0; i < nodesCount; i++) {
                nodes.add(i);
                edges.add(new ArrayList<>());
            }

            while ((line = bufferedReader.readLine()) != null) {
                List<Integer> tokens = Stream.of(line.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                int startNode = tokens.get(0);
                for (int i = 1; i < tokens.size(); i++) {
                    edges.get(startNode).add(tokens.get(i));
                }
            }

            return new Graph(nodes, edges);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Integer> edgesFrom(Integer node) {
        return edges.get(node);
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public List<List<Integer>> getEdges() {
        return edges;
    }

    public boolean nodeHasNeighbour(int node, int neighbour) {
        return edges.get(node).contains(neighbour);
    }

    public String toString() {
        return "Graph has the edges: " + edges + " and the edges: " + edges;
    }
}
