package main;

import java.util.Optional;
import java.util.TreeSet;

public class GeneralSearchAlgorithmInformed {
    
    /**
     * 
     * @param node Parent node
     * @param state New state for the node
     * @return New node with the new state
     */
    public static BestFNode makeNode(Optional<BestFNode> node, Coordinate state, Coordinate goal)
    {
        double stateAngleRadians = Math.toRadians(state.getAngle());
        double goalAngleRadians = Math.toRadians(goal.getAngle());
        double hCost = Math.sqrt(Math.pow(state.getParallel(), 2) + Math.pow(goal.getParallel(), 2) 
            - 2 * state.getParallel() * goal.getParallel() * Math.cos(goalAngleRadians - stateAngleRadians));
        if (node.isEmpty())
        {
            return new BestFNode(state, Optional.empty(), state.toString(), 0, 0, hCost, hCost);
        }
        Node unwrappedNode = node.get();

        String action = unwrappedNode.getAction() + state.toString();
        double pathCost = unwrappedNode.getPathCost() + GeneralSearchAlgorithm.cost(unwrappedNode.getState(), state);
        
        BestFNode n = new BestFNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost);
        return n;
    }

    public static TreeSet<BestFNode> successorFn(BestFNode node, Coordinate goal)
    {
        TreeSet<BestFNode> successors = new TreeSet<>();
        Coordinate state = node.getState();
        for (Coordinate neighbour : state.getNeighbours())
        {
            BestFNode newNode = makeNode(Optional.of(node), neighbour, goal);
            successors.add(newNode);
        }
        return successors;
    }
}
