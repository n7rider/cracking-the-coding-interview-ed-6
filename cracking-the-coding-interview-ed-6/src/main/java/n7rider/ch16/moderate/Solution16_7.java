package n7rider.ch16.moderate;

import static org.junit.Assert.assertEquals;

/**
 * Number Max: Write a method that finds the maximum of two numbers. You should not use if-else
 * or any other comparison operator.
 *
 * After finishing:
 * Bitwise is possible, but can get complex
 *
 * After comparing:
 * It is complex. if a-b is negative e.g., for 10, 15, you can't just make the number positive by
 * changing the sign of the int because it is stored in 2's complement.
 * Without if-else, you can't decide when to do the simple math, when to undo 2's complement.
 *
 * The book tries to multiply a with 0 and b with 1 if a-b is negative. It finds 0 or 1
 * based on the sign bit of the int. Then, it chooses 'a' if:
 * (numbers areDiffSigned AND a is positive) OR (numbers areSameSigned and (a-b) is positive).
 * Else it chooses the other

 */
public class Solution16_7 {
    public static void main(String[] args) {
        assertEquals(15, max(15, 10));
        assertEquals(15, max(10, 15));
        assertEquals(-10, max(-10, -15));
        assertEquals(10, max(10, -15));

//        assertEquals(15, getMax(15, 10));
//        assertEquals(15, getMax(10, 15));
//        assertEquals(-10, getMax(-10, -15));
//        assertEquals(10, getMax(10, -15));
    }

    public static int max(int a, int b) {
        int c = a + b;
        int d = Math.abs(a - b);
//        System.out.println(Integer.toUnsignedLong(d)); - An alternative to using Math.abs
        return (c + d) / 2;
    }

    static int sign(int a) {
        return flip((a >> 31) & 0x1);
    }

    static int flip(int bit) {
        return 1 ^ bit;
    }

    static int getMax(int a, int b) {

        int c = a - b;

        // Book uses 1 for positive, 0 for negative numbers. Odd
        int sa = sign(a);
        int sb = sign(b);
        int sc = sign(c);

        // Is 1 only if a and b have different signs. Badly named var. Should be named `areDiffSigned`.
        int use_sign_of_a = sa ^ sb;
        // Is 1 only if a and b have same signs. Badly named var. Should be named `areSameSigned`.
        int use_sign_of_c = flip(sa ^ sb);

        /* Use 'a' if:
         - first part: areDiffSigned, and a is positive ==> e.g., 10, -100
         - areSameSigned, and c is positive ==> e.g., 15, 10
         Will always be 1 or 0, and not 2. This is because areDiffSigned and areSameSigned are never always same.
         */
        int k = use_sign_of_a * sa + use_sign_of_c * sc;
        int q = flip(k); // Make 'b' go away

        return a * k + b * q;
    }
}

/*
a: 15, b: 10

a + b + (a - b) = 2a.
In above ex, you get a=15

a: 10, b: 15
25 + (-5) = 20 => a = 10
25 + |(-5)| = 30 => a = 15

Bitwise: difficult/cumbersome than the above to make it work without if-else because:
You need to look from left side and find the first no. with a non-zero bit

a: -10, b:-15
-25 + |-5| = 20 => a = 10

Looked at solution, it uses some bitwise operation.
Can I do it instead of Math.abs?
    Create a mask that has 0 for sign bit, but 1 everywhere else
    mask1 = 1 << 31
    maskFinal = ~mask2
    c = c & maskFinal

01111111111111111111111111111
10000000000000000000000000101

11111111111111111111111111011
 */