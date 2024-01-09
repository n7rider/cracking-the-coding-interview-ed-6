package n7rider.ch6.math_and_logic;

import static org.junit.Assert.assertEquals;

/**
 * 6.1
 * The Heavy Pill: You have 20 bottles of pills. 19 bottles have 1.0 gram pills, but one has pills of weight
 * 1.1 grams. Given a scale that provides an exact measurement, how would you find the heavy bottle?
 * You can only use the scale once.
 *
 * After finishing:
 * Had checked with solution
 * Remember, double / 5 is still integer division.
 *
 * After checking with solution:
 * Question more details if you feel they are enough to solve the problem e.g., can a bottle have
 * several pills? If we can't solve this using bottles, can we solve using pills in the bottles etc.
 *
 */
public class Question6_1 {
    public static void main(String[] args) {
        double[] weights1 = {1, 1, 1.1, 1, 1};
        assertEquals(3,  findBottleWithHeavyPill(weights1, 1, 1.1));
    }

    static int findBottleWithHeavyPill(double[] weights, double idealWeight, double oddPillWeight) {
        double totalWeight = 0;
        for(int i = 1; i <= weights.length; i++) {
            totalWeight = totalWeight + (i * weights[i - 1]);
        }

        double idealTotalWeight = (weights.length / 2.0) * ( 1 + weights.length);

        int bottleNum = (int) ((totalWeight - idealTotalWeight) / ( oddPillWeight - idealWeight));

        System.out.println(String.format("totalWeight: %f, idealTotalWeight: %f, bottleNum: %d",
                totalWeight, idealTotalWeight, bottleNum));

        return bottleNum;
    }
}

/**
 * Brute force - we'll use 4 attempts - 20 -> 10 -> 5 -> 2 -> 1
 *
 * After checking solution:
 * If we took one pill from Bottle #1 and two pills from Bottle #2, what would the scale show? It depends. If
 * Bottle #1 were the heavy bottle, we would get 3.1 grams. If Bottle #2 were the heavy bottle, we would get
 * 3.2 grams. And that is the trick to this problem.
 *
 * So, 1 * B1 + 2 * B2 ... + 20 * B20 is the sum
 *
 * if B2 = B3 = .... = B20 = 1 but B1 = 1.1, then sum = (1 + 20)  * 20 / 2 = 210.1 g
 * But if B2 = 1.1, then sum = 210.2g
 *
 * Therefore, bottle no. = (weight - 210g) / 0.1
 *
 *
 * findBottleWithHeavyPill(int[] weights, idealWeight)
 *
 * for i = 1 to weights.size
 *    weight += i * weight[i-1]
 *
 * idealW = (weights.size / 2) * (1 + weights.size)
 *
 * bottle_num = (weight - idealW) / eachPillWeight
 */