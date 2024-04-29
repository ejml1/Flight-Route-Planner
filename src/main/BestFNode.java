package main;

import java.util.Optional;

public class BestFNode extends Node {
    
    private double hCost;
    private double fCost;

    public BestFNode(Coordinate state, Node parent, String action, int depth, double pathCost, double hCost, double fCost) {
        super(state, parent, action, depth, pathCost);
        this.hCost = hCost;
        this.fCost = fCost;
    }

    public BestFNode(Coordinate state, Optional<Node> parent, String action, int depth, double pathCost, double hCost, double fCost) {
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
        if (other instanceof BestFNode) {
            BestFNode otherBestFNode = (BestFNode) other;
            double otherFCost = otherBestFNode.getFCost();
            if (this.fCost == otherFCost)
                return super.compareTo(otherBestFNode);
            else
                return Double.compare(this.fCost, otherFCost);
        }
        return super.compareTo(other);
    }
}
