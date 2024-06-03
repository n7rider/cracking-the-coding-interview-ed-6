package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.HashMap;

/**
 * Permutations without Dups: Write a method to compute all permutations of a string of unique
 * characters.
 *
 * After finishing:
 * Permutation is easy, but finding each substring is not.
 * Using bitwise to find all substring can be easier.
 */
public class Question8_7 {

    public static void main(String[] args) {
        PermutationFinder.printAllPermutations("cats");
    }

    static class PermutationFinder {

        static HashMap<Integer, Integer> factMap = new HashMap<>();

        static void printAllPermutations(String inp) {
            for(int i = 1; i <= inp.length(); i++) {
                int n = i;
                int loopTurns = findNpr(inp.length(), n) / fact(n);
                printAllPermutationsRec(inp, i, i, loopTurns);
            }

        }

        private static void printAllPermutationsRec(String inp, int n, int lenCovered, int loopTurns) {

            // The recursion simulates factorial, so looping does the rest of the product e.g., 1! = 1,
            // but we have 3 permutations for single-length chars, so the loopTurns var does the rest that recursion doesn't.
            // int loopTurns = findNpr(inp.length(), n) / fact(n);
            if(loopTurns == 0) {
                return;
            } else {
                if(n == 1) {
                    System.out.println(inp.substring(0, lenCovered));
                    if(lenCovered == 1) {
                        inp = rotatedString(inp, inp.length());
                        printAllPermutationsRec(inp, n, lenCovered, loopTurns - 1);
                    }
                } else {
                    for(int i = 1; i <= n; i++) {
                        printAllPermutationsRec(inp, n-1, lenCovered, loopTurns - 1);
                        inp = rotatedString(inp, n);
                    }
                }
            }


        }

        private static int findNpr(int n, int r) {
            return fact(n) / fact(n-r);
        }

        private static int fact(int n) {
            if (factMap.get(n) != null) {
                return factMap.get(n);
            } else {
                int p = 1;
                for(int i = 1; i <= n; i++) {
                    p = p * i;
                }
                factMap.put(n, p);
                return p;
            }
        }

        private static String rotatedString(String inp, int n) {
            // When n = 2 and lenCovered = 2, cat --> cta
            //                                012
            // When n = 3 and lenCovered = 3, cat --> tca

            int newIdx = inp.length() - n;
            char lastChar = inp.charAt(inp.length() - 1);
            char[] inpAsCharArray = inp.toCharArray();
            for(int i = inp.length()- 1; i > newIdx; i--) {
                inpAsCharArray[i] = inpAsCharArray[i - 1];
            }
            inpAsCharArray[newIdx] = lastChar;
            return String.valueOf(inpAsCharArray);
        }
    }


}



/*
foxes
cats
cat

1 2 6 24 = n!
c - c
ca - ca ac
cat - cat cta tca tac atc act
cats - 6 * 4 = 24

cat - c a t | ca ac at tc at ta | cat cta tca tac atc act


cat -
 n = 0 |
   move 0..0 to the end
   and print 0..0 chars
   loop x times | nPr for n=3, r=1 = 3, so nPr / 0...(0+1)! | This is 3 for n=1, 3 for n=2, 1 for n=3
 print c | atc
 print a | tca
 print t | cat
 n = 1 |
   move 0..1 to the end
   for i = 0..n
     rec(n-1)
   loop 0..< len times (i..e, 3 times)

 twice rec(n = 0) | cat
   print ca and ct
 rotate to tca
 twice rec(n = 0) | tca
   print tc and ta
 rotate to atc
 twice rec(n = 0) | atc
   print at and ac
 rotate to cat

 n = 2 |
   move 0..2 to the end
   for i = 0..n
     rec(n-1)
   loop 0..< len times (i..e, 3 times)

 rec(n=3-1) | cat
   rec(n=2-1) | cat
     print cat | cta
 rotate last to n-3 (for 1 based index)(Logic change)

n = 1
 ca
 n = 0
 print ca
 rotate to ac
 n = 0
 print ac

n = 2
 cat
 n = 1
 cat
   n = 0
   print cat
   rotate to cta
   n = 0
   print cta
   rotate to cat
 rotate to tca
 n = 1

 n = 1
 */