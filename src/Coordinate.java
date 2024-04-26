import java.util.ArrayList;

public class Coordinate {
    
    private int parallel;
    private int angle;
    private ArrayList<Coordinate> neighbours;

    public Coordinate(int parallel, int angle) {
        this.parallel = parallel;
        this.angle = angle;
        this.neighbours = new ArrayList<Coordinate>();
    }

    public void addNeighbour(Coordinate neighbour) {
        this.neighbours.add(neighbour);
    }

    public ArrayList<Coordinate> getNeighbours() {
        return this.neighbours;
    }

    public String toString() {
        return "(" + this.parallel + ":" + this.angle + ")";
    }
}
