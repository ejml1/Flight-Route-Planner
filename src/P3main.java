import java.util.Queue;
import java.util.Stack;

import main.AStar;
import main.BFS;
import main.BestF;
import main.BestFNode;
import main.DFS;
import main.Node;
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
    
        
		/*
		 * The system must print the following information from your search methods
		 * All code below is for demonstration purposes
		 */
        
        //Example: java P3main BFS 5 2:45 1:180

		// 1) Print the Frontier at each step before polling 

		boolean uninformed=true;

		//FOR UNINFORMED SEARCH 


		// if(uninformed) {
		// 	String frontier_string="";

		// 	//starting point (2:45), 
		// 	//insert node in the frontier, then print the frontier:
		// 	frontier_string="[(2:45)]";

		// 	System.out.println(frontier_string);

		// 	//extract (2:45)
		// 	//insert successors in the frontier (1:45),(2:0),(2:90),(3:45), then print the frontier,  and repeat for all steps until a path is found or not 


		// 	frontier_string="[(1:45),(2:0),(2:90),(3:45)]\n"
		// 			+ "[(2:0),(2:90),(3:45),(1:0),(1:90)]\n"
		// 			+ "[(2:90),(3:45),(1:0),(1:90),(2:315),(3:0)]\n"
		// 			+ "[(3:45),(1:0),(1:90),(2:315),(3:0),(2:135),(3:90)]\n"
		// 			+ "[(1:0),(1:90),(2:315),(3:0),(2:135),(3:90),(4:45)]\n"
		// 			+ "[(1:90),(2:315),(3:0),(2:135),(3:90),(4:45),(1:315)]\n"
		// 			+ "[(2:315),(3:0),(2:135),(3:90),(4:45),(1:315),(1:135)]\n"
		// 			+ "[(3:0),(2:135),(3:90),(4:45),(1:315),(1:135),(2:270),(3:315)]\n"
		// 			+ "[(2:135),(3:90),(4:45),(1:315),(1:135),(2:270),(3:315),(4:0)]\n"
		// 			+ "[(3:90),(4:45),(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135)]\n"
		// 			+ "[(4:45),(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90)]\n"
		// 			+ "[(1:315),(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90)]\n"
		// 			+ "[(1:135),(2:270),(3:315),(4:0),(2:180),(3:135),(4:90),(1:270)]\n"
		// 			+ "[(2:270),(3:315),(4:0),(2:180),(3:135),(4:90),(1:270),(1:180)]\n"
		// 			+ "[(3:315),(4:0),(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270)]\n"
		// 			+ "[(4:0),(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315)]\n"
		// 			+ "[(2:180),(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315)]\n"
		// 			+ "[(3:135),(4:90),(1:270),(1:180),(2:225),(3:270),(4:315),(3:180)]\n"
		// 			+ "[(4:90),(1:270),(1:180),(2:225),(3:270),(4:315),(3:180),(4:135)]\n"
		// 			+ "[(1:270),(1:180),(2:225),(3:270),(4:315),(3:180),(4:135)]\n"
		// 			+ "[(1:180),(2:225),(3:270),(4:315),(3:180),(4:135),(1:225)]";


		// 	System.out.println(frontier_string);




		// 	// 2) The final three lines must be the path, path cost, and number of nodes visited/explored, in this order


		// 	boolean path_found=true;
		// 	String path="(2:45)(1:45)(1:90)(1:135)(1:180)";
		// 	double path_cost=3.356194490192345;
		// 	int n_explored=22;

		// 	if(path_found) {
		// 		System.out.println(path);
		// 		System.out.println(String.format("%.3f",path_cost));
		// 	}else {
		// 		System.out.println("fail");
		// 	}

		// 	System.out.println(n_explored);

		// }else {
		// 	// FOR INFORMED SEARCH f-cost must also be included 
			
		// 	String output="[(2:45)2.798]\n"
		// 			+"...\n"
		// 			+"(2:45)(1:45)(1:90)(1:135)(1:180)\n"
		// 			+"3.356\n"
		// 			+"5";
		// 	System.out.println(output);
		// }

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
			PriorityQueue<BestFNode> frontierBestF = new PriorityQueue<BestFNode>();
			BestF.search(world, frontierBestF);
			break;
		case "AStar": //run AStar
			PriorityQueue<BestFNode> frontierAStar = new PriorityQueue<BestFNode>();
			AStar.search(world, frontierAStar);
			break;
		case "SMAStar": //run SMAStar
			break;
		}

	}



}
