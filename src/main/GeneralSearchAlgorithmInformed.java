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
    
    public static void search(World problem, PriorityQueue<BestFNode> frontier, String algo)
    {
        int nExplored = 0;

        BestFNode initialNode = GeneralSearchAlgorithmInformed.makeNode(Optional.empty(), problem.getInitialState(), problem.getGoal(), algo);
        frontier.add(initialNode);

        HashMap<Coordinate, BestFNode> inFrontier = new HashMap<Coordinate, BestFNode>();
        inFrontier.put(problem.getInitialState(), initialNode);

        PriorityQueue<BestFNode> frontierDeepCopy = new PriorityQueue<BestFNode>(frontier);

        Set<Coordinate> explored = new HashSet<Coordinate>();
        do
        {         
            if (frontier.isEmpty())
            {
                System.out.println("fail");
                System.out.println(nExplored);
                return;
            }
            System.out.println(frontierToString(frontierDeepCopy));

            BestFNode node = frontier.remove();

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
                TreeSet<BestFNode> successors = new TreeSet<BestFNode>();
                if (algo.equals(BESTF))
                    successors = BestF.expand(node, frontier, explored, inFrontier, problem.getGoal());
                else
                    successors = AStar.expand(node, frontier, explored, inFrontier, problem.getGoal());
                // Add successors in ascending order
                for (BestFNode state : successors)
                {
                    frontier.add(state);
                    inFrontier.put(state.getState(), state);
                }
                frontierDeepCopy = new PriorityQueue<BestFNode>(frontier);
            }
        } while (true);
    }

    /**
     * 
     * @param node Parent node
     * @param state New state for the node
     * @return New node with the new state
     */
    public static BestFNode makeNode(Optional<BestFNode> node, Coordinate state, Coordinate goal, String algo)
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
        
        BestFNode n;
        if (algo.equals(BESTF))
            n = new BestFNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost);
        else
            n = new BestFNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost + pathCost);
        return n;
    }

    public static TreeSet<BestFNode> successorFn(BestFNode node, Coordinate goal, String algo)
    {
        TreeSet<BestFNode> successors = new TreeSet<>();
        Coordinate state = node.getState();
        for (Coordinate neighbour : state.getNeighbours())
        {
            BestFNode newNode = makeNode(Optional.of(node), neighbour, goal, algo);
            successors.add(newNode);
        }
        return successors;
    }

    /**
     * Convert the frontierString to a string
     * @param frontierString A LinkedList that contains the states of the nodes in the frontier
     * @param frontierScoreString A ordered set that contains the f-cost of the nodes in the frontier
     * @return A string representation of the frontierString
     */
    private static String frontierToString(PriorityQueue<BestFNode> frontierDeepCopy)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (!frontierDeepCopy.isEmpty())
        {
            BestFNode node = frontierDeepCopy.remove();
            sb.append(node.getState().toString());
            sb.append(String.format("%.3f", node.getFCost()));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
