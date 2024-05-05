package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
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
    public void testShrinkFrontier()
    {
        Coordinate c1 = new Coordinate(1, 0);
        Coordinate c2 = new Coordinate(1, 45);
        Coordinate c3 = new Coordinate(1, 90);
        Coordinate c4 = new Coordinate(2, 0);

        SMAStarNode node1 = new SMAStarNode(c1, Optional.empty(), "", 0, 0, 0, 0, new TreeSet<>(), true);
        SMAStarNode node2 = new SMAStarNode(c2, node1, "", 0, 0, 0, 1, new TreeSet<>(), true);
        SMAStarNode node3 = new SMAStarNode(c3, node1, "", 0, 0, 0, 2, new TreeSet<>(), true);
        SMAStarNode node4 = new SMAStarNode(c4, node1, "", 0, 0, 0, 3, new TreeSet<>(), true);

        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        frontier.add(node2);
        frontier.add(node3);
        frontier.add(node4);

        HashMap<Coordinate, SMAStarNode> inFrontier = new HashMap<Coordinate, SMAStarNode>();
        inFrontier.put(c2, node2);
        inFrontier.put(c3, node3);
        inFrontier.put(c4, node4);

        PriorityQueue<SMAStarNode> shrunkenFrontier = SMAStar.shrinkFrontier(frontier, 2, inFrontier);

        assertEquals(2, shrunkenFrontier.size());
        assertTrue(shrunkenFrontier.contains(node2));
        assertTrue(shrunkenFrontier.contains(node3));

        assertEquals(1, node1.getForgotten().size());
        assertTrue(node1.getForgotten().contains(node4));
    }

    @Test
    public void testShrinkFrontierReAddParent()
    {
        Coordinate c1 = new Coordinate(1, 0);
        Coordinate c2 = new Coordinate(2, 0);
        Coordinate c3 = new Coordinate(2, 45);
        Coordinate c4 = new Coordinate(3, 0);
        Coordinate c5 = new Coordinate(3, 45);
        Coordinate c6 = new Coordinate(3, 90);

        SMAStarNode node1 = new SMAStarNode(c1, Optional.empty(), "", 1, 0, 0, 1, new TreeSet<>(), true);
        SMAStarNode node2 = new SMAStarNode(c2, node1, "", 2, 0, 0, 2, new TreeSet<>(), true);
        SMAStarNode node3 = new SMAStarNode(c3, node1, "", 2, 0, 0, 2, new TreeSet<>(), true);
        SMAStarNode node4 = new SMAStarNode(c4, node2, "", 3, 0, 0, 3, new TreeSet<>(), true);
        SMAStarNode node5 = new SMAStarNode(c5, node2, "", 3, 0, 0, 3, new TreeSet<>(), true);
        SMAStarNode node6 = new SMAStarNode(c6, node3, "", 2, 0, 0, 3, new TreeSet<>(), true);
    

        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        frontier.add(node4);
        frontier.add(node5);
        frontier.add(node6);

        HashMap<Coordinate, SMAStarNode> inFrontier = new HashMap<Coordinate, SMAStarNode>();
        inFrontier.put(c4, node4);
        inFrontier.put(c5, node5);
        inFrontier.put(c6, node6);

        PriorityQueue<SMAStarNode> shrunkenFrontier = SMAStar.shrinkFrontier(frontier, 2, inFrontier);

        assertEquals(2, shrunkenFrontier.size());
        assertTrue(shrunkenFrontier.contains(node3));
        assertTrue(shrunkenFrontier.contains(node4));

        assertEquals(3, node3.getFCost());
        assertEquals(1, node3.getForgotten().size());
    }

    @Test
    public void testGetWorstLeafNode()
    {
        SMAStarNode node1 = new SMAStarNode(new Coordinate(1, 0), Optional.empty(), "", 0, 0, 0, 2, new TreeSet<>(), true);
        SMAStarNode node2 = new SMAStarNode(new Coordinate(1, 45), Optional.empty(), "", 0, 0, 0, 1, new TreeSet<>(), true);

        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        frontier.add(node1);
        frontier.add(node2);

        assertEquals(node1, SMAStar.getWorstLeafNode(frontier));
    }

    @Test
    public void testGetWorstLeafNodeTieBreak()
    {
        SMAStarNode node1 = new SMAStarNode(new Coordinate(1, 0), Optional.empty(), "", 0, 0, 0, 1, new TreeSet<>(), true);
        SMAStarNode node2 = new SMAStarNode(new Coordinate(1, 45), Optional.empty(), "", 0, 0, 0, 1, new TreeSet<>(), true);
        SMAStarNode node3 = new SMAStarNode(new Coordinate(2, 45), Optional.empty(), "", 0, 0, 0, 1, new TreeSet<>(), true);
        SMAStarNode node4 = new SMAStarNode(new Coordinate(2, 0), Optional.empty(), "", 0, 0, 0, 1, new TreeSet<>(), true);

        PriorityQueue<SMAStarNode> frontier = new PriorityQueue<SMAStarNode>();
        frontier.add(node1);
        frontier.add(node2);
        frontier.add(node3);
        frontier.add(node4);

        assertEquals(node3, SMAStar.getWorstLeafNode(frontier));
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
