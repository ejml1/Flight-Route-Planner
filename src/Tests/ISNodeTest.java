package Tests;

import java.util.Optional;
import java.util.PriorityQueue;

import org.junit.Test;

import main.Coordinate;
import main.ISNode;
import main.Node;

public class ISNodeTest {
    
    @Test
    public void testISNodeOrder()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        ISNode n1 = new ISNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 2);
        ISNode n2 = new ISNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n2);
        assert(frontier.poll() == n1);
    }

    @Test
    public void testISNodeTieBreak1()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        ISNode n1 = new ISNode(new Coordinate(0, 45), dummyNode, "", 0, 0, 0, 1);
        ISNode n2 = new ISNode(new Coordinate(0, 0), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n2);
        assert(frontier.poll() == n1);
    }

    @Test
    public void testISNodeTieBreak2()
    {
        Node dummyNode = new Node(new Coordinate(0, 0), Optional.empty(), "", 0, 0);
        ISNode n1 = new ISNode(new Coordinate(0, 45), dummyNode, "", 0, 0, 0, 1);
        ISNode n2 = new ISNode(new Coordinate(0, 90), dummyNode, "", 0, 0, 0, 1);
        PriorityQueue<ISNode> frontier = new PriorityQueue<ISNode>();
        frontier.add(n1);
        frontier.add(n2);
        assert(frontier.poll() == n1);
        assert(frontier.poll() == n2);
    }
}
