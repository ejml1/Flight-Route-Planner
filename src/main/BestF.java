package main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class BestF {
    
    public static void search(World problem, PriorityQueue<BestFNode> frontier)
    {
        int nExplored = 0;

        BestFNode initialNode = GeneralSearchAlgorithmInformed.makeNode(Optional.empty(), problem.getInitialState(), problem.getGoal());
        frontier.add(initialNode);

        Set<Coordinate> inFrontier = new HashSet<Coordinate>();
        inFrontier.add(problem.getInitialState());

        LinkedList<String> frontierString = new LinkedList<String>();
        frontierString.add(initialNode.getState().toString());

        Set<Coordinate> explored = new HashSet<Coordinate>();
        do
        {         
            if (frontier.isEmpty())
            {
                System.out.println("fail");
                System.out.println(nExplored);
                return;
            }
            System.out.println(frontierToString(frontierString));

            BestFNode node = frontier.remove();
            frontierString.removeFirst();

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
                TreeSet<BestFNode> successors = expand(node, frontier, explored, inFrontier, problem.getGoal());
                // Add successors in ascending order
                for (BestFNode state : successors)
                {
                    frontier.add(state);
                    inFrontier.add(state.getState());
                    frontierString.add(state.getState().toString());
                }
                
            }
        } while (true);
    }

    private static TreeSet<BestFNode> expand(BestFNode node, PriorityQueue<BestFNode> frontier, Set<Coordinate> explored, Set<Coordinate> inFrontier, Coordinate goal)
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

    /**
     * Convert the frontierString to a string
     * @param frontierString A LinkedList that contains the states of the nodes in the frontier
     * @return A string representation of the frontierString
     */
    private static String frontierToString(LinkedList<String> frontierString)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        // Look at the frontierString in ascending order
        for (String s : frontierString)
        {
            sb.append(s);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}
