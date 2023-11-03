import java.util.*;

public class BFS {

	private Queue<Integer> queue;
	private ArrayList<Integer>[] graph;
	private int[] edgeTo;
	private boolean[] visited;
	private int[] colors; // 1 == RED, 2 == BLUE

	public BFS(int n) {
		queue = new ArrayDeque<>();
		graph = new ArrayList[n + 1];
		edgeTo = new int[n + 1];
		visited = new boolean[n + 1];
		colors = new int[n + 1];
		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<>();
		}
	}

	public void addEdge(int v, int w) {
		graph[v].add(w);
		graph[w].add(v);
	}


	public void bfs(int v) {

		queue.add(v);
		while (!queue.isEmpty()) {
			int curr = queue.remove();
			visited[curr] = true;
			//System.out.println(curr + " ");
			for (int i = 0; i < graph[curr].size(); i++) {
				int vertex = graph[curr].get(i);
				if (!visited[vertex]) {
					queue.add(vertex);
					edgeTo[vertex] = curr;
				}
			}
		}
	}	

	public void findPath(int v, int target) {
		bfs(v);

		ArrayList<Integer> path = new ArrayList<>();
		int curr = v;
		for (int i = 0; i < edgeTo.length; i++) {
			if (edgeTo[i] == curr) {
				curr = i;
				path.add(i);
				if (curr == target) {
					break;
				}
			}
		}

		for (int i: path) {
			System.out.println(i);
		}
		
	}

	public boolean isBipartite() {
		Arrays.fill(colors, 0);
		boolean ret = colorGraph(0, 1);
		Arrays.fill(visited, false);
		return ret;
	}

	private boolean colorGraph(int vertex, int color) {
		boolean fin = true;
		if (visited[vertex] && colors[vertex] != color) {
			return false;
		} else if (visited[vertex] && colors[vertex] == color) {
			return true;
		} else if (!visited[vertex]) {
			visited[vertex] = true;
			colors[vertex] = color;
			for (int i = 0; i < graph[vertex].size(); i++) {
				int v = graph[vertex].get(i);
				if (color == 1) {
					fin &= colorGraph(v, 2);
				} else {
					fin &= colorGraph(v, 1);
				}
			}
		}
		return fin;
	}

	public static void main(String[] args) {
		BFS graph = new BFS(10);
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
		//graph.bfs(0);
		//graph.findPath(0, 9);
		System.out.println("is graph bipartite ? " + graph.isBipartite());
		BFS graph2 = new BFS(10);
		graph2.addEdge(0, 1);
		graph2.addEdge(1, 2);
		graph2.addEdge(2, 3);
		graph2.addEdge(2, 4);
		graph2.addEdge(2, 7);
		//graph2.addEdge(7, 4);
		graph2.addEdge(3, 6);
		graph2.addEdge(3, 5);
		graph2.addEdge(5, 8);
		graph2.addEdge(8, 9);
		System.out.println("is graph2 bipartite ? " + graph2.isBipartite());

	}
}