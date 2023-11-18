import java.util.ArrayList;
import java.util.HashMap;

// Direct Mapping
public class Cache {

	private Disk disk;
	private int[][] cache;
	private String byteInstr;
	private int wordsPerBlock;
	private int bytesPerBlock;
	private int numCacheBlocks;
	private double numTagBits;
	private double numIndexBits;
	private double numOffSetBits;

	public Cache() {
		disk = new Disk();
		byteInstr = "00";

		wordsPerBlock = 1; // default
		numCacheBlocks = 4; // default
		bytesPerBlock = wordsPerBlock * 4;
		cache = new int[numCacheBlocks][2 + wordsPerBlock];
		// Cache Size Input
		// if 1 word per block : # of index bits = log(# of blocks in Cache), len(tag) = 32 - len(index)
		// else : # of offset bits = log(wordsPerBlock), # of index bits = log(# of blocks in Cache)[offset: length], len(tag) = 32 - len(index) - len(offset) 

		if (wordsPerBlock == 1) {
			numOffSetBits = 0;
		} else {
			numOffSetBits = (int) Math.ceil(Math.log(wordsPerBlock));
		}

		numIndexBits = (int) Math.ceil(Math.log(numCacheBlocks)); // 2
		// 8 is a placeholder for the total number of bits that represent the instruction address
		numTagBits = 8 - numIndexBits - numOffSetBits;

		cacheInfo();
	}

	/* USER FUNCTIONS */
	// addr -> 0xFF
	public void fetchAddr(String addr) {
		// Direct Map Cache
		 // TODO Add offset functionality for a Ccahe with more than one word per block
		byteInstr = addr.substring(2, addr.length());
		String[] tio = TIO(hexToBinary(addr));
		int tag = Integer.parseInt(tio[0], 2);
		int index = Integer.parseInt(tio[1], 2) % numCacheBlocks;

		// MISS
		if (cache[index][1] != tag || cache[index][0] == 0) {
			// STORE DATA THEN RETURN
			System.out.println("MISS at ADDR " + addr);
			IF(addr); // store data in cache
		} else if (cache[index][1] == tag && cache[index][0] != 0) {
			// RETURN DATA
			System.out.println("HIT at ADDR " + addr + " : "+ cache[index][2]);
		}
		return;
	}

	// Instruction Fetch && Store Binary
	private String IF(String addr) {
		String ret = hexToBinary(addr);
		storeTIO(ret);
		return ret;
	}

	private String hexToBinary(String s) {
		String binary = "";
		String convert = "";
		for (int i = 2; i < s.length(); i++) {
			int res = getInt(s, i);
			if (res == -1) {
				return "EMPTY STRING";
			}
			
			convert += Integer.toString(res);
		}
		int blockAddr = calculateBlockAddress(Integer.parseInt(convert));
		byteInstr = s.substring(2, s.length());

		// "binary" is the binary string of the Block Address used to generate TIO
		binary = padLeft(Integer.toBinaryString(blockAddr), 8); // default value of 8 bits
		return binary;
	}

	private String padLeft(String s, int i) {
		if (s.length() >= i) {
			return s;
		} else {
			while (s.length() != i) {
				s = "0" + s;
			}
			return s;
		}
	}

	private int getInt(String s, int i) {
		char c = s.charAt(i);
		if (c >= 48 && c <= 57) {
			int convert = c - 48;
			return convert;
		} else if (c >= 97 && c <= 102) {
			int convert = 10;
			int j = 0;
			char[] vals = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
			while (j < vals.length && c != vals[j]) {
				convert++;
				j++;
			}
			return convert;
		}
		return -1;
	}

	// Block Addr = Byte Addr / Bytes per Block -> (1 word == 4 bytes == 32 bits)
	// Index = Block Addr % # of Blocks in Cache
	// Used to has index into the Cache
	private int calculateBlockAddress(int byteAddress) {
		// How many words are in each block
		// words will give me the number of bytes
		int blockAddress = byteAddress / bytesPerBlock;
		return blockAddress;
	}

	private String[] TIO(String binary) {
		String offset = "";
		String index, tag;
		if (numOffSetBits != 0) {
			offset = binary.substring((int) (numTagBits + numIndexBits), 8);
		}
		tag = binary.substring(0, (int) numTagBits);
		index = binary.substring((int) numTagBits, (int) (numTagBits + numIndexBits));
		return new String[] {tag, index, offset};
	}

	private void storeTIO(String binary) {
		String[] tio = TIO(binary);
		int tag, index, offset;
		tag = Integer.parseInt(tio[0], 2);
		index = Integer.parseInt(tio[1], 2) % numCacheBlocks;
		offset = 0;

		if (tio[2].length() > 0) {
			offset = Integer.parseInt(tio[2], 2);
		}

		System.out.println("TIO: " + tag + " " + index + " " + offset);

		int data = disk.getData("0x" + byteInstr); // String s = Integer.toHexString(int num)
		cache[index][1] = tag;
		cache[index][0] = 1; // valid bit
		cache[index][2] = data;

	}



	private class Disk {
		private HashMap<String, Integer> testInstructions;
		private String[] addr;
		private int index;

		public Disk() {
			index = 0;
			testInstructions = new HashMap<>(); //
			addr = new String[] {"0x03", "0x0a", "0x0c", "0xaa", "0xc4"};
			int[] testData = new int[] {1, 2, 3, 4, 5};
			for (int i = 0; i < addr.length; i++) {
				testInstructions.put(addr[i], testData[i]);
			}
		}

		public int getData(String address) {
			int ret = testInstructions.get(address);
			index++;
			if (index % addr.length == 0) {
				index = 0;
			}
			return ret;
		}
	}


	/* DEBUGGING AND TESTING FUNCTIONS */

	private void cacheInfo() {

		System.out.println("Cache Info: ");
		System.out.println();
		System.out.println("Number of Blocks in Cache: " + numCacheBlocks + ", WordsPerBlock: " + wordsPerBlock + " ");
		System.out.println("Bytes Per Block: " + bytesPerBlock + ", Bits Per Block: " + (bytesPerBlock * 8));
		System.out.println("Number of Tag Bits: " + numTagBits + ", Number of Index Bits: " + numIndexBits + ", Number of Offset Bits: " + numOffSetBits);
		System.out.println();
	}

	public void printCache() {
		System.out.println("v T D");
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				System.out.print(cache[i][j] + " ");
			}
			System.out.println();
		}
	}





	public static void main(String[] args) {
		Cache cache = new Cache();
		String[] addr = new String[] {"0x03", "0x0a", "0x0c", "0xaa", "0xc4"};
		for (String s : addr) {
			cache.fetchAddr(s);
		}
		cache.fetchAddr(addr[addr.length - 1]);

		cache.printCache();

		//System.out.println(Integer.parseInt("c", 16)); // EASY STRING (BINARY OR HEX) to INTEGER w/ RADIX/BASE (2, 16)
	}
}