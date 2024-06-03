package n7rider.ch8.recursion_and_dynamic_prog;

import java.io.PrintStream;
import java.util.*;

/**
 Permutations with Dups: Write a method to compute all permutations of a string whose characters
 are not necessarily unique. The list of permutations should not have duplicates.

 After finishing:
 This condition countInNewStr >= countInInputThusFar was crucial to get the algorithm right..
 In a string abbot, this ensures when the second b is considered, a combination is added only if
 it two b's. All cases that use single b's were already added when previous b was considered.

 Skipped permutation logic. I have already done it twice in this chapter.

 After comparing:
 The book creates a map of char,count. It uses the same logic as the previous problem, but rather
 than running a for loop for n times, it iterates through every char in the hashmap  and calls
 the recursive method everytime. And before calling the recursive method, it deducts the count for
 the char from the map (so the combination won't be duplicated.

 I need to get familiar with alg to print permutations of the same length without dupes. My technique
 for cat | cta won't work for this req. without major refactoring
 */
public class Question8_8 {
    public static void main(String[] args) {
         System.out.println(PermutationFinder.printNonDuplicatePerms("abbot"));
//        System.out.println(PermutationFinder.printNonDuplicatePerms("acacia"));
    }

    static class PermutationFinder {
        static Set<Character> charSet = new HashSet();
        static List<String> printNonDuplicatePerms(String input) {
            return findCombinations(input, input.length() - 1);
        }

        private static List<String> findCombinations(String input, int currLength) {
            if(currLength == -1) {
                return List.of("");
            } else {
                List<String> outputSoFar = findCombinations(input, currLength - 1);
                List<String> newOutput = new ArrayList<>();
                newOutput.addAll(outputSoFar);
                char currChar = input.charAt(currLength);
                boolean isNew = charSet.add(currChar);
                for(String str : outputSoFar) {
                    String newString = str + currChar;
                    if(isNew) {
                        newOutput.add(newString);
                        System.out.printf("   Added %s. isNew = %s\n", newString, isNew);
                    } else {
                        boolean hasOnlyCurrChar = newString.replace(String.valueOf(currChar), "").equals("");
                        int countInNewStr = findCount(newString, currChar);
                        int countInInputThusFar = findCount(input.substring(0, currLength + 1), currChar);
                        if(countInNewStr >= countInInputThusFar) {
                            newOutput.add(newString);
                            System.out.printf("   Added %s. countInNewStr = %s | countInInputThusFar = %s\n",
                                    newString, countInNewStr, countInInputThusFar);
                        } else {
                            System.out.printf("   Skipped %s. countInNewStr = %s | countInInputThusFar = %s\n",
                                    newString, countInNewStr, countInInputThusFar);
                        }
                    }
                }
                System.out.println("Combinations found so far: " + newOutput);
                return newOutput;
            }
        }

        private static int findCount(String newString, char currChar) {
            int count = 0;
            for(int i = 0; i < newString.length(); i++) {
                char c = newString.charAt(i);
                if(c == currChar) {
                    count++;
                }
            }
            return count;
        }
    }
}

/*
    If char is added already
      If occur of char > 1 (Even if char is occurring 3+ times)
        skip all strings of size < c

comb:
  combRec(inp, len-1)

combRec (inp, len):
  if len == -1
    return new arraylist(" ")

  output = combRec(inp, len - 1)
  for each str in output
    NewStr = str.add(inp.charAt(len))
    if char is added already
        If occur of char > 1 (Even if char is occurring 3+ times)
            skip all strings of size < c
        else
            NewOutput.add(NewStr)
    printPerms(NewOutput)

  return NewOutput + output
 */

/*
aca
"",
a, c, (a)
ac ca aa (aa) (ac) (ca)

Simplest way: Set or hashmap

ac(a)
"",
a, c
ac ca [aa] MISSING

aboo -> abo
"",

abbot => abot

Missing for len=2, bb
for len=3, bbt btb abb obb tbb etc.
5P3 = 5!/2! = 60

Use bbot -> bot
Missing for len =2, bb

{}      // Added - {}
{} + b // Added - b
{} + b + [b] + bb // Added b already, so change logic, but what?

{} + b + bb + o + bo + bbo
{} + b + bb + o + bo + bbo + t + bt + bbt + ot + bot

We've avoided
b
bo
bt bot

aboat
{}
{} + a
{} + a + b + ab
{} + a + b + ab + o + ao + bo + abo
// A is added already
{} + a + b + ab + o + ao + bo + abo + [a] + aa + [ba] + [oa] + aoa + [boa] + aboa

So refining the logic
    If char is added already
      If occur of char > 1
        skip all strings of size < c

aaab
0 {}
1 {} + a
2 {} + a + [a] + aa
3 {} + a + [a] + aa + [a] + [aa] + [aa] + aaa
3 if we omit in 2 already
  {} + a       + aa + [a] + [aa] + aaa

Refining the logic
    If char is added already
      If occur of char > 1 (Even if char is occurring 3+ times)
        skip all strings of size < c

comb:
  combRec(inp, len-1)

combRec (inp, len):
  if len == -1
    return new arraylist(" ")

  output = combRec(inp, len - 1)
  for each str in output
    NewStr = str.add(inp.charAt(len))
    if char is added already
        If occur of char > 1 (Even if char is occurring 3+ times)
            skip all strings of size < c
        else
            NewOutput.add(NewStr)
    printPerms(NewOutput)

  return NewOutput + output
 */