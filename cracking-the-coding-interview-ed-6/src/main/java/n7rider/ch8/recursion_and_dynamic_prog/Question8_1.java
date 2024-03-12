package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3
 * steps at a time. Implement a method to count how many possible ways the child can run up the
 * stairs.
 *
 * After finishing solution:
 * Not sure I got this part correct 'remStepCount = remStepCount - excess'. If we're using a combination of
 * (1, 2, 3), I might use more 2's than 3's, so excess won't be correct in that case. I need 000, 001, 010 logic for that.
 *
 * After comparing:
 * Looks like I had focused on memoization, but not thinking using top-down/bottom-up/merge approaches. Try again.
 */
public class Question8_1 {

    public static void main(String[] args) {
        System.out.println(StairwayProblem.numWays(3, 7));

        System.out.println(StairwayProblem.countWays(7));

    }

    static class StairwayProblem {
        static int[] cache;


        static int countWays(int n) {
            int[] memo = new int[n + 1];
            Arrays.fill(memo, -1);
            return countWays(n, memo);
        }

        static int countWays(int n, int[] memo) {
            if (n < 0) {
                return 0;
            } else if (n == 0) {
                return 1;
            } else if (memo[n] > -1) {
                return memo[n];
            } else {
                memo[n] = countWays(n - 1, memo) + countWays(n - 2, memo) + countWays ( n - 3, memo) ;
                return memo[n];
            }
        }


    static int numWays(int maxStepSize, int totalStepCount) {
        cache = new int[totalStepCount + 1];
        int sum = 0;
        for(int i = 1; i <= maxStepSize; i++) {
            sum += countWays(i, maxStepSize, totalStepCount);
        }
        return sum;
    }

    static int fact(int n) {
        if(n <= 1) {
            return 1;
        }

        if(cache[n] == 0) {
            int result = fact(n - 1) * n;
            cache[n] = result;
            return result;
        } else {
            return cache[n];
        }
        /*
        int p = 1;
        for (int i = 1; i <= n; i++) {
            p  *= i;
        }
        return p;
        */
    }

    static int combination(int n, int r) {
        return fact(n) / (fact (n-r) * fact(r));
    }

    static int permutation(int n, int r) {
        return fact(n) / fact(n-r);
    }

    static int findSumOfEle(int currStepSize, int[] possibleStepCountsArr) {
        int s = 0;
        for (int i = 0; i < currStepSize; i++) {
            s += possibleStepCountsArr[i];
        }
        return s;
     }

     // Changes [1, 2, 3] to [2, 3, 1]
     static void rotateArray(int[] possibleStepCountsArr) {
        int temp = possibleStepCountsArr[0];
        for(int i = 1; i < possibleStepCountsArr.length; i++) {
            possibleStepCountsArr[i-1] = possibleStepCountsArr[i];
        }
        possibleStepCountsArr[possibleStepCountsArr.length - 1] = 0;
     }

    static int countWays(int currStepSize, int maxStepSize, int totalStepCount) {
        if(currStepSize == 1) {
            return totalStepCount;
        }

        int[] possibleStepCountsArr = new int[maxStepSize];
        for(int i = 1; i<= maxStepSize; i++) {
            possibleStepCountsArr[i-1] = i; //creates array of [1, 2, 3] etc.
        }

        // decides how many times we process e.g., for currStepSize 2, we process 1,2 | 2,3 | 3,1. For 3, we just do 1,2,3
        int loopTimes = combination(maxStepSize, currStepSize);

        int localTotal = 0;

        for(int i = 1; i <= loopTimes; i++) {
            int sumOfCurrentPossibleSteps = findSumOfEle(currStepSize, possibleStepCountsArr); // if we consider [1, 2], this is 1+2 = 3
            int excess = sumOfCurrentPossibleSteps - currStepSize; //  if excess = 1, we start from n-1
            int remStepCount = totalStepCount - excess;

            int countOfNewSteps = currStepSize - 1; // How many non-1's are there. for [1,2], it's 1. for [1,2,3], it's 2.
            int sumCounter = 0; // This counts iterations in while loop. Used to ensure
            while(remStepCount > 0 && sumCounter < Math.round(totalStepCount / sumOfCurrentPossibleSteps)) {
                int p = permutation(remStepCount, countOfNewSteps);
                localTotal = localTotal + p;
                remStepCount = remStepCount - excess;
                countOfNewSteps++;
                sumCounter++;
            }
            rotateArray(possibleStepCountsArr);
        }

        return localTotal;
    }
    }

}

/**
 * Case 1: 1, 1., ... e
 * Case 2: 1, 2, 1, ... e
 * Case 3: 1, 2, 3, ... e
 *
 * Case n: 3, 3,... e
 *
 *
 * if e = 5 steps,
 *
 * case 1: 1, 1, 1, 1, 1... 5
 * case 2: 1, 1, 1, 2... 5
 * 3: 1, 2, 2... 5
 * 4: 2, 3.. 5
 * 5: 3, 2 ..
 *
 * This is a lot
 *
 * Trying another approach
 *
 * Ways with just 1: 1
 * Ways with 1 and 2: (1....x), (1....x.) = n-1
 * Ways with 1 and 2 2's = n-2
 * ..
 * ..
 * All ways with 1 and 2: (n-1) . (n-2) .... (n-m) // m is when sum reaches e
 *
 * Ways with 1, 2 and 3:
 * ...
 *
 * We need an alg to find no. of ways for each combination
 * i.e., N(1) = 1,
 * N(1, 2) = (n-1) . (n-2) .... (n-m)
 * N(2, 3) = (n-3) . (n-4) .... (n-m)
 * N(1, 3) = (n-2). (n-3) .. (n-m)
 * N(1, 2, 3) = (n-3).(n-4) ...(n-m)
 *
 * How do we find all the N(x) we need?
 *
 * First find for single digs i.e., N(1), N(2)
 * Then use two N(1, 2), N(2, 3) // for this take first from (1, 2, 3), rotate, then take first (2, 3, 1). Do this for 3 times
 * Why 3 times? 3C2 = 3.
 *
 * numWays(int maxStepSize, int totalStepCount) // maxStepSize = 3 since child can take up to 3 at a time
 *   int sum = 0
 *   for(int i = 1; i <= maxStepSize; i++)
 *     sum += countWays(i, maxStepSize, totalStepCount)
 *   return sum
 *
 * int combination(n, r) // TODO Memoize
 *   return fact(n) / ( fact(n - r) * fact(r) );
 *
 * int fact(n) // TODO Memoize
 *   int p = 1;
 *   for i = 1 to n
 *     p *= i
 *   return p
 *
 * int findSumOfEle(int n) // TODO Memoize
 *   int s = 0
 *   for (int i = 0; i < currStepSize; i++)
 *     s += allPossStepCount[i]
 *   return s
 *
 * countWays(int currStepSize, int maxStepSize, int totalStepCount)
 *   if currStepSize == 1
 *     return totalStepCount
 *
 *   int allPossStepCount[] = new int[maxStepSize];
 *   for (int i = 1; i<= maxStepSize; i++)
 *     allPossStepCount[i] = i; // create array like [1, 2, 3] which will be rotated for each combination
 *
 *  // decides how many times we process e.g., for currStepSize 2, we process 1,2 | 2,3 | 3,1. For 3, we just do 1,2,3
 *   int loopTimes = combination(maxStepSize, currStepSize);
 *
 *   int localTotal = 0;
 *   for (int i = 1; i <= loopTimes; i++)
 *     int sumOfEle = findSumOfEle(currStepSize, allPossStepCount)
 *     int excess = sumOfEle - currStepSize; // if excess = 1, we start from n-1
 *
 *   Style 1:
 *     int remStepCount = totalStepCount - excess;
 *     int p = 1;
 *     int i = 1;
 *     // Replace this with nPr is not possible since 1,2 is not same as 1,3. Child will reach top sooner*
 *     while(remStepCount > 0)
 *       p*= remStepCount // if totalStep = 7, we start with 1, 1, 1, 1, 1, 2, then 1, 1, 1, 2, 2, then 1, 2, 2, 2
 *       remStepCount = remStepCount - i++;
 *     p+= localTotal
 *     rotateArray(allPossStepCount)
 *
 *
 *   Style 2:
 *     int remStepCount = totalStepCount - excess;
 *
 *     while(remStepCount > 0)
 *       int p = 1;
 *       p = permutation(remStepCount, currStepSize)
 *       p += localTotal;
 *       remStepCount -= excess
 *     rotateArray(allPossStepCount)
 *
 *  Rewriting countWays method:
 *
 * countWays(int currStepSize, int maxStepSize, int totalStepCount)
 *   if currStepSize == 1
 *     return totalStepCount
 *
 *   int allPossStepCount[] = new int[maxStepSize];
 *   for (int i = 1; i<= maxStepSize; i++)
 *     allPossStepCount[i] = i; // create array like [1, 2, 3] which will be rotated for each combination
 *
 *  // decides how many times we process e.g., for currStepSize 2, we process 1,2 | 2,3 | 3,1. For 3, we just do 1,2,3
 *   int loopTimes = combination(maxStepSize, currStepSize);
 *
 *   int localTotal = 0;
 *   for (int i = 1; i <= loopTimes; i++)
 *     int sumOfEle = findSumOfEle(currStepSize, allPossStepCount)
 *     int excess = sumOfEle - currStepSize; // if excess = 1, we start from n-1
 *     int remStepCount = totalStepCount - excess;
 *
 *     while(remStepCount > 0)
 *       int p = 1;
 *       p = permutation(remStepCount, currStepSize)
 *       p += localTotal;
 *       remStepCount -= excess
 *     rotateArray(allPossStepCount)
 *   return localTotal
 */