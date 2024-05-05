package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Coordinate;
import main.SMAStar;
import main.SMAStarNode;
import main.World;

public class SMAStarTest {

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
    public void testNormalPath1Fail()
    {
        World world = new World(3, "1:180", "1:315");
        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        SMAStar.search(world, frontier, 3);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    @Test
    public void testNormalPath1Success()
    {
        World world = new World(4, "1:180", "1:315");
        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        SMAStar.search(world, frontier, 4);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        System.out.println(frontier.size());
        assertEquals("(1:180)(1:225)(1:270)(1:315)", actualLines[actualLines.length - 3]);
    }

    @Test
    public void testOriginGoal1()
    {
        World world = new World(2, "1:0", "0:0");
        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        SMAStar.search(world, frontier, 2);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    @Test
    public void testOriginGoal2()
    {
        World world = new World(2, "1:0", "0:180");
        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        SMAStar.search(world, frontier, 2);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    @Test
    public void testAncestors()
    {
        SMAStarNode parent = new SMAStarNode(new Coordinate(1, 0), Optional.empty(), "", 0, 0, 0, 0, new TreeSet<>(), false);
        SMAStarNode child1 = new SMAStarNode(new Coordinate(2, 0), Optional.of(parent), "", 0, 0, 0, 0, new TreeSet<>(), false);
        SMAStarNode child2 = new SMAStarNode(new Coordinate(3, 0), Optional.of(child1), "", 0, 0, 0, 0, new TreeSet<>(), false);

        TreeSet<SMAStarNode> ancestors = SMAStar.ancestors(child2);
        assert(ancestors.contains(child1));
        assert(ancestors.contains(parent));
    }

}
