/**
 * Implementation of Dijkstra's Algorithm to compute the shortest paths from a start node to
 * all other nodes in a graph with positive edge weights. This implementation uses an
 * indexed priority queue to determine which path to take.
 * 
 * Time Complexity: O(V + E)
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */
import java.util.*;

public class Dijkstra {
	// [node, edge_weight]
	private PriorityQueue<Pair> pq; // priority Queue
	private HashMap<Integer, Pair> ipq; // indexed priority queue
	private HashMap<Integer, ArrayList<int[]>> graph;

	private boolean visited[];

	/*
 	@param n, number of vertices in the graph.
  	*/
	public Dijkstra(int n) {
		pq = new PriorityQueue<>();
		ipq = new HashMap<>();
		graph = new HashMap<>();
		visited = new boolean[n + 1];
		createGraph(n);
	}

	/*
 	Creates the adjacency lists for the nodes in the graph.
 	@param n, the number of nodes in the graph.
 	*/
	private void createGraph(int n) {
		for (int i = 0; i < n + 1; i++) {
			graph.put(i, new ArrayList<>());
		}
	}

	/*
 	Adds a directed edge from u to v.
 	@param u, vertex
  	@param v, vertex
   	@param weight, the weight of the edge between u and v
 	*/
	public void addEdge(int u, int v, int weight) {
		ArrayList<int[]> u_edges = graph.get(u);
		u_edges.add(new int[] {v, weight});
	}

	/*
 	Calculates the distance of the shortest path from the start node to all other
  	nodes in the graph. The indexed priority queue is sorted by the shortest distance calcuated
   	so far.
 	@param start, start vertex
  	@output, the final distance array.
 	*/
	public int[] shortestPath(int start) {
		int[] distance = new int[graph.size()];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;
		Pair s = new Pair(start, 0);

		// update the priority queue and the indexed priority queue
		pq.add(s);
		ipq.put(start, s);

		while (pq.size() > 0) {
			Pair process = pq.poll();
			int vertex = process.getVertex();
			if (visited[vertex]) {
				continue;
			}
			visited[vertex] = true;
			int weight = process.getWeight();
			ArrayList<int[]> edges = graph.get(vertex);
			for (int i = 0; i < edges.size(); i++) {
				int newWeight = edges.get(i)[1] + weight;
				int v = edges.get(i)[0];
				relaxEdges(v, newWeight, distance);
			}
		}
		return distance;

	}

	/*
 	Relaxes the edge leading to the vertex if the newWeight is better than the distance
  	calculated so far. If the distance is better, the priority queue is updated with the
   	vertex and its new distance from the start node.
 	@param vertex, vertex
  	@param newWeight, possible new distance for the vertex
   	@param distances, the distances array with the length of the shortest paths calculated so far.
 	*/
	private void relaxEdges(int vertex, int newWeight, int[] distances) {
		if (distances[vertex] == Integer.MAX_VALUE) {
			distances[vertex] = newWeight;
			Pair newGuy = new Pair(vertex, newWeight);
			ipq.put(vertex, newGuy);
			pq.add(newGuy);
		} else if (newWeight < distances[vertex]) {
			distances[vertex] = newWeight;
			Pair newGuy = new Pair(vertex, distances[vertex]);
			Pair oldGuy = ipq.get(vertex);
			pq.remove(oldGuy);
			pq.add(newGuy);
			ipq.put(vertex, newGuy);
		}
	}

	/* Class representing the end point vertex and the weight of the edge leading to it. */
	public class Pair implements Comparable<Pair> {
		private int[] pair;

		public Pair(int vertex, int weight) {
			pair = new int[] {vertex, weight};
		}

		public int getWeight() {
			return pair[1];
		}

		public int getVertex() {
			return pair[0];
		}

		public int compareTo(Pair p) {
			if (getWeight() < p.getWeight()) {
				return -1;
			} else if (getWeight() > p.getWeight()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	// Dijkstra's
	// Relax all edges from currNode
	// Select next node based on smallest edge weight
	// PQ [(v, dist[u] + weight(u, v))]
	public static void main(String[] args) {
		Dijkstra dj = new Dijkstra(6);
		//dj.testPQ();
		dj.addEdge(1, 2, 5);
		dj.addEdge(1, 3, 1);
		dj.addEdge(2, 3, 2);
		dj.addEdge(3, 2, 3);
		dj.addEdge(2, 4, 3);
		dj.addEdge(2, 5, 20);
		dj.addEdge(3, 5, 12);
		dj.addEdge(4, 3, 3);
		dj.addEdge(4, 5, 2);
		dj.addEdge(4, 6, 6);
		dj.addEdge(5, 6, 1);

		int[] d = dj.shortestPath(1);

		for (int i = 0; i < d.length; i++) {
			System.out.println(i + " " + d[i]);
		}
	}
}
