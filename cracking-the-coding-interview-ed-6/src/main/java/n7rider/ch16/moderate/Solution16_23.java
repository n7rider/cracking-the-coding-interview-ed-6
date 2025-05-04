package n7rider.ch16.moderate;

/**
 * Rand7 from Rands: Implement a method rand7() given rand5( ). That is, given a method that
 * generates a random number between O and 4 (inclusive), write a method that generates a random
 * number between O and 6 (inclusive).
 *
 * After comparing:
 * I can further into combinations, but not sure if that's needed for random. The while loop looks odd for a problem
 * for random(), but this could just be a brainteaser rather than a real world solution.
 *
 * After finishing:
 * The while loop is the way, but the approach is different and goes with combinations from the beginning. (0,0)... (4,4)
 * has 25 combinations and the book plots them up first, and the sum for each combination.
 * Now, we don't need to find LCM between 5 and 7, but to find equal no. of occurrences for each number.
 * The simplest solution is to do 5 * rand5 for 5 times (each no. each equal chance) + rand5() (I think if we don't add
 * this, the possible results are 0,5,10,15,20 which can return only values of 0,5,3,1,5 when modded by 7, so not truly
 * random. Then, ignore and retry if sum=21...24 and not in (0,20) because 0..20 has equal parts of 7.
 *
 * The idea is to use random5(), but you can add or multiply or whatever operations you want with it. Then, project it
 * until you reach the number that's divisible by the target and crop out the rest
 *
 * The optimal solution is to generate 2 * rand5() (0..9 but no odd nums), then call rand5() and do rand5 % 2 to inject
 * an odd number to this value (We retry if we get 4 here, because in (0..4), there'll be 2 chances for odd and even
 * otherwise. Now we have all evens between 0 and 9, we can retry if ele >=7, but return otherwise.
 */
public class Solution16_23 {
}

/*
rand5 - 0...4
rand7 - 0...6

A simple solution: Use LCM? Sum or differences?
(rand5 + rand5) - 3 (i.e., 2 * 5 - 3 = 7) won't always work. Can go negative
(rand5 + rand5) % 7 can work, but will not be truly random. 5-6 need two numbers to be reach, 0-1 can be reached in multiple ways (0,1, 7, 8)
    and have 20% prob. reach

What about (7 * rand5)  % 7 ? // using LCM = 7*5

min = 0, max = 28, max is div by 7, so the no. of ways each no between 0..6 can be reached is distributed
Wait, 0 can be reached 5 ways (inc. 28) others can ber eached only 4 ways.
Problem = 0, not contributing a value.

So repeat (rand5 + 1) for 7 times % 7 i.e., (1..5) for 7 times % 7
min = 7, max = 35, no the same problem is there, it is just shifted by 7.

 */