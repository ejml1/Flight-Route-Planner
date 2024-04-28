package main;
import java.util.TreeSet;

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

    @Override
    public int compareTo(Coordinate other) {
        int parallelComparison = Integer.compare(this.parallel, other.parallel);
        if (parallelComparison != 0) {
            return parallelComparison;
        }

        return Integer.compare(this.angle, other.angle);
    }

    public String toString() {
        return "(" + this.parallel + ":" + this.angle + ")";
    }
}
