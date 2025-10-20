package n7rider.ch17.hard;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Random Set: Write a method to randomly generate a set of m integers from an array of size n. Each
 * element must have equal probability of being chosen.
 *
 * After finishing:
 * My algorithm is simple - Choose a random number, add it to out, then swap it with last. Repeat until out is filled up.
 * This way, once a number is selected, others have equal probability among themselves.
 *
 * After comparing:
 * The book copies over the original values, then runs a loop from m to n - choosing an index in out at random and filling with it arr[i]
 * It looks like the prob may not be equal since the random(O, i) only goes up to the current element, but like the previous problem,
 * it is just the other way around with my approach. What happens first in this solution happens last in mine.
 * I do 'm' swaps, but the book does 'm' copies in the beginning.
 *
 */
public class Solution17_3 {
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };
        System.out.println(randomSet(arr, 3));
        System.out.println(randomSet(arr, 10));
    }

    static Set<Integer> randomSet(int[] arr, int m) {
        // Validations - arr is not null, or empty, m is > 0, m is <= arr.length
        int n = arr.length;
        Set<Integer> out = new HashSet<>();
        for(int i = 0; i < m; i++) {
            int randIdx = findRandom(n);
            out.add(arr[randIdx]);
            swap(arr, randIdx, n - 1);
            n--;
        }
        return out;
    }

    private static int findRandom(int end) {
        // This can be a singleton when the solution is in its own class
        Random r = new Random();
        return r.nextInt(end);
    }

    private static void swap(int[] arr, int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
}

/*
Simplest version:
Generate random of (0, n). Kick out selected, gen random (0, n-1) and so on.
for i = 0 to m
    randIdx = math.rand(0, n)
    out.add(a[randIdx])
    swap arr[n-1] & arr[randIdx]
    n--

Alternate ways:
- Always generate rand(0, n)
Repeat if it's already in the out (Need to match box idx and val though, or need to maintain idx in out and not the value).

Generate rand(0, n-i) but jump over selected. That's the worst O(n) everytime.

Choose simplest or alternate(1)?
    Which one is truly random?

With the first one, prob increases everytime e.g., 1/17 to 1/16... after each selection
Alternate(1) = Prob remains same. We just repeat if a dupe is made.
It's a choice. But both give equal probability for each element, so choosing simplest





 */