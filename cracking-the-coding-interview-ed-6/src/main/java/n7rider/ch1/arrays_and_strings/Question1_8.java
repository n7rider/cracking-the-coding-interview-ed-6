package n7rider.ch1.arrays_and_strings;

/**
 * Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
 * column are set to 0.
 * Hints:#17, #74, #702
 * <p>
 * Post-run observations:
 * - Runtime is O(m x n). Space complexity is O((m x n) + m + n)
 * <p>
 * After comparing with solution:
 * The solution sets [i][0] and [0][j] to zero, then looks at the first row,
 * first col to fill up the rest of the row and col. It's complex, takes more time
 * but saves space. Having reduced space from O(mn) to O(m+n), I'll stick to my code.
 */
public class Question1_8 {

    public static void main(String[] args) {
        int a[][] = {{2, 1, 2}, {8, 8, 0}, {9, 3, 6}, {4, 8, 2}};
        setRowAndColTo0(a);
    }

    private static void setRowAndColTo0(int[][] a) {
        printMatrix(a);

        int m = a.length;
        int n = a[0].length;
        boolean[] rowStore = new boolean[m];
        boolean[] colStore = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 0) {
                    if (!isRowZeroed(i, rowStore) && !isColZeroed(j, colStore)) {
                        makeRowZero(a, i, rowStore);
                        makeColZero(a, j, colStore);
                    }
                }
            }
        }

        printMatrix(a);
    }

    private static void printMatrix(int[][] a) {
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isRowZeroed(int i, boolean[] rowStore) {
        return rowStore[i];
    }

    private static boolean isColZeroed(int j, boolean[] colStore) {
        return colStore[j];
    }

    private static void makeRowZero(int[][] a, int i, boolean[] rowStore) {
        for (int x = 0; x < a[0].length; x++) {
            a[i][x] = 0;
        }
        rowStore[i] = true;
    }

    private static void makeColZero(int[][] a, int j, boolean[] colStore) {
        for (int y = 0; y < a.length; y++) {
            a[y][j] = 0;
        }
        colStore[j] = true;
    }


}

/**
 * 2 1 0    2 1 2
 * 0 0 0 <= 8 8 0
 * 9 3 0    9 3 6
 * 4 8 0    4 8 2
 * <p>
 * Approaches:
 * 1. Do it in one go using a map of positions with actual zero - Let's do this
 * 2. Do it in second for loop after storing positions
 * <p>
 * for i = 0 to m-1
 *   for j = 0 to n-1
 *     if a[i][j] == 0 && i,j NOT in map
 *       store i,j to map
 * for x = 0 to m-1
 *   a[x][j] = 0
 * for y = 0 to n-1
 *   a[y][i] = 0
 *   return a
 *
 * -- store i,j in map
 * Options:
 * 1. Create boolean[][] of size mxn
 * 2. Option 2 - Create row[] of size m, col[] of size n.
 * set row[i]=true
 * set col[j]=true
 * <p>
 * -- store i,j in map
 * just check if(row[i] && col[j])
 * <p>
 * This uses m+n, instead of mxn space
 * <p>
 * Example:
 * i=1, j =2 |
 * i=2, j =2 |
 */