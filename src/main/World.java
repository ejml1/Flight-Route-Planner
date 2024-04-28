package main;
import java.util.HashMap;

public class World {

    private int worldSize;
    private HashMap<String, Coordinate> coordinates;
    private Coordinate initialState;
    private Coordinate goal;

    public World(int worldSize, String initialState, String goal) {
        this.worldSize = worldSize;
        this.coordinates = new HashMap<String, Coordinate>();

        int[] angles = {0, 45, 90, 135, 180, 225, 270, 315};

        for (int parallel = 1; parallel < worldSize; parallel++)
        {
            for (int angle : angles)
            {
                Coordinate coordinate = new Coordinate(parallel, angle);
                this.coordinates.put(coordinate.toString(), coordinate);
            }
        }

        for (int parallel = 1; parallel < worldSize; parallel++)
        {
            for (int angle : angles)
            {
                addNeighbours(parallel, angle);
            }
        }

        int initialParallel = Integer.parseInt(initialState.split(":")[0]);
        int initialAngle = Integer.parseInt(initialState.split(":")[1]);
        this.initialState = this.coordinates.get(getCoordinateString(initialParallel, initialAngle));

        int goalParallel = Integer.parseInt(goal.split(":")[0]);
        int goalAngle = Integer.parseInt(goal.split(":")[1]);
        this.goal = this.coordinates.get(getCoordinateString(goalParallel, goalAngle));

    }

    private void addNeighbours(int parallel, int angle) {
        Coordinate coordinate = this.coordinates.get(getCoordinateString(parallel, angle));

        String neighbour1 = getCoordinateString(parallel - 1, angle);
        String neighbour2 = getCoordinateString(parallel + 1, angle);

        String neighbour3 = getCoordinateString(parallel, angle - 45);
        String neighbour4 = getCoordinateString(parallel, angle + 45);

        if (angle == 0)
            neighbour3 = getCoordinateString(parallel, 315);
        else if (angle == 315)
            neighbour4 = getCoordinateString(parallel, 0);

        if (coordinates.containsKey(neighbour1))
            coordinate.addNeighbour(coordinates.get(neighbour1));
        if (coordinates.containsKey(neighbour2))
            coordinate.addNeighbour(coordinates.get(neighbour2));
        if (coordinates.containsKey(neighbour3))
            coordinate.addNeighbour(coordinates.get(neighbour3));
        if (coordinates.containsKey(neighbour4))
            coordinate.addNeighbour(coordinates.get(neighbour4));
    }

    private String getCoordinateString(int parallel, int angle) {
        return "(" + parallel + ":" + angle + ")";
    }

    public HashMap<String,Coordinate> getCoordinates() {
        return this.coordinates;
    }
    public int getWorldSize() {
        return this.worldSize;
    }

    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }
    public void setCoordinates(HashMap<String,Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinate getInitialState() {
        return this.initialState;
    }

    public void setInitialState(Coordinate initialState) {
        this.initialState = initialState;
    }

    public Coordinate getGoal() {
        return this.goal;
    }

    public void setGoal(Coordinate goal) {
        this.goal = goal;
    }

}
