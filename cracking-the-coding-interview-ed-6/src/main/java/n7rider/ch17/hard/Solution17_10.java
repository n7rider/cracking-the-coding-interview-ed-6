package n7rider.ch17.hard;

/**
 * Majority Element: A majority element is an element that makes up more than half of the items in
 * an array. Given a positive integers array, find the majority element. If there is no majority element,
 * return-1. Do this in O(N) time and 0(1) space.
 * EXAMPLE
 * Input:
 * Output:
 * 1 2 5 9 5 9 5 5 5
 * 5
 *
 * On finishing:
 * Not sure, I can get it to O(n). so I stopped
 *
 * On comparing:
 * The book's solution will work only when an item has majority in a contiguous space e.g., 7 in 3, 1, 7, 1, 1, 7, 7, 3, 7, 7, 7
 * has a majority inside the subset of 7, 7, 3, 7, 7, 7.
 * It won't work for an example like 1, 1, 7, 2, 2, 7, 3, 3, 7, 4, 4, 7
 */

public class Solution17_10 {
}

/*
Simplest solution:
- Create hash map of <num, count>
- For each item in array,
    update count in hash map
- For each item in hash map
    update max with the biggest count
    return max or -1

Run time - O(2n)
Space - O(2n)

How do we optimize this?

Keep track of max as we iterate the first time, so run time = O(n)
But space is still O(2n)


Solution that can run in O(1) space:

for i = 0 to n
    currNo = arr[i]
    for j = i to n
        counter++ on seeing currNo
    if counter > half
        return currNo
return -1

This needs O(1) space but needs O(n^2) times


A middle ground:
    Sort array in-memory e.g., quick sort
    And count as go through the loop for the longest recurring one

Runs in O(1) space but needs O(n + n log n) time

Observations:
If we are through 50% of the items, the output should have appeared already (if it exists)

A finicky detail oriented solution:
Use a special int e.g., INT_MIN that won't occur in the series as a tombstone marker
for i = 0 to n/2 // If no. didn't occur before half mark, it can't be the majority
    if num is tombstone_marked
        skip
    count_curr = 0
    int curr_num
    for j = i to n
        remaining = n - i
        if curr_num + rem < 51%
            break // this is not the maximum
        tombstone_market (curr)

This will take O(n^2) on paper, but actually O(n^2 / 4) or even lesser if a few numbers are repeated.
If n = 100, and unique_num = 20 (and 10 on each side of 50), then it runs in O(n/2 * n / 10).
Here, it is O(5), so much closer to O(n) than O(n^2)


 */