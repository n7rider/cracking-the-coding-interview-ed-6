package n7rider.ch16.moderate;

/**
 * Best Line: Given a two-dimensional graph with points on it, find a line which passes the most
 * number of points.
 *
 * After finishing:
 * Brute force mechanism is good enough, since reducing the load helps but it is still O(n^2).
 *
 * I wasn't sure the brute force mechanism will be the answer (turned out it is the only way),
 * so I wanted to peek at the solution before going ahead. I need not give up this soon the next time, and at least flesh out
 * the solution.
 *
 * After comparing:
 * I had overlooked the fact that having the same slope isn't enough, we need the same y-intercept too (or x-intercept).
 * Otherwise, (1,1) (2,2) will be considered in the same line of (-5,0)(-4,1).
 *
 * Also, the solution creates an arraylist with a hashmap. I would have created a composite key of slope & intercept, and
 * found the solution without iterating through the hashmap entries.
 *
 *
 */
public class Solution16_14 {
}

/*
0,0 | 1,1 | 2,2 | 3, 4 | 4,5
Answer is x-y=0

Brute force:
find slope for each n1...nn, then nn...nn = n^2 slopes
Find no. with max occurrences

Testing with above ex:
   1     2   3   4        5
1  -     1   1   1.3    1.2
2  1     -   1   1.5    1.33
3  1     1   -   2      1.5
4  1.3  1.5  2   -      1
5  1.2  1.33 1.5 1      -

Iterate through each row and find max
Here max occ = 2 for m = 1

Reduce effort: Calc only for one half of triangle?


 */