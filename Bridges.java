import java.util.*;

public class Bridges {
	
	private HashMap<Integer, ArrayList<Integer>> graph; // directed graph
	private boolean[] visited;
	private int[] ids;
	private int[] low_link;
	private int UID;
	private ArrayList<Integer> bridges;

	public Bridges(int n) {
		graph = new HashMap<>();
		createGraph(n);
	}

	/*
	Create the adjacency lists for the graph with n vertices.
	@param n, number of vertices in the graph
	*/
	public void createGraph(int n) {
		for (int i = 0; i < n + 1; i++) {
			graph.put(i, new ArrayList<>());
		}
	}

	/*
	Add an edge from u -> v
	@param u, vertex
	@param v, vertex
	*/
	public void addEdge(int u, int v) {
		graph.get(u).add(v);
	}

	/*
	Runs DFS starting at the start node and populates the low_link and
	ids arrays to find the bridges in the graph
	@param node, starting node for DFS
	*/
	private void dfs(int node) {
		if (visited[node]) {
			return;
		} else {
			visited[node] = true;
			ids[node] = UID;
			low_link[node] = ids[node];
			UID++;
			ArrayList<Integer> edges = graph.get(node);
			for (int i = 0; i < edges.size(); i++) {
				int vertex = edges.get(i);
				if (!visited[vertex]) {
					dfs(vertex);
					low_link[node] = Math.min(low_link[node], low_link[vertex]); // did the dfs find a smaller UID
					if (ids[node] < low_link[vertex]) { // vertex has no path back to the parent node
						bridges.add(node);
						bridges.add(vertex);
					}
				} else {
					low_link[node] = Math.min(ids[vertex], low_link[node]);
				}
			}
		}
	}

	/*
	Finds all bridges in the current graph and prints them out.
	*/
	public void findBridges() {
		visited = new boolean[graph.size() + 1];
		ids = new int[graph.size() + 1];
		low_link = new int[graph.size() + 1];

		bridges = new ArrayList<>();
		Arrays.fill(ids, Integer.MAX_VALUE);
		Arrays.fill(low_link, Integer.MAX_VALUE);
		UID = 0;

		for (int i = 0; i < graph.size(); i++) {
			if (!visited[i]) {
				dfs(i);
			}
		}
		bridges();
	}

	/* Debug Functions */
	public void low_link() {
		for (int i = 0; i < low_link.length; i++) {
			System.out.println(i + " " + low_link[i]);
		}
		System.out.println();
	}

	public void ID() {
		for (int i = 0; i < ids.length; i++) {
			System.out.println(i + " " + ids[i]);
		}
		System.out.println();
	}

	public void bridges() {
		for (int i = 0; i < bridges.size(); i+= 2) {
			int u = bridges.get(i);
			int v = bridges.get(i + 1);
			System.out.println("Edge From " + u + " to " + v);
		}
	}

	public static void main(String[] args) {
		Bridges b = new Bridges(7);
		b.addEdge(1, 2);
		b.addEdge(2, 3);
		b.addEdge(3, 1);
		b.addEdge(3, 4);
		b.addEdge(4, 5);
		b.addEdge(5, 6);
		b.addEdge(6, 4);
		b.findBridges();
		// Output
		// "Edge From 3 to 4"
		
		/* 
		DEBUG

		b.low_link();
		-- OUTPUT --
		0 2147483647
		1 1
		2 1
		3 1
		4 4
		5 4
		6 4
		7 7

		b.ID();
		-- OUTPUT --
		0 2147483647
		1 1
		2 2
		3 3
		4 4
		5 5
		6 6
		7 7
		*/

		b = new Bridges(8);
		b.addEdge(0, 1);
		b.addEdge(1, 2);
		b.addEdge(2, 0);
		b.addEdge(2, 3);
		b.addEdge(3, 4);
		b.addEdge(2, 5);
		b.addEdge(5, 6);
		b.addEdge(6, 7);
		b.addEdge(7, 8);
		b.addEdge(8, 5);

		b.findBridges();

		b.low_link();
		// Example Output
		/*
		Edge From 3 to 4
		Edge From 2 to 3
		Edge From 2 to 5
		0 0
		1 0
		2 0
		3 3
		4 4
		5 5
		6 5
		7 5
		8 5
		9 2147483647
		*/
	}
}
