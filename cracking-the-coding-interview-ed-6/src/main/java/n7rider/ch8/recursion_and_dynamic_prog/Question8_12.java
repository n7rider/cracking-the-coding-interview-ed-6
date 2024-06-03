package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.HashMap;
import java.util.HashSet;

/**
 Eight Queens: Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
 so that none of them share the same row, column, or diagonal. In this case, "diagonal" means all
 diagonals, not just the two that bisect the board

 After comparing with the solution:
 - The book uses the hasConflicts() method for first row also
 - I use a hashSet to carry state, the book carries one 1-D arrays to store cols (No need for rows since we put
 one in one row using the for loop anyway. Using 2-D arrays is a  waste, but I guess hashSet isn't
 - I create a copy of the hashSet everytime, and the book clones the array. Similar strategy

 */
public class Question8_12 {
    public static void main(String[] args) {
        System.out.println(ChessArranger.arrange());
    }

    static class ChessArranger {
        static final int SIZE = 8;

        static int arrange() {
            return arrange(0, null);
        }

        private static int arrange(int row, HashSet<Integer> set) {
            if(row == SIZE) {
                if(set.size() == SIZE) {
                    System.out.println("Combination found: " + set);
                    return 1;
                } else {
                    System.out.println("Combinations discarded: " + set);
                    return 0;
                }
            }

            int out = 0;
            for(int i = 0; i < SIZE; i++) {
                if(row == 0) {
                    set = new HashSet<>();
                } else {
                    if(hasConflicts(row, i, set)) {
                        continue;
                    }
                }

                HashSet<Integer> newSet = new HashSet<>();
                newSet.addAll(set);
                newSet.add(row * 10 + i);
                System.out.printf("Valid entry: %d %d\n", row, i);
                out = out + arrange(row + 1, newSet);

                // Already placed an entry in this row. Break
//                if(row != 0) {
//                    break;
//                }
            }

            return out;
        }

        private static boolean hasConflicts(int row, int col, HashSet<Integer> set) {
            int diff = row - col;
            int sum = row + col;
            // Doing these statements separately for easier debugging. Efficient if joined
            boolean hasColConflict = set.stream().anyMatch(ele -> ele % 10 == col);
            boolean hasRowConflict = set.stream().anyMatch(ele -> ele / 10 == row);
            boolean hasRightDiagonalConflict = set.stream().anyMatch(ele -> ele / 10 - ele % 10 == diff);
            boolean hasLeftDiagonalConflict = set.stream().anyMatch(ele -> ele / 10 + ele % 10 == sum);
            // 0,2 | 1,1, 2,0
           //  System.out.printf("Conflict results: %d | %d == %s | %s | %s | %s\n", row, col, hasColConflict, hasRowConflict, hasRightDiagonalConflict, set);
            return hasRowConflict || hasColConflict || hasRightDiagonalConflict || hasLeftDiagonalConflict;
        }


    }
}

/*
Let's use 4x4
Ex:
0 0 1 0
1 0 0 0
0 0 0 1
0 1 0 0

There must be an entry in each row, so try by placing queen in each column of each row
Avoid obvious improbs - same row, column, diagonal

placer(row, map]

if(row == 8)
  if map.size == 8
    print
    return 1
  stop

int out = 0
for int i = 0 to 7
  if row == 0
    create new map
  else
    check validations
      - continue; on same row
      - continue; on same column
      - continue; on same diag

  place q on [row][i]
  add entry to map
  out = out + placer[q[], row + 1, map]

return out

placer(0, null)

 */