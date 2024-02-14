/**
 * Implementation of Topological Sort. This algorithm sorts vertices based on
 * edge dependencies in a DAG.
 * 
 * Time Complexity: O(V + E)
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */
import java.util.*;

public class TopologicalSort {
	
	private HashMap<Integer, ArrayList<int[]>> graph;
	private boolean[] visited;

	private int[] paths;

	/*
 	@param n, size of the graph.
 	*/
	public TopologicalSort(int n) {
		graph = new HashMap<>();
		visited = new boolean[n + 1];

		paths = new int[n + 1];
		createGraph(n);
	}

	/*
	@param n, create the adjacency lists for all n vertices in the graph.
	*/
	private void createGraph(int n) {
		for (int i = 1; i < n + 1; i++) {
			graph.put(i, new ArrayList<>());
			paths[i] = Integer.MAX_VALUE;
		}
	}

	/*
 	Creates a single directed edge going from u to v.
 	@param u, vertex
  	@param v, vertex
	@param weight, the edge weigth between u and v
 	*/
	public void addEdge(int u, int v, int weight) {
		ArrayList<int[]> u_edges = graph.get(u);
		u_edges.add(new int[] {v, weight});
	}

	/*
 	Runs Single Source Shortest Path from the start node.
  	@param start, starting vertex for the shortest path algorithm.
   	@output the shortest paths distance array from the start node.
 	*/
	public int[] sssp(int start) {
		// run topological sort
		ArrayList<Integer> ts = topologicalSort();
		reset();
		paths[start] = 0;

		for (int i = 0; i < ts.size(); i++) {
			int vertex = ts.get(i);
			sssp_helper(vertex);
		}
		int node_a = ts.get(0);

		sssp_helper(node_a);
		
		return paths;
	}

	/*
 	Relax all of the edges coming from the vertex if the distance is better.
 	@param vertex, vertex 
 	*/
	private void sssp_helper(int vertex) {
		ArrayList<int[]> u_edges = graph.get(vertex);
		for (int i = 0; i < u_edges.size(); i++) {
			int v = u_edges.get(i)[0];
			int w = u_edges.get(i)[1];
			if (paths[vertex] == Integer.MAX_VALUE) {
				relaxEdge(v, w);
			} else {
				relaxEdge(v, paths[vertex] + w);
			}
		}
	}

	/*
 	Update the shortest distance to vertex if the new distance is better.
 	@param vertex, vertex 
  	@param new_weight, a possible new shortest path for vertex if it is better than the current distance.
 	*/
	private void relaxEdge(int vertex, int new_weight) {
		if (paths[vertex] > new_weight) {
			paths[vertex] = new_weight;
		}
	}

	private void reset() {
		Arrays.fill(visited, false);
		Arrays.fill(paths, Integer.MAX_VALUE);
	}

	/*
 	Creates the topological sort array.
 	@param currNode, vertex
  	@param arr, the topological sort array.
 	*/
	private ArrayList<Integer> dfs(int currNode, ArrayList<Integer> arr) {
		if (visited[currNode]) {
			return null;
		} else {
			visited[currNode] = true;
			ArrayList<int[]> currNodeEdges = graph.get(currNode);
			for (int i = 0; i < currNodeEdges.size(); i++) {
				int vertex = currNodeEdges.get(i)[0];
				if (!visited[vertex]) {
					dfs(vertex, arr);
				}
			}
		}

		arr.add(currNode);
		return arr;
	}

	/*
 	Runs topological sort on the current graph.
	*/
	private ArrayList<Integer> topologicalSort() {
		ArrayList<Integer> arr;
		ArrayList<Integer> tp = new ArrayList<>();
		for (int i = 1; i < graph.size(); i++) {
			arr = new ArrayList<>();
			dfs(i, arr);
			for (int j = 0; j < arr.size(); j++) {
				tp.add(0, arr.get(j));
			}
		}

		for (int i = 0; i < tp.size(); i++) {
			System.out.print(tp.get(i) + " ");
		}
		System.out.println();

		return tp;
	}

	public static void main(String[] args) {
		TopologicalSort tp = new TopologicalSort(8);
		tp.addEdge(1, 2, 3);
		tp.addEdge(1, 3, 6);
		tp.addEdge(2, 3, 4);
		tp.addEdge(2, 4, 4);
		tp.addEdge(2, 5, 11);
		tp.addEdge(3, 4, 8);
		tp.addEdge(3, 7, 11);
		tp.addEdge(4, 5, -4);
		tp.addEdge(4, 6, 5);
		tp.addEdge(4, 7, 2);
		tp.addEdge(5, 8, 9);
		tp.addEdge(6, 8, 1);
		tp.addEdge(7, 8, 2);
		int[] path = tp.sssp(1);

		for (int i = 0; i < path.length; i++) {
			System.out.println(i + " " + path[i]);
		}

		System.out.println();

		path = tp.sssp(2);
		
		for (int i = 0; i < path.length; i++) {
			System.out.println(i + " " + path[i]);
		}
	}
}
