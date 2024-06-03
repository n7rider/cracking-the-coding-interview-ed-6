package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Paint Fill: Implement the "paint fill" function that one might see on many image editing programs.
 That is, given a screen (represented by a two-dimensional array of colors), a point, and a new color,
 fill in the surrounding area until the color changes from the original color.

 After finishing:
 This is like the maze problem. The cache avoids dupes but it's not an optimization, we need it.
 I thought Dyn Prog tries to get things memoized, not sure if we can do anything further.

 After comparing:
 The book doesn't use the set. Rather it just checks if the color is still the oldColor. That's more efficient
 */

import java.util.Set;
import java.util.HashSet;
public class Question8_10 {

    public static void main(String[] args) {
        ImageEditor.init();
        ImageEditor.paintArea(2, 10, '*');
        ImageEditor.printOutput();
    }

    static class ImageEditor {
        static Set<Integer> visitedPoints; // can be a Set<Long>
        static char arr[][] = new char[5][20];

        static void paintArea(int x, int y, char newColor) {
            visitedPoints = new HashSet<>();
            char oldColor = arr[x][y];
            paintArea(x, y, oldColor, newColor);
        }

        static private void paintArea(int x, int y, char oldColor, char newColor) {
            if(isOutsideScreen(x, y) || visitedPoints.contains(x * arr[0].length + y)) {
                System.out.printf("%d:%d is outside screen or is a dupe\n", x, y);
                return;
            }

            if(arr[x][y] == oldColor) {
                arr[x][y] = newColor;
                visitedPoints.add(x * arr[0].length + y);

                paintArea(x, y - 1, oldColor, newColor); // top
                paintArea(x - 1, y, oldColor, newColor); // left
                paintArea(x + 1, y, oldColor, newColor); // right
                paintArea(x, y + 1, oldColor, newColor); // bottom
            } else {
                System.out.printf("%d:%d has a different color %c\n", x, y, arr[x][y]);
            }
        }

        static private boolean isOutsideScreen(int x, int y) {
            if (x < 0 || y < 0 || x >= arr.length || y >= arr[0].length) {
                return true;
            } else {
                return false;
            }
        }

        static void init() {
            for(int i = 0; i < arr.length; i++) {
                for(int j = 0; j < arr[0].length; j++) {
                    arr[i][j] = '-';
                }
            }

            for(int j = 4; j < 17; j++) {
                arr[1][j] = '.';
            }

            for(int j = 1; j < 11; j++) {
                arr[2][j] = '.';
            }

            for(int j = 7; j < 11; j++) {
                arr[3][j] = '.';
            }

            for(int j = 14; j < 20; j++) {
                arr[4][j] = '.';
            }
        }

        static void printOutput() {
            for(int i = 0; i < arr.length; i++) {
                    System.out.println(arr[i]);
            }
        }
    }
}


/*
-------------------
---.............---
-.........---------
-------..----------
-------------......

5x20

paint & keep going towards top
paint & keep going towards left
paint & keep going towards right
paint & keep going towards bottom

paintArea(int x, int y, int newCol):

clearSet()
int oldColor = arr[x][y];
paintArea(x, y, oldCol, newCol)

paintArea(x, y, oldCol, newCol):
  isOutsideScreen(arr, x, y) || set.contains(x* arr[0].size + y)
    return

  if(arr[x][y] == oldCol)
    arr[x][y] = newCol
    set.add(x* arr[0].size + y)

  paintArea(x, y-1, oldCol, newCol) // top
  paintArea(x-1, y, oldCol, newCol) // left
  paintArea(x+1, y, oldCol, newCol) // right
  paintArea(x, y+1, oldCol, newCol) // bottom


isOutsideScreen(arr, x, y)
  if x < 0 || y < 0 || x >= arr.size || y >= arr[0].size
    return true
 */