package main;

import java.util.Optional;

public class ISNode extends Node {
    
    private double hCost;
    private double fCost;

    public ISNode(Coordinate state, Node parent, String action, int depth, double pathCost, double hCost, double fCost) {
        super(state, parent, action, depth, pathCost);
        this.hCost = hCost;
        this.fCost = fCost;
    }

    public ISNode(Coordinate state, Optional<Node> parent, String action, int depth, double pathCost, double hCost, double fCost) {
        super(state, parent, action, depth, pathCost);
        this.hCost = hCost;
        this.fCost = fCost;
    }

    public double getHCost() {
        return this.hCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    public double getFCost() {
        return this.fCost;
    }

    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    @Override
    public int compareTo(Node other) {
        if (other instanceof ISNode) {
            ISNode otherISNode = (ISNode) other;
            double otherFCost = otherISNode.getFCost();
            if (this.fCost == otherFCost)
                return super.compareTo(otherISNode);
            else
                return Double.compare(this.fCost, otherFCost);
        }
        return super.compareTo(other);
    }
}
