package n7rider.ch17.hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Kth Multiple: Design an algorithm to find the kth number such that the only prime factors are 3, 5,
 * and 7. Note that 3, 5, and 7 do not have to be factors, but it should not have any other prime factors.
 * For example, the first several multiples would be (in order) 1, 3, 5, 7, 9, 15, 21.
 *
 * After finishing:
 * Feels more procedural than thought-process oriented. I added steps to cache things (to avoid redoing), to optimize.
 *
 * After comparing:
 * When the book says the solution is 3^i * 5^j * 7^k, I see I missed this way of approach.
 * The book first runs 3 loops on these, and then chooses the smallest. To optimize, it uses the queue, then takes
 * the first item and multiples it by 3, 5, 7 and puts these 3 products in the queue - and so on. It also avoids dupes
 * (e.g., 3 * 5 is same as 5 * 3) by having 3 separate queues, then taking the smallest of among all 3, then multiplying
 * this by 3, 5, 7 in the first (3's queue), just 5 and 7 in the second queue (5's queue), and just 7 in the second.
 * The min of all queue represents the kth number at any time. Neat
 *
 */
public class Solution17_9 {
    public static void main(String[] args) {
        System.out.println(findNon357PrimeFactor(35));
    }

    public static List<Integer> findNon357PrimeFactor(int k) {
        // Validate k > 0
        var primes = new ArrayList<Integer>();
        primes.add(2);
        var out = new ArrayList<Integer>();
        out.add(1);
        if(k == 1) {
            return out;
        }

        int curr = 2;
        while(out.size() < k) {
            boolean cond1 = isDivBy357(curr);
            boolean cond2 = isDivByKnownPrimes(primes, curr);

            // If not div by 3, 5, 7, we would have quit already at notDivBy357. No need to check for those.
            // If div by 2, we would have quit already at isDivByKnownPrimes. No need to check for this. Start from 11.
            boolean cond3 = isPrimeOtherThan357(cond1, cond2, curr);

            if(cond3) {
                primes.add(curr);
            }

            if(cond1 && !cond2) {
                System.out.println("Found one: " + curr);
                out.add(curr);
            } else {
//                System.out.println("Skipping: " + curr);
            }


            curr++;
        }
        System.out.println(primes);

        return out;
    }

    private static boolean isDivBy357(int curr) {
        return curr % 3 == 0 || curr % 5 == 0 || curr % 7 == 0;
    }

    private static boolean isDivByKnownPrimes(List<Integer> primes, int curr) {
        for(Integer prime: primes) {
            if(curr % prime == 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPrimeOtherThan357(boolean divBy357, boolean divByKnownPrimes, int curr) {
        if(divBy357 || divByKnownPrimes) {
            return false;
        }

        int divisor = 11;
        double sqrt = Math.sqrt(curr);
        while(divisor <= sqrt) {
            if(curr % divisor == 0) {
                System.out.println("Disqualified");
                return false;
            }
            divisor+=2; // We've filtered even no.s already
        }
        return true;
    }
}

/*
only prime factors are 3, 5, and 7
Examples: 1, 3, 5, 7, 9, 15, 21

Simple pointers:
Avoid other prime numbers: Use just 3, 5, 7 or combinations

Simplest solution:
if k=1, return 1
for other nos
curr = 2, found = 1
while found < k
    cond1 = isDivisibleByKnownPrimes    // O(n) but not really prime numbers are few and far between
    cond2 = isPrimeNo()                 // O(log n) because we can stop at sq root
        Add to set if yes
    cond3 = isDivisibleBy 3 or 5 or 7   // mutually exclusive to cond2      . Takes O(1)

    if !cond1 & cond3
        found++
        Add curr to result set

    curr++

Runtime is n * (O(n) + O(log n)) = O(n^2) + O(n log n)

How do we optimize?
Run cond(3) first. Smaller to test
    Can divide by these numbers rec to reduce to smaller number
Memoize cond1?
    Use set from cond2

Memoize cond2?
    Check if divisible by 2, 3, 5, 7
        False if yes
    Then start i from 11
        ignore i if divisible by 2, 3, 5, 7     // Eliminates more than 66% of numbers
        Check if curr is div by i
            False if yes

 */