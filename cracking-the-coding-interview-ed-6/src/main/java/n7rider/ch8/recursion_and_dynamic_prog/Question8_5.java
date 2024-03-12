package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Recursive Multiply: Write a recursive function to multiply two positive integers without using the
 * operator.You can use addition, subtraction, and bit shifting, but you should minimize the number
 of those operations

 After finishing:
 The solution runs in O(log y) time where y is the smaller number. But if a num. is negative, I need to
 detect and keep negative sign using (val >> 31) & 1. This returns 1 if number is negative.

 After comparing with solution:
 The book does recursive add by continuing dividing by 2 and adding the output e.g., for 15 * 13,
 13/2 = 6 which carries reminder 1, so add 15 + 15 + 15 to output. Next time, 6/2 has no reminder, so
 just add 15 + 15 to eat. Keep doing this until one number becomes 0. I thought it'll be too inefficient,
 but it has the same runtime as mine. I need to think it through more while planning an algorithm.
 Not going to redo this since the runtime is the same.
 */
public class Question8_5 {

    public static void main(String[] args) {
        int out = findProduct(11, 10);
        System.out.println(out);
    }

    static int findProduct(int x, int y) {
        // Extract signs and add it to the output if exactly one number is negative
        int c = 0;
        if (x > y) {
            return findProductRec(x, y, c);
        } else {
            return findProductRec(y, x, c);
        }
    }

    static int findProductRec(int x, int y, int c) {
        if( y <= 0) {
            System.out.printf("x = %d | y = %d | c = %d%n", x, y, c);
            return 0;
        } else {
            int out = 0;
            if((y & 1) == 1) {
                out = (x << c) + findProductRec(x, y >> 1, c + 1);
            } else {
                out = findProductRec(x, y >> 1, c + 1);
            }
            System.out.printf("x = %d | y = %d | c = %d | out = %d%n", x, y, c, out);
            return out;
        }

    }
}

/**
 * Without using * for x*y, we can add simply x for y times
 * But if y >> x, we can flip the fn. to y * x
 * But if y and x are both large, this fails
 *
 * If one of the numbers is divisible by 2, we can left shift the other, but this can be difficult for
 * an even no like 1010010. Also if no. is not divisible by 3, we need another logic
 *
 * 7 * 5 = 35 (100 011) in binary
 *
 *     1 1 1 (x)
 *     1 0 1 (y)
 *     -----
 *     1 1 1
 * 1 1 1 0
 * ---------
 *10 0 0 1 1
 *
 *
 * Bkp original x and y
 * int out = 0
 * int c = 0
 * while y > 0
 *   if y & 1 == 1
 *     out = out + x << c
 *   c++
 *   y = y >> 1
 *
 * return out
 *
 *
 *       1 0 1 1 (11) (x)
 *       1 0 1 0 (10) (y)
 *       -------
 *
 *  c = 0: y&1 = 0: out = 0
 *  c = 1: y&1 = 1: out = 10110
 *  c = 2: y&1 = 0: out = 10110
 *  c = 3: y&1 = 1: out = 10110 + 1011000 = 1101110 = 64 + 32 + 8 + 4 + 2 = 110
 *
 *  This approach works. Can we make it better?
 *
 *  We need a recursive function. How do convert this?
 *
 *  base case:
 *    if y <= 0
 *      return out
 *
 *  rec case (x, y, c):
 *    out = 0;
 *    if(y & 1 == 1)
 *      out = x << c + rec(x, y >> 1, c+1)
 *    else
 *      out = rec(x, y >> 1, c+1)
 *
 *
 *  invoke method(x, y, 0)
 *
 *  ---
 *
 *  c = 0: y&1=0: out = rec(1011, 1, 1)
 *                out = 1011 << 1 + rec(1011, 0, 2)
 *                out = 10110 + rec(1011, 1, 3)
 *                out = 10110 + 1011000 + rec(1011, 0, 4)
 *
 *
 */