package n7rider.ch16.moderate;

import java.util.HashMap;

import static n7rider.ch16.moderate.Solution16_4.Direction.*;
import static org.junit.Assert.assertEquals;

/**
 * Tic Tac Win: Design an algorithm to figure out if someone has won a game of tic-tac-toe.
 *
 * After finishing: The key is to proceed forward only i.e., we moved right, bottom, diag down right, diag down left,
 * but never up, left, diag up right or diag up left. So, there were no loops and a need for a map to keep tracking.
 * This could have been a little simpler if we wanted to just print true or false.
 *
 * After comparing with solution:
 * The book suggests using int in the matrix, but won't make much difference IMO since you can't use the sum to arrive
 * at a conclusion, we need to count.
 * I approached this just like a maze that's proceeding forward. The box suggests using for loops, I wanted to do
 * initially, but thought that's too simple for an interview.
 * The book encapsulates row, col, soFar counts in a single class to minimize the number of variables you pass.
 * The book also encapsulates the logic for each direction (as )the corresponding +1 you make to row, col as per direction)
 * in the rowIncrement or colIncrement field, but that neither simplifies nor complicates things. However, this lets it
 * create a list of instructions to check, and then replace all the recursive branching by just checking the list of instructions.
 * This is like passing a method, but passing a class of data that tells what to look, how long to look, what to increment.
 */
public class Solution16_4 {
    public static void main(String[] args) {
        /*
        - - -
        - X O
        - - -
         */
        char[][] board1 = new char[][] {{' ', ' ', ' '}, {' ', 'X', 'O'}, {' ', ' ', ' '}};
        assertEquals('F', checkVictory(board1, 2));

         /*
        - - - -
        - X O X
        - - X 0
        0 X - -
         */
        char[][] board2 = new char[][] {{' ', ' ', ' ', ' '}, {' ', 'X', 'O', 'X'}, {' ', ' ', 'X', 'O'}, {'O', 'X', ' ', ' '}};
        assertEquals('X', checkVictory(board2, 2));
    }

    static char checkVictory(char[][] board, int countToWin) {
        // Assume board must be a square
        if (board == null || board.length == 0 || board.length != board[0].length || board.length < countToWin) {
            return 'F';
        }

        HashMap<String, Boolean> visitedMap = new HashMap<>();
        return checkVictoryInt(board, 0, 0, ' ', null, countToWin, 0);
    }

    static enum Direction {
        DOWN, RIGHT, DIAG_POS, DIAG_NEG
    }

    private static char checkVictoryInt(char[][] board, int row, int col, char soFarChar, Direction dir, int countToWin, int soFar) {
        String key = row + "_" + col;
        int remEntries = 0;
        if (dir == DOWN) {
            remEntries = board.length - row;
        } else if(dir == RIGHT) {
            remEntries = board.length - col;
        } else {
            remEntries = Math.min(board.length - row, board.length - col);
        }

        if(row < 0 || col < 0 || row >= board.length || col >= board.length || (remEntries + soFar < countToWin)) {
            return 'F';
        }

        char currSign = board[row][col];
        if(currSign == ' ') {
            soFar = 0;
        } else {
            soFar = soFarChar == currSign ? soFar + 1 : 1;
        }

        if(soFar == countToWin) {
            System.out.printf("Won by %c in the dir: %s at pos %d:%d", currSign, dir, row, col);
            return currSign;
        }

        char result = checkVictoryInt(board, row + 1, col, currSign, DOWN, countToWin, soFar);
        if(result != 'F') {
            return result;
        }
        result = checkVictoryInt(board, row, col + 1, currSign, RIGHT, countToWin, soFar);
        if(result != 'F') {
            return result;
            }
        result = checkVictoryInt(board, row + 1, col + 1, currSign, DIAG_POS, countToWin, soFar);
        if(result != 'F') {
            return result;
        } else {
            return checkVictoryInt(board, row + 1, col - 1, currSign, DIAG_NEG, countToWin, soFar);
        }
    }


}

/*
Variable rules:
Matrix size - m*m
Winning block size - n

Win conditions:
Check horizontal - L to R
Check vertical - T to B
Check diagonal - TL to BR

Conditions don't need to be checked from the other side e.g. horizontal R to L, because we start from beginning.
We can find it out using the above 3 lines anyway.

Simple logic
start at 0,0
look up for n cells with the same sign - horizontally, vertically, diagonally
    if any meet, declare this sign as winner
    quit
increase to 0,1 and repeat.. (can use 2d or 1D counting)

Pseudocode:
checkVictory(mx)

checkVictory(mx, n)
  int count = 0; // goes from 0 to m^2 - 1. do count / m for row, count %m for col
  checkVicInt(mx, 0, n)

checkVicInt(mx, count, n)
   if count >= m^2
      return null

  int row = getRow(count); // count / m
  int col = getCol(count) // count % m
  char sign = mx[row][col]

  if sign is empty
    return checkVicInt(mx, count + 1, n)

  int flag1 = checkHorizontal(mx, row, col, n) // Check next n cells, quit if col reaches m, return true or false
  int f2 = checkVert(mx, row, col, n) // Check next n cells, quit if row reaches m, return true or false
  int f3 = checkDiag(mx, row, col, n) // Check next n cells, quit if row reaches m, return true or false

  if flag1 || f2 || f3
    return sign
  else
    return checkVicInt(mx, count + 1, n)

Ways to simplify:
  - Add index to each cell e.g., rowsScored, colsScored or diagsScored (L -> R, T -> B only)
  

 */