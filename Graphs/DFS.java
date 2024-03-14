/**
 * Implementation of the Depth First Search Algorithm (DFS). This algorithm recursively
 * traverses the edges of all nodes in a connected component.
 * 
 * Time Complexity: O(V + E)
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */

import java.util.*;

public class DFS {
	
	private Stack<Integer> stack;
	private ArrayList<Integer>[] graph;
	private boolean[] visited;
	private int[] edgeTo;

	/*
 	@param n, number of vertices in the graph.
 	*/
	public DFS(int n) {
		graph = new ArrayList[n + 1];
		visited = new boolean[n + 1];
		edgeTo = new int[n + 1];
		stack = new Stack<>();

		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<>();
		}
	}

	/*
 	Adds an undirected edge from v to w
  	@param v, vertex
   	@param w, vertex
  	*/
	public void addEdge(int v, int w) {
		graph[v].add(w);
		graph[w].add(v);
	}

	/*
 	Rund DFS and finds the path from starting vertex v to the target node.
 	@param v, starting vertex
  	@param target, target vertex
 	*/
	public void findPath(int v, int target) {
		dfs(v);
		edgeTo[v] = Integer.MAX_VALUE;
		stack.push(v);
		int curr = v;
		for (int i = 0; i < edgeTo.length; i++) {
			if (edgeTo[i] == curr) {
				curr = i;
				stack.push(curr);
				if (curr == target) {
					break;
				}
			}
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
	}


	/*
 	Runs the depth first search algorithm starting at vertex v.
 	@param v, vertex
 	*/
	public void dfs(int v) {
		visited[v] = true;
		for (int i = 0; i < graph[v].size(); i++) {
			int vertex = graph[v].get(i);
			if (!visited[vertex]) {
				dfs(vertex);
				edgeTo[vertex] = v;
			}
		}
	}

	public static void main(String[] args) {
		DFS graph = new DFS(10);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(2, 4);
		graph.addEdge(2, 7);
		graph.addEdge(7, 4);
		graph.addEdge(3, 6);
		graph.addEdge(3, 5);
		graph.addEdge(5, 8);
		graph.addEdge(8, 9);
		graph.findPath(0, 9);
		//graph.dfs(0); 
	}
}
