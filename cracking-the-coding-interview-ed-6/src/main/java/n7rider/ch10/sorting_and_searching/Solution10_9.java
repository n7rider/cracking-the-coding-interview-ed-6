package n7rider.ch10.sorting_and_searching;

/**
 * Sorted Matrix Search: Given an M x N matrix in which each row and each column is sorted in
 * ascending order, write a method to find an element.
 *
 * Approach
 * I'm doing binary search over the maze path-finder algorithm, step into all directions, then run validations.
 * Runs in O(log(n)).
 *
 * After comparison:
 * The book has 2 approaches:
 * Approach 1: Start from top right, walk through the diagonals, eliminating rows and columns as it goes. This can
 * take O(m) or O(n)
 * Approach 2: Binary search through the diagonal O(log(n)) to eliminate two corners. Then repeat this in the other
 * two potential corners until a match is found (O(log(n)).
 *
 * I tried to do something like approach 2 first. It didn't pan out, because I was trying to binary search the
 * entire 2 potential corners, and not their diagonals. Anyway, the current approach I have also works fine.
 */
public class Solution10_9 {
    public static void main(String[] args) {
        int[][] m1 = new int[][]{{1, 2}, {3, 4}};
        System.out.println(findElement(m1, 4));
        System.out.println();

        int[][] m2 = new int[][]{
                {1, 3, 5, 7, 9, 11, 312},
                {4, 6, 8, 10, 12, 14, 319},
                {7, 10, 13, 16, 18, 20, 333},
                {10, 11, 15, 35, 38, 45, 347},
                {13, 14, 16, 39, 39, 50, 395},
                {16, 20, 35, 55, 60, 99, 399},
                {716, 720, 735, 755, 760, 799, 799}
        };
        System.out.println(findElement(m2, 16));
    }

    static int findElement(int[][] m, int ele) {
        if (m == null || m.length < 1 || m[0].length < 1 || m.length != m[0].length) {
            // Skip
            System.out.println("Validations failed");
            return -1;
        }

        return findElement(0, m.length - 1, 0, m[0].length - 1, m, ele);
    }

    private static int findElement(int minX, int maxX, int minY, int maxY, int[][] matrix, int searchedEle) {
        if(minX > maxX || minY > maxY) {
            return -1;
        }

        int centerX = (minX + maxX) / 2;
        int centerY = (minY + maxY) / 2;
        int currEle = matrix[centerX][centerY];
        if(currEle == searchedEle) {
            System.out.printf("\nFound ele at: %d, %d\n", centerX, centerY);
            return currEle;
        }

        // LEFT or RIGHT
        System.out.printf("Curr X: %d, Curr Y: %d | H-Direction: left?: %s\n", centerX, centerY, searchedEle < currEle);
        int resultH = searchedEle < currEle
                ? findElement(minX, maxX, minY, centerY - 1, matrix, searchedEle)
                : findElement(minX, maxX, centerY + 1, maxY, matrix, searchedEle);

        if(resultH != -1) {
            return resultH;
        }

        // TOP or BOTTOM
        System.out.printf("Curr X: %d, Curr Y: %d | V-Direction: top?: %s\n", centerX, centerY, searchedEle < currEle);
        return searchedEle < currEle
                ? findElement(minX, centerX - 1, minY, maxY, matrix, searchedEle)
                : findElement(centerX + 1, maxX, minY, maxY, matrix, searchedEle);
    }
}

/*
Example:
1 [2]
3  4

 1  3  5  7  9 11 312
 4  6  8 10 12 14 319
 7 10 13 16 18 20 333
10 11 15 35 38 45 347
13 14 16 39 39 50 395
16 20 35 55 60 99 399

goto(start, end, DIR, other-dimension) //od could be row or col. based on DIR
    if start > end
       return -1

    find center

    if center == element
        return element


    // LEFT OR RIGHT
    result =
        if element < center // Go left
            goto(start, center-y - 1, o-d)
        else
            goto(center-y + 1, end, o-d)

    if result != -1
        return result

    // TOP OR BOTTOM
    result =
        if element < center // Go top
            goto(start, center-x - 1, o-d)
        else
            goto(start, center-x + 1, end, o-d)

    return result


goto(0, colCount - 1, rowCount - 1)


 */