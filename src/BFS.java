import java.util.Set;
import java.util.TreeSet;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;

public class BFS {
    
    public static void treeSearch(World problem, Queue<Node> frontier)
    {
        int nExplored = 0;

        Node initialNode = GeneralSearchAlgorithm.makeNode(Optional.empty(), problem.getInitialState());
        frontier.add(initialNode);

        Set<Coordinate> inFrontier = new HashSet<Coordinate>();
        inFrontier.add(problem.getInitialState());

        LinkedList<String> frontierString = new LinkedList<String>();
        frontierString.add(initialNode.getState().toString());
        // System.out.println(frontierToString(frontierString));

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

            Node node = frontier.remove();
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
                TreeSet<Node> successors = expand(node, frontier, explored, inFrontier);
                for (Node state : successors)
                {
                    frontier.add(state);
                    inFrontier.add(state.getState());
                    frontierString.add(state.getState().toString());
                }
                
            }
        } while (true);
    }

    private static TreeSet<Node> expand(Node node, Queue<Node> frontier, Set<Coordinate> explored, Set<Coordinate> inFrontier)
    {
        Set<Node> nextStates = GeneralSearchAlgorithm.successorFn(node);
        TreeSet<Node> successors = new TreeSet<Node>();
        for (Node state : nextStates)
        {
            if (!explored.contains(state.getState()) && !inFrontier.contains(state.getState()))
            {
                Node nd = GeneralSearchAlgorithm.makeNode(Optional.of(node), state.getState());
                successors.add(nd);
            }
        }
        return successors;
    }

    private static String frontierToString(LinkedList<String> frontierString)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
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
