package n7rider.ch17.hard;

import static org.junit.Assert.assertEquals;

/**
 * Add Without Plus: Write a function that adds two numbers. You should not use+ or any arithmetic
 * operators.
 *
 * After finishing:
 * --
 *
 * After comparing:
 * My carryCheck can be replaced with an XOR. Logically if a and b are same, cBit is same as carry.
 * In the else side, if a and b are not same, then carry will never be 1, and cBit = 1.
 *
 * My logic works, but we don't need the additional and checks.
 */
public class Solution17_1 {
    public static void main(String[] args) {
        assertEquals(33, add(12, 21));
        assertEquals(-9, add(12, -21));
        assertEquals(-3, add(-2, -1));
        assertEquals(-7, add(-0, -7));
        assertEquals(0, add(0, 0));
    }

    static int add (int a, int b) {
        int c = 0;
        int carry = 0;
        final int INT_SIZE = 32;
        // Validations - if a ==0 & b ==0, return 0.
        for(int i = 0; i < INT_SIZE; i++) {
            int aBit = getBitAt(a, i);
            int bBit = getBitAt(b, i);
            int andOut = (aBit & bBit) & carry;
            int orOut = (aBit | bBit) | carry;

            int cBit = 0;
            if(andOut == 1) {
                cBit = 1;
                carry = 1;
            } else if(orOut == 1) {
                int carryCheck = (aBit & bBit) | (aBit & carry) | (bBit & carry);
                if (carryCheck == 1) { // We have 2 ones
                    carry = 1;
                } else {
                    cBit = 1;
                    carry = 0;
                }
            } else {
                carry = 0;
            }

            c = setBitAt(c, i, cBit);
        }
        return c;
    }

    private static int getBitAt(int a, int idx) {
        int mask = 1;
        mask = mask << idx;

        mask = a & mask;
        if(mask == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private static int setBitAt(int c, int idx, int val) {
        if(val != 0) {
            int mask = 1;
            mask = mask << idx;

            c = c | mask;
        }
        return c;
    }


}

/*
12
21
---
33

Bitwise is an option.
Simulate math-class OR gates going from right to left, with carry

12 + 21 is done this way

    1 1 0 0
  1 0 1 0 1
-------------
1 0 0 0 0 1

Algorithm
Start from right
    Extract a bit for both
    ANDout & ORout = Do AND and OR of ((a , b) , c) (OR will return 1 for 1 & 1, and that's not enough to find carry)
    If both are 1
        output bit = 0, carry = 1
Repeat until the end

Pseudocode
int has 32 bits
carrybit = 0
for int i = 0 to 31
    abit = getBitAt(a, i)
    bbit = getBitAt(b, i)
    andout = (abit AND bbit) AND carrybit
    orout = (abit OR bbit) OR carrybit
    if andout == 1 // For 1 + 1 + 1
        cbit = 1, carry = 1
    else if orout == 1 // Find if we got 1, or 10
        if (A AND B) OR (B and C) OR (A and C) == 1 // Atleast 2 bits are 0
            cbit = 0, carry = 1
        else
            cbit = 1, carry = 0
    else
        cbit = 0, carry = 0

    setBitAt(c, i, cbit) // This method can do nothing if cbit = 0


 0 0 0 ---> OR = 0
 0 0 1 ---> Cbit = 1. How about (A AND B) OR (B and C) OR (A and C). Works
 0 1 0 ---> Cbit = 1
 0 1 1
 1 0 0 ---> Cbit = 1
 1 0 1
 1 1 0
 1 1 1 ---> AND = 1

getBitAt(a, i)
    int mask = 1
    mask = 1 << i
    out = mask & a == 0 ? 0 : 1

setBitAt(c, i, cbit)
    if cbit == 0
        return
    int mask = 1
    mask = 1 << i
    c = c | mask


 */