package main;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class GeneralSearchAlgorithmInformed {
    
    static final String BESTF = "BestF";
    static final String ASTAR = "AStar";
    
    public static void search(World problem, PriorityQueue<ISNode> frontier, String algo)
    {
        int nExplored = 0;

        ISNode initialNode = GeneralSearchAlgorithmInformed.makeNode(Optional.empty(), problem.getInitialState(), problem.getGoal(), algo);
        frontier.add(initialNode);

        HashMap<Coordinate, ISNode> inFrontier = new HashMap<Coordinate, ISNode>();
        inFrontier.put(problem.getInitialState(), initialNode);

        Set<Coordinate> explored = new HashSet<Coordinate>();
        do
        {         
            if (frontier.isEmpty())
            {
                System.out.println("fail");
                System.out.println(nExplored);
                return;
            }
            System.out.println(frontierToString(frontier));

            ISNode node = frontier.remove();
            inFrontier.remove(node.getState());

            explored.add(node.getState());
            nExplored++;
            if (GeneralSearchAlgorithm.goalTest(node.getState(), problem.getGoal()))
            {
                System.out.println(node.getAction());
                System.out.println(String.format("%.3f", node.getPathCost()));
                System.out.println(nExplored);
                return;
            }
            else
            {
                TreeSet<ISNode> successors = new TreeSet<ISNode>();
                if (algo.equals(BESTF))
                    successors = BestF.expand(node, frontier, explored, inFrontier, problem.getGoal());
                else
                    successors = AStar.expand(node, frontier, explored, inFrontier, problem.getGoal());
                // Add successors in ascending order
                for (ISNode state : successors)
                {
                    frontier.add(state);
                    inFrontier.put(state.getState(), state);
                }
            }
        } while (true);
    }

    /**
     * @param node Parent node
     * @param state New state for the node
     * @param goal Goal state
     * @param algo Algorithm to use (BestF, AStar)
     * @return New node with the new state
     */
    public static ISNode makeNode(Optional<ISNode> node, Coordinate state, Coordinate goal, String algo)
    {
        double stateAngleRadians = Math.toRadians(state.getAngle());
        double goalAngleRadians = Math.toRadians(goal.getAngle());
        double hCost = Math.sqrt(Math.pow(state.getParallel(), 2) + Math.pow(goal.getParallel(), 2) 
            - 2 * state.getParallel() * goal.getParallel() * Math.cos(goalAngleRadians - stateAngleRadians));
        if (node.isEmpty())
        {
            return new ISNode(state, Optional.empty(), state.toString(), 1, 0, hCost, hCost);
        }
        Node unwrappedNode = node.get();

        String action = unwrappedNode.getAction() + state.toString();
        double pathCost = unwrappedNode.getPathCost() + GeneralSearchAlgorithm.cost(unwrappedNode.getState(), state);
        
        ISNode n;
        if (algo.equals(BESTF))
            n = new ISNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost);
        else
            n = new ISNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost + pathCost);
        return n;
    }

    /**
     * Convert the frontier to a string representation of the states
     * @param frontier A priority queue of nodes
     * @return A string representation of the frontier
     */
    public static String frontierToString(PriorityQueue<? extends ISNode> frontier)
    {
        PriorityQueue<? extends ISNode> frontierDeepCopy = new PriorityQueue<ISNode>(frontier);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while(!frontierDeepCopy.isEmpty())
        {
            ISNode node = frontierDeepCopy.remove();
            sb.append(node.getState().toString());
            sb.append(String.format("%.3f", node.getFCost()));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
