package n7rider.ch6.math_and_logic;

/**
 * 6.5
 * Jugs of Water: You have a five-quart jug, a three-quart jug, and an unlimited supply of water (but
 * no measuring cups). How would you come up with exactly four quarts of water? Note that the jugs
 * are oddly shaped, such that filling up exactly "half" of the jug would be impossible.
 *
 * After finishing,
 * It makes sense mathematically after solving, but couldn't find any equation for this.
 *
 * After checking with book:
 * Same as mine.
 *
 * Book also highlights this point: If the two jug sizes are relatively prime,
 * you can measure any value between one and the sum of the jug sizes.
 */
public class Question6_5 {
}

/**
 *
 * 5 qt, 3 q
 *
 * Output = 4
 *
 * Potential solutions:
 * 5x + 3y = 4 (Not possible)
 * 5x - 3y = 4
 *
 * LCM = 60
 *
 * Let's try with x = 12?
 *
 * 60 - 3y = 4
 * y = 56 /3 = 18.33
 *
 *
 * Try y = 20
 * 5x = 4
 * Won't work
 *
 * If both x = 1, y = 1 then diff will be 2.
 *
 * If both x = 2, y = 2, then diff will be 4
 *
 * So, get 5q twice, remove 3q twice?
 *
 *
 * Jars: 5qt 3qt
 * 0 3 | Added to j2
 * 3 0 | Tx
 * 3 3 | Added to j2
 * 5 1 | Tx
 * 0 1 | Empty
 * 1 0 | Tx
 * 1 3 | Added to j2
 * 4 0 | Complete
 *
 * What did we do?
 * (3 + 3) % 5
 * + 3
 *
 *
 *
 */