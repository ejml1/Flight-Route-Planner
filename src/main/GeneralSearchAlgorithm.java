package main;
import java.util.Optional;
import java.util.TreeSet;

public class GeneralSearchAlgorithm {

    /**
     * 
     * @param node Parent node
     * @param state New state for the node
     * @return New node with the new state
     */
    public static Node makeNode(Optional<Node> node, Coordinate state)
    {
        if (node.isEmpty())
        {
            return new Node(state, Optional.empty(), state.toString(), 1, 0);
        }
        Node unwrappedNode = node.get();

        String action = unwrappedNode.getAction() + state.toString();
        double pathCost = unwrappedNode.getPathCost() + cost(unwrappedNode.getState(), state);
        
        Node n = new Node(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost);
        return n;
    }
    
    public static double cost(Coordinate s1, Coordinate s2)
    {
        int parallel1 = s1.getParallel();

        int parallel2 = s2.getParallel();

        if (Math.abs(parallel1 - parallel2)  == 1)
            return 1;
        else if (parallel1 == parallel2)
        {
            double distance = (2 * Math.PI * parallel1) / 8;
            return distance;
        }
        
        System.out.println("Coordinates are not adjacent");
        System.exit(1);
        return 0;
    }

    public static boolean goalTest(Coordinate state, Coordinate goal)
    {
        return state.equals(goal);
    }

    public static TreeSet<Node> successorFn(Node node)
    {
        TreeSet<Node> successors = new TreeSet<>();
        Coordinate state = node.getState();
        for (Coordinate neighbour : state.getNeighbours())
        {
            Node newNode = makeNode(Optional.of(node), neighbour);
            successors.add(newNode);
        }
        return successors;
    }

}
