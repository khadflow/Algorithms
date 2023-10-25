// javac DisjointSets.java
// java DisjointSets
// Documents\Gitlet\Algorithms

public class DisjointSets {

	public int[] arr;
	public int[] size;

	// O(N)
	public DisjointSets(int N) {
		arr = new int[N];
		size = new int[N];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
			size[i] = 1;
		}
	}

	// 0(1)
	public void quick_union(int x, int y) {
		if (arr[x] == arr[y]) {
			// same parent, already connected
			return;
		}
		// Minimize tree depth

		int px = arr[x];
		int py = arr[y];

		if (size[x] < size[y]) {	// X is the smaller tree
			arr[x] = py;
			size[y] += size[x];
		} else { 					// Y is the smaller tree;
			arr[y] = px;
			size[x] += size[y];
		}

	}

	// O(log N)
	public int findRoot(int x) {
		if (x == arr[x]) {
			return x;
		}

		// Path Compression
		int i = x;
		while (i != arr[i]) {
			arr[i] = arr[arr[i]]; // Current value's new parent will be its grandparent
			i = arr[i];
		}
		return i;
	}

	// O(1)
	public boolean isConnected(int x, int y) {
		// Path Compression will keep this true
		return arr[x] == arr[y] ? true : false;
	}


	public static void main(String[] args) {
		DisjointSets ds = new DisjointSets(10);
		ds.quick_union(0, 1);
		ds.quick_union(1, 2);
		System.out.println("Reflexive: " + ds.isConnected(1, 1)); // reflexive
		System.out.println("Symmetric: " + ds.isConnected(1, 0)); // Symmetric
		System.out.println("Transitive: " + ds.isConnected(0, 2)); // transitive


		System.out.println(ds.isConnected(2, 3));
		ds.quick_union(2, 3);
		System.out.println(ds.isConnected(2, 3));

		/*System.out.println("Direct: " + ds.isConnected(1, 2));
		System.out.println("Direct: " + ds.isConnected(0, 1)); 
		System.out.println("FindRoot of 1: " + ds.findRoot(1));
		System.out.println("FindRoot of 2: " + ds.findRoot(2));
		System.out.println("FindRoot of 3: " + ds.findRoot(3));*/

		for (int i = 0; i < ds.arr.length; i++) {
			System.out.print(ds.arr[i] + " ");
		}
		System.out.println();

		for (int i = 0; i < ds.size.length; i++) {
			System.out.print(ds.size[i] + " ");
		}
	}


	// Create Connectivity 2D array and run it using DS.
}