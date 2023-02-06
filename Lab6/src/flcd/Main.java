package flcd;

import flcd.logic.HamiltonianFinder;
import flcd.model.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Graph graph = Graph.provideGraph("src/flcd/resources/graph1.txt");
//        Graph graph = Graph.provideGraph("src/flcd/resources/graph2.txt");
        Graph graph = Graph.provideGraph("src/flcd/resources/graph3.txt");

        HamiltonianFinder hamiltonianFinder = new HamiltonianFinder(graph);
        try {
            System.out.println(hamiltonianFinder.findPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}