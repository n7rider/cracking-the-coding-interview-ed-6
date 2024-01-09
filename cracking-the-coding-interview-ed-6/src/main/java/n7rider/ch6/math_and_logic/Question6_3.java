package n7rider.ch6.math_and_logic;

/**
 * 6.3
 * Dominos: There is an 8x8 chessboard in which two diagonally opposite corners have been cut off.
 * You are given 31 dominos, and a single domino can cover exactly two squares. Can you use the 31
 * dominos to cover the entire board? Prove your answer (by providing an example or showing why
 * it's impossible).
 *
 * After finishing:
 * My solution works, but is a little manual i.e., brute-force-ish
 *
 * After checking with solution:
 * The result is the same.
 *
 * The book has a better logical solution. In a chessboard, these dominos will always take a white
 * and a black square. Cutting off corners removes 2 squares of the same color. It's impossible for
 * the dominos to fill these up.
 *
 * I couldn't have figured this without drawing up a chess board myself. This is more of a whiteboard problem
 *
 */
public class Question6_3 {
}

/**
 * Scale it down 4*4, 31->15 dominos
 *
 * X X x
 * Y X X x
 * Y X X Y
 *   X X Y
 *
 * x x x
 * x x x x
 * x x x x
 *   x x x
 *
 *   Fitting is possible if we cut off corners on the same side.
 *   But with even number of rows, we'll come to the opposite on the 4th or the 8th row, but there is
 *   no corner square to land on.
 *
 *
 */

