package main;

import java.util.HashMap;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class AStar {
    
    public static void search(World problem, PriorityQueue<BestFNode> frontier)
    {
        GeneralSearchAlgorithmInformed.search(problem, frontier, GeneralSearchAlgorithmInformed.ASTAR);
    }

    public static TreeSet<BestFNode> expand(BestFNode node, PriorityQueue<BestFNode> frontier, Set<Coordinate> explored, HashMap<Coordinate, BestFNode> inFrontier, Coordinate goal)
    {
        Set<BestFNode> nextStates = GeneralSearchAlgorithmInformed.successorFn(node, goal, GeneralSearchAlgorithmInformed.ASTAR);
        TreeSet<BestFNode> successors = new TreeSet<BestFNode>();
        for (BestFNode state : nextStates)
        {
            if (!explored.contains(state.getState()) && !inFrontier.containsKey(state.getState()))
            {
                BestFNode nd = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal, GeneralSearchAlgorithmInformed.ASTAR);
                successors.add(nd);
            }
            if (inFrontier.containsKey(state.getState()))
            {
                BestFNode nd = inFrontier.get(state.getState());
                if (nd.getPathCost() > state.getPathCost())
                {
                    frontier.remove(nd);
                    inFrontier.remove(state.getState());
                    BestFNode newNode = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal, GeneralSearchAlgorithmInformed.ASTAR);
                    successors.add(newNode);
                }
            }
        }
        return successors;
    }

}
