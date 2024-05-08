package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import main.World;

public class WorldTest {
 
    @Test
    public void testOedipus2() {
        World world = new World(2, "1:0", "1:45");
        ArrayList<String> states = new ArrayList<String>();
        states.add("(1:0)");
        states.add("(1:45)");
        states.add("(1:90)");
        states.add("(1:135)");
        states.add("(1:180)");
        states.add("(1:225)");
        states.add("(1:270)");
        states.add("(1:315)");
        
        for (String state : world.getCoordinates().keySet())
        {
            assertTrue(states.contains(state));    
        }
    }

    @Test
    public void testOedipus3()
    {
        World world = new World(3, "1:0", "1:45");
        ArrayList<String> states = new ArrayList<String>();
        states.add("(1:0)");
        states.add("(1:45)");
        states.add("(1:90)");
        states.add("(1:135)");
        states.add("(1:180)");
        states.add("(1:225)");
        states.add("(1:270)");
        states.add("(1:315)");
        states.add("(2:0)");
        states.add("(2:45)");
        states.add("(2:90)");
        states.add("(2:135)");
        states.add("(2:180)");
        states.add("(2:225)");
        states.add("(2:270)");
        states.add("(2:315)");
        
        for (String state : world.getCoordinates().keySet())
        {
            assertTrue(states.contains(state));    
        }
    }

    @Test
    public void testNoOrigin()
    {
        World world = new World(3, "1:0", "1:45");

        assertFalse(world.getCoordinates().containsKey("(0:0)"));
    }
}
