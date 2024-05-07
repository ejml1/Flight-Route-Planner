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
        Set<Coordinate> nextStates = node.getState().getNeighbours();
        TreeSet<ISNode> successors = new TreeSet<ISNode>();
        for (Coordinate state : nextStates)
        {
            ISNode nd = GeneralSearchAlgorithmInformed.makeNode(Optional.of(node), state, goal, GeneralSearchAlgorithmInformed.ASTAR);
            if (!explored.contains(state) && !inFrontier.containsKey(state))
            {   
                successors.add(nd);
            }
            if (inFrontier.containsKey(state))
            {
                ISNode ndInFrontier = inFrontier.get(state);
                if (ndInFrontier.getPathCost() > nd.getPathCost())
                {
                    frontier.remove(ndInFrontier);
                    inFrontier.remove(state);
                    frontier.add(nd);
                    inFrontier.put(state, nd);
                }
            }
        }
        return successors;
    }

}
