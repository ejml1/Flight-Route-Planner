import java.util.Queue;
import java.util.Stack;

import main.AStar;
import main.BFS;
import main.BestF;
import main.ISNode;
import main.DFS;
import main.Node;
import main.SMAStar;
import main.SMAStarNode;
import main.World;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 
 * This class contains some examples of required inputs and outputs
 * 
 * @author alice toniolo
 *
 * run with 
 * java P3main <Algo> <N> <ds:as> <dg:ag>
 * we assume <N> <ds:as> <dg:ag> are valid parameters, no need to check
 */


public class P3main {

	public static void main(String[] args) {
		
		if(args.length<4) {
			System.out.println("usage: java P3main <DFS|BFS|AStar|BestF|SMAStar|...> <N> <ds:as> <dg:ag> [<param>]");
			System.exit(1);
		}
		//Print initial information --- please do not delete
		System.out.println("World: Oedipus "+args[1]);
		System.out.println("Departure airport -- Start: "+args[2]);
		System.out.println("Destination airport -- Goal: "+args[3]);
		System.out.println("Search algorithm: "+args[0]);
		System.out.println();
		//end initial information
		
		//run your search algorithm 
		runSearch(args[0],Integer.parseInt(args[1]),args[2],args[3]);
	}

	private static void runSearch(String algo, int size, String start, String goal) {
		World world = new World(size, start, goal);
		switch(algo) {
		case "BFS": //run BFS
			Queue<Node> frontier = new LinkedList<Node>();
			BFS.treeSearch(world, frontier);
			break;
		case "DFS": //run DFS
			Stack<Node> frontierDFS = new Stack<Node>();
			DFS.treeSearch(world, frontierDFS);
			break;  
		case "BestF": //run BestF
			PriorityQueue<ISNode> frontierBestF = new PriorityQueue<ISNode>();
			BestF.search(world, frontierBestF);
			break;
		case "AStar": //run AStar
			PriorityQueue<ISNode> frontierAStar = new PriorityQueue<ISNode>();
			AStar.search(world, frontierAStar);
			break;
		case "SMAStar": //run SMAStar
			PriorityQueue<SMAStarNode> frontierSMAStar = new PriorityQueue<SMAStarNode>();
			SMAStar.search(world, frontierSMAStar, size);
			break;
		}

	}



}
