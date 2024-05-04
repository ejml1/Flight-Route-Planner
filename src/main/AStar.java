package main;

import java.util.HashMap;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class AStar {
    
    public static void search(World problem, PriorityQueue<ISNode> frontier)
    {
        GeneralSearchAlgorithmInformed.search(problem, frontier, GeneralSearchAlgorithmInformed.ASTAR);
    }

    public static TreeSet<ISNode> expand(ISNode node, PriorityQueue<ISNode> frontier, Set<Coordinate> explored, HashMap<Coordinate, ISNode> inFrontier, Coordinate goal)
    {
        Set<ISNode> nextStates = GeneralSearchAlgorithmInformed.successorFn(node, goal, GeneralSearchAlgorithmInformed.ASTAR);
        TreeSet<ISNode> successors = new TreeSet<ISNode>();
        for (ISNode state : nextStates)
        {
            if (!explored.contains(state.getState()) && !inFrontier.containsKey(state.getState()))
            {
                ISNode nd = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal, GeneralSearchAlgorithmInformed.ASTAR);
                successors.add(nd);
            }
            if (inFrontier.containsKey(state.getState()))
            {
                ISNode nd = inFrontier.get(state.getState());
                if (nd.getPathCost() > state.getPathCost())
                {
                    frontier.remove(nd);
                    inFrontier.remove(state.getState());
                    // TODO: Update Informed Search to match lectures
                    ISNode newNode = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state.getState(), goal, GeneralSearchAlgorithmInformed.ASTAR);
                    successors.add(newNode);
                    // END TODO
                }
            }
        }
        return successors;
    }

}
