package n7rider.ch6.math_and_logic;

/**
 * 6.8
 * The Egg Drop Problem: There is a building of 100 floors. If an egg drops from the Nth floor or
 * above, it will break. If it's dropped from any floor below, it will not break. You're given two eggs. Find
 * N, while minimizing the number of drops for the worst case.
 *
 * After comparing:
 * Mine is considered, but the solution optimizes it by going deeper with the fact that e2_count = e1_step - 1
 * For a perfectly load balanced system, e1_count and e2_count should be similar (as seen from examples, that
 * gives the lowest value)
 * To further make them closer, we need to reduced e1's steps one drop at a time e.g., if e1_step = 20, reduce
 * it to 19, to 18 and so on. e2 will take the next no i.e., 17. This way 20, 19, 18, 17 is better than 20, 20, 20, 19
 * So the equation is x + (x-1) + (x-2) + ... + 1 = 100. x = 13.65.
 * Worst case value is 13.65. This is better than the 19 we get with going with an equi-distant step of 10 for e1.
 */
public class Question6_8 {
}

/**
 * Using binary sort, we need log (2) 100 ~= 7 eggs
 *
 * Egg 1 - keep dropping with binary sort
 *
 * Egg 2 - go from the bottom limit towards breaking point
 *
 * Scenario 1 : egg 1 breaks at 50
 *  egg 2 goes from 1 to 50, and finds the breaking point
 *
 * Scenario 2 : egg 1 breaks above 50 i.e., survives 50, 75, but breaks at 87
 *  egg 2 goes from 75 to 87
 *
 *
 * Minimize drops for worst case
 *
 * What is worst? If we want to be < 20, we plan to drop egg 1 at most 10 times, and egg 2 at most 10 times
 *
 * For worst case:
 * e1 = steps of 10, e2 = 9 | n = 19
 * e1 = steps of 20 i.e., 5, e2 = 19 | n = 24
 * e1 = steps of 15 i.e., 7, e2 = 14 | n 21
 *
 * Algorithm:
 *
 * int min = INT_MAX
 *
 * e1_step = 1
 * e1_count = 100 / e1_step
 * e2_count = e1_step - 1
 *
 * total = e1_count + e2_count
 * if (total < min)
 *   ideal = e1_step
 *
 *
 *
 *
 *
 *
 */