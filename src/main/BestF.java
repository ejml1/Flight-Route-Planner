package main;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

public class BestF {
    
    public static void search(World problem, PriorityQueue<ISNode> frontier)
    {
        GeneralSearchAlgorithmInformed.search(problem, frontier, "BestF");
    }

    public static TreeSet<ISNode> expand(ISNode node, PriorityQueue<ISNode> frontier, Set<Coordinate> explored, HashMap<Coordinate, ISNode> inFrontier, Coordinate goal)
    {
        Set<ISNode> nextStates = GeneralSearchAlgorithmInformed.successorFn(node, goal, GeneralSearchAlgorithmInformed.BESTF);
        TreeSet<ISNode> successors = new TreeSet<ISNode>();
        for (ISNode state : nextStates)
        {
            if (!explored.contains(state.getState()) && !inFrontier.containsKey(state.getState()))
            {
                ISNode nd = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal, GeneralSearchAlgorithmInformed.BESTF);
                successors.add(nd);
            }
        }
        return successors;
    }

}
