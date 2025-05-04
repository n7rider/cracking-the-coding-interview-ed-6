package n7rider.ch16.moderate;

/**
 * Langton's Ant: An ant is sitting on an infinite grid of white and black squares. It initially faces right.
 * At each step, it does the following:
 * (1) At a white square, flip the color of the square, turn 90 degrees right (clockwise), and move forward
 * one unit.
 * (2) At a black square, flip the color of the square, turn 90 degrees left (counter-clockwise), and move
 * forward one unit.
 * Write a program to simulate the first K moves that the ant makes and print the final board as a grid.
 * Note that you are not provided with the data structure to represent the grid. This is something you
 * must design yourself. The only input to your method is K. You should print the final grid and return
 * nothing. The method signature might be something like void printKMoves ( int K).
 *
 * After finishing:
 * This can cause dilemma between readable code and simplified code. I should go for the simplest working
 * code first, but go for refactoring. It's terrible if you try to refactor right away and fail the basic use case
 *
 * After comparing:
 * The book talks about the ant going in circles, but only after running the codee, I came to know that the book assumes
 * the initial tile is black, and that the grid can be dynamically expanded in any direction.
 * My solution was geared more towards a chess like fixed grid, and the ant standing on a white tile.
 * I could have written code that would have worked for both colors.
 * Anyway, if I were to write after understanding the requirements, I would have created an array first and gone for a set
 * just like the solution, but no one knows now.
 */
public class Solution16_22 {
    public static void main(String[] args) {
        printMoves(4, 4, 1, 1, 5);
        System.out.println();
        printMoves(4, 4, 1, 1, 4);
        System.out.println();
        printMoves(2, 2, 1, 1, 5);
    }

    static void printMoves(int m, int n, int o1, int o2, int k) {
        // Validate k
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                boolean whiteTileFlag = isWhiteTile(i, j);
                if(isInPath(o1, o2, i, j, k)) {
                    printCurrTile(!whiteTileFlag);
                } else {
                    printCurrTile(whiteTileFlag);
                }
            }
            System.out.println();
        }
    }

    private static boolean isWhiteTile(int i, int j) {
        return (i + j) % 2 == 0;
    }

    private static boolean isInPath(int o1, int o2, int i, int j, int k) {
        // Check if it's in the diagonal
        if(((i - o1) != (j - o2)) // White tile
                && ((i - o1 - 1) != (j - o2) || j < o2)) { // Black tile under the above white tile is fine
                                                    // || Exclude Black tile to the left of origin
            return false;
        }

        // Exclude all entries before origin
        // For turn 1, 3 etc. we flip diagonal. We use ceil because we need to include origin when k = 1 and so on.
        // For turn 2, 4 etc. we flip tile under diagonal. We need to exclude this we finish at odd no, we use '<'
        if(i >= o1 && i - o1 < (Math.ceil(k / 2.0))) {
            // System.out.printf("In path - %d, %d\n", i, j);
            return true;
        } else {
            return false;
        }
    }

    private static void printCurrTile(boolean flag) {
        System.out.print(flag ? " W " : " B ");
    }

}
/*
w b w b w b
b W b w b w
w b w b w b
b w B w b w

Can use an array to store, but that looks straightforward. Is there a better way?
The white and black is predictable, so just use a method to calculate?
                                                    _
Seems ant will just go on a path looking a waterfall |_
                                                       |_ and so on.

So if ant starts at i,j. It will keep flipping these:
white tiles = i,j & i+1,j+1 ... i+k,j+k
black tiles: i,j+1 * i+1,j+2 ... i+k-1,j+k

To print the path:
simplest way:
init_pos = x1,y1
for int i = 0 to k
    Validate arr[x1+i][y1+i] // white tile
    Flip arr[x1+i][y1+i]
    Validate arr[x1][y1 i] // black tile
    Flip arr[x1][y1 i]

for int i = 0 to n
    Print arr ele

Utility method to replace all this:
for unaffected tile:
    validate i,j // both < n, >=0
    if i + j %2 == 0 ? white : black

for affected tile:
    isAffected(i, j, x1, y1, k)
    !unaffectedTile

isAffected(i, j, x1, y1, k)
    // in path of ant or not
    assert i-x1 && j-y1 are equal // white tile
        || i-x1 && j-y1-1 are equal /// black tile

    // within k moves or not
    assert i-x1 && j-y1 <=k
        && i-x1 && j-y1-1 <=k

    // can combine the above too

//  Saves O(n * n) space if it's a square, or O(m . n)










 */