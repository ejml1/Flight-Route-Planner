package main;

import java.util.Optional;
import java.util.TreeSet;

public class SMAStarNode extends ISNode {

    private TreeSet<SMAStarNode> forgotten;
    private boolean isLeaf;

    public SMAStarNode(Coordinate state, Node parent, String action, int depth, double pathCost, double hCost, double fCost, TreeSet<SMAStarNode> forgotten, boolean isLeaf) {
        super(state, parent, action, depth, pathCost, hCost, fCost);
        this.forgotten = forgotten;
        this.isLeaf = isLeaf;
    }

    public SMAStarNode(Coordinate state, Optional<Node> parent, String action, int depth, double pathCost, double hCost, double fCost, TreeSet<SMAStarNode> forgotten, boolean isLeaf) {
        super(state, parent, action, depth, pathCost, hCost, fCost);
        this.forgotten = forgotten;
        this.isLeaf = isLeaf;
    }

    public void addForgotten(SMAStarNode node) {
        this.forgotten.add(node);
    }

    public void removeForgotten(SMAStarNode node) {
        this.forgotten.remove(node);
    }

    public TreeSet<SMAStarNode> getForgotten() {
        return this.forgotten;
    }

    public void setForgotten(TreeSet<SMAStarNode> forgotten) {
        this.forgotten = forgotten;
    }

    public boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
  
}
