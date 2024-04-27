import java.util.Optional;

public class Node {

    private Coordinate state;
    private Optional<Node> parent;
    private String action;
    private int depth;
    private float pathCost;

    public Node() {
        this.state = new Coordinate();
        this.parent = Optional.empty();
        this.action = "";
        this.depth = 0;
        this.pathCost = 0;
    }

    public Node(Coordinate state, Node parent, String newState, int depth, float pathCost) {
        this.state = state;
        this.parent = Optional.of(parent);
        this.action = this.action;
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

    public float getPathCost() {
        return this.pathCost;
    }

    public void setPathCost(float pathCost) {
        this.pathCost = pathCost;
    }


    
}
