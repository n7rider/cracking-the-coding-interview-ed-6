package n7rider.ch7.object_oriented_design;

/**
 * Othello: Othello is played as follows: Each Othello piece is white on one side and black on the other.
 * When a piece is surrounded by its opponents on both the left and right sides, or both the top and
 * bottom, it is said to be captured and its color is flipped. On your turn, you must capture at least one
 * of your opponent's pieces. The game ends when either user has no more valid moves. The win is
 * assigned to the person with the most pieces. Implement the object-oriented design for Othello.
 *
 * After comparing with solution:
 * You can also keep Game and Board together. The logical separation doesn't help much, and you might
 * call methods from both classes in some places.
 */
public class Question7_8 {
    abstract class Board {
        int size;
        Color[][] board;


        public Board(int size) {
            this.size = size;
            this.board = new Color[size][size];
        }

        abstract Game startGame(Player player1, Player player2);
    }

    public enum Color {
        WHITE, BLACK
    }

    abstract class Player {
        Color color;

        public Player(Color color) {
            this.color = color;
        }

        abstract void makeMove(int row, int column);

    }

    abstract class Game {
        Game id;
        Player[] players;

        Player previousMoveBy;
        String previousMovePosition = "";

        // -1, -1 will skip move
        abstract void validateAndAddMove(Player player, int row, int column);

        private void endGame() {
            // ...
        }
    }


}

/**
 * Components
 * Board // singleton
 * Piece
 * Player
 * Game
 *
 * Fields and methods
 *
 * Board
 *   int[][] boardPos
 *   startGame(Player1, Player2) // just instantiates a game
 *
 * Piece
 *   Color
 *   Id // random
 *
 * Player
 *   assignedColor
 *   makeMove(this, piece)
 *
 *
 *   skipMove(this)
 *
 *
 *
 * Game
 *   String lastMoveBy
 *   String lastMoveAt
 *   Map<String, Integer> calculateScore()
 *     for i
 *       for j
 *         increment whiteCount or Blackcount
 *     return map
 *   int skipMoveConsecutiveCount
 *
 *   validateMove()
 *       check if lastMoveBy is same player. If yes throw ex
 *
 *       if(skip)
 *         update lastMoveAt
 *
 *       Look at all 4 dir. if all are same color as user
 *         throw ex
 *       Take first side with diff color,
 *         if recursive (lookup(side, existingPiece)) until myColor or empty is found
 *           if lastPiece = empty
 *             throw ex
 *           if lastPiece = myColor
 *             from currentPiece to lastPiece
 *               temp = lookup(side, currentPiece)
 *               flip temp to this_player.color
 *
 *
 *
 * Flow
 *  Init board
 *  Create 2 players
 *
 *  assignedColor(player1)
 *  assignedColor(player2)
 *
 *  startGame(player1, player2)
 *    validate colors are unique
 *    clear board i.e., boardPos[][] = null for all
 *
 *
 *
 *
 */