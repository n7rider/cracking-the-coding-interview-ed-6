package n7rider.ch17.hard;

/**
 * Max Submatrix: Given an NxN matrix of positive and negative integers, write code to find the
 * submatrix with the largest possible sum.
 *
 * After finishing:
 * Runtime is high at O(N^2 + N^4). The runtime is each height * each width * at each row * (at each col skipped by the max) *
 * fetch sum across rows (cached).
 *
 * After comparing:
 * The book banks on a previous problem that solves the max subarray in O(n) time (in Solution 16_17). It just banks of the fact
 * that if you approach on one side, you'll find the max subarray at the point where sum is max (Negative numbers bring down the
 * overall sum, so the theory still stands even if negative numbers are in the picture).
 * The solution reduces this problem to the idea that given a start row number and end row number, we just need to find the
 * sum of all numbers in the column (reducing each column's entries to a single entry), and then across the row, find the max
 * sub array. You can subtract from the beginning to remove negative or smaller numbers, so it's O(2n).
 * Considering we need to do this for a range of rowStart-rowEnd, and then collapse columns, then the subarray mechanism, it's O(N^4)
 * or O(R^3. C).
 *
 * Mine is similar but uses a O(n) cache to store sums from one side.
 */
public class Solution17_24 {

}

/*
Based on previous problem, how about we start creating a matrix from the bottom right?

Algorithm:
Assume N=9
Start from 1*1 and expand? (Probably 9*9 is bigger, but if it is not, going from 1 to 9 gives massive caching/memoizing
advantages e.g., sum(3*3) from the last = sum(2*2) + 2 above, 2 to the side, then the diagonal left.

We can create sum row-wise & column-wise or square-wise. The former is simple, we'll stick to it.
Also, submatrix can or cannot be square, so square-wise may not be efficient
sum(5*5) in the middle, say, sum(5*5) from (2,1) to (6,5) is sum(2nd row, 2-8 cols) - sum(2nd row, 7-8 cols) and so on

Algorithm:
Run a run from end 8,8 to 0,0
Create a n*n hashmap storing sum from last to first
    at each cell, eg., 7,5 - we need row-wise sum and col-wise sum, so we need 2 matrices that stores each of these
    row_wise int[][] and col_wise int[][]
    for int i = N-1 to 0
        for j = i to 0
            row_wise_prev = if(row_wise[i][j+1] out of bounds) return 0 : else return row_wise[i][j+1]
            row_wise[i][j] = row_wise_prev + A[i][j]

            col_wise_prev = if(col_wise[i+1][j] out of bounds) return 0 : else return col_wise[i+1][j]
            col_wise_prev[i][j] = col_wise_prev + A[i][j]

Now try all rect. sizes of submatrices, and find the largest one - we might need // Brute force
Can we find largest sum during the above loop and leverage that somewhere?

Brute-force

int max = 0,
int[] sm[4] // notes start x, y, length, width
for int i = N-1 to 0 // size of the matrix (OR) length of the matrix
    for j = 0 to N-1-i   // beginning row of matrix
      for col = 0 to N-1-i   // beginning col of matrix
        for k = 0 to N-1-i // height of the matrix (OR) width of the matrix. Not needed if we're looking only for squares
            curr_val = 0
            for l = j to <= k
                curr_val += row_wise[l][k] - row_wise[l][k + i] // This can be cached too. Cache by (x, y, h, w)
            set max if curr_val > max // Also note

Other attempts:
Can store max and min of each row, and explore only from there but this may not work with a clear logic

Here is an example:

-3  5  -2  -9   //  In each row,m max sum is at a different idx
 7 -2   3  -7
 1  1  -9   4
 2 -2   8   1

Biggest submatrix here =
7
1
2

So, most likely, the max will always be involved in the largest submatrix.

My question is what if there are close seconds, that will choose the max submatrix e.g.,

0  7   6
7 -9   6
7 -9   6
No, I still see one of the max used there.
So, the only difference is the col loop is not required, and is replace with max() of the row.

Runtime reduces from O(N^5) to O(N^4).

If we run from smaller to bigger cubes, we can cache curr_val by x,y,h,w. We can go it each val from the start, then
concat to find bigger values. 





 */