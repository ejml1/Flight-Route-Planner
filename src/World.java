import java.util.ArrayList;
import java.util.HashMap;

public class World {

    private HashMap<String, Coordinate> coordinates;

    public World(int worldSize) {
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
    }

    public HashMap<String,Coordinate> getCoordinates() {
        return this.coordinates;
    }


}
