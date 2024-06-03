package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Parens: Implement an algorithm to print all valid (e.g., properly opened and closed) combinations
 of n pairs of parentheses.
 EXAMPLE
 Input: 3
 Output: ( ( () ) ) , ( () () ) , ( () ) () , () ( () ) , () () ()

 After finishing:
 Didn't finish, but swapping from one element to another using index positions is too tricky. There must be
 a better wy.

 
 */
public class Question8_9_Attempt2 {
    public static void main(String[] args) {

    }


}

/*
111 000

Create 111 000
Start at zeroPos=3 | onesBefore=3 | zeroCardinal=1
moveZeroToLeft(string, zeroPos)




moveZeroToLeft(string, zeroPos, zeroCardinal)

  if zeroCardinal < onesBefore // flip if there are more ones before the zero
    swap string[zeroPos-1], string[zeroPos]
    onesBefore--
    zeroPos-- // 11 01 00
    repeat for next zero (string, zeroPos) i.e., moveZeroToLeft(string, newZeroPos, zeroCardinal + 1)

    moveZeroToLeft(string, zeroPos)


11 10 00 PRINT (Happens not in the current rec method)
 11 01 00 PRINT
   Call for next zero
     11 01 00 PRINT (Happens not in the current rec method)
       11 00 10 PRINT
         Call for next zero
            11 00 10 PRINT  (Happens not in the current rec method)
                Call for next zero
                    ---
            Can't move anymore

  This won't be able to differentiate first zero and second zero unless you remember the position of prev zero too.

  There are simply too many states to maintain





 */
