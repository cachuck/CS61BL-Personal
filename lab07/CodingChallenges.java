import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;


public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        Set<Integer> set = new HashSet<Integer>();
        int maxNum = -1;
        int missingNum = -1;
        for (int value = 0; value < values.length; value++) {
            set.add(values[value]);
            if (values[value] > maxNum) maxNum = values[value];
        }
        //System.out.println("DEBUG maxNum = " + maxNum);
        for (int i = 0; i != maxNum; i++) {
            if (!set.contains(i)) {
                missingNum = i;
                //System.out.println("DEBUG missingNum = " + missingNum);
                break;
            } //else System.out.println("DEBUG contains = " + i);
        }
        //System.out.println("DEBUG return = " + missingNum);
        return missingNum;
    }

    /**
     * Returns true if and only if two integers in the array sum up to n.
     * Assume all values in the array are unique.
     */

    public static boolean sumTo(int[] values, int n) { // {1, 2, 3, 4, 5} , 7
        Set<Integer> set = new HashSet<Integer>();
        for (int value = 0; value < values.length; value++) {
            set.add(values[value]);
        }
        for (int i : set) {
            if (set.contains(n - i)) return true;
        }

        return false;
    }


    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */

    public static boolean isPermutation(String s1, String s2) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char charindex = s1.charAt(i);
            if (map1.containsKey(charindex)) {
                map1.replace(charindex, map1.get(charindex) + 1);
            } else {
                map1.put(charindex, 1);
            }
        }
        for (int i = 0; i < s2.length(); i++) {
            char charindex2 = s2.charAt(i);
            if (map2.containsKey(charindex2)) {
                map2.replace(charindex2, map2.get(charindex2) + 1);
            } else {
                map2.put(charindex2, 1);
            }
        }
        return map1.equals(map2);
    }
}