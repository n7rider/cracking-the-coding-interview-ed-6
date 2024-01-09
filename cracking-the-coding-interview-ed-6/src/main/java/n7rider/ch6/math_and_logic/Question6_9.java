package n7rider.ch6.math_and_logic;

/**
 * 6.9
 * 100 Lockers: There are 100 closed lockers in a hallway. A man begins by opening all 100 lockers.
 * Next, he closes every second locker. Then, on his third pass, he toggles every third locker (closes it if
 * it is open or opens it if it is closed). This process continues for 100 passes, such that on each pass i,
 * the man toggles every ith locker. After his 100th pass in the hallway, in which he toggles only locker
 * #100, how many lockers are open?
 *
 * After completing:
 * The number of factors is odd for perfect squares, but is even for anything else (prime, composite, odd, even - all)
 */
public class Question6_9 {
}

/**
 * Simplest solution: Brute-force it
 *
 * Walk through:
 * n = 8
 *
 * n = 1 | All are open
 * n = 2 | Open = 1 3 5 7 | Closed = 2 4 6 8
 * n = 3 | Open = 1 5 6 7 | Closed = 2 3 4 8
 * n = 4 | Open = 1 4 5 6 7 8 | Closed = 2 3
 * n = 5 | Open = 1 4 6 7 8 | Closed = 2 3 5
 * n = 6 | Open = 1 4 7 8 | Closed = 2 3 5 6
 * n = 7 | Open = 1 4 8 | Closed = 2 3 5 6 7
 * n = 8 | Open = 1 4 | Closed = 2 3 5 6 7 8
 *
 * 1 - remains open
 * Prime numbers - remain closed | opens at 1, closes at their nth
 * For n, find count of number of factors. If cnf is odd, it remains open. But finding cnf is costlier.
 *
 * Can we use previously learnt results (memoization) to make it better?
 *
 * Step 1 - create array with all open
 * Step 2 - Close 2
 * Step 3 - From 3, keep finding prime numbers until 100
 *  For each prime number p
 *      close it
 *      close p * odd multiple until the product > 100
 *
 * So remaining will be non-prime numbers with even multiples of prime
 * e.g., 24 - flips at 1, 2, 4, 6, 8, 12, 24 | Closed
 * 20 - 1, 2, 4, 5, 10, 20 | Closed
 *
 * These also can get closed, because since they are divisible by 2, they can break down to an odd multiple * prime * 2
 * So prime * 2 essentially means odd multiple * prime * 2 where odd_multiple = 1
 *
 * (I've glanced the answer by now)
 *
 * For 25, flips at 1, 5, 25 | Open. There is no another number to flip it off.
 *
 * So it closes only at perfect squares, therefore the answer is 10.
 *
 */