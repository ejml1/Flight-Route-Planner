package main;
import java.util.Optional;

public class Node implements Comparable<Node>{

    private Coordinate state;
    private Optional<Node> parent;
    private String action;
    private int depth;
    private double pathCost;

    public Node(Coordinate state, Node parent, String action, int depth, double pathCost) {
        this(state, Optional.of(parent), action, depth, pathCost);
    }
    
    public Node(Coordinate state, Optional<Node> parent, String action, int depth, double pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.pathCost = pathCost;
    }

    public Coordinate getState() {
        return this.state;
    }

    public void setState(Coordinate state) {
        this.state = state;
    }

    public Optional<Node> getParent() {
        return this.parent;
    }

    public void setParent(Optional<Node> parent) {
        this.parent = parent;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getPathCost() {
        return this.pathCost;
    }

    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public int compareTo(Node other) {
        return this.state.compareTo(other.getState());
    }

    
}
