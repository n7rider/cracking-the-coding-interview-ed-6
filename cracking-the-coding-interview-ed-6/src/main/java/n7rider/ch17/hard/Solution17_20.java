package n7rider.ch17.hard;

/**
 * Continuous Median: Numbers are randomly generated and passed to a method. Write a program
 * to find and maintain the median value as new values are generated.
 *
 * After finishing:
 * Runtime of O(n log n) seems basic, but taking less than (log n) for each item on average seems a big ask.
 *
 * After comparing:
 * Same as my min and max heap appraoch
 */
public class Solution17_20 {
}

/*
Median = middle most value

5 3 7 | Median = 5
5 3 7 9 11 | Median = 7

Simplest solution:
Sort nums as they arrive
Random access to n/2 th item

Runtime - O(n log n)
Memory - O(n)

---

Ways to improve:
What if we use a self-balancing tree? Root is always the min but balancing is complex.

Runtime is still O(n log n)
Memory - O(n)

--

How about 2 min heap?

The first heap stores first half from small to large, second stores second half from small to large
Keep count in both
If diff-in-count >=2, transfer from one to other

The top of second heap is always the median.

e.g.,
5 - insert to min heap
5 3 - insert to min heap, diff too high, heaps are [3] [5]
5 3 7 - heaps [3] [5, 7] - Median is still 5
5 3 7 9 - insert to second heap. diff too high, heaps are [3, 5] [7, 9]
5 3 7 9 11 - insert to second heap. heaps are [3, 5] [7, 9, 11]

Runtime is still O(n log n)
Memory - O(n)

--
Memory needs to be O(n) because items added to one side can skew median towards it, so we need all items

If all items are in display, we need to fit each new item in its place which can take log n, so n log n is the total.

Can we go to O(1) with a hashmap? Then sequential access is lost.
 */