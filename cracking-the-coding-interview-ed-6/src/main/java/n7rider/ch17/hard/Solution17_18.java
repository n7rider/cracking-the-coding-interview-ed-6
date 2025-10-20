package n7rider.ch17.hard;

/**
 * Shortest Supersequence: You are given two arrays, one shorter (with all distinct elements) and one
 * longer. Find the shortest subarray in the longer array that contains all the elements in the shorter
 * array. The items can appear in any order.
 * EXAMPLE
 * Input:{1, 5, 9} I {7, 5, 9, 0, 2, 1, 3, 5, 7, 9. 1, 1, 5, 8, 8, 9, 7}
 * Output: [ 7, 10] (the underlined portion above)
 *
 * After implementing:
 * I'm not a big fan of how I jumped to the idea, but I think the solution looks decent.
 * Maybe I should focus on this too. Intuitive solutions might work, but need a good segue to those.
 *
 * After comparing:
 * My bruteforce solution can be more efficient. I was looking for different sliding window sizes, but
 * that's not a factor. We are going to stop anyway as soon as we find the sequence since we want only the smallest.
 *
 * The book arrives at the longest-smallest using a truth table, so that's a good segue to intuitions. If there
 * are none, that's a good way to find ideas.
 *
 * The book uses a different way though. It stores the maximum idx at which all entries are found until this index, and
 * then finds diff curr-index at each step to calculate the smallest..
 * I use the map.contains() to check if an item is in B, and so the runtime is O(S). but the book actually goes
 * through the entries of B to find the max index for each ele, and so the runtime is O(SB).
 * The magic/not-so-magic-ness of hashmap might come into discussion in scenarios like this.
 */
public class Solution17_18 {
}

/*
Simplest solution:

Create hashmap Integer, List<Integer> - bIndex, positionsInA
Create remChar = b.len
Convert B to a set
For i in A
    if B.contains i
        Add i's index to hashmap
        remChar--
    if remChar == 0
        stop

This is not the shortest subarray but the first subarray.
With look up as O(1), the runtime is O(a)

----

Simplest solution with shortest subarray:

We can't stop where prev soln ends, and another starts
We need to look at each possible subarray e.g., size 3 sliding windows, size 4 sliding windows and son


for i = b.len to a.len // sliding window size. 3 to 17 here
    for j = 0 to a.len - b.len // sliding window range. Start from 0, but stop at 17-3 = 14 for the first one and so on
        for k = j to b.len // each ele in sliding window.
            create set with all elements for j's range
        check if set has all of b's contents
        if true
            return j, j + b.len
return none

Runtime: O(a * a * a * b) = O(a^3 . b)

----

The above gives the smallest subarray, but can we improve this.

Since we have a hashmap, we can consider this like LED indicators?
All should flash as we go through a
We'll go one by one, there's no sliding window, but as soon as we get a dupe, we remove it and calculate new len

init hashmap with <Integer, Integer> = hashmap <b-ele, index-in-a>
initially, all ele's have proper key, but val is -1

eleLeft = b.len
for i = 0 to a.len - 1
    curr = a[i]
    if map.contains(curr)
        if eleLeft != 0 && map.get(curr) == -1
            eleLeft--  // find if all ele are in the hashmap
        map.put(curr, i)
        // find curr range (since prev entry was kicked out) - use priority queue frot his
        pq.put(i)
        pq.popOldest() // remove prev entry for cur ele
        pq.peek // oldest entry currently
        curr range = curr - pq.peek
        if curr range < min-range
            min-range = curr-range // also can save idxes this way

Runs in O(a), and storage is O(b) for map + O(b) for queue

No further memoization since we already use map

I realize pq won't work if items arrive out of sequence e.g., 1, 3, 5, 3
We just need to replace items at will, and also calculate lowest to highest
To prevent calculating lowest to highest, we can maintain FIFO. but to replace items at will, we need random access.
e.g., we might want need to delete the mid 3 in 1, 3, 5, 3. We already have the hashmap to maintain this location
.
So the only change is to replace this PQ with an array list.


 */