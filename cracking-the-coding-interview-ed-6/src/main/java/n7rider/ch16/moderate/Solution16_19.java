package n7rider.ch16.moderate;

import java.util.*;

/**
 * Pond Sizes: You have an integer matrix representing a plot of land, where the value at that location
 * represents the height above sea level. A value of zero indicates water. A pond is a region of
 * water connected vertically, horizontally, or diagonally. The size of the pond is the total number of
 * connected water cells. Write a method to compute the sizes of all ponds in the matrix.
 * EXAMPLE
 * Input:
 * 0 2 1 0
 * 0 1 0 1
 * 1 1 0 1
 * 0 1 0 1
 * Output: 2, 4, 1 (in any order)
 *
 * After finishing:
 * The maze algorithm prints 3 also, so changed currWaterArea from int to an unmodifiable Set, then get pond size
 * from there
 *
 * After comparing:
 * The maze algorithm works indeed, all I needed to do is: currWaterArea + (currWaterArea from recursive calls) in the
 * helper. Also, the recursive calls to step in all directions is made by simply calling for i = -1 to 1, j = -1 to 1.
 * Neat!
 */
public class Solution16_19 {
    public static void main(String[] args) {
        int[][] land = new int[][] {{0, 2, 1, 0}, {0, 1, 0, 1}, {1, 1, 0, 1}, {0, 1, 0, 1}};
        System.out.println("\n\n" + findPondSize(land));
    }

    static Set<Integer> findPondSize(int[][] land) {
        // validate matrix is not null, size > 0, values between 0 and 2
        int[][] pondSize = new int[land.length][land[0].length];
        Set<Integer> out = new TreeSet<>(); // Just for testability, HashSet is fine too
        helper(land, pondSize, new HashSet<>(), 0, 0);


        for(int i = 0; i < pondSize.length; i++) {
            System.out.println(Arrays.toString(pondSize[i]));
            for(int j = 0; j < pondSize[0].length; j++) {
                out.add(pondSize[i][j]);
            }
        }
        out.remove(0);
        return out;
    }

    private static void helper(int[][] land, int[][] pondSize, Set<Coord> currWaterArea, int i, int j) {

        if(isLocationValid(land, i, j)) {
            System.out.printf("Now at (%d, %d). currWaterArea: %s\n", i, j, currWaterArea);
            if(land[i][j] == 0) {
                currWaterArea = new HashSet<>(currWaterArea);
                currWaterArea.add(new Coord(i, j));
                System.out.printf("More water found at (%d, %d). Added to currWaterArea: %s\n", i, j, currWaterArea);
            } else {
                currWaterArea = new HashSet<>();
                System.out.printf("NO water found at (%d, %d). Dump currWaterArea: %s\n", i, j, currWaterArea);
            }

            for(Coord coord: currWaterArea) {
                if(currWaterArea.size() > pondSize[coord.x][coord.y]) {
                    System.out.printf("Overrode pondSize at %d, %d from %d to %d\n", coord.x, coord.y,
                            pondSize[coord.x][coord.y], currWaterArea.size() );
                }
                pondSize[coord.x][coord.y] = Math.max(pondSize[coord.x][coord.y], currWaterArea.size());
            }

            System.out.println();



            helper(land, pondSize, currWaterArea, i + 1, j - 1); // diagonal - bottom left
            helper(land, pondSize, currWaterArea, i, j + 1); // right
            helper(land, pondSize, currWaterArea, i + 1, j); // down
            helper(land, pondSize, currWaterArea, i + 1, j + 1); // diagonal - bottom right


        }

    }

    private static boolean isLocationValid(int[][] land, int i, int j) {
        return i >= 0 && i < land.length &&
                j >= 0 && j < land[0].length;
    }

    static class Coord {
        int x;
        int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d) ", x, y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Coord)) {
                return false;
            }
            Coord c2 = (Coord) o;
            return x == c2.x && y == c2.y;
        }

    }
}


/*
Is this like the maze solution? i.e., step on all 4 directions, set visited = true, and repeat

Let's try that as the basic solution:
Set start pos = 0,0
Start

Validate if location is valid e.g., -1, 0 is not
Validate if location is visited
Is curr == water
    Add to curr-water-area
Else
    Add curr-water-area to set
Add visited[i][j] = true // This is not enough for diag?? It will be if we step on all 8 sides as opposed to 4
Step on all 8 sides passing curr-water

Pseudocode:
pond-sizes (land)
    validate land-matrix
    helper(land, boolMatrix, new Set(), 0, 0, 0)

helper(land, boolMatrix, out, currWaterArea, i, j)
    isCoOrdValid(land, i, j)
    ifLocNotVisited(boolMatrix, i, j)

    is land[i][j] == 0  // can be enum
        currWaterArea++
    else
        out.add currWaterArea

    boolMatrix[i][j] = true

    // step on all 8 sides (if we are just moving forward i.e., only right and down, then we need just 3 sides
    // i.e., down, right, diag-down-right
    // No, then it won't work for a pond of 0,3 and 1,2 because 0,3 won't look to its left
    // Just call all eight
    // Or add selectively? add bottom-left diag. All 4 don't need to be.

    helper.... i + 1, j
    helper.... i, j + 1
    helper.... i + 1, j - 1
    helper.... i + 1, j + 1

Dry run:
    (0, 3) & (1, 2) won't form because (1, 2)..(1,4) would have been picked by down movement, and the boolean would have
    been flipped on already. We don't want 3 in the output, we only want 4.

    Don't need visited if we move only in forward dir?

    Even then, we need to remove 3 and add 4 to the output.

    What if we do right and bottom-left-diag first? That should work.

    What if we have a lower triangular pattern?
    .... 0
       0 0

    It should work.

After writing code:
    This will work, but 3 will also be added to the set but it shouldn't be.

    Easiest solution - Storage heavy but carry an LL around, and when you add to out, check a map if this is already in any
    other entry, then remove it

    Any way to optimize?
    What about using visited matrix, rather than as boolean, use as int? But you still need to carry an LL in the params (instead of
    curr water area).
    Even then, we need a hash map of co-ord, water-size to look up.

    What if we make 0 to -1. This will make it impossible for other combs to pick it up
    But they still will try in other dir. and fail
    What about taking the max in the method? No won't work

    In a gist, we need to know if these co-ord are covered by a bigger pool, so we need some means of looking at out and removing
    things when we find a bigger one.
    Here's the alg:
        Pass another array pondSize, another LL currWaterArea
        When we stop // land != 0
            look at all ele in currWaterArea
                change to this if this > existing

        Run through the matrix and add all non-zero to set
        // This can't be run inline when checking hte LL because, we might remove a size that could be created by
        another pond e.g., you remove 3 here, but what if another pond of size 3 actually exists?





 */