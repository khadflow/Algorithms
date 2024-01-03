public class Matrix {
	
	private int[][] A;
	private int[] dimensions;
	
	public Matrix(int[][] matrix) {
		A = matrix;
		dimensions = new int[2];
		dimensions[0] = A.length; // # of rows
		dimensions[1] = A[0].length; // # of columns
	}

	public int[] getDimensions() {
		return dimensions;
	}

	public int[][] multiply(Matrix B) {
		int[] dim = B.getDimensions();

		if (dim[0] != dimensions[1] && dim[1] != dimensions[0]) {
			System.out.println("Invalid matrix dimensions.");
			System.out.println("A matrix dimensions -- Rows " + dimensions[0] + " Columns " + dimensions[1]);
			System.out.println("B matrix dimensions -- Rows " + dim[0] + " Columns " + dim[1]);
			return null;
		}
		else {
			
			int[][] fin = new int[dimensions[0]][dim[1]];
			System.out.println("Final dimensions: " + fin.length + " x " + fin[0].length);

			
			for (int i = 0; i < fin[0].length; i++) { // i == B column
				for (int j = 0; j < fin.length; j++) { // j == A row
					int[] product = multiplyVectors(A, B.A, j, i);
					int dot = sumArr(product);
					fin[j][i] = dot;
				}
			}
			
			return fin;
		}
	}

	private int[] multiplyVectors(int[][] A, int[][] B, int row, int col) {
		int[] r = A[row];
		int[] c = new int[B.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = B[i][col];
		}

		if (r.length != c.length) {
			System.out.println("Row " + r.length + " Column " + c.length);
			return null;
		}
		int[] ret = new int[r.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (r[i] * c[i]);
		}

		return ret;
	}

	private int sumArr(int[] arr) {
		int sum = 0;
		if (arr == null) {
			return -1;
		}
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}

	public static void main(String args[]) {
		/*		
		3x2
		B == [[1, 2],
	 	[3, 4],
	 	[5, 6]]

		 2x3
		A == [[1, 2, 3],
	 	[4, 5, 6]]


	 	== [[22, 30],
	  	[49, 64]]

		*/

		// EXAMPLE #1
		int[][] A, B;
		A = new int[2][3];
		B = new int[3][2];

		A[0][0] = 1;
		A[0][1] = 2;
		A[0][2] = 3;

		A[1][0] = 4;
		A[1][1] = 5;
		A[1][2] = 6;


		B[0][0] = 1;
		B[0][1] = 2;

		B[1][0] = 3;
		B[1][1] = 4;

		B[2][0] = 5;
		B[2][1] = 6;


		Matrix A_matrix = new Matrix(A);
		Matrix B_matrix = new Matrix(B);

		int[][] fin = A_matrix.multiply(B_matrix);

		for (int i = 0; i < fin.length; i++) {
			for (int j = 0; j < fin[i].length; j++) {
				System.out.print(fin[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();


		// EXAMPLE #2
		A = new int[2][3];

		A[0][0] = 1;
		A[0][1] = 1;
		A[0][2] = 1;

		A[1][0] = 2;
		A[1][1] = 2;
		A[1][2] = 2;


		B = new int[3][2];

		B[0][0] = 1;
		B[0][1] = 1;
		
		B[1][0] = 2;
		B[1][1] = 2;

		B[2][0] = 3;
		B[2][1] = 3;

		A_matrix = new Matrix(A);
		B_matrix = new Matrix(B);
		fin = B_matrix.multiply(A_matrix);

		for (int i = 0; i < fin.length; i++) {
			for (int j = 0; j < fin[i].length; j++) {
				System.out.print(fin[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();


		// EXAMPLE #3
		A = new int[3][3];

		A[0][0] = 6;
		A[0][1] = 2;
		A[0][2] = 4;

		A[1][0] = -1;
		A[1][1] = 4;
		A[1][2] = 3;

		A[2][0] = -2;
		A[2][1] = 9;
		A[2][2] = 3;

		B = new int[3][1];

		B[0][0] = 4;
		B[1][0] = -2;
		B[2][0] = 1;

		A_matrix = new Matrix(A);
		B_matrix = new Matrix(B);
		fin = A_matrix.multiply(B_matrix);

		for (int i = 0; i < fin.length; i++) {
			for (int j = 0; j < fin[i].length; j++) {
				System.out.print(fin[i][j] + " ");
			}
			System.out.println();
		}
	}
}