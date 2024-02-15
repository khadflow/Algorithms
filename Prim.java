/**
 * Implementation of Prim's MST Algorithm. This algorithm finds the
 * Minimum Spanning Tree of a graph by picking edges to add to the MST
 * by the next smallest edge weight until all vertices are connected.
 * This implementation uses an Indexed Priority Queue to sort and track
 * the shortest edge weight to a vertex so far.
 * 
 * Time Complexity: O(E * log(V))
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Prim {
	private boolean[] visited;
	private int nodeCount;
	private Map<Integer, Edge> ipq;
	private PriorityQueue<Edge> pq;
	private List<Edge> MST;

	public Prim() {
		pq = new PriorityQueue<>();
	}

	/*
	Adds an undirected edge between vertex u and vertex v
	@param graph
	@param u, vertex
	@param v, vertex
	@param weight. the weight between u and v
	*/
	public void addEdge(Map<Integer, List<Edge>> graph, int u, int v, int weight) {
		graph.get(u).add(new Edge(v, weight));
		graph.get(v).add(new Edge(u, weight));
	}

	/*
	Generates the MST for the given graph.
	@param graph, an adjacency list representing the undirected graph.
	@output, a list with all the edges in the MST.
	*/
	public List<Edge> MST(Map<Integer, List<Edge>> graph) {
		visited = new boolean[graph.size()];
		MST = new ArrayList<>();
		nodeCount = 0;
		pq = new PriorityQueue<>();
		ipq = new HashMap<>();
		int startNode = 0;

		List<Edge> edges = graph.get(startNode);
		
		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			pq.add(e);
		}

		findMST(graph, startNode);

		if (ipq.size() != graph.size() - 1) {
			System.out.println("Invalid MST generated. Possible Disconnected Components.");
			return MST;
		}

		for (int i : ipq.keySet()) {
			MST.add(ipq.get(i));
		}

		return MST;
	}

	/*
	@param graph, the adjacency list representing the graph.
	@param startNode, the start place to run the MST algorithm. By default, set to 0.
	*/
	private void findMST(Map<Integer, List<Edge>> graph, int startNode) {
		visited[startNode] = true;
		nodeCount++;
		while (!pq.isEmpty()) {
			Edge e = pq.poll();
			int vertex = e.getVertex();

			if (!visited[vertex]) {
				visited[vertex] = true;
				nodeCount++;
				ipq.put(vertex, e);
				List<Edge> vertexEdges = graph.get(vertex);
				for (int i = 0; i < vertexEdges.size(); i++) {
					pq.add(vertexEdges.get(i)); // add all edges from vertex into the PQ
				}
			} else {
				// check if there is a shorter edge to the vertex already processed.
				Edge oldE = ipq.get(vertex);
				int oldWeight = e.getWeight();
				if (e.getWeight() < oldWeight) {
					// replace the edge in the ipq and in the MST
					ipq.put(vertex, e);
				}

			}
		}
	}

	public class Edge implements Comparable<Edge> {
		private int vertex;
		private int weight;

		public Edge(int v, int w) {
			vertex = v;
			weight = w;
		}

		public int getWeight() {
			return weight;
		}

		public int getVertex() {
			return vertex;
		}

		public int compareTo(Edge e) {
			if (weight < e.getWeight()) {
				return -1;
			} else if (weight > e.getWeight()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public static void main(String[] args) {
		Prim p = new Prim();
		Map<Integer, List<Edge>> graph = new HashMap<>();
		int N = 7;
		for (int i = 0; i < N; i++) {
			graph.put(i, new ArrayList<>());
		}
		p.addEdge(graph, 0, 2, 0);
		p.addEdge(graph, 0, 1, 9);
		p.addEdge(graph, 0, 3, 5);
		p.addEdge(graph, 0, 5, 7);

		p.addEdge(graph, 2, 5, 6);

		p.addEdge(graph, 5, 6, 1);

		p.addEdge(graph, 1, 4, 3);
		p.addEdge(graph, 3, 6, 3);
		p.addEdge(graph, 6, 4, 6);
		p.addEdge(graph, 3, 5, 2);
		p.addEdge(graph, 1, 3, -2);
		p.addEdge(graph, 6, 1, 4);
		List<Edge> edges = p.MST(graph);

		for (int i = 0; i < edges.size(); i++) {
			System.out.println(edges.get(i).getVertex() + " with weight " + edges.get(i).getWeight());
		}
		System.out.println();

		Map<Integer, List<Edge>> graphTwo = new HashMap<>();
		N = 8;
		for (int i = 0; i < N; i++) {
			graphTwo.put(i, new ArrayList<>());
		}
		p.addEdge(graphTwo, 0, 1, 10);
		p.addEdge(graphTwo, 0, 2, 1);
		p.addEdge(graphTwo, 0, 3, 4);

		p.addEdge(graphTwo, 1, 2, 3);
		p.addEdge(graphTwo, 2, 3, 2);

		p.addEdge(graphTwo, 1, 4, 0);
		p.addEdge(graphTwo, 2, 5, 8);
		p.addEdge(graphTwo, 3, 5, 2);
		p.addEdge(graphTwo, 3, 6, 7);

		p.addEdge(graphTwo, 4, 5, 1);
		p.addEdge(graphTwo, 5, 6, 6);

		p.addEdge(graphTwo, 4, 7, 8);
		p.addEdge(graphTwo, 5, 7, 9);
		p.addEdge(graphTwo, 6, 7, 12);

		edges = p.MST(graphTwo);

		for (int i = 0; i < edges.size(); i++) {
			System.out.println(edges.get(i).getVertex() + " with weight " + edges.get(i).getWeight());
		}
		System.out.println();


		Map<Integer, List<Edge>> graphThree = new HashMap<>();
		N = 4;
		for (int i = 0; i < N; i++) {
			graphThree.put(i, new ArrayList<>());
		}
		p.addEdge(graphThree, 0, 1, 1);
		p.addEdge(graphThree, 0, 2, 2);
		p.addEdge(graphThree, 1, 2, 4);

		edges = p.MST(graphThree);

		for (int i = 0; i < edges.size(); i++) {
			System.out.println(edges.get(i).getVertex() + " with weight " + edges.get(i).getWeight());
		}

	}
}