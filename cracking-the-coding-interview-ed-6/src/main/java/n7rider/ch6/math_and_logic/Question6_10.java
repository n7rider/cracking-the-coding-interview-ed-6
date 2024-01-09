package n7rider.ch6.math_and_logic;

/**
 * 6.10
 * Poison: You have 1000 bottles of soda, and exactly one is poisoned. You have 10 test strips which
 * can be used to detect poison. A single drop of poison will turn the test strip positive permanently.
 * You can put any number of drops on a test strip at once and you can reuse a test strip as many times
 * as you'd like (as long as the results are negative). However, you can only run tests once per day and
 * it takes seven days to return a result. How would you figure out the poisoned bottle in as few days
 * as possible?
 * FOLLOW UP
 * Write code to simulate your approach.
 *
 * After finishing:
 * - Binary search works, but looks too simple.
 * - Tried to find ideal number using quadratic equation, but that isn't very helpful for 1000, probably
 * will be helpful to generalize the problem.
 *
 * After comparing solution:
 * - Use 10 strips to represent ~2^10 bottles, so use each strip to represent each bit. I figured 10 and
 * 1000 play a role in the solution, but didn't figure this though.
 */
public class Question6_10 {
}

/**
 * By binary search, finding one in 1000 takes 10 turns. But binary search doesn't work here.
 *
 * One strip can cover multiple bottles.
 *
 * So use one strip to test 100 each? That'll round out to 100 bottles, we want a single bottle.
 *
 * Halving,
 *
 * use 5 strips to test 1000 bottles? It'll give us 200, but we can't isolate 1 from 200 with the
 * remaining 5 strips. We need 8 strips for that.
 *
 * log (1000 / n) <= 10 - n
 *
 * log 8 = 3
 * 2^3 = 8
 *
 * 2 ^ (10 - n) <= 1000 / n
 *
 * Flip notions, consider n = strips needed in the second step
 * 2 ^ n <= 1000 / (10 - n)
 * 10 * 2 ^ n - 2 ^ (n + 1) <= 1000
 *
 * 2 ^ (n + 1) (5 - 1) <= 1000
 * 2 ^ n <= 1000 / ( 4 * 2) = 125
 * 2 ^ n <= 125, so n <= 7?
 *
 * So use 3 strips to limit poison to 333 bottles. 7 can isolate only 128. No
 * use 4 strips to limit poison to 250 bottles. 6 can isolate only 64.
 *
 *
 * use 2 strips to limit poison to 500 bottles, 8 strips can cover 500
 *
 * Glanced solution, in a hurry, probably shouldn't have started this.
 *
 * We can reuse the strips, so simply reuse the remaining 9 in first turn, 8 in second turn and so on.
 *
 * 1000 = 100 each, 10 left
 * 900 = 90 each, 9 left
 * 810 =
 * ...
 * won't be enough.
 * We are reducing the percent we cover.
 *
 * Go by binary search
 *
 * 1000 / 2 - 500 each, 9 left
 * 500 / 2 = 250, 8 left
 * ...
 * This will work, but will take 9 weeks
 *
 * Coming back to n <= 7
 *
 * Day 1 = use 3 strips to limit poison to 333 bottles.
 * Day 2 = use same formula to find n?
 * 2 ^ n <= 333 / (9 - n)
 *
 * If we go with 7 left i.e., use 3, it'll take 333, 111, 37, 13, 5, 1 (Use 5 at once), so 6 weeks.
 * If we go with 6 left i.e., use 4 (since n <=7), it'll take 250, 66, 17, 5, 1, so 5 weeks.
 * If we go with 0 left i.e., use 10 (since n <=0), it'll take 100, 12, 2, 1, so 4 weeks.
 *
 *
 *
 *
 *
 */
