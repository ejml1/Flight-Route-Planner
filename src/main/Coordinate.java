package main;
import java.util.TreeSet;

/**
 * Coordinate class is a representation of the state of the search problem
 */
public class Coordinate implements Comparable<Coordinate>{
    
    private int parallel;
    private int angle;
    private TreeSet<Coordinate> neighbours;

    public Coordinate(int parallel, int angle) {
        this.parallel = parallel;
        this.angle = angle;
        this.neighbours = new TreeSet<Coordinate>();
    }

    public void addNeighbour(Coordinate neighbour) {
        this.neighbours.add(neighbour);
    }

    public TreeSet<Coordinate> getNeighbours() {
        return this.neighbours;
    }

    public int getParallel() {
        return this.parallel;
    }

    public void setParallel(int parallel) {
        this.parallel = parallel;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    public void setNeighbours(TreeSet<Coordinate> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Ensures that the coordinates are compared to each other based on their parallel first. If two coordinates have the same parallel, 
     * then they are compared based on their angle.
     */
    @Override
    public int compareTo(Coordinate other) {
        int parallelComparison = Integer.compare(this.parallel, other.parallel);
        if (parallelComparison != 0) {
            return parallelComparison;
        }

        return Integer.compare(this.angle, other.angle);
    }

    /**
     * Wraps the coordinate in a tuple like string (d:angle). For example (1:45)
     */
    public String toString() {
        return "(" + this.parallel + ":" + this.angle + ")";
    }
}
