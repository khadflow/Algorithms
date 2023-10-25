import java.util.*;

public class ThreeSum {

    // O(N^2log(N))
    public List<List<Integer>> threeSum(int[] nums) {

        HashMap<Integer, Integer> hash = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                hash.put(nums[i], hash.get(nums[i]) + 1);
            } else {
                hash.put(nums[i], 1);
            }
        }

        // O(N log(N)) -- sorting the list before searching 
        Arrays.sort(nums);

        Set<List<Integer>> set = new HashSet<>();
        ArrayList<List<Integer>> arr = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            ArrayList<Integer> push = new ArrayList<>();

            // decrement the value if it's currently being considered
            hash.put(nums[i], hash.get(nums[i]) - 1);
            for (int j = i + 1; j < nums.length; j++) {

                // decrement the value if it's currently being considered
                hash.put(nums[j], hash.get(nums[j]) - 1);
                int search = -(nums[i] + nums[j]);
                
                // hashing the values gives better search/lookup time
                if (hash.containsKey(search) && hash.get(search) > 0) {
                    push.add(nums[i]);
                    push.add(nums[j]);
                    push.add(search);
                    Collections.sort(push);
                    set.add(push);
                    push = new ArrayList<>();
                }

                // increment the value after consideration 
                hash.put(nums[j], hash.get(nums[j]) + 1);
            }

            // increment the value after consideration 
            hash.put(nums[i], hash.get(nums[i]) + 1);
        }
        for (Object o: set) {
            arr.add((List<Integer>)o);
        }
        return arr;

    }


    public static void main(String[] args) {
        ThreeSum t = new ThreeSum();
        int[] nums = {-1,0,1,2,-1,-4};              // Expected Output [[-1,-1,2],[-1,0,1]]
        List<List<Integer>> arr = t.threeSum(nums);
        //System.out.println(arr.size());
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.get(i).size(); j++) {
                System.out.println(arr.get(i).get(j));        
            }
            System.out.println();
        }
    }
}