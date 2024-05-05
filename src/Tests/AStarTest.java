package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.AStar;
import main.ISNode;
import main.World;

public class AStarTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }    

    @Test
    public void testNormalPath1()
    {
        World world = new World(3, "1:180", "1:315");
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        AStar.search(world, frontier);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("(1:180)(1:225)(1:270)(1:315)", actualLines[actualLines.length - 3]);
    }

    @Test
    public void testNormalPath2()
    {
        World world = new World(3, "1:0", "2:45");
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        AStar.search(world, frontier);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("(1:0)(1:45)(2:45)", actualLines[actualLines.length - 3]);
    }

    @Test
    public void testOriginGoal1()
    {
        World world = new World(2, "1:0", "0:0");
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        AStar.search(world, frontier);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    @Test
    public void testOriginGoal2()
    {
        World world = new World(2, "1:0", "0:180");
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        AStar.search(world, frontier);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

}
