package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns.
 The robot can only move in two directions, right and down, but certain cells are "off limits" such that
 the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to
 the bottom right.

 After finishing solution:
 Got the top-down approach right (thinking from the end), but not sure where memoization can be applied here.

 After comparing with solution:
 I found where to apply memoization after walking through the output. Walking through an example helps. For coding,
 a bigger example like 7*7 helps. But for walkthrough, a smaller example like 4*4 helps.
 */
public class Question8_2 {
    public static void main(String[] args) {

        RobotGrid robotGrid = new RobotGrid(8, 8, new int[][]{
                {0, 4}, {1, 4}, {2, 4}
        });
        robotGrid.reachEnd(7, 7);
    }

    static class RobotGrid {
        boolean tileOffLimitFlag[][];
        int r;
        int c;
        boolean isProblemSolved;

        RobotGrid(int r, int c, int[][] offLimitTiles) {
            // Assuming offLimitTiles is a 2D array of format [l][2]. Can add validation for that too.
            this.r = r;
            this.c = c;
            this.tileOffLimitFlag = new boolean[r][c];
            for(int i = 0; i < offLimitTiles.length; i++) {
                int row = offLimitTiles[i][0];
                int col = offLimitTiles[i][1];
                tileOffLimitFlag[row][col] = true;
            }
        }

        void reachEnd(int x, int y) {
            move(x - 1, y, String.format("%d,%d", x, y));
            move(x, y + 1, String.format("%d,%d", x, y));
        }

        void move(int x, int y, String pathSoFar) {
            if(isProblemSolved || !isTileValid(x, y)) {
                System.out.println(String.format("Not valid path at %d,%d\n", x, y));
                return;
            }

            System.out.println(String.format("Currently in %d,%d", x, y));

            if(x == 0 && y == 0) {
                System.out.println("Path so far: " + pathSoFar);
                isProblemSolved = true;
                return;
            }

            move(x - 1, y, String.format("%d,%d -> %s", x, y, pathSoFar));
            addToOffLimitsIfApplicable(x-1, y);

            move(x, y - 1, String.format("%d,%d -> %s", x, y, pathSoFar));
            addToOffLimitsIfApplicable(x, y-1);
        }

        void addToOffLimitsIfApplicable(int x, int y) {
            if(!isProblemSolved && !isTileOutOfBounds(x, y)) {
                tileOffLimitFlag[x][y] = true;
                System.out.println(String.format("Added to invalid tiles : %d,%d\n", x, y));
            }
        }

        boolean isTileValid(int x, int y) {
            return !isTileOutOfBounds(x, y) && !tileOffLimitFlag[x][y];
        }

        boolean isTileOutOfBounds(int x, int y) {
            if(x < 0 || x >= r) {
                return true;
            }
            if(y < 0 || y >= c) {
                return true;
            }
            return false;
        }

    }
}

/*
  0 0 0 0
  0 0 0 0
  0 x 0 0
  0 0 0 0

  Tile
    bool offLimits

  Board
    Tile[][]

  initBoard
    new Board(int... offLimits) // Parse these as rxc each

  Top-down
    To reach the end (x, y) from (0,0), goDown(x-1, y) OR goRight(x, y-1)

  Bottom-up
    if end is reached
      print pathSoFar
      break
    goDown(String pathSoFar, int x, int y)
    else
      goRight(String pathSoFar, int x, int y)

  Merge
    goDown() + goRight()

  Solution:
    move(int newCo-ord):
      if flag is set
        return
      if can't go to this tile
        return
      if end is reached now
        set flag
        print path so far

      else
        move(right of newCo-ord)
        move(bottom of newCo-ord)


 How do we memoize this?

   pathSoFar = end
   goTo(x-1, y, pathSoFar) OR goTo(x, y-1, pathSoFar)

   goTo(x, y, pathSoFar):
     if can't go to this tile: // boundary check, off-limit check
       return

     if reached start:
       print pathSoFar
       // can set off a circuit breaker here
       return

     goTo(x-1, y, currTile + pathSoFar) OR goTo(x, y-1,  currTile + pathSoFar)



 */
