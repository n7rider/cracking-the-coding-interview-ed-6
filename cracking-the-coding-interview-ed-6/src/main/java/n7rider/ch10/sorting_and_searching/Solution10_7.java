package n7rider.ch10.sorting_and_searching;

import java.util.List;

/**
 * Missing Int: Given an input file with four billion non-negative integers, provide an algorithm to
 * generate an integer that is not contained in the file. Assume you have 1 GB of memory available for
 * this task.
 * FOLLOW UP
 * What if you have only 10 MB of memory? Assume that all the values are distinct and we now have
 * no more than one billion non-negative integers.
 *
 * After finishing:
 * TIL, arrays, Strings, list can accept only int as size, so it can become a bottleneck.
 * I used long to represent number 2 bil-4 bil, but otherwise long is not needed at all.
 *
 * Since input size is 4 billion, the book assumes 2 billion of them are duplicates (and not that the
 * input can vary from 1-4 billion). Assumptions can vary.
 *
 * For looking through larger files with smaller memory, my plan is to do shell sort, then look up in smaller
 * sections supported by the memory
 *
 * After comparing:
 * Similar solution, the book also solves them in batches.
 *
 *
 */
public class Solution10_7 {
    public static void main(String[] args) {

        // Assume this is an LL with long but values up to 4 billion. Extra bit is needed to represent 2 billion - 4 billion.
        List<Long> inp = List.of(0L, 1L, 2L, 3L, 4L, 5L, 7L, 8L, 2147483648L);

        long out = findNewNum(inp);
        System.out.println(out);

    }

    static long findNewNum(List<Long> inp) {
        long[] b = new long[Integer.MAX_VALUE / 16];
        createMarkers(b, inp);
        for(long i = 0; i < Integer.MAX_VALUE * 2L; i++) {
            if(!exists(i, b)) {
                return i;
            }
        }


        return -1;

    }

    private static void createMarkers(long[] b, List<Long> inp) {
        for(long i : inp) {
            int idx = (int) (i / 64);
            int pos = (int) i % 64;
            long valueToAdd = 1 << pos;

            b[idx] = b[idx] | valueToAdd;
        }
    }

    private static boolean exists(long i, long[] b) {
        int idx =  (int) (i / 64);
        int pos = (int) i % 64;
        long valueToCheck = 1 << pos;

        return (b[idx] & valueToCheck) > 0;
    }

}

/*
A hash set can be used to check if the number is present or not. But it needs to store all
available in memory.

4 billion ints. Size = ?

1 int = 4 bits, 4 billion ints = 16 billion bits = 16 million KB = 16 000 MB ~= 16 GB

With 1 GB memory, that's not possible

However, 4 bits = 32 bytes, and that hold a 1 or 0 for 32 numbers.

So, we have 32x capacity this way. Therefore, 16GB / 32 = 0.5 GB of byte[] is enough

Algorithm:
int pos = 1 << n;
find pos / 8, pos % 8 // can't store in bits, need to store in bytes
if (byte[pos] == 1)
  duplicate, continue
else
  found

After starting to code:
Arrays can't allow long for array size (only ints are allowed), so need to use something else.

Maybe long LL for input and long array for storage, no that still won't be enough for storage
The requirement says 4 billion integers, so an int might be enough to store everything.

In java, int limit is 2*10^9 , long limit is 9 * 10^18. Including sign bit, int is enough to store 4 billion int values' markers,
so that's enough e.g., int i = 2, find bit no. 2/32 = 0. For 2*10^9, 2*10^9 / 32 = 8 * 10^7, so it's enough.
But bitwise operators did not play fine with overflow involved (for values 2 bil - 4 bil), the int idx can be negative, you need
 nested for loops when finding a new number, so it's easier to use long[]

With long[], the process is same but we divide by 64. It's actually easier to use long or a bigger data type since we divide by
a larger number.

Looking at the book, it uses Scanner library for inp, and

Follow through:
With 10 MB, number of int markers stored = 8 bits * 1000 bytes * 1000 * 10 = 80,000,000 i.e., 80 mil markers.

We have 1 billion ints

We need to compress further by 0.5 GB / 10 MB ie., 0.01 GB = 50x, but data reduced by 4x, so 12x compression is needed

There are 1 billion ints, but we can store only 80 mil markers. How to compress 12 elements to 1 marker?

The only solution I can think of:
- sort in batches like shell sort or quick sort
- enter elements to marker[] until out of storage
- remove all bits with entry 1 from the last, save it to an int e.g., if last 100 bits are 1, save int i = 100, and remove all 100 bits
Bits saved = 100 - 32 = 68 bits
- if any element is missing that's the item we want





 */