package n7rider.ch7.object_oriented_design;

import java.util.List;
/**
 * Jigsaw: Implement an NxN jigsaw puzzle. Design the data structures and explain an algorithm to
 * solve the puzzle. You can assume that you have a fitsWith method which, when passed two
 * puzzle edges, returns true if the two edges belong together.
 *
 * After finishing:
 * After starting the code, I realized that Tongue and Slots are just types of Edge.
 * I need to focus on identifying common types
 *
 * After comparing with solution:
 * Similar to mine with O(n^2) runtime to check isFinished, but also adds orientation to the mix.
 * I think it's not needed for mine if I use pieceId and side to check connections
 * 
 */
public class Question7_6 {
    abstract class PuzzleBoard {
        List<Piece> pieces;

        abstract void createPieces(int count);

        boolean isFinished;
    }

    abstract class Piece {
        String id;

        List<Edge> edges;

        void fitsWith(Piece piece, Side side) {

        }
    }

    abstract class Edge {
        Side side;
        EdgeType edgeType;
    }

    enum Side {
        TOP, RIGHT, LEFT, BOTTOM;
    }

    enum EdgeType {
        TONGUE, SLOT;
    }


}

/**
 * Components:
 *
 * PuzzleBoard
 * Pieces
 * Tongue
 * Slot
 *
 * Fields and methods
 *
 * PuzzleBoard
 *   Pieces[]
 *   createPieces(count)
 *     randomized tongue and slot creator
 *   isFinished
 *
 * Piece
 *   id (Format: N_N like a matrix element]
 *   Tongue[]
 *   Slot[]
 *   fitsWith(piece, side)
 *   connections(piece, side) // only if we want to check if puzzle is complete using graphs and networks
 *
 * Tongue
 *   Side: TOP | BOTTOM | LEFT | RIGHT
 *
 * Slot
 *   Side: TOP | BOTTOM | LEFT | RIGHT
 *
 * It's not a good design to have a List<Edge> in Piece and
 * have Tongue and Slot inside. We can have Tongue[] and Slot[] but
 * an easier error-free way is to have four edges in each object
 * Edge
 *   Tongue
 *   Slot
 *
 * Flow
 *
 * PuzzleBoard - init with singleton
 *
 * board.createPieces(count)
 *   for i = 1 to n
 *     for j = 1 to n
 *       for corners i.e., i or j = 1 or n, no tongues or slots on that side
 *       for the rest
 *         if we randomize tongue and slot, we need to match with other elements
 *           i.e., for 2_2, lookup 1_2 and 2_1 to decide the tongue and slot for those sides
 *
 *         or we can go with an established pattern
 *
 *           i.e., all pieces have tongue on left and bottom
 *           all pieces have slot on right and top
 *
 *
 *
 *  piece.fitsWith(piece, side)
 *    check by piece id and side
 *    OR we can check slot / tongue on that side
 *    piece1.connections(piece2, side)
 *    // We can allow all connections for future fixing or reject connections right away
 *
 *  board.isFinished?
 *    for i = 1 to n
 *      for j = 1 to n
 *      if j == 1 && i != 1
 *      // Add check for pieceId too if we want to avoid finishing by just connecting fittable pieces (and not actually finishing picture)
 *          if(connections(piece[i][j].top) == null
 *             return false
 *
 *      if(connections(piece[i][j].top) == null
 *          return false
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */