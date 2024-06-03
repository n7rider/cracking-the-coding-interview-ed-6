package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.Arrays;

/**
 Coins: Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents), and
 pennies (1 cent), write code to calculate the number of ways of representing n cents.

 After comparing:
 I use the recursion method to find the count of one denom, and use findDenom() to find the rest.
 The solution uses findCombs to find all by varying n. The index logic is similar though.
 It also uses a map to hold previously calculated combinations.
 But most importantly, the book is just counting ways (that's the task, not printing all combs)
 */
public class Question8_11 {
    public static void main(String[] args) {
        CombinationFinder.findCombs(113);

    }

    static class CombinationFinder {
        static int[] den = { 25, 10, 5, 1 };

        static void findCombs(int n) {
            findCombs(n, new int[den.length], -1);
        }

        private static void findCombs(int n, int[] c, int idx) {
            if(idx >= c.length) {
                return;
            }

            c = findDenom(n, c, idx);
            System.out.println(Arrays.toString(c));

            // -1 because there's no combinations with the last denom e.g., with 3 cents, there is only 1 combination
            for(int i = 0; i < c.length - 1; i++) {
                // Find the first non-zero denom, and decrement it
                if(c[i] > 0) {
                    c[i]--;
                    // Move to next denom if curr is not available anymore
                    // If we move to next when c[i]==0, the next one will not take its max possible value, but just what c[i+1] previously had
                    int newIdx = c[i] == -1 ? i + 1 : i;
                    findCombs(n, c, newIdx);
                }
            }
        }

        private static int[] findDenom(int n, int[] c, int idx) {

            int maxVal = idx == -1 ? Integer.MAX_VALUE : c[idx];
            int i = idx == -1 ? 0 : idx;
            int rest = n;
            for(; i < den.length && rest != 0; i++) {
                if(i == idx) {
                    c[i] = Math.min(rest / den[i], maxVal);
                } else {
                    c[i] = rest / den[i];
                }

                rest = rest - (den[i] * c[i]);
            }
            return c;
        }
    }
}

/*
100 cents
This is selection, not arrangement
25:10:5:1 = {1..4 | 1..10 | 1..20 | 1..100}

Start with max-possible c0
Fill with max-possible c1, c2, c3

Reduce c0 by 1
Fill with max-possible c1, c2, c3

When c0 =0
Just use c1, c2, c3

38
1 | 1 | 0 | 1
0 | 3 | 1 | 3
0 | 2   3   3
0   1   5   3
0   0   7   3
...

findComb(n)
  int c[] = findDenom(n, int[4])
  findComb(n, c[], 0)

findComb(n, c[], idx)
  if(idx > c.len)
    return

  print c[] // print combination

  find earliest ele with c[i] > 0

  c[i]--
  if(c[i] == 0)
    findComb(n, c[], i + 1)
  else
    findComb(n, c[], i)

findDenom(int, int[4])
  find max-poss 25
  find max-poss 10
  find max-poss 5
  find max-poss 1
  return c[]


 */
