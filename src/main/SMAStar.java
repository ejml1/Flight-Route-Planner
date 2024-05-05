package main;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Optional;

public class SMAStar {
    
    static final double INF = 10000.0;

    public static void search(World problem, PriorityQueue<SMAStarNode> frontier, int mem)
    {
        int nExplored = 0;

        SMAStarNode initialNode = makeNode(Optional.empty(), problem.getInitialState(), problem.getGoal());
        frontier.add(initialNode);

        HashMap<Coordinate, SMAStarNode> inFrontier = new HashMap<Coordinate, SMAStarNode>();
        inFrontier.put(problem.getInitialState(), initialNode);

        PriorityQueue<ISNode> frontierDeepCopy = new PriorityQueue<>(frontier);
        do
        {
            if (frontier.isEmpty())
            {
                System.out.println("fail");
                System.out.println(nExplored);
                return;
            }
            System.out.println(GeneralSearchAlgorithmInformed.frontierToString(frontierDeepCopy));

            SMAStarNode node = frontier.remove();
            inFrontier.remove(node.getState());

            nExplored++;
            
            if (GeneralSearchAlgorithm.goalTest(node.getState(), problem.getGoal()))
            {
                System.out.println(node.getAction());
                System.out.println(String.format("%.3f", node.getPathCost()));
                System.out.println(nExplored);
                return;
            }
            else
            {
                if (node.getFCost() == INF)
                {
                    System.out.println("fail");
                    System.out.println(nExplored);
                    return;
                }
                else
                {
                    TreeSet<SMAStarNode> succList = processExpand(node, problem, frontier, inFrontier, problem.getGoal(), mem);
                    for (SMAStarNode ns : succList)
                    {
                        frontier.add(ns);
                        inFrontier.put(ns.getState(), ns);
                    }
                    frontier = shrinkFrontier(frontier, mem, inFrontier);
                }
                frontierDeepCopy = new PriorityQueue<>(frontier);
            }
        } while (true);
    }

    private static TreeSet<SMAStarNode> processExpand(SMAStarNode nd, World problem, PriorityQueue<SMAStarNode> frontier, HashMap<Coordinate, SMAStarNode> inFrontier, Coordinate goal, int mem)
    {
        TreeSet<SMAStarNode> succList;
        if (nd.getForgotten().isEmpty())
        {
            succList = expand(nd, problem, frontier, inFrontier, goal);
        }
        else
        {
            succList = nd.getForgotten();
        }
        TreeSet<SMAStarNode> toAdd = new TreeSet<>();
        for (SMAStarNode ns : succList)
        {
            if (nd.getForgotten().contains(ns))
            {
                nd.getForgotten().remove(ns);
            }
            else
            {
                // ns.getDepth + 1 to account for current depth of ns
                if (!GeneralSearchAlgorithm.goalTest(ns.getState(), goal) && ns.getDepth() + 1 == mem)
                {
                    ns.setFCost(INF);
                }
                ns.setIsLeaf(true);
                SMAStarNode nsParent = (SMAStarNode) ns.getParent().get();
                nsParent.setIsLeaf(false);
                toAdd.add(ns);
            }
        }
        succList.addAll(toAdd);
        return succList;
    }

    private static TreeSet<SMAStarNode> expand(SMAStarNode node, World problem, PriorityQueue<SMAStarNode> frontier, HashMap<Coordinate, SMAStarNode> inFrontier, Coordinate goal)
    {
        Set<SMAStarNode> nextStates = successorFn(node, problem);
        TreeSet<SMAStarNode> successors = new TreeSet<SMAStarNode>();
        for (SMAStarNode state : nextStates)
        {
            if (!inFrontier.containsKey(state.getState()))
            {
                SMAStarNode nd = makeNode(Optional.of(node), state.getState(), goal);
                successors.add(nd);
            }
            if (inFrontier.containsKey(state.getState()))
            {
                SMAStarNode nd = inFrontier.get(state.getState());
                if (nd.getPathCost() > state.getPathCost())
                {
                    frontier.remove(nd);
                    inFrontier.remove(state.getState());
                    SMAStarNode newNode = makeNode(Optional.of(node), state.getState(), goal);
                    frontier.add(newNode);
                    inFrontier.put(state.getState(), newNode);
                }
            }
        }
        return successors;
    }

    private static SMAStarNode makeNode(Optional<SMAStarNode> node, Coordinate state, Coordinate goal) 
    {
        double stateAngleRadians = Math.toRadians(state.getAngle());
        double goalAngleRadians = Math.toRadians(goal.getAngle());
        double hCost = Math.sqrt(Math.pow(state.getParallel(), 2) + Math.pow(goal.getParallel(), 2) 
            - 2 * state.getParallel() * goal.getParallel() * Math.cos(goalAngleRadians - stateAngleRadians));
        if (node.isEmpty())
        {
            return new SMAStarNode(state, Optional.empty(), state.toString(), 0, 0, hCost, hCost, 
                new TreeSet<SMAStarNode>(), false);
        }
        Node unwrappedNode = node.get();

        String action = unwrappedNode.getAction() + state.toString();
        double pathCost = unwrappedNode.getPathCost() + GeneralSearchAlgorithm.cost(unwrappedNode.getState(), state);

        SMAStarNode n = new SMAStarNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost + pathCost, 
            new TreeSet<SMAStarNode>(), false);
        return n;
    }

    private static TreeSet<SMAStarNode> successorFn(SMAStarNode node, World problem)
    {
        TreeSet<SMAStarNode> successors = new TreeSet<>();
        Coordinate state = node.getState();
        for (Coordinate neighbour : state.getNeighbours())
        {
            SMAStarNode newNode = makeNode(Optional.of(node), neighbour, problem.getGoal());
            successors.add(newNode);
        }
        return successors;
    }

    private static PriorityQueue<SMAStarNode> shrinkFrontier(PriorityQueue<SMAStarNode> frontier, int mem, HashMap<Coordinate, SMAStarNode> inFrontier)
    {
        while (frontier.size() > mem)
        {
            SMAStarNode nd = getWorstLeafNode(frontier);
            frontier.remove(nd);
            inFrontier.remove(nd.getState());
            nd.setIsLeaf(false);

            if (nd.getParent().isPresent())
            {
                SMAStarNode ndParent = (SMAStarNode) nd.getParent().get();
                ndParent.addForgotten(nd);
                double leastF = ndParent.getForgotten().first().getFCost();
                ndParent.setFCost(leastF);

                PriorityQueue<SMAStarNode> frontierDeepCopy = new PriorityQueue<>(frontier);
                for (SMAStarNode nx : frontierDeepCopy)
                {
                    if (ancestors(nx).contains(nd))
                    {
                        ndParent.setIsLeaf(true);
                        frontier.add(ndParent);
                        inFrontier.put(ndParent.getState(), ndParent);
                    }
                }
            }
        }
        return frontier;
    }

    private static SMAStarNode getWorstLeafNode(PriorityQueue<SMAStarNode> frontier)
    {
        PriorityQueue<SMAStarNode> frontierDeepCopy = new PriorityQueue<>(frontier);
        Optional<SMAStarNode> worstLeafNode = Optional.empty();
        do 
        {
            SMAStarNode node = frontierDeepCopy.remove();
            if (node.isIsLeaf())
            {
                if (worstLeafNode.isEmpty())
                {
                    worstLeafNode = Optional.of(node);
                }
                else
                {
                    if (node.getFCost() >= worstLeafNode.get().getFCost())
                    {
                        worstLeafNode = Optional.of(node);
                    }
                }
            }
            
        } while (!frontierDeepCopy.isEmpty());
        return worstLeafNode.get();
    }

    private static TreeSet<SMAStarNode> ancestors(SMAStarNode nx)
    {
        TreeSet<SMAStarNode> ancestors = new TreeSet<>();
        Optional<Node> parent = nx.getParent();
        while (parent.isPresent())
        {
            SMAStarNode nd = (SMAStarNode) parent.get();
            ancestors.add(nd);
            parent = nd.getParent();
        }
        return ancestors;
    }
}
