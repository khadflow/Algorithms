import java.util.*;

public class BellmanFord {

	private HashMap<Integer, ArrayList<int[]>> graph; // directed graph with edge weights
	private ArrayList<int[]> edges; // from -> to
	
	/* 
	Dijkstra's Algorith, fails on a graph with negative edge weights
	Bellman Ford works well on a graph with negative edge weights anmd detects negative cycles
	Runtime : O(VE)
	*/
	public BellmanFord(int n) {
		graph = new HashMap<>();
		edges = new ArrayList<>();
		createGraph(n);
	}

	/*
	@param n, n is the size of the graph
	*/
	private void createGraph(int n) {
		for (int i = 1; i < n + 1; i ++) {
			graph.put(i, new ArrayList<>());
		}
	}

	/*
	This function calculates the shortest distance from startNode to all other nodes in the graph
	@param startNode
	@output distance array, returns an integer array calculating the shortest path from the
			start node to every other node in the graph
	*/
	private int[] shortestPath(int startNode) {
		int[] distance = new int[graph.size() + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[startNode] = 0;

		// repeat this process V - 1 times
		for (int i = 1; i < graph.size() + 1; i++) {
			// continuously relax the edges
			for (int e = 0; e < edges.size(); e++) {
				int[] edge = edges.get(e); // u -> v with weight = weight
				relaxEdge(edge[0], edge[1], edge[2], distance);
			}
		}


		// Re-run to detect cycles
		for (int i = 1; i < graph.size() + 1; i++) {
			// continuously relax the edges
			for (int e = 0; e < edges.size(); e++) {
				int[] edge = edges.get(e); // u -> v with weight = weight
				boolean isRelaxed = relaxEdge(edge[0], edge[1], edge[2], distance);
				if (isRelaxed) {
					Arrays.fill(distance, -1);
					return distance;
				}
			}
		}
		return distance;
	}

	/*
	Realxes the edge going into vertex TO from FROM if it beats the current shortest path
	@param from, the tail of the edge
	@param to, the endpoint of the edge
	@param weight, the weight of the edge between from and to
	@param distance[], holds the current shortest distances of each vertex from the starting node
	@output boolean, true if the value of to is updated, false otherwise
	*/
	private boolean relaxEdge(int from, int to, int weight, int[] distance) {
		int original = distance[to];
		distance[to] = Math.min(distance[from] + weight, distance[to]);
		return original != distance[to];
	}

	/*
	Add a directed edge from u to v with weight = weight
	@param u, the vertex at the tail of the edge
	@param v, the vertex at the end of the edge
	@param weight, the edge weight going from u -> v
	*/
	private void addEdge(int u, int v, int weight) {
		int[] adjEdge = new int[] {v, weight};
		graph.get(u).add(adjEdge);
		int[] edge = new int[] {u, v, weight};
		edges.add(edge);
	}

	public static void main(String[] args) {
		
		// Successful Bellman Ford Run #1
		/*
		BellmanFord bf = new BellmanFord(5);
		bf.addEdge(1, 2, 6);
		bf.addEdge(1, 3, 7);

		bf.addEdge(2, 4, 5);
		bf.addEdge(2, 5, -4);
		bf.addEdge(2, 3, 8);

		bf.addEdge(3, 4, -3);
		bf.addEdge(3, 5, 9);

		bf.addEdge(4, 2, -2);

		bf.addEdge(5, 4, 7);
		bf.addEdge(5, 1, 2);

		int[] d = bf.shortestPath(1);
		for (int i = 0; i < d.length; i++) {
			System.out.println(i + " " + d[i]);
		}
		*/
		// Example Output
		/*
		0 2147483647
		1 0
		2 2
		3 7
		4 4
		5 -2
		*/


		// Successful Bellman Ford Run #2
		/*
		BellmanFord bf = new BellmanFord(3);
		bf.addEdge(1, 2, -1);
		bf.addEdge(2, 3, 2);
		bf.addEdge(3, 1, 3);

		int[] d = bf.shortestPath(2);
		for (int i = 0; i < d.length; i++) {
			System.out.println(i + " " + d[i]);
		}
		*/
		// Example Output
		/*
		0 2147483647
		1 5
		2 0
		3 2
		*/

		// Detecting Negative Cycles
		/*
		BellmanFord bf = new BellmanFord(3);
		bf.addEdge(1, 2, -6);
		bf.addEdge(2, 3, 2);
		bf.addEdge(3, 1, 3);

		int[] d = bf.shortestPath(2);
		for (int i = 0; i < d.length; i++) {
			System.out.println(i + " " + d[i]);
		}
		*/
		// Example Output
		/*
		0 -1
		1 -1
		2 -1
		3 -1
		*/
	}
}