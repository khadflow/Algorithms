import java.security.SecureRandom;
import java.util.*;

public class RAM {

	// HEX
	// EX "x01010010" => 0000 0001 0000 0001 0000 0000 0001 0000 (32-bit instruction)  
	private ArrayList<String> instructions;

	private int[] powersOfTwo = new int[] {3, 10, 20, 30, 40, 50, 60, 70, 80};
	private TreeMap<Character, ArrayList<String>> validCharacters;
	private String[] infoSizes = new String[] {"Byte", "KiloByte",
									"MegaByte", "GigaByte", "TerraByte",
									"PetaByte", "ExaByte", "ZettaByte",
									"YottaByte"};

	public RAM() {
		validCharacters = new TreeMap<>();
		instructions = new ArrayList<>();
		for (int i = 0; i < infoSizes.length; i++) {
			char c = infoSizes[i].charAt(0);
			validCharacters.put(c, new ArrayList<String>());
		}
	}

	// Input of the form ("x00000000") for HEX to 32-bit BINARY
	public void addInstruction(String hex) {
		if (hex.length() != 10 || hex.charAt(0) != '0' || hex.charAt(1) != 'x') {
			System.out.println("Invalid Input " + hex);
			System.out.println("Input should be of form : 0x00000000 for a 32-bit instruction");
			System.out.println();
			return;
		} else {
			String binary = "";
			int i = 2;
			while (i < hex.length()) {
				char c = hex.charAt(i);
				if (c < 48 || (c > 57 && c < 97) || c > 102) {
					System.out.println("Invalid Input " + hex);
					System.out.println("Input should be of form : 0x00000000 for a 32-bit instruction");
					return;
				}
				int conversion = c;
				if (c >= 48 && c <= 57) {
					binary += padLeft(Integer.toBinaryString(c - 48), 4);
					binary += " "; // easier read
				} else {
					int h = 10;
					int index = 0;
					char[] hexArr = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
					// a b c d e f
					while (index < hexArr.length) {
						if (c == hexArr[index]) {
							binary += padLeft(Integer.toBinaryString(h), 4);
							binary += " "; // easier read
							break;
						} else {
							h++;
							index++;
						}
					}
					
				}
				i++;
			}
			instructions.add(binary);
		}
	}

	private String padLeft(String s, int i) {
		if (s.length() >= i) {
			System.out.println("String length " + s.length() + " is greater than or equal to " + i);
			return s;
		} else {
			while (s.length() < i) {
				s = "0" + s;
			}
			return s;
		}
	}

	public void readInstructions() {
		for (String s: instructions) {
			System.out.println(s);
		}
	}

	public void addLib(String s) {
		if (s.length() <= 0) {
			return;
		}
		char c = s.charAt(0);
		if (validCharacters.containsKey(c)) {
			//add to adLib list
			validCharacters.get(c).add(s);
		}
	}

	public void printValues() {
		System.out.println("Ad Lib Values:");
		System.out.println();
		for (Character c: validCharacters.keySet()) {
			ArrayList<String> arr = validCharacters.get(c);
			for (int i = 0; i < arr.size(); i++) {
				System.out.print(arr.get(i) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void createAdLib() {
		String ret = "";
		Random r = new SecureRandom();
		for (int i = 0; i < infoSizes.length; i++) {
			char c = infoSizes[i].charAt(0);
			ArrayList<String> wordList = validCharacters.get(c);
			if (wordList.size() == 0) {
				System.out.println("Not Enough Characters");
				System.out.println();
				return;
			}
			int randVal = r.nextInt(wordList.size());
			ret += wordList.get(randVal) + " ";
		}
		System.out.println(ret);
		System.out.println();
	}

	public static void main(String[] args) {
		RAM ram = new RAM();
		/*ram.addLib("Betty");
		ram.addLib("Keeps");
		ram.addLib("Many");
		ram.addLib("Goals");
		ram.addLib("Together");
		ram.addLib("Patiently");
		ram.addLib("Expecting");
		ram.addLib("Zebra");
		ram.createAdLib(); // not enough values
		ram.addLib("Yawns");
		ram.createAdLib();
		

		ram.addLib("Bob");
		ram.addLib("Kicks");
		ram.addLib("Galleries");
		ram.addLib("Practically");
		ram.addLib("Experiencing");
		ram.addLib("Zany");
		ram.addLib("Yellow");
		ram.createAdLib();
		ram.printValues();*/

		//System.out.println(Integer.toBinaryString(""));
		ram.addInstruction("0x0000000f");
		ram.addInstruction("0xffffffff");
		ram.addInstruction("0xa0000001");
		ram.addInstruction("0x00e00011");
		ram.addInstruction("0x00g00001"); // wrong
		ram.addInstruction("0x001100b1");
		ram.readInstructions();

	}
}
