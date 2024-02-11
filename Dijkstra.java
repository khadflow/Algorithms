import java.util.*;

public class Dijkstra {
	// [node, edge_weight]
	private PriorityQueue<Pair> pq; // priority Queue
	private HashMap<Integer, Pair> ipq; // indexed priority queue
	private HashMap<Integer, ArrayList<int[]>> graph;

	private boolean visited[];

	public Dijkstra(int n) {
		pq = new PriorityQueue<>();
		ipq = new HashMap<>();
		graph = new HashMap<>();
		visited = new boolean[n + 1];
		createGraph(n);
	}


	private void createGraph(int n) {
		for (int i = 0; i < n + 1; i++) {
			graph.put(i, new ArrayList<>());
		}
	}

	// DAG
	// Add an edge from u -> v exclusively
	public void addEdge(int u, int v, int weight) {
		ArrayList<int[]> u_edges = graph.get(u);
		u_edges.add(new int[] {v, weight});
	}


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
				// Should I add this to the pq now?
				int newWeight = edges.get(i)[1] + weight;
				int v = edges.get(i)[0];
				relaxEdges(v, newWeight, distance);
			}
		}
		return distance;

	}

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

	// Pairs representing (vertex, edge_weight)
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

	// TEST SUCCESSFUL
	// Testing PQ ordering Pair(vertex, weight)
	// Indexed Priority Queue (vertex -> *Pair(vertex, weight))
	// If the new pair is better than the old pair, then I replace it.
	// get pointer to the pair from Indexed PQ : check if new weight is better first
	// if neew weight is better: look for pointer in the actual PQ and remove it
	// Replace the Pair() in both the IPQ and the PQ
	/*public void testPQ() {

		int start = 50;
		for (int i = 1; i < 50; i++) {
			Pair p = new Pair(i, start);
			ipq.put(i, p);
			pq.add(p);
			start--;
		}

		// emptying the Priority Queue
		while (pq.size() > 0) {
			Pair c = pq.poll();
			System.out.println(c.getVertex() + " " + c.getWeight());
		}

		// Check if pair in IPQ maps correctly to pair in PQ
		// LOGIC TO UPDATE QUEUE
		int newWeight = 0;
		Pair p = ipq.get(1); // weight of 50
		if (pq.contains(p) && p.getWeight() > newWeight) {
			Pair newP = new Pair(p.getVertex(), newWeight);
			pq.remove(p);
			pq.add(newP);
			ipq.put(newP.getVertex(), newP);
		}

		while (pq.size() > 0) {
			Pair c = pq.poll();
			System.out.println(c.getVertex() + " " + c.getWeight());
		}
	}*/

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