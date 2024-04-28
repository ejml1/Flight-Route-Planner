package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.BFS;
import main.Node;
import main.World;

public class BFSTest {
    
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

    /**
     * Test that the flight cannot go over the pole (0:angle)
     */
    @Test
    public void testOpposite()
    {
        World world = new World(2, "1:0", "1:180");
        Queue<Node> frontier = new LinkedList<Node>();
        BFS.treeSearch(world, frontier);

        String[] expectedLines = {
            "[(1:0)]",
            "[(1:45),(1:315)]",
            "[(1:315),(1:90)]",
            "[(1:90),(1:270)]",
            "[(1:270),(1:135)]",
            "[(1:135),(1:225)]",
            "[(1:225),(1:180)]",
            "[(1:180)]",
            "(1:0)(1:45)(1:90)(1:135)(1:180)",
            "3.142",
            "8"
        };

        String[] actualLines = outContent.toString().trim().split("\\r?\\n");

        for (int i = 0; i < expectedLines.length; i++)
        {
            assertEquals(expectedLines[i], actualLines[i]);
        }
    }

    /**
     * Test that the there is no path found if the destination goes beyond the last parallel
     */
    @Test
    public void testWithinParallel()
    {
        World world = new World(2, "1:0", "2:0");
        Queue<Node> frontier = new LinkedList<Node>();
        BFS.treeSearch(world, frontier);

        String[] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    /**
     * Test that different pole coordinates are valid but the flight cannot go over the pole (0:angle)
     */
    @Test
    public void testPoleCoordinates()
    {
        World world = new World(5, "2:45", "0:90");
        Queue<Node> frontier = new LinkedList<Node>();
        BFS.treeSearch(world, frontier);

        String[] expectedLines = {
            "[(2:45)]",
            "[(1:45),(2:0),(2:90),(3:45)]",
            "[(2:0),(2:90),(3:45),(1:0),(1:90)]",
            "[(2:90),(3:45),(1:0),(1:90),(2:315),(3:0)]",
            "[(3:45),(1:0),(1:90),(2:315),(3:0),(2:135),(3:90)]",
            "[(1:0),(1:90),(2:315),(3:0),(2:135),(3:90),(4:45)]",
            "[(1:90),(2:315),(3:0),(2:135),(3:90),(4:45),(1:315)]",
            "[(2:315),(3:0),(2:135),(3:90),(4:45),(1:315),(1:135)]",
            "[(3:0),(2:135),(3:90),(4:45),(1:315),(1:135),(2:270),(3:315)]",
            "[(2:135),(3:90),(4:45),(1:315),(1:135),(2:270),(3:315),(4:0)]",
            "[(3:90),(4:45),(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135)]",
            "[(4:45),(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90)]",
            "[(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90)]",
            "[(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90),(1:270)]",
            "[(2:270),(3:315),(4:0),(2:180),(3:135),(4:90),(1:270),(1:180)]",
            "[(3:315),(4:0),(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270)]",
            "[(4:0),(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315)]",
            "[(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315)]",
            "[(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315),(3:180)]",
            "[(4:90),(1:270),(1:180),(2:225),(3:270),(4:315),(3:180),(4:135)]",
            "[(1:270),(1:180),(2:225),(3:270),(4:315),(3:180),(4:135)]",
            "[(1:180),(2:225),(3:270),(4:315),(3:180),(4:135),(1:225)]",
            "[(2:225),(3:270),(4:315),(3:180),(4:135),(1:225)]",
            "[(3:270),(4:315),(3:180),(4:135),(1:225),(3:225)]",
            "[(4:315),(3:180),(4:135),(1:225),(3:225),(4:270)]",
            "[(3:180),(4:135),(1:225),(3:225),(4:270)]",
            "[(4:135),(1:225),(3:225),(4:270),(4:180)]",
            "[(1:225),(3:225),(4:270),(4:180)]",
            "[(3:225),(4:270),(4:180)]",
            "[(4:270),(4:180),(4:225)]",
            "[(4:180),(4:225)]",
            "[(4:225)]",
            "fail",
            "32"
        };

        String[] actualLines = outContent.toString().trim().split("\\r?\\n");

        for (int i = 0; i < expectedLines.length; i++)
        {
            assertEquals(expectedLines[i], actualLines[i]);
        }
    }
}
