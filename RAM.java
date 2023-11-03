import java.util.*;

public class RAM {

	private String[] infoSizes = new String[] {"Byte", "KiloByte",
									"MegaByte", "GigaByte", "TerraByte",
									"PetaByte", "ExaByte", "ZettaByte",
									"YottaByte"};
	private int[] powersOfTwo = new int[] {3, 10, 20, 30, 40, 50, 60, 70, 80};
	private TreeMap<Character, ArrayList<String>> validCharacters;
	//private ArrayList<String>[] adLib;

	public RAM() {
		validCharacters = new TreeMap<>();
		for (int i = 0; i < infoSizes.length; i++) {
			char c = infoSizes[i].charAt(0);
			validCharacters.put(c, new ArrayList<String>());
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
		// TreeMap will allow sorted key value print outs
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
		Random r = new Random();
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
		ram.addLib("Betty");
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
		ram.printValues();

	}
}