package n7rider.ch16.moderate;

/**
 * Pairs with Sum: Design an algorithm to find all pairs of integers within an array which sum to a
 * specified value.
 *
 * After finishing:
 * Looks like we can do more using sum-arr[idx], but can't find much use, without iterating at O(n^2) time
 *
 * After comparing:
 * Same as mine
 */
public class Solution16_24 {
}

/*
arr = [1, 5, 7, 10, 4, 3, 1, 14, 10, 8, 11] | sum = 15

Simplest solution:
O(n^2) - Two loops
    if(a[i] + a[j] == sum)
        add to output

Can we solve this with O(2n) or O(mn) by running through the array multiple times?

1. Iterate through array, store to hashmap
2. Iterate through array, see if sum-arr[idx] exists in the map

Can we do this outside the additional storage?
1. Replace hashmap with hash set
2. Sort and iterate from both sides? O(n log n) + O(2n)

 */