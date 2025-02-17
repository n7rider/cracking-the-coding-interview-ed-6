package n7rider.ch10.sorting_and_searching;

/**
 * Find Duplicates: You have an array with all the numbers from 1 to N, where N is at most 32,000. The
 * array may have duplicate entries and you do not know what N is. With only 4 kilobytes of memory
 * available, how would you print all duplicate elements in the array?
 *
 * Approach:
 * Same as mine
 *
 * After implementing:
 * To manipulate bytes, first convert to int using Byte.toUnsignedInt(myByte).
 * After doing bitwise manipulation, convert back to byte and store using byte = (byte) myInt.
 * Taking a byte from a byte entry passes by value, so do byteArr[idx] = (byte) myInt.
 * Doing x = byteArray[idx], and then x = (byte) myInt does nothing
 *
 * After comparing:
 * The book solution creates its own class BitSet
 */
public class Solution10_8 {
    public static void main(String[] args) {

        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 10, 11, 9};
        printDuplicates(arr);
    }

    static void printDuplicates(int[] arr) {
        // 4 KB = 4096 bytes = 32768 bits
        byte[] p1 = new byte[4000]; // Presence/absence of number
        byte[] p2 = new byte[96]; // Duplicate/unique number

        printDuplicatesHelper(p1, p2, arr);
    }

    static private void printDuplicatesHelper(byte[] presenceMarker, byte[] uniquenessMarker, int[] arr) {
        for(int ele: arr) {

            byte currByte = presenceMarker[ele / 8];
            int x = Byte.toUnsignedInt(currByte);
            int currIdx = ele % 8;
            int bitwiseMask = 1 << currIdx;
            if((x & bitwiseMask) == 0) {
                // Number doesn't exist yet
                x = x | bitwiseMask;
                presenceMarker[ele / 8] = (byte) x;
            } else {
                // Number exists already
                System.out.println("Duplicate entry: " + ele);
            }
        }
    }

}

/*
4 KB = 4096 bytes
1 int = 4 bytes, so 1024 numbers.

storage = 1024
array len = 32000

Batches may not be enough since duplicates for the earliest numbers can arrive much later.

Worst case:
Do in-memory sort e.g., quicksort
Keep iterating until i == i+1

How about we use boolean markers to store

boolean takes 1 byte.

int storage = 1024
array len = 32000
boolean storage = 4096
using bit of int to store value = 4096 bytes * 8 = 32768 bits

so, each bit can represent a number.

But, how do we mark duplicates?

We only have 2 states in bits - but we have 3 states - number present, not present, duplicate present

If state 2 doesn't happen, then it's straightforward.

Another solution is to use another binary marker in the remaining 768 bits i.e., 96 bytes:

1. 32000 bits store 1, 0 if number is present | absent
2. 768 bits store 1, 0 if number is duplicate | unique
3. When printing duplicates, navigate in first marker, find state in second marker, print, then increment second marker

Downside: Will 768 bits be enough?? Maybe no.

So, tweak the algorithm to periodically flush second marker

1. 32000 bits store 1, 0 if number is present | absent
2. 768 bits store 1, 0 if number is duplicate | unique
3. If 768 bits are reached in second, navigate in first marker, find state in second marker, print, then increment second marker
4. Start from beginning of 768 bits (probably take away 4 bytes for the starting point of second marker)

We can make it all simple by just using the first marker and then printing duplicates immediately


 */