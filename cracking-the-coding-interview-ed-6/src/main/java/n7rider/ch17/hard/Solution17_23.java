package n7rider.ch17.hard;

/**
 * Max Black Square: Imagine you have a square matrix, where each cell (pixel) is either black or white
 * Design an algorithm to find the maximum subsquare such that all four borders are filled with black
 * pixels.
 *
 * After finishing:
 * I'm using a row-wise and a column-wise hashmap to catch locations of all black squares (no sorting needed, they
 * are sorted as they do from the top-down). Then, look up the hashmaps to find the biggest square. Start from the
 * largest entry in the values.
 *
 * After comparing:
 * The book starts from the bottom right and keeps adding black squares to the left and bottom of each entry. Then we
 * can look up cells from the top left to find the biggest square -- by checking count of black squares to the left/bottom
 * from the corner cells (and subtract ones from the bottom/right corners to eliminate squares outside the range of
 * the current square).
 * Classic use of when running from the back is more beneficial.
 *
 *
 */
public class Solution17_23 {
}

/*
square matrix of size n*n
each cell can be randomly black or white

We find borders of the biggest square with black borders

Simplest way:
Start with the biggest square
Keep reducing size and do sliding windows until a biggest is found
Stop: At any black square if size is down to 1

bool sq [][]

for (size = sq.size - 1; size >= 0; size--) // Size goes from n-1 to 0 to avoid ArrayIdxOutOfBounds
    for (int i = 0; i + size < sq.size; i++)  // Row
      for (int j = 0; j +size < sq.size; j++)  // Column
         borders = (i,j),  (i, j+size),   (i+size,j),    (i+size,j+size)
         if all borders ARE black // Includes one tile check too.
            return borders

Runtime is: n  * n * n = O(n^3)

How to bring it down?

Do a run through the square first, and search only between first and last black tiles, so size = last.row-last.col
If result = 4 out of 8 tiles, we would have just done half the work
If result = 1, runtime is almost O(n^2).
Average is still O(n^3)

Caching entries as we go?
Do a run through the square first.
Store black tile location in 2 hash maps, by row, and then by col. We can do separate runs rowwise and columnwise to get contents auto-sorted
    if black tiles are in 2,2, | 2,8, | 4,4, then store hashByRow(2 = {2,8}, 4={4}), hashByCol={2={2}, 8={2}, 4={4}
    Also get the first and last entries during the run.
    for i  = first black entry to last // or run through one hashmap's keys - whichever is the smallest
        req-count = last - first
        for each entry in hashmapBy Row // Start from largest
            // Proceed only if there are req-count entries in the hash map entry
            entry.count == req-count && colHashMap.get(i).count == req-count &&    // top line && left line
                colHashMap.get(entry.val) == req-count &&                          // right line
                rowHashMap.get(entry.val) == req-count                             // bottom line


Runtime:
O(n^2) for the first run,
n/2 (for the i loop) * O(n/2)^2  // n/2 is the average size of hash map



 */