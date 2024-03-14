/**
 * Implementation for finding an Eulerian Path and Circuit on a graph.
 * This implementation verifies the existence of a path or circuit
 * and returns a valid path.
 * 
 * Time Complexity: O(E)
 * 
 * @author Khadijah Flowers, khadijah20flowers@gmail.com
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EulerianPC {
	
	private int[] incoming;
	private int[] outgoing;
	private boolean isDirected;
	private boolean[] visited;
	private int start, end;
	private ArrayList<Integer> path;
	private int graphSize;

	/*
	@param directed, a boolean to determine whether or not the graphs being considered will be directed or undirected.
	*/
	public EulerianPC(boolean directed) {
		isDirected = directed;
	}

	/*
	Given the adjacency list of a graph, determine if it is possible to find an Eulerian Circuit.
	@param adjList, the adjacency list representing the graph.
	@output boolean, true if an Eulerian Circuit is possible, false otherwise
	*/
	private boolean hasEulerianCircuit(Map<Integer, List<Integer>> adjList) {
		incoming = new int[adjList.size()];
		outgoing = new int[adjList.size()];

		graphSize = 0;
		int N = adjList.size();

		for (int i = 0; i < N; i++) {
			List<Integer> edges = adjList.get(i);
			graphSize += edges.size();
			for (int j = 0; j < edges.size(); j++) {
				if (isDirected) {
					outgoing[i]++;
					incoming[edges.get(j)]++;
				} else {
					outgoing[i]++; // edges COMING OUT OF vertex
					incoming[edges.get(j)]++; // edges GOING TO vertex
				}
			}
		}
		//printEdgeCounts();
		for (int i = 0; i < incoming.length; i++) {
			if (isDirected && outgoing[i] != incoming[i]) {
				return false;
			} else if (!isDirected && outgoing[i] % 2 != 0) {
				return false;
			}
		}
		return true;
	}

	/*
	Given the adjacency list of a graph, determine if it is possible to find an Eulerian Path.
	@param adjList, the adjacency list representing the graph.
	@output boolean, true if an Eulerian Path is possible, false otherwise
	*/
	private boolean hasEulerianPath(Map<Integer, List<Integer>> adjList) {
		incoming = new int[adjList.size()];
		outgoing = new int[adjList.size()];
		start = -1;
		end = -1;
		graphSize = 0;

		int N = adjList.size();
		for (int i = 0; i < N; i++) {
			List<Integer> edges = adjList.get(i);
			graphSize += edges.size();
			for (int j = 0; j < edges.size(); j++) {
				outgoing[i]++; // edges COMING OUT OF vertex
				incoming[edges.get(j)]++; // edges GOING TO vertex
			}
		}

		// undirected
		int oddDegreeVertex = 0;

		// directed
		int oddInDegree = 0;
		int oddOutdegree = 0;

		for (int i = 0; i < outgoing.length; i++) {
			if (!isDirected && incoming[i] % 2 != 0) {
				oddDegreeVertex++;
				if (start == -1) {
					start = i;
				}
				else if (end == -1) {
					end = i;
				}
			} else if (isDirected) {
				if ((incoming[i] - outgoing[i] == 1)) {
					if (end == -1) {
						end = i;
					}
					oddInDegree++;
				} else if ((outgoing[i] - incoming[i] == 1)) {
					if (start == -1) {
						start = i;
					}
					oddOutdegree++;
				} else if (outgoing[i] != incoming[i]) {
					//printEdgeCounts();
					return false;
				}
			}
		}
		//printEdgeCounts();
		if (!isDirected && (oddDegreeVertex == 0 || oddDegreeVertex == 2)) {
			return true;
		} else if (isDirected && ((oddOutdegree == 0 && oddOutdegree == 0) || (oddInDegree == 1 && oddOutdegree == 1))) {
			return true;
		}
		return false;
	}

	/*
	Debugging function to print out the INCOMING and OUTGOING edge counts.
	*/
	private void printEdgeCounts() {
		for (int i = 0; i < incoming.length; i++) {
			System.out.println(i + " incoming: " + incoming[i] + " outgoing: " + outgoing[i]);
		}
	}

	/*
	Given the adjacency list of a graph, return it's Eulerian Path if one exists.
	@param adjList, the adjacency list representing the graph.
	*/
	public void getEulerianPath(Map<Integer, List<Integer>> adjList) {
		if (!hasEulerianPath(adjList)) {
			System.out.println("\nEulerian Path does not exist.");
			return;
		}
		visited = new boolean[adjList.size()];
		path = new ArrayList<>();

		if (start != -1 && end != -1) {
			getPath(adjList, start);
		} else {
			// Choose a starting vertex with nonzero edges.
			for (int i = 0; i < adjList.size(); i++) {
				if (adjList.get(i).size() > 0) {
					getPath(adjList, i);
					break;
				}
			}
		}

		if ((isDirected && path.size() != graphSize + 1) || (!isDirected && path.size() != graphSize + 1)) {
			System.out.println("\nPath does not exist. Possible Disconnected Components.");
			return;
		} else if (start != -1 && end != -1) {
			System.out.println("\nAn Eulerian Path does exist. Start at node " + start + " end at node " + end);
		} else {
			System.out.println("\nAn Eulerian Path does exist. Start anywhere.");
		}

		for (int i = 0; i < path.size(); i++) {
			System.out.print(path.get(i) + " ");
		}
		System.out.println();
	}

	/*
	Given the adjacency list of a graph, return it's Eulerian Circuit if one exists.
	@param adjList, the adjacency list representing the graph.
	*/
	public void getEulerianCircuit(Map<Integer, List<Integer>> adjList) {
		if (!hasEulerianCircuit(adjList)) {
			System.out.println("\nEulerian Circuit does not exist.");
			return;
		}
		System.out.println("\nVerifying Circuit is connected...");
		getEulerianPath(adjList);
		if ((isDirected && path.size() != graphSize + 1) || (!isDirected && path.size() != graphSize + 1)) {
			System.out.println("Path does not exist. Disconnected Components.");
			return;
		}
		System.out.println("\nAn Eulerian Circuit does exist. Start anywhere.");
	}

	/*
	@param graph, the graph with the Eulerian Path
	@param startNode, the starting node of the path
	*/
	public void getPath(Map<Integer, List<Integer>> graph, int startNode) {
		List<Integer> edges = graph.get(startNode);
		while (outgoing[startNode] != 0) {
			int vertex = edges.get(--outgoing[startNode]);
			getPath(graph, vertex);
		}
		path.add(0, startNode);
	}

	/*
	Adding a directed edge from u -> v or adding an undirected edge between u and v 
	to the graph depending on the type of Eulerian Path/Circuit solver.
	@param graph, the adjacency list representing the graph.
	@param u, vertex.
	@param v, vertex.
	*/
	public void addEdge(Map<Integer, List<Integer>> graph, int u, int v) {
		if (isDirected) {
			graph.get(u).add(v);
		} else {
			graph.get(u).add(v);
			graph.get(v).add(u);
		}
	}

	/*
	Debugging function to print out the graph.
	@param list, the adjacency list of the graph.
	*/
	public void printList(Map<Integer, List<Integer>> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i);
			for (int j = 0; j < list.get(i).size(); j++) {
				System.out.print(list.get(i).get(j) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}


	public static void main(String[] args) {

		/* Directed Graph */

		EulerianPC epc_directed = new EulerianPC(true);

		Map<Integer, List<Integer>> adjList_one, adjList_two, adjList_three;

		adjList_one = new HashMap<>();
		int N = 7;
		for (int i = 0; i < N; i++) {
			adjList_one.put(i, new ArrayList<>());
		}

		epc_directed.addEdge(adjList_one, 1, 3);
		epc_directed.addEdge(adjList_one, 1, 2);

		epc_directed.addEdge(adjList_one, 2, 2);
		epc_directed.addEdge(adjList_one, 2, 4);
		epc_directed.addEdge(adjList_one, 2, 4);

		epc_directed.addEdge(adjList_one, 3, 1);
		epc_directed.addEdge(adjList_one, 3, 5);
		epc_directed.addEdge(adjList_one, 3, 2);

		epc_directed.addEdge(adjList_one, 4, 6);
		epc_directed.addEdge(adjList_one, 4, 3);

		epc_directed.addEdge(adjList_one, 5, 6);

		epc_directed.addEdge(adjList_one, 6, 3);


		adjList_two = new HashMap<>();
		N = 4;
		for (int i = 0; i < N; i++) {
			adjList_two.put(i, new ArrayList<>());
		}

		epc_directed.addEdge(adjList_two, 0, 1);
		epc_directed.addEdge(adjList_two, 0, 1);

		epc_directed.addEdge(adjList_two, 1, 2);
		epc_directed.addEdge(adjList_two, 1, 3);

		epc_directed.addEdge(adjList_two, 2, 0);

		epc_directed.addEdge(adjList_two, 3, 0);



		adjList_three = new HashMap<>();
		N = 4;
		for (int i = 0; i < N; i++) {
			adjList_three.put(i, new ArrayList<>());
		}

		epc_directed.addEdge(adjList_three, 0, 1);
		epc_directed.addEdge(adjList_three, 1, 2);

		epc_directed.addEdge(adjList_three, 3, 0);
		epc_directed.addEdge(adjList_three, 3, 2);


		// Eulerian Path - Directed Graph
		System.out.println("Directed");
		System.out.println("Path Results:");
		epc_directed.getEulerianPath(adjList_one);
		/*epc_directed.getEulerianPath(adjList_two);
		epc_directed.getEulerianPath(adjList_three);*/
		System.out.println("\n");

		// Eulerian Circuit - Directed Graph
		System.out.println("Circuit Results:");
		epc_directed.getEulerianCircuit(adjList_one);
		/*epc_directed.getEulerianCircuit(adjList_two);
		epc_directed.getEulerianCircuit(adjList_three);*/
		System.out.println("\n");

		/* Undirected Graph */
		EulerianPC epc_undirected = new EulerianPC(false);
		Map<Integer, List<Integer>> adjListOne, adjListTwo, adjListThree, adjListFour;

		adjListOne = new HashMap<>();
		N = 6;
		for (int i = 0; i < N; i++) {
			adjListOne.put(i, new ArrayList<>());
		}

		epc_undirected.addEdge(adjListOne, 0, 1);
		epc_undirected.addEdge(adjListOne, 0, 2);

		epc_undirected.addEdge(adjListOne, 1, 2);
		epc_undirected.addEdge(adjListOne, 1, 3);
		epc_undirected.addEdge(adjListOne, 1, 4);

		epc_undirected.addEdge(adjListOne, 2, 3);
		epc_undirected.addEdge(adjListOne, 2, 4);

		epc_undirected.addEdge(adjListOne, 3, 4);
		epc_undirected.addEdge(adjListOne, 3, 5);

		epc_undirected.addEdge(adjListOne, 4, 5);

		adjListTwo = new HashMap<>();
		N = 5;
		for (int i = 0; i < N; i++) {
			adjListTwo.put(i, new ArrayList<>());
		}

		epc_undirected.addEdge(adjListTwo, 0, 1);
		epc_undirected.addEdge(adjListTwo, 0, 2);

		epc_undirected.addEdge(adjListTwo, 2, 3);
		epc_undirected.addEdge(adjListTwo, 2, 4);

		epc_undirected.addEdge(adjListTwo, 1, 3);
		epc_undirected.addEdge(adjListTwo, 1, 4);

		epc_undirected.addEdge(adjListTwo, 3, 4);

		adjListThree = new HashMap<>();
		N = 5;
		for (int i = 0; i < N; i++) {
			adjListThree.put(i, new ArrayList<>());
		}

		epc_undirected.addEdge(adjListThree, 0, 1);
		epc_undirected.addEdge(adjListThree, 0, 2);

		epc_undirected.addEdge(adjListThree, 1, 2);
		epc_undirected.addEdge(adjListThree, 1, 3);
		epc_undirected.addEdge(adjListThree, 1, 4);

		epc_undirected.addEdge(adjListThree, 2, 3);
		epc_undirected.addEdge(adjListThree, 2, 4);

		epc_undirected.addEdge(adjListThree, 3, 4);


		adjListFour = new HashMap<>();
		N = 7;
		for (int i = 0; i < N; i++) {
			adjListFour.put(i, new ArrayList<>());
		}

		epc_undirected.addEdge(adjListFour, 0, 1);
		epc_undirected.addEdge(adjListFour, 1, 2);
		epc_undirected.addEdge(adjListFour, 2, 3);
		epc_undirected.addEdge(adjListFour, 3, 0);

		epc_undirected.addEdge(adjListFour, 4, 5);
		epc_undirected.addEdge(adjListFour, 5, 6);
		epc_undirected.addEdge(adjListFour, 6, 4);

		// Eulerian Path - Undirected Graph
		System.out.println("Undirected");
		System.out.println("Path Results:");
		/*epc_undirected.getEulerianPath(adjListOne);
		epc_undirected.getEulerianPath(adjListTwo);
		epc_undirected.getEulerianPath(adjListThree);*/
		epc_undirected.getEulerianPath(adjListFour);
		System.out.println("\n");

		// Eulerian Circuit - Undirected Graph
		System.out.println("Circuit Results:");
		/*epc_undirected.getEulerianCircuit(adjListOne);
		epc_undirected.getEulerianCircuit(adjListTwo);
		epc_undirected.getEulerianCircuit(adjListThree);*/
		epc_undirected.getEulerianCircuit(adjListFour);
	}
}
