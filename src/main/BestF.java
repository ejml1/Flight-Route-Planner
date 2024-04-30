package main;

import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class BestF {
    
    public static void search(World problem, PriorityQueue<BestFNode> frontier)
    {
        GeneralSearchAlgorithmInformed.search(problem, frontier, "BestF");
    }

    public static TreeSet<BestFNode> expand(BestFNode node, PriorityQueue<BestFNode> frontier, Set<Coordinate> explored, Set<Coordinate> inFrontier, Coordinate goal)
    {
        Set<BestFNode> nextStates = GeneralSearchAlgorithmInformed.successorFn(node, goal);
        TreeSet<BestFNode> successors = new TreeSet<BestFNode>();
        for (BestFNode state : nextStates)
        {
            if (!explored.contains(state.getState()) && !inFrontier.contains(state.getState()))
            {
                BestFNode nd = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal);
                successors.add(nd);
            }
        }
        return successors;
    }

}
