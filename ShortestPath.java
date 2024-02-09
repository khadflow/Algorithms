import java.util.*;

public class ShortestPath {

	private int[] nodes;
	private HashMap<Integer, ArrayList<Integer>> edges;
	private boolean[] visited;
	private int[] edgeTo;
	private int[] paths;

	// does not include 0
	public ShortestPath(int n) {
		visited = new boolean[n + 1];
		edgeTo = new int[n + 1];
		paths = new int[n + 1];
		nodes = new int[n + 1];
		edges = new HashMap<>();
		createGraph();
	}

	private void createGraph() {
		for (int i = 1; i < nodes.length; i++) {
			edges.put(i, new ArrayList<>());
		}
	}

	private void reset() {
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
			paths[i] = Integer.MAX_VALUE;
			edgeTo[i] = 0;
		}
	}

	public void addEdge(int a, int b) {
		ArrayList<Integer> a_edges, b_edges;
		a_edges = edges.get(a);
		b_edges = edges.get(b);
		a_edges.add(b);
		b_edges.add(a);
	}

	// pl == path length
	public void shortestPath(int s, int t) {
		reset();
		findPath(s, t, 0);
	}

	private void findPath(int s, int t, int pl) {
		if (s == t) {
			return;
		} else if (visited[s]) {
			return;
		} else {
			visited[s] = true;
			paths[s] = Math.min(pl, paths[s]);
			pl++;
			ArrayList<Integer> ed = edges.get(s);
			for (int i = 0; i < ed.size(); i++) {
				if (!visited[ed.get(i)]) {
					edgeTo[ed.get(i)] = s;
					findPath(ed.get(i), t, pl);
				}
				if (paths[ed.get(i)] > pl) {
					paths[ed.get(i)] = pl;
					edgeTo[ed.get(i)] = s;
				}
			}
		}
	}

	public void print_edges() {
		for (int i = 0; i < edgeTo.length; i++) {
			System.out.println(i + " " + edgeTo[i] + " " + paths[i]);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		ShortestPath sp = new ShortestPath(10);
		sp.addEdge(1, 2);
		sp.addEdge(2, 8);
		sp.addEdge(2, 4);
		sp.addEdge(2, 6);
		sp.addEdge(8, 7);
		sp.addEdge(6, 5);
		sp.addEdge(1, 3);
		sp.addEdge(3, 4);
		sp.addEdge(4, 5);

		sp.shortestPath(1, 6);
		sp.print_edges();

		sp.shortestPath(9, 10);
		sp.print_edges();

		sp.shortestPath(4, 8);
		sp.print_edges();


	}
}
