package n7rider.ch6.math_and_logic;

/**
 * 6.4
 * Ants on a Triangle: There are three ants on different vertices of a triangle. What is the probability of
 * collision (between any two or all of them) if they start walking on the sides of the triangle? Assume
 * that each ant randomly picks a direction, with either direction being equally likely to be chosen, and
 * that they walk at the same speed.
 * Similarly, find the probability of collision with n ants on an n-vertex polygon.
 */
public class Question6_4 {
}

/**
 *
 *    ^
 *   | \
 *  |   \
 *  -----
 *
 *  chance of collision = only if at-least one has a diff direction than the other
 *
 *  P = P(two in diff dir) + P (one in diff dir)
 *    = 1/2 * 1/2 * 1/2 + 1/2 * 1/2 * 1/2 = 1/4
 *
 *  Alternative way: ALl possible events:
 *  C C C | C C ac | C ac C | ac C C | ac ac C | ac C ac | C ac ac | ac ac ac
 *
 *  Total events = 8
 *  Events with at least one ac but not all 3 ac = 6 / 8 = 3 / 4
 *
 *  Above P = 1/4 is wrong.
 *
 *  It should be P = P(one ac) +  P(two ac) = (1/2 * 1 * 1) + (1/2 * 1/2 * 1) = 1/2 + 1/4 = 3/4
 *
 *  Convert to n-vertex polygon?
 *
 *  Collision will not happen only it all ants move in the same direction, so P = 1 - 2 * (1/2^n)
 *
 *  Simplifying, P = 1 - 1/2^(n-1)
 */