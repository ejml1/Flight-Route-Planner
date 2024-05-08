package main;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Optional;

public class SMAStar {
    
    public static final double INF = 10000.0;

    public static void search(World problem, PriorityQueue<SMAStarNode> frontier, int mem)
    {
        int nExplored = 0;

        SMAStarNode initialNode = makeNode(Optional.empty(), problem.getInitialState(), problem.getGoal());
        frontier.add(initialNode);

        HashMap<Coordinate, SMAStarNode> inFrontier = new HashMap<Coordinate, SMAStarNode>();
        inFrontier.put(problem.getInitialState(), initialNode);

        do
        {
            if (frontier.isEmpty())
            {
                System.out.println("fail");
                System.out.println(nExplored);
                return;
            }
            System.out.println(GeneralSearchAlgorithmInformed.frontierToString(frontier));
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
                
            }
        } while (true);
    }

    private static TreeSet<SMAStarNode> processExpand(SMAStarNode nd, World problem, PriorityQueue<SMAStarNode> frontier, HashMap<Coordinate, SMAStarNode> inFrontier, Coordinate goal, int mem)
    {
        TreeSet<SMAStarNode> succList;
        if (nd.getForgotten().isEmpty())
        {
            succList = expand(nd, problem, frontier, inFrontier, goal, mem);
        }
        else
        {
            succList = new TreeSet<SMAStarNode>(nd.getForgotten());
        }

        for (SMAStarNode ns : succList)
        {
            if (nd.getForgotten().contains(ns))
            {
                nd.removeForgotten(nd);
            }
            else if (!GeneralSearchAlgorithm.goalTest(ns.getState(), goal) && ns.getDepth() == mem)
            {
                ns.setFCost(INF);
            }
            ns.setIsLeaf(true);
            SMAStarNode nsParent = (SMAStarNode) ns.getParent().get();
            nsParent.setIsLeaf(false);
        }
        return succList;
    }

    private static TreeSet<SMAStarNode> expand(SMAStarNode node, World problem, PriorityQueue<SMAStarNode> frontier, HashMap<Coordinate, SMAStarNode> inFrontier, Coordinate goal, int mem)
    {
        Set<Coordinate> nextStates = node.getState().getNeighbours();
        TreeSet<SMAStarNode> successors = new TreeSet<SMAStarNode>();
        for (Coordinate state : nextStates)
        {
            SMAStarNode nd = makeNode(Optional.of(node), state, goal);
            if (!inFrontier.containsKey(state))
            {
                successors.add(nd);
            }
            // Extra condition nd.getDepth() < mem to ensure that a node at depth = mem with value < INF is NOT added into the frontier 
            else if (inFrontier.containsKey(state) && nd.getDepth() < mem)
            {
                SMAStarNode ndInFrontier = inFrontier.get(state);
                if (ndInFrontier.getPathCost() > nd.getPathCost())
                {
                    frontier.remove(ndInFrontier);
                    inFrontier.remove(state);
                    // Set nd as leaf node before adding to frontier
                    nd.setIsLeaf(true);
                    frontier.add(nd);
                    inFrontier.put(state, nd);
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
            return new SMAStarNode(state, Optional.empty(), state.toString(), 1, 0, hCost, hCost, 
                new TreeSet<SMAStarNode>(), false);
        }
        Node unwrappedNode = node.get();

        String action = unwrappedNode.getAction() + state.toString();
        double pathCost = unwrappedNode.getPathCost() + GeneralSearchAlgorithm.cost(unwrappedNode.getState(), state);

        SMAStarNode n = new SMAStarNode(state, unwrappedNode, action, unwrappedNode.getDepth() + 1, pathCost, hCost, hCost + pathCost, 
            new TreeSet<SMAStarNode>(), false);
        return n;
    }

    public static PriorityQueue<SMAStarNode> shrinkFrontier(PriorityQueue<SMAStarNode> frontier, int mem, HashMap<Coordinate, SMAStarNode> inFrontier)
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
                boolean needsReinsertion = true;
                for (SMAStarNode nx : frontier)
                {
                    if (ancestors(nx).contains(ndParent))
                    {
                        needsReinsertion = false;
                        break;
                    }
                }
                if (needsReinsertion)
                {
                    ndParent.setIsLeaf(true);
                    frontier.add(ndParent);
                    inFrontier.put(ndParent.getState(), ndParent);
                }
            }
        }
        return frontier;
    }

    public static SMAStarNode getWorstLeafNode(PriorityQueue<SMAStarNode> frontier)
    {
        Optional<SMAStarNode> worstLeafNode = Optional.empty();
        PriorityQueue<SMAStarNode> frontierCopy = new PriorityQueue<>(frontier);
        while (!frontierCopy.isEmpty())
        {
            SMAStarNode node = frontierCopy.remove();
            if (node.getIsLeaf())
            {
                if (worstLeafNode.isEmpty())
                {
                    worstLeafNode = Optional.of(node);
                }
                else if (node.getFCost() >= worstLeafNode.get().getFCost())
                {
                    worstLeafNode = Optional.of(node);
                }
            }      
        } 
        return worstLeafNode.get();
    }

    public static TreeSet<SMAStarNode> ancestors(SMAStarNode nx)
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
