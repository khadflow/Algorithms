/**
 * Implementation of the Tarjan Algorithm to find all strongly connected components
 * in a graph using DFS and the stack data structure.
 * 
 * Time Complexity: O(V + E)
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Tarjan {
	
	private HashMap<Integer, ArrayList<Integer>> graph; // directed edge
	private boolean[] stack;
	private boolean[] visited;
	private Stack<Integer> vertexStack;

	private int[] ids, llv;
	private int UID;

	/*
 	@param n, size of the graph.
  	*/
	public Tarjan(int n) {
		graph = new HashMap<>();
		createGraph(n);
	}

	/*
	Generates the adjacency lists for n vertices in the graph.
	@param n
	*/
	private void createGraph(int n) {
		for (int i = 0; i < n; i++) {
			graph.put(i, new ArrayList<>());
		}
	}

	/*
	Adds a directed edge from u -> v to u's adjacency list
	@param u, vertex
	@param v, vertex
	*/
	public void addEdge(int u, int v) {
		graph.get(u).add(v);
	}

	/*
	@output the low link values array for the graph
	*/
	public int[] SCC() {
		ids = new int[graph.size()];
		llv = new int[graph.size()];
		Arrays.fill(llv, Integer.MAX_VALUE);
		visited = new boolean[graph.size()];
		stack = new boolean[graph.size()];
		UID = 0;

		vertexStack = new Stack<>();

		for (int i = 0; i < graph.size(); i++) {
			if (!visited[i]) {
				dfs(i);
			}
		}
		return llv;
	}

	/*
	Runs an augmented version of DFS.
	Assigns UIDs to each vertex and a Low Link value that is updated
	either when one of it's neighbors returns from the DFS callback
	(on the stack) with a lower link value OR when one of it's neighbors
	that's been visited (still on the stack) has a lower UID value.
	@param node, starting node to run DFS
	*/
	private void dfs(int node) {
		if (visited[node]) {
			return;
		} else {
			visited[node] = true;
			llv[node] = node;
			ids[node] = UID;
			UID++;
			vertexStack.push(node);

			ArrayList<Integer> edges = graph.get(node);
			stack[node] = true;

			for (int i = 0; i < edges.size(); i++) {
				
				int vertex = edges.get(i);
				if (!visited[vertex]) {
					dfs(vertex);
					if (stack[vertex]) {
						llv[node] = Math.min(llv[node], llv[vertex]);
					}
				} else {
					// if this vertex has been visited already
					// update the llv of the current node to the
					// id of this vertex if it is smaller
					if (stack[vertex]) {
						llv[node] = Math.min(llv[node], ids[vertex]);
					}
				}
			}

			// the llv(node) is also the smallest value that can be found in this SCC
			if (ids[node] == llv[node]) {
				for (int v = vertexStack.pop(); ; v = vertexStack.pop()) {
					/*
					update all of the nodes on the stack
					with the low link value of the smallest
					ID/LLV on the stack
					!!! ONLY UPDATE llv if the vertex is ON the stack
					*/
					if (node == v && stack[v]) {
						llv[v] = llv[node];
						stack[v] = false;
						break;
					} else if (stack[v]) {
						llv[v] = llv[node];
						stack[v] = false;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Tarjan t = new Tarjan(8);
		t.addEdge(0, 1);
		t.addEdge(1, 2);
		t.addEdge(2, 0);
		t.addEdge(6, 2);
		t.addEdge(6, 0);
		t.addEdge(6, 4);
		t.addEdge(4, 5);
		t.addEdge(5, 6);
		t.addEdge(5, 0);
		t.addEdge(3, 4);
		t.addEdge(3, 7);
		t.addEdge(7, 3);
		t.addEdge(7, 5);

		int[] llv = t.SCC();
		for (int i = 0; i < llv.length; i++) {
			System.out.println(i + " " + llv[i]);
		}

		// Example Output
		/*
		0 0
		1 0
		2 0
		3 3
		4 4
		5 4
		6 4
		7 3
		*/
	}
}
