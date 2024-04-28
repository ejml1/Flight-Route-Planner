package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.DFS;
import main.Node;
import main.World;

public class DFSTest {

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
        Stack<Node> frontier = new Stack<Node>();
        DFS.treeSearch(world, frontier);

        String[] expectedLines = {
            "[(1:0)]",
            "[(1:45),(1:315)]",
            "[(1:90),(1:315)]",
            "[(1:135),(1:315)]",
            "[(1:180),(1:315)]",
            "(1:0)(1:45)(1:90)(1:135)(1:180)",
            "3.142",
            "5"
        };

        String[] actualLines = outContent.toString().trim().split("\\r?\\n");

        for (int i = 0; i < expectedLines.length; i++)
        {
            assertEquals(expectedLines[i], actualLines[i]);
        }
    }
}
