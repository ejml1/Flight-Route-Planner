package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;
import java.util.PriorityQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.World;
import main.BestFNode;
import main.Coordinate;
import main.Node;
import main.BestF;

public class BestFTest {
    
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
    public void testBestFNodeOrder()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        BestFNode n1 = new BestFNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 2);
        BestFNode n2 = new BestFNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n2);
        assert(frontier.poll() == n1);
    }

    @Test
    public void testBestFNodeTieBreak1()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        BestFNode n1 = new BestFNode(new Coordinate(0, 45), dummyNode, "", 0, 0, 0, 1);
        BestFNode n2 = new BestFNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n2);
        assert(frontier.poll() == n1);
    }

    @Test
    public void testBestFNodeTieBreak2()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        BestFNode n1 = new BestFNode(new Coordinate(0, 45), dummyNode, "", 0, 0, 0, 1);
        BestFNode n2 = new BestFNode(new Coordinate(0, 90), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n1);
        assert(frontier.poll() == n2);
    }

    @Test
    public void testNormalPath1()
    {
        World world = new World(3, "1:180", "1:315");
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        BestF.search(world, frontier);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("(1:180)(1:225)(1:270)(1:315)", actualLines[actualLines.length - 3]);
    }

    @Test
    public void testNormalPath2()
    {
        World world = new World(3, "1:0", "2:45");
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        BestF.search(world, frontier);

        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("(1:0)(1:45)(2:45)", actualLines[actualLines.length - 3]);
    }

    @Test
    public void testOriginGoal1()
    {
        World world = new World(2, "1:0", "0:0");
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        BestF.search(world, frontier);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }

    @Test
    public void testOriginGoal2()
    {
        World world = new World(2, "1:0", "0:180");
        PriorityQueue<BestFNode> frontier = new PriorityQueue<BestFNode>();
        BestF.search(world, frontier);
        
        String [] actualLines = outContent.toString().trim().split("\\r?\\n");

        assertEquals("fail", actualLines[actualLines.length - 2]);
    }
}
