package n7rider.ch1.arrays_and_strings;

/**
 * Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
 * bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
 * Hints: #51, # 100
 *
 * Post-run observations:
 * - Runtime is O(n^2)
 * <p>
 * After comparing with solution:
 * If you swapped the other way around, you would have needed only one backup (Hence no method to do copy)
 * The solution names n-1-i etc. as first, last etc. This keeps the code more readable
 * If you're repeating something often, this can be usually refactored
 *
 */
public class Question1_7 {
    public static void main(String[] args) {
        char a[][] = {{'0', '1', '2', '3'}, {'4', '5', '6', '7'}, {'8', '9', '0', 'a'}, {'b', 'c', 'd', 'e'}};
        rotateMatrix(a);
    }

    private static void rotateMatrix(char[][] arr) {
        // Skip null check, square matrix check (arr.length != arr[0].length)
        printMatrix(arr);

        int n = arr.length;
        for (int z = 0; z < arr.length / 2; z++) {
            for (int i = z; i < n - 1; i++) {
                char backup = arr[z][i];
                backup = copy(arr, backup, i, n - 1);
                // n -1 is the last one in the current square considered
                // -i in (n -1 -i) is the offset. 0,3 -> 3,3 | 1,3 -> 3, and so on.
                // +z is added to neutralize decreasing 'n' every time outer loop iterates
                backup = copy(arr, backup, n - 1, n - 1 - i + z);
                backup = copy(arr, backup, n - 1 - i + z, z);
                copy(arr, backup, z, i);
            }
            n--;
        }

        printMatrix(arr);
    }

    private static char copy(char[][] arr, char sourceVal, int destRow, int destCol) {
        char temp = arr[destRow][destCol];
        arr[destRow][destCol] = sourceVal;
        return temp;
    }

    private static void printMatrix(char[][] arr) {
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/**
 * 0 1 2 3     b 8 4 0
 * 4 5 6 7     c 9 5 1
 * 8 9 0 a  => d 0 6 2
 * b c d e     e a 7 3
 * <p>
 * Rotate by 90-deg clockwise
 * <p>
 * 00 => 03 | 03 => 33 | 33 => 30 | 30 => 00
 * 01 => 13 | 13 => 32 | 32 => 20 | 20 => 01
 * 02 => 23 | 23 => 31 | 31 => 10 | 10 => 02
 *
 * 11 => 12 | 12 => 22 | 22 => 21 | 21 => 11 (But 11 => 12 | 12 => 21 | 21 => 11 | 11 => 11
 * <p>
 * Step 1: 0,i -> i,n
 * Step 2: i,n -> n, n-i
 * Step 3: n, n-i -> n-i, 0
 * Step 4: n-i, 0 -> 0, i
 *
 *
 */