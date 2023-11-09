import java.util.*;

/* Public (e, N) - Private (d, Thetat(N))
	M < N
	C = M^e mod N
	M = C^d mod N
	p and q must be co-prime

PRIME NUMBERS:
1, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97

Reference: https://www.cs.utexas.edu/~mitra/honors/soln.html
*/
public class Euler {

	public long N;
	public long e;
	private long theta;
	private long d;

	public Euler(long p, long q) {
		if (!isPrime(q) || !isPrime(q)) {
			System.out.println("One or more of the inputs is not prime. Create a new object.");
			return;
		}
		createKeys(p, q);
		System.out.println("Public Key: " + "(" + e + ", " + N + ")");
	}

	public boolean isPrime(long a) {
		long half = a / 2;
		for (int i = 2; i < half; i++) {
			if (a % i == 0) {
				return false;
			}
		}
		return true;
	}

	/*
	Euler's Theorem:
	for co-prime p and q
	a^(p - 1) == 1 mod p
	a^(k(p - 1)) == 1 mod p
	*/
	private void createKeys(long p, long q) {
		N = p * q;
		System.out.println("N: " + N);
		
		theta = (p - 1) * (q - 1);
		System.out.println("Theta: " + theta);

		e = 7; // Efficient algorithm to find a random value?
		while (gcd(e, theta) != 1) {
			e -= 1;
		}
		System.out.println("e: " + e);

		// d = e^-1 mod (p-1)(q-1)
		// ed == 1 mod theta(N)
		d = 1;
		while (e * d % theta != 1) {
			d++;
		}
		System.out.println("d: " + d);
	}

	public long encodeMessage(long M) {
		if (M >= N) {
			System.out.println("Failed to encode message.");
			return 0;
		} else {
			long c = (long) Math.pow(M, e);
			return c % N;
		}
	}

	public long decodeMessage(long C) {
		return (long) Math.pow(C, d) % N;
	}

	public long gcd(long a, long b) {
		if (a == b) {
			return a;
		} else if (a > b) {
			return gcd(a - b, b);
		} else {
			return gcd(a, b - a);
		}
	}

	public long binaryExpo(long a, long b) {
		if (b <= 0) {
			return 1;
		} else if (b == 1) {
			return a;
		} else {
			long temp = binaryExpo(a, b / 2);
			return temp * temp;
		}
	}

	/*
	Euler's Formula:
	e^(i * theta) = cos (theta) + isin (theta)

	Euler's Identity:
	e^(i * pi) + 1 = 0
	*/
	public void eulers() {
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		System.out.println("cos: " + cos + " i * sin: " + sin + " theta: " + theta);
		System.out.println();
	}

	public void eulers(double t) {
		double sin = Math.sin(t);
		double cos = Math.cos(t);
		System.out.println("cos: " + cos + " i * sin: " + sin + " theta: " + t);
		System.out.println("Euler's Identity: " + ((1 + cos + sin) < 1 ? 0 : (1 + cos + sin)));
		System.out.println();
	}

	public static void main(String[] args) {
		Euler rsa = new Euler(3, 11);
		
		// EVEN POWER
		System.out.println("Binary Expo: " + rsa.binaryExpo(4, 8));
		// ODD POWER
		System.out.println("Binary Expo: " + rsa.binaryExpo(4, 7));

		long M = 8;
		long C = rsa.encodeMessage(M);
		System.out.println("Encoding message: " + M + " to ciphertext: " + C);
		System.out.println("Decoding message: " + C + " to ciphertext: " + rsa.decodeMessage(C));

		rsa.eulers();
		rsa.eulers(Math.PI / 6);
		rsa.eulers(Math.PI / 4);

		// Euler's Identity
		rsa.eulers(Math.PI);
	}
}