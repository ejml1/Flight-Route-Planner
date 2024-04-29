package Tests;

import java.util.Optional;
import java.util.PriorityQueue;

import org.junit.Test;

import main.World;
import main.BestFNode;
import main.Coordinate;
import main.Node;

public class BestFTest {
    
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
}
