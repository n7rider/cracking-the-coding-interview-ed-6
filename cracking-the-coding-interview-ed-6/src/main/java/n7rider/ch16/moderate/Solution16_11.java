package n7rider.ch16.moderate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Diving Board: You are building a diving board by placing a bunch of planks of wood end-to-end.
 * There are two types of planks, one of length shorter and one of length longer. You must use
 * exactly K planks of wood. Write a method to generate all possible lengths for the diving board.
 *
 * After finishing:
 * This looks like dynamic programming. I've used recursion to solve the solution piece-meal, so I guess
 * this is some kind of memoization. Recursion with more thought into how the problem is solved, and
 * how to split it into steps. I moved away from solutions that were highly specialized for 2 types of
 * planks, and focused on N types of planks with K planks in total.
 *
 * After comparing with solution:
 * The book's optimal solution is the brute force I used - using the realization that only K values are
 * possible with 2 types. The memoization is done with using both pieces in a single go.
 * i.e., in the internal method, do recursive calls rec(use-shorter-len)  & rec(use-longer-len).
 *
 * But I tried to go with all combinations i.e., for 3 types, mine will use 003 (i.e., 3 of type 3), 012, 021,
 * 030, 102 etc. That's what you have to do when you have 'n' types.
 */
public class Solution16_11 {
    public static void main(String[] args) {
        var out1 = findAllPossibleLen(new int[]{1, 2}, 10);
        assertEquals(11, out1.size());

        var out2 = findAllPossibleLen(new int[]{1, 2, 3}, 10);
        assertEquals(21, out2.size());
    }

    static Set<Integer> outList = new HashSet<>();

    // Assumes all lengths are sent as array, add a launch method one layer upstream to convert if this is not the case
    public static Set<Integer> findAllPossibleLen(int[] typeArr, int k) {
        outList = new HashSet<>();
        findLenInt(typeArr, k, 0, 0, 0);
        return outList;
    }

    private static void findLenInt(int[] typeArr, int k, int currIdx, int planksSoFar, int lenSoFar) {
        if(currIdx == typeArr.length - 1) {
            int possibleLen = lenSoFar + ( (k - planksSoFar) * typeArr[currIdx]);
//            System.out.println(possibleLen);
            outList.add(possibleLen);
        } else {
            for (int i = 0; i <= Math.min(k, k - planksSoFar); i++) {
                int len = typeArr[currIdx] * i;
                findLenInt(typeArr, k, currIdx + 1, planksSoFar + i, lenSoFar + len);
            }
        }
    }
}

/*
Total no. of planks = K
Combinations possible (0 sh, K long).... (K sh, 0 long)
Ex. k = 4
0, 4 | 1, 3 | 2, 2, | 3, 1 | 4, 0

Simplest solution:
for int i = 0 to k (k excluded)
   set.add(i * sh + (k - i) * lo)

print set.size // dupes are excluded

Is it even possible for a dupe?

if count of sh = x, count of lo = k-x
If y and z are their lengths, xy + (k-x)z
Too complex

ex: k = 7
sh 1 lo 2 -> 2,5 = 12
1,6 = 13

Yeah, if sh < lo, then it's a decreasing fn as we go (0 sh, k lo) to... (k sh, 0 lo)
So, all possible lengths = K

Generate all possible lengths:
Brute force:
for int i = 0 to k (k excluded)
   print (i * short) + ((k - i) * long)

Works for 2 types, what about 3 types?
0 0 k
0 1 k-1
0 2 k-2
...
1 0 k-1

for int i = 0 to k
  for int j = 0 to k
    // for int x = 0 to k
        i * sh + j * me + (k - i - j) * lo

How about we do without explicit for loops? For n types, we can't create n for loops before-hand
What about recursion?

Algorithm:
Represent types as array - type[]
Represent count as array - count[]

equation essentially is: type[0] * count[0] + type[1] * count[1] ..... + type[k-1] * count [k-1]

What about recursing 2 at a time?

meethod(idx=0, total=0, len-so-far=0)

method (curr-idx, total, len-so-far)
if idx == k - 1
   sum = curr-idx * (k - total) + len-so-far
   print
else
    for int x = 0 to max(k, total-k)
        int op1 = x * type[curr-idx]
        method(curr-idx + 1, total + op1)

 */