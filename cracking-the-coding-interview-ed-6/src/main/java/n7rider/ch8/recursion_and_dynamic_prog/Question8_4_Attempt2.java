package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 Power Set: Write a method to return all subsets of a set.

 After finishing algorithm:
 Same as book
 Subsets is for finding all combinations

 */
public class Question8_4_Attempt2 {
    public static void main(String[] args) {
        Set<String> set = Set.of("a1", "a2", "a3", "a4");
        System.out.println(getAllSubsets(set));
    }

    static List<List<String>> getAllSubsets(Set<String> set) {
        List<String> input = set.stream().map(e -> e).collect(Collectors.toList());
        return getAllSubsetsInt(input, set.size() - 1);
    }

    static List<List<String>> getAllSubsetsInt(List<String> inputSet, int idx) {
        if(idx == -1) {
            List<String> emptyList = List.of();
            return List.of(emptyList);
        }

        var output = new ArrayList<List<String>>();
        var subsets = getAllSubsetsInt(inputSet, idx - 1);
        var currEle = inputSet.get(idx);
        for(List<String> list: subsets) {
            List<String> newList = new ArrayList<>();
            newList.addAll(list);
            newList.add(currEle);
            output.add(newList);
        }
        output.addAll(subsets);
        return output;

    }
}

/*
a1, a2, a3, a4

1: Include just empty: {}
2: Include  a1: {} + {a1}
3. Include a2: {} + {a1} + {a2} + {a1, a2}

base case:
  if count == -1
    return {{}}

recursive case (idx)
  var output = new list<list>
  var subsetsSoFar = method(idx-1)
  output.addAll(subsetsSoFar)

  var currValue = set.get(idx)
  for(list l1: list<list> subsetsSoFar)
    newList = new ArrayList
    newList.addAll(l1)
    newList.add(currValue)
    output.add(newList)

  return output





 */