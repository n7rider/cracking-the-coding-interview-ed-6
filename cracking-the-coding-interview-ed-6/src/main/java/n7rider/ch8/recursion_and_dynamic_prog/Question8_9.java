package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Parens: Implement an algorithm to print all valid (e.g., properly opened and closed) combinations
 of n pairs of parentheses.
 EXAMPLE
 Input: 3
 Output: ( ( () ) ) , ( () () ) , ( () ) () , () ( () ) , () () ()

 After finishing:
 This takes log(n) for each number. Total count of no. is 2^n-1 to 2_n
 So, runtime is O(2^n log n)

 An alternate logic is to start from ((( ))) and to move each ) to left only if ('s preceding is >= it.
 Though finding dupes becomes tricky in that, the runtime won't be as high as O(2^n log n).
 I want to explore that idea.

 After comparing:
 It's been a while and I didn't realize that I didn't even use recursion, so my solution is quite primitive.

 The book once again finds permutation for n entries by inserting the nth element each time into permutation
 of n-1 entries
 e.g., In (), insert () into each place up to the first half (Usually it's up to the end, here, we'll just
 create duplicates. This creates ()() and (()). Similar logic works for n=3 but there will be duplicates.

 To prevent checking for duplicates, it uses recursion just like the one used for maze

 // base case
 return if leftRem < 0 ||  rightRem < leftRem
 //recursion case
 if leftRem and right are 0
    list.add(str)
 else
    str[index] = '('; // Add left and recurse
    addParen(list, leftRem - 1, rightRem, str, index + 1);
    str[index] = ')'; // Add right and recurse
    addParen(list, leftRem, rightRem - 1, str, index + 1);

 // Invocation
 addParen(list, count, count, str, 0)

 This will return ((( ))), then (( ) ()), then (( ) ) (), then ( ) (( )), then ( ) ( ) ( )

 Like maze, it goes greedy for left paren, and add right paren only if left paren is out of stock or if
 there are not left parens before it..

 */
public class Question8_9 {
    public static void main(String[] args) {
        printParenCombinations(3);
    }

    static void printParenCombinations(final int n) {
        int limitPlusOneBit = (1 << (2 * n));
        int limit = limitPlusOneBit - 1;

        for(int i = limitPlusOneBit / 2; i <= limit; i++) {
            boolean isValid = isValidPair(i, (2 * n) - 1);
            if(isValid) {
                printParen(i, (2 * n) - 1);
            }
        }
    }

    private static boolean isValidPair(int i, int maskPos) {
        int count1 = 0;
        int count0 = 0;
        while(maskPos >= 0) {
            int mask = 1 << maskPos;
            if((i & mask) != 0) {
                count1++;
            } else {
                count0++;
            }

            if(count1 - count0 < 0) {
                System.out.printf("%s is not valid. Early exit. mask = %s. maskPos=%d. count1=%d, count0 = %d\n",
                        Integer.toBinaryString(i), Integer.toBinaryString(mask), maskPos, count1, count0);
                return false;
            } else {
                maskPos--;
            }
        }

        if(count1 != count0) {
            System.out.printf("%s is not valid. count1=%d, count0 = %d\n",
                    Integer.toBinaryString(i), count1, count0);
            return false;
        } else {
            System.out.printf("%d is valid\n", i);
            return true;
        }
    }

    private static void printParen(int i, int maskPos) {
        StringBuffer out = new StringBuffer();
        while(maskPos >= 0) {
            int mask = 1 << maskPos;
            if((i & mask) != 0) {
                out.append("(");
            } else {
                out.append(")");
            }
            maskPos--;
        }
        System.out.println(out.toString());
    }
}

/*
111000 | 110101 | 110010 | 101100 | 101010

((()))

000000

combinations(n) // n=3
  maxLimit = 1 << 2n - 1 // 111 111

  for(i = maxLimit / 2; i <= maxLimit ; i++)
    bool isValid = isValidPair(i, n)
    if(isValid)
      printParentheses(i)


isValidPair(i, n)

  int count1 = 0
  int count0 = 0
  int maskPos = 2n - 1
  while(i > 0)
    int mask = 1 >> maskPos
    if i & mask == 1
      count1++
    else
      count0++

    if count1 - count0 < 0
      return false
    else
      maskPos--

  if count1 - count0 < 0 || count1 != count0
    return false
  else
    return true

printParentheses(i)

    String out = ""
    int maskPos = 2n - 1
      while(i > 0)
        int mask = 1 >> maskPos
        if i & mask == 1
          out += (
        else
          out += )
    print out


Alternate just using strings?
Find all permutations (Using cat, cat method)
Check validity of each string

111000 | 11 01 00 | 10 11 00 | 11 00 10 | 10 10 10 |
 */
