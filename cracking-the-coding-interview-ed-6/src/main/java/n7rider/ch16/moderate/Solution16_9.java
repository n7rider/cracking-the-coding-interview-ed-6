package n7rider.ch16.moderate;

import static org.junit.Assert.assertEquals;

/**
 * Operations: Write methods to implement the multiply, subtract, and divide operations for integers.
 * The results of all of these are integers. Use only the add operator.
 *
 * After finishing:
 * Bitwise would have taken a long time, so I had a peek at the solution which used bitwise only for sub, and I
 * did the same.
 *  2's complement for sub() with a < b didn't work for me because I was creating a mask for 2's complement (going by how
 *  it works in school book. Then I did some sysout, and saw that I don't have to do that. sub() looks very simple now.
 *
 * After comparing with solution:
 * Calling the same method again with inverted params if numbers are in the reverse order is a neat trick.
 */
public class Solution16_9 {
    public static void main(String[] args) {
        assertEquals(15, sub(22, 7));
        assertEquals(-15, sub(7, 22));

        assertEquals(154, mul(22, 7));
        assertEquals(-154, mul(-7, 22));
        assertEquals(-154, mul(7, -22));
        assertEquals(154, mul(-7, -22));

        assertEquals(22, div(154, 7));
        assertEquals(-22, div(154, -7));
        assertEquals(-22, div(-154, 7));
        assertEquals(22, div(-154, -7));

    }

    static int sub(int a, int b) {
        int twoC = ~b + 1; // negate
        int c = (a + twoC);
        return c;
    }

    private static int getNoOfBits(int b) {
        int c = 0;
        while(b != 0) {
            b = b >> 1;
            c++;
        }
        return c;
    }

    static int mul(int a, int b) {

        if(Math.abs(a) < Math.abs(b)) {
            mul(b, a);
        }
        boolean signFlag = a < 0 ^ b < 0;
        a = Math.abs(a);
        b = Math.abs(b);

        int sum = 0;
        for(int i = 0; i < b; i++) {
            sum += a;
        }
        if(signFlag) {
            return ~sum + 1;
        }
        return sum;

    }

    static int div(int a, int b) {
       int q = 0;
       boolean signFlag = a < 0 ^ b < 0;
        a = Math.abs(a);
        b = Math.abs(b);

       while (b <= a) {
           q++;
           a = a - b;
       }
        if(signFlag) {
            return ~q + 1;
        }
        return q;
    }
}

/*
Simplest path: c = a OP b
Subtract: ??
Multiply: Keep adding a to c for b times
Divide: c = a / b => a = b * c
    Create temp var d
    So keep adding 'b' to 'd' until 'a' is reached
    The counter value 'c' is the output

Thinking along the same lines, subtract is similar
    c = a - b -> 22 - 5 = 17
    for i = 0 to inf
      d = b + i
      if d == a
        return c

Assumptions:
    Initial cond change based on a > b or a < b
    e.g., In subtract, if a > b, i starts at 0
    But if a < b and both are negative e.g., -5 - 22,
        Nvm, it works

Other ways: Using unary - operator can simplify subtract
Bitwise manipulation:
    multiply:
        a * b = Simulate multiplication table?
            a + a << 1 + .....

        Keeps it to O(32) for sure. Performs better than the above, but uses AND op. Also AND simulates multiplication.
    divide:
        can reuse multiply to achieve division, but will take same time as above
    subtract:
        can be easier with 2's complement? let's try:

        13 - 6

        1101 - 0110 = 0111

        1101
        1010    2's complement of 6
        0111    Works!!

Let's write up something for division too:

36 / 9 i.e., 100100 / 1001


1001 |________
     | 100100

We need to find number of bits in b, then extract that many bits in a i.e., c = a >> a.len - b
Then, do subtract (the 2's comp way) to find diff, and concat it with a's bits such that diff.len + a's bits = b's len
Repeat

Is there an alternate way?
How about using 2?
e.g., Keep left shifting b, until a is found or if we go above a, then go back and add until we reach here.
Or find the diff:
e.g., a = 10010, we went to 100000. We can just do int diff = 100000 - 10010, then we can walk back from 'b'. Either way works

Peeked at the book to see if it uses numerical way or bitwise. I posted above that bitwise is better, but I just wanted
to see which way the book went. Bitwise it is, I'll do the same. So, here is the pseudocode:

Subtract:
1. 1's complement - Negate b's bits
2. 2's complement - Add 1
3. Find no. of bits in step 2 i.e. loop until (2) >> 1 == 0. Then mask = 1 << bits + 1 - 1, so we reject all bits to the left
3. Add a to step 3.

Multiply:
loop until (b) >> 1 = 0
int c
extract last bit, c = c + (a << c & b's curr bit)

return c


Divide:
36 / 4 = 8 + 1 Works
36 / 6. We stop at 24 i.e., 10100, then it tries to reach 48
So, keep adding from 24
Worst case? If a is 1024 ~ 2048, we'll end up adding b  upto 1023 times
So, recursion?
So, when we stop at 24 for 36/6, subtract x = 36 - 24, then result = c + div (x, b) which will go recursive
 */