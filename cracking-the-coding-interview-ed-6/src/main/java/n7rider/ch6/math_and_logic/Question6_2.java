package n7rider.ch6.math_and_logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * 6.2
 * Basketball: You have a basketball hoop and someone says that you can play one of two games.
 * Game 1: You get one shot to make the hoop.
 * Game 2: You get three shots and you have to make two of three shots.
 * If p is the probability of making a particular shot, for which values of p should you pick one game
 * or the other?
 */
public class Question6_2 {
    public static void main(String[] args) {
        assertEquals(1, choose(0));
        assertEquals(1, choose(0.25));
        assertEquals(1, choose(0.5));
        assertEquals(2, choose(0.75));
        assertEquals(1, choose(1)); // Both will have the same prob

        assertThrows(IllegalArgumentException.class, () -> choose(2));

    }

    static int choose(double prob) {
        if(prob < 0 || prob > 1) {
            throw new IllegalArgumentException("Invalid probability");
        }

        double g1 = prob;

        double g2 = (prob * prob * prob) + 3 * (prob * prob * (1-prob));

        if(g1 >= g2) {
            return 1;
        } else {
            return 2;
        }
    }

}

/**
 * Let's say P = 1/2
 * g1 = 1/2
 * g2 = 2^3 = 8 combinations available. WWW WWL WLW LWW LLW LWL WLL LLL
 *
 * Use P = 1/3 (1/2 interferes with WL logic)
 *
 * g1 = 1/3
 * g2 = 1/3 * 1/3 (2 heads) + 1/3 * 1/3 * 1/3 = 1/9 + 1/27 = 4/27
 *
 * 1/3 > 4/27, so take g1
 *
 * Use higher P e.g., P = 0.9
 *
 * g1 = 0.9
 * g2 = 9/10 * 9/10 + 9/10 * 9/10 * 9/10 = 81/100 + 729/1000 = (810+729)/1000 = 1.5/10
 * So take P2?
 *
 * Or you can't add and just take P(2 shots) as the prob.? If that's the case, always pick g1.
 *
 * AFTER CHECKING SOLUTION,
 *
 * g2 logic (I had) = p^2 + p^3
 * But actual logic will be = p^3 + P(missing 1st, missing 2nd, missing 3rd)
 *  = p^3 + 3 * (P * P * (1-p)
 *  = p^3 + 3p^2 - 3p^3
 *  = 3p^2 - 2p^3
 *
 *  Go with g2 only if 3p^2 - 2p^3 > p
 *
 *  Simplifying,
 *  3p - 2p^2 > 1
 *  0 > 2p^2 - 3p +1
 *  2p^2 - 3p + 1 < 0
 *  We have 2p^2, so the terms will 2p, p
 *  We have +1, so the constant will be 1, 1 of -1, -1
 *  Since we have - 3p in the middle, constant will be -1, -1
 *
 *  (2p - 1) (p - 1) < 0
 *
 *  p = 1/2, p = 1
 *
 *  So go with g2 only if p = 1/2 up to p = 1
 *
 *
 * Code Alg:
 *
 * choose(p)
 *
 * validate 0 <= p <= 1
 *
 * g1 = p
 * g2 = 3p^2 - 2p^3
 *
 * choose the greater num
 *
 *
 *
 *
 */