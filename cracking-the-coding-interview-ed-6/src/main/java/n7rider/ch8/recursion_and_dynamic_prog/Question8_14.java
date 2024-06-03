package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 Boolean Evaluation: Given a boolean expression consisting of the symbols 0 (false), 1 (true), &
 (AND), | (OR), and ^ (XOR), and a desired boolean result value result, implement a function to
 count the number of ways of parenthesizing the expression such that it evaluates to result.
 EXAMPLE
 countEval("1^0|0|1", false) -> 2
 countEval("0&0&0&1^1|0", true) -> 10

 Didn't finish. I wasn't sure one or two or several parentheses were allowed, and the parsing goes on and on.
 I doubt if this can be finished within 40 mins.

 After comparing with solution:
 The solution is concise but complex. It recursively splits the exp into LHS, sym, RHS.
 Then for each operator, it looks for a specific state on each side e.g., for 1^0|0|1, the LHS (1&0)|(0|1) can be
 false as required only if both are 0. In code, this is
 countEval(left | right, true) = countEval(left, true) * countEval(right, false) + countEval(left, false) * countEval(right, true)
 + countEval(left, true) * countEval(right, true)
 Therefore to find false, just do the opposite:
 countEval(expression, false)= totalEval(expression) - countEval(expression, true)

 To apply paren at different stages, there is no actual injection of paren.
 But a loop starts at exp[i] and increments by 2 (thus always landing on symbols).
 Base case is when there is only one no. on a side. We recursively call for both sides for both results.

 int leftTrue = countEval(left, true);
 int leftFalse = countEval(left, false);
 int rightTrue = countEval(right, true);
 int rightFalse = countEval(right, false);

 The second arg is what we compare with when we get to the base case.

 To collect all, we do:
 int total= (leftTrue + leftFalse) * (rightTrue + rightFalse);

 This is done because it because evaluating all symbols simpler e.g., if sym == &, add only leftTrue & rightTrue etc.

 IMO we can consider this as a binary number e.g., 1^0|0|1 as a four digit primary number and choose what to put in paren.
 But this would need an eval engine that can read paren, or processes strings can eval the paren part first.
 And doesn't tell clearly what is required e.g., for 1110, do you use (1)(1)(1)0 or (111)0.

 I was going towards an eval engine that can process injected paren, but it gets too complex as soon as you start parsing

 The symbols are not the core of the problem, because even my eval engine is simple when handling paren.
 The paren part is the tricky part. I guess a better way to approach this is to think recursively, and if the problem
 is bigger, see if you can solve it for a smaller example, or find answer for a case like countEval(left, true) that can
 used as a support to solve the problem.
 */
public class Question8_14 {

    public static void main(String[] args) {
        System.out.println(CombinationsFinder.countEval("1^0|0|1", false));
    }

    static class CombinationsFinder {
        static Map<String, String> resultsMap = new HashMap<>();
        static int countEval(String inp, boolean result) {
            // Skip validations - Trim, Test first and last char for sym, test alternation of num and sym,
            // symbols other than &|^
            // Length at least 3
            resultsMap = new HashMap<>();
            int count = 0;

            String finalRes = eval(inp, " ", "");
            boolean finalResBoolean = finalRes.equals("1");
            if(finalResBoolean == result) {
                count+= inp.length() / 2;
            }

            for(int i = 2; i < inp.length() -1; i++) {
                for(int j = i + 2; j <= inp.length() - 1; j++) {
                    String injectedInp = inp.substring(0, i + 1) + "(" + inp.substring(i + 1, j + 1) + ")" + inp.substring(j + 1);
                    System.out.println("Injected input : " + injectedInp);
                    finalRes = eval(injectedInp, " ", "");
                    finalResBoolean = finalRes.equals("1");
                    if(finalResBoolean == result) {
                        count++;
                    }
                }

            }

            return count;

        }

        static private String eval(String inp, String expSoFar, String resultSoFar) {
            int parenStartIdx = inp.indexOf('(');
            if(parenStartIdx != -1) {
                // Assuming there's only one set of paren
                int parenEndIdx = inp.indexOf(')');
                String expInParen = inp.substring(parenStartIdx + 1, parenEndIdx - 1);
                String parenResult = eval(expInParen, "", " ");
                inp = inp.replace("(" + expInParen + ")", parenResult);
                eval(inp, " ", "");
            }

            if(inp.length() > 1) {
                String currSubString = inp.substring(0,3);
                String currExp = expSoFar + currSubString;
                String cachedValue = resultsMap.get(currExp);
                if(cachedValue == null) {
                    resultSoFar = evalMath(currSubString);
                    resultsMap.put(currExp, resultSoFar);
                    System.out.println("Entry added to cache: " + currExp + " --> " + resultSoFar);
                } else {
                    resultSoFar = resultsMap.get(currExp);
                    System.out.println("Entry loaded from cache: " + currExp + " --> " + resultSoFar);
                }
                inp = inp.replaceFirst(currSubString, resultSoFar);
                expSoFar = expSoFar.substring(0, expSoFar.length() - 1) + inp.substring(0, 2); // Remove 1st num, it's already there
                eval(inp, expSoFar, resultSoFar);
            }
            System.out.printf("Result for inp: %s is %s", inp, resultSoFar);
            return resultSoFar;
        }

        static private String evalMath(String evalExp) {
            int op1 = charToBoolean(evalExp.charAt(0));
            int op2 = charToBoolean(evalExp.charAt(2));
            char sym = evalExp.charAt(1);

            int result;
            if(sym == '|') {
                result = op1 | op2;
            } else if(sym == '&') {
                result = op1 & op2;
            } else {
                result = op1 ^ op2;
            }

            return result == 0 ? "0" : "1";
        }

        static int charToBoolean(char inp) {
            return Integer.parseInt(String.valueOf(inp));
        }

    }
}
/*
Try all combinations from 1..x... n, 2..3, 2..4 ... 2..n and similarly to n-1...n
(1..x doesn't make any difference,  but we can still count it)
So O(n^2) attempts naively

Choose only if result matches, then inc

1^0|0|1
Eval (without paren):
From left, take 3 char, process it
Replace 3 with current out
Take next 3 (out + next 2)

Eval (with paren at start)
(1^0)|0|1
1. Paren found
2. Remove (
3. Process normally till end of paren
4. Remove )
5. Normal way
This isn't even needed. If the prev option returns right result, then just add n-1 times to result
because it's the same for out 1..2, 1..3, 1...n


Eval (with paren at middle)
1^(0|0|1)
if paren found:
  do a rec. call i.e.,
    rather than take 3
    process paren
    then take 3

Is this better? Or splitting into nos[] and syms[]

This part is simple. Lok for ( in the last char

Skip validations

str = injectParAt(1,x, exp)
Run evalInternal(str)

injectParAt(start,end, exp):
  if i == 1 Returns just exp
  else goTo idx=start*2. Append a (
  goTo idx=end*2 of original. Append a )
  Copy everything to the end

evalInternal(str)
  Do Eval (without paren)
  Do Eval (with paren at middle)
  add result to hashMap - how?

1^0|0|1
Add 1^0,res to hashmap
Carry around processedSoFar
evalInternal(injectedStr, out, processedSoFar)

 */