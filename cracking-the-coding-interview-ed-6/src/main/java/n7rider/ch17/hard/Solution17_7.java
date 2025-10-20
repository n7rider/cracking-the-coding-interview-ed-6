package n7rider.ch17.hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;


/**
 * Baby Names: Each year, the government releases a list of the 10000 most common baby names
 * and their frequencies (the number of babies with that name). The only problem with this is that
 * some names have multiple spellings. For example, "John" and ''.Jon" are essentially the same name
 * but would be listed separately in the list. Given two lists, one of names/frequencies and the other
 * of pairs of equivalent names, write an algorithm to print a new list of the true frequency of each
 * name. Note that if John and Jon are synonyms, and Jon and Johnny are synonyms, then John and
 * Johnny are synonyms. (It is both transitive and symmetric.) In the final list, any name can be used
 * as the "real" name.
 * EXAMPLE
 * Input:
 * Names: John (15), Jon (12), Chris (13), Kris (4), Christopher (19)
 * Synonyms: (Jon, John), (John, Johnny), (Chris, Kris), (Chris, Christopher)
 * Output: John (27), Kris (36)
 *
 * After finishing:
 * This feels like a good candidate for merge forest or a similar algorithm, but not sure if there's a strategy straight away.
 * This solution with hash map works too and runs in O(n + m) time, but also comes with 3-5 hash map reads + 2 hash map writes
 *
 * After comparing:
 * So, a merge forest is easier to do a DFS once you visualize it as a graph. Runs in O(n + m) time, and the only overhead is
 * adding a Set read, and a Set write.
 */
public class Solution17_7 {
    public static void main(String[] args) {
        Map<String, Integer> names = Map.of("John", 15, "Jon", 12, "Chris", 13, "Kris", 4, "Christopher", 19, "Max", 1);
        List<Pair> synonyms = List.of(new Pair("Jon", "John"), new Pair("John", "Johnny"), new Pair("Chris", "Kris"), new Pair("Christopher", "Kris"));
        System.out.println(findTrueFrequency(names, synonyms));
    }

    public static Map<String, Integer> findTrueFrequency(Map<String, Integer> names, List<Pair> synonyms) {
        Map<String, String> flatMap = new HashMap<>();
        Map<String, Integer> outputMap = new HashMap<>();

        for(Pair pair: synonyms) {
            String first = pair.first;
            String second = pair.second;

            String outputMapKey = getOutputMapKey(first, second, flatMap);

            Integer currOutputValue = outputMap.getOrDefault(outputMapKey, 0);
            outputMap.put(outputMapKey, currOutputValue +
                    (flatMap.containsKey(first) ? 0 : names.getOrDefault(first, 0)) +
                    (flatMap.containsKey(second) ? 0 : names.getOrDefault(second, 0)));

            flatMap.put(first, outputMapKey);
            flatMap.put(second, outputMapKey);
        }

        for(String name: names.keySet()) {
            if (!flatMap.containsKey(name)) {
                outputMap.put(name, names.get(name));
            }
        }


            return outputMap;
    }

    // The first or second might have showed up earlier, so we look for the entry in output map based on this.
    // If neither has come up before, we'll randomly choose the second.
    private static String getOutputMapKey(String first, String second, Map<String, String> flatMap) {
        if(flatMap.get(first) != null) {
            return flatMap.get(first);
        }
        if(flatMap.get(second) != null) {
            return flatMap.get(second);
        }
        return second;

    }

    static class Pair {
        String first;
        String second;

        public Pair(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

}

/*
Brute force:

Map<String, int> namesCount
Map<String, String> synonyms
Map<String, NameStats> flatMap // NameStats- Set<String, namesCount

for(each key: synonyms.keySet)
    if(namesCount.key exists && synonyms.get(key) exists)
        // Two entries are created in the flatMap, but they access the same object
        if flatMap.get() == null for both
            create NameStats object with both names, both count
        else
            get NameStats object from either
        flatMap.put(name1, NameStats object + count)
        flatMap.put(name2, same NameStats object)

for(each key: flatMap)
    outputMap.put(key, count)

Runtime - O(m) + O(n)
Additional Storage - O(n*m) + O(n)
--

How do we optimize this?
How about making flatMap a Map of <String, String>, and make its value point to output map directly


Map<String, String> flatMap
Map<String, Int> output

for(each key: synonyms.keySet)
    if(outputMap.get() == null) for both
        outputMap.put(any key, combined value)

    else
        outputKey = outputMap.get(key1) != null ? outputMap.get(key1) : outputMap.get(key2)
        outputMap.get(outputKey) + other value // Can add both if you can set previously concluded to zero

    flatMap.put(key1, outputKey)
    flatMap.put(key2, outputKey)

for(each key: flatMap)
    outputMap.put(key, count)

Runtime - O(m) + O(n)
Additional Storage - 0 // Because strings are reused, and flatMap just reuses these values. If we still consider references,
    it's O(n)

 */
