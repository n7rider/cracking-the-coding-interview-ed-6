package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.Arrays;

/**
 * Triple Step: A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3
 * steps at a time. Implement a method to count how many possible ways the child can run up the
 * stairs.
 *
 * After finishing:
 * This is attempt 2. I had referred to the book before starting.
 * However, writing down possible solutions helped a lot. I tried to find a mathematical formula right away
 * the last time. Should just write examples and see how to piece them together using those.
 *
 * After comparing with solution:
 * Same as mine
 */
public class Question8_1_Attempt2 {

    public static void main(String[] args) {
        System.out.println(StairwayProblem.countWays(7));
    }

    static class StairwayProblem {
        static int countWays(int stepCount) {
            return addWays(stepCount - 1) + addWays(stepCount - 2) + addWays(stepCount - 3);
        }

        static int addWays(int currStepCount) {
            if(currStepCount < 0) {
                return 0;
            } else if(currStepCount == 0) {
                return 1;
            } else {
                return addWays(currStepCount - 1) + addWays(currStepCount - 2) + addWays(currStepCount - 3);
            }
        }


    }

}

/**
 * How do you reach the last step? By hopping 1-step, or 2 or 3
 *
 * So ways = count(n-1) + count(n-2) + count(n-3) // add all possible ways
 *
 * e.g., if there are 3 steps:
 * ways = count(2) + count(1) + count(0)
 *      count(2) can only be 1 i.e., val(1) = 1
 *      count(1) i.e., val(3-1) = (1,1)|(2) = 2 i.e., val(2)= 2
 *      count(0) = (1,1,1)|(1,2)|(2,1)|(3) = 4 i.e., val(3) = 4
 *
 * For 4 steps,
 * count(0) = (1,1,1,1)|(1,1,2)|(1,2,1)|(2,1,1)|(2,2)|(3,1)|(1,3) = 7
 *
 *
 *  val(3) can be said as val(1) + val(2) + val(0) (assume this is 1. That's the way to make this generic case work for val(1) and val(2).
 *  Otherwise, you have to write special cases for <0, 0, 1 and 2.)
 *
 *
 *
 *
 */