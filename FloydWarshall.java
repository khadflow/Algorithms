import java.util.*;

public class FloydWarshall {

	private int[][] graph; // directed graph with edge weights
	private int[][] distances;
	
	/* 
	Floyd-Warshall is an APSP Algoritm
	All Pairs Shortest Path (APSP)
	Calculates whether or not there is a better distance from u -> v
	going through intermediate nodes {1, 2,..., k}
	Runtime: O(V^3)
	*/
	public FloydWarshall(int n) {
		graph = new int[n + 1][n + 1];
		distances = new int[n + 1][n + 1];
		createGraph(n);
	}

	/*
	@param n, the size / number of vertices in the graph
	*/
	private void createGraph(int n) {
		for (int i = 0; i < n + 1; i ++) {
			Arrays.fill(graph[i], Integer.MAX_VALUE);
			graph[i][i] = 0;
		}
	}

	/*
	Initializes all values in the distances array to Infinity
	before computing All Pairs Shortest Path.
	The shortest distance (i, i) = 0
	*/
	private void resetDistances() {
		for (int i = 0; i < distances.length; i++) {
			Arrays.fill(distances[i], Integer.MAX_VALUE);
			distances[i][i] = 0;
		}
	}

	/*
	Copies all existing edges from the graph to the distances array
	*/
	private void copyEdges() {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (graph[i][j] != Integer.MAX_VALUE) {
					distances[i][j] = graph[i][j];
				}
			}
		}
	}

	/*
	Calculates the shortest distance for all pairs on vertices
	@output distance array, returns a 2D integer array calculating the shortest path
			between all vertices in the graph
	*/
	private int[][] APSP() {
		resetDistances();
		copyEdges();
		for (int k = 1; k < graph.length; k++) {
			for (int i = 1; i < graph.length; i++) {
				for (int j = 1; j < graph.length; j++) {
					int edge_ik = distances[i][k];
					int edge_kj = distances[k][j];
					if (edge_ik != Integer.MAX_VALUE && edge_kj != Integer.MAX_VALUE) {
						distances[i][j] = Math.min(distances[i][j], edge_ik + edge_kj);
					}
					
				}
			}
		}
		return distances;
	}

	/*
	Add a directed edge from u to v with weight = weight
	@param u, the vertex at the tail of the edge
	@param v, the vertex at the end of the edge
	@param weight, the edge weight going from u -> v
	*/
	private void addEdge(int u, int v, int weight) {
		graph[u][v] = weight;
	}

	public static void main(String[] args) {
		// Successful Floyd Warshall Run
		/*
		FloydWarshall fw = new FloydWarshall(4);

		fw.addEdge(1, 3, -2);
		fw.addEdge(3, 4, 2);
		fw.addEdge(4, 2, -1);
		fw.addEdge(2, 1, 4);
		fw.addEdge(2, 3, 3);

		int[][] d = fw.APSP();
		System.out.print(" ");
		for (int i = 0; i < d.length; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int i = 0; i < d.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < d.length; j++) {
				if (d[i][j] == Integer.MAX_VALUE) {
					System.out.print("~ ");
					continue;
				}
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		*/
		// Example Output
		/*
		  0 1 2 3 4
		0 0 ~ ~ ~ ~
		1 ~ 0 -1 -2 0
		2 ~ 4 0 2 4
		3 ~ 5 1 0 2
		4 ~ 3 -1 1 0
		*/

		// Floyd Warshall Negative Cycle Detection
		// Negative values along the diagonal
		/*
		FloydWarshall fw = new FloydWarshall(3);
		fw.addEdge(1, 2, 1);
		fw.addEdge(2, 3, 2);
		fw.addEdge(3, 1, -6);

		int[][] d = fw.APSP();
		System.out.print(" ");
		for (int i = 0; i < d.length; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int i = 0; i < d.length; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < d.length; j++) {
				if (d[i][j] == Integer.MAX_VALUE) {
					System.out.print("~ ");
					continue;
				}
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		*/
		// Example Output
		/*
		  0 1 2 3
		0 0 ~ ~ ~
		1 ~ -3 -2 0
		2 ~ -4 -3 -1
		3 ~ -9 -8 -6
		*/
	}
}