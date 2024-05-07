package main;
import java.util.Set;
import java.util.TreeSet;
import java.util.Stack;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;

public class DFS {

    public static void treeSearch(World problem, Stack<Node> frontier)
    {
        int nExplored = 0;

        Node initialNode = GeneralSearchAlgorithm.makeNode(Optional.empty(), problem.getInitialState());
        // INSERT-FRONT
        frontier.push(initialNode);

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

            // REMOVE-FRONT
            Node node = frontier.pop();
            inFrontier.remove(node.getState());
            frontierString.removeLast();

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
                TreeSet<Node> successors = expand(node, frontier, explored, inFrontier);
                // Add successors in descending order (INSERT-ALL-FRONT)
                for (int i = successors.size() - 1; i >= 0; i--)
                {
                    Node state = successors.pollLast();
                    frontier.push(state);
                    inFrontier.add(state.getState());
                    frontierString.add(state.getState().toString());
                }
                
            }
        } while (true);
    }

    private static TreeSet<Node> expand(Node node, Stack<Node> frontier, Set<Coordinate> explored, Set<Coordinate> inFrontier)
    {
        // SUCCESSOR-FN
        Set<Coordinate> nextStates = node.getState().getNeighbours();
        TreeSet<Node> successors = new TreeSet<Node>();
        for (Coordinate state : nextStates)
        {
            if (!explored.contains(state) && !inFrontier.contains(state))
            {
                Node nd = GeneralSearchAlgorithm.makeNode(Optional.of(node), state);
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
        // Look at the frontierString in reverse order
        for (int i = frontierString.size() - 1; i >= 0; i--)
        {
            sb.append(frontierString.get(i));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
