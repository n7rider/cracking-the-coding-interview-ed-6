package n7rider.ch16.moderate;

import java.util.Arrays;

/**
 * Number Swapper: Write a function to swap a number in place (that is, without temporary variables).
 *
 * After finishing:
 * Even with point by reference, if we refer to a new value, the previous reference that the invoking method
 * has will not change. So, we need to point by reference and mutate the passed objects, or pass a new set of
 * objects as the output. I chose to create a primitive array, and mutate the array.
 *
 * After comparing with the solution:
 * The book talks about an algorithm (similar to mine) and another one using XOR, but nothing about the point by reference
 * problems.
 */
public class Solution16_1 {
    public static void main(String[] args) {
        int[] a = new int[] {10, 20};
        swap(a);
        System.out.println(Arrays.toString(a));

        a = new int[] {-5, -10};
        swap(a);
        System.out.println(Arrays.toString(a));

        a = new int[] {10, -10};
        swap(a);
        System.out.println(Arrays.toString(a));
    }

    static void swap(int[] a) {
        if (a == null || a.length != 2) {
            throw new IllegalArgumentException("Invalid input");
        }
        a[0] = a[0] + a[1];
        a[1] = a[0] - a[1];
        a[0] = a[0] - a[1];
    }
}

/*
Typical swap:
swap(a, b) // Use objects for point by reference, do in-method swap for primitives
Integer temp = a;
a = b;
b = temp;

Without temp:
- Add both to a: a = (a + b)
- Set b to a : b = (a + b) - b
- Now set a to b: a = (a + b) - a

a = a + b;
b = a - b;
a = a - b;
 */
