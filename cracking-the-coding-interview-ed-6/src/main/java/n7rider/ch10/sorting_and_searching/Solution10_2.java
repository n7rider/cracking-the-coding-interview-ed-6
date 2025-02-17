package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Group Anagrams: Write a method to sort an array of strings so that all the anagrams are next to
 * each other.
 *
 * After finishing:
 * Tried using map of map but it's an overkill. We can just use arrays instead.
 * Map of items is not needed e.g., A=2,B=1 for BAA. We can just sort the chars as AAB.
 *
 * After comparing:
 * Similar to the book solution. The book solution also calls it a variant of bucket sort since we
 * try to look at the first/last few chars to make a decision with the sorting.
 */
public class Solution10_2 {

    public static void main(String[] args) {
        String[] arr = {"ARRARRA", "ABBA", "ABB", "RADAR", "DOOR", "DADA", "RODO", "AZ", "ZA"};
        System.out.println(Arrays.toString(partitionAnagrams(arr)));
    }

    static HashMap<String, String> sortedCharMap = new HashMap<>();
    static String[] partitionAnagrams(String[] arr) {

        Comparator<String> anagramComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                o1 = getFromMapOrSort(o1);
                o2 = getFromMapOrSort(o2);
                return o1.compareTo(o2);
            }
        };
        Arrays.sort(arr, anagramComparator);
        return arr;
    }

    private static String getFromMapOrSort(String s) {
        if(sortedCharMap.containsKey(s)) {
            return sortedCharMap.get(s);
        } else {
            char[] stringAsCharArray = s.toCharArray();
            Arrays.sort(stringAsCharArray);
            String sortedString = String.valueOf(stringAsCharArray);
            sortedCharMap.put(s, sortedString);
            return sortedString;
        }
    }
}

/*


*/