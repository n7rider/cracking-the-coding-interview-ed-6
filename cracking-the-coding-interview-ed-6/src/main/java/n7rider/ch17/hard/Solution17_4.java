package n7rider.ch17.hard;

import java.util.Arrays;

/**
 * Missing Number: An array A contains all the integers from O to n, except for one number which
 * is missing. In this problem, we cannot access an entire integer in A with a single operation. The
 * elements of A are represented in binary, and the only operation we can use to access them is "fetch
 * the jth bit of A[ i ],"which takes constant time. Write code to find the missing integer. Can you do
 * it in O(n) time?
 *
 * After finishing:
 * Left the more readable block as a comment, but the code uses the more concise form.
 *
 * After comparing:
 * The book uses bitwise operator to push 1 or 0 to the result, and we don't need an array for the result. Also, using
 * recursion enables the book to prevent the recurrent copying of arrays. However, this was possible only because
 * the book was using arraylists internally. I thought this problem required primitive tech only - because otherwise, you
 * don't even need the bucket sort technique.
 */
public class Solution17_4 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 0, 0},
                {0, 1, 0, 1}, {0, 1, 1, 1}, {1, 0, 0, 0}}; // 0-8, 6 is missing
        System.out.println(Arrays.toString(findMissing(arr)));

        int[][] arr2 = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 0, 1, 1}, {0, 1, 0, 0}}; // 0-5, 5 is missing
        System.out.println(Arrays.toString(findMissing(arr2)));
    }

    static int[] findMissing(int[][] arr) {
        int currPos = 0;

        int[] out = new int[arr[0].length];


        int[][] zeroArr, oneArr;

        while (arr.length > 0 && currPos < arr[0].length) { // Or can check currPos < log(n)
            int zeroC = 0, oneC = 0;
            zeroArr = new int[((int) Math.ceil(arr.length / 2.0)) + 1][]; // Always have n/2 + 1. Because 4 nums can be split 3 0's and 1 1's.
            oneArr = new int[((int) Math.ceil(arr.length / 2.0)) + 1][];

            System.out.printf("\nArray lengths: %d, %d, %d\n", arr.length, zeroArr.length, oneArr.length);
            for (int i = 0; i < arr.length; i++) {
                int currBit = arr[i][arr[0].length - 1 - currPos];
                if (currBit == 0) {
                    zeroArr[zeroC++] = arr[i];
                    System.out.printf("Added %s to zeroArr \n", Arrays.toString(arr[i]));
                } else {
                    oneArr[oneC++] = arr[i];
                    System.out.printf("Added %s to oneArr \n", Arrays.toString(arr[i]));
                }
            }
            if ((arr.length % 2 == 0 && zeroC > oneC) ||
                    (arr.length % 2 != 0 && zeroC != oneC)) {
                out[currPos] = 1;
                arr = Arrays.copyOf(oneArr, oneC);
            } else {
                out[currPos] = 0;
                arr = Arrays.copyOf(zeroArr, zeroC);
            }
//            if (arr.length % 2 == 0) {
//                if (zeroC > oneC) { // missing no. is in oneC
//                    out[currPos] = 1;
//                    arr = Arrays.copyOf(oneArr, oneC);
//                } else {
//                    out[currPos] = 0;
//                    arr = Arrays.copyOf(zeroArr, zeroC);
//                }
//            } else {
//                if (zeroC != oneC) { // missing no is in oneC
//                    out[currPos] = 1;
//                    arr = Arrays.copyOf(oneArr, oneC);
//                } else {
//                    out[currPos] = 0;
//                    arr = Arrays.copyOf(zeroArr, zeroC);
//                }
//            }

            System.out.println("Out so far: " + Arrays.toString(out));

            currPos++;
        }
        return out;
    }
}

/*
idx=0 // Start at LSB
init arr[] zeroC, oneC // not using a set because we can iterate inside this set
int out = 0
while(1)
    for int i = 0 to n // n or n+1 based on include/exclude
        int currBit = arr[i][j]
        if(currBit == 0)
            zeroC.add(currBit)
        else
            oneC.add(currBit)
    if(n % 2 == 0) // 1s = 0s
        arr[] = zeroC.len > oneC.len ? oneC : zeroC // Whichever is smaller has one missing, and needs to be pried further
        out = zeroC.len > oneC.len ? out + 1 << 1 : out << 0 // Can can set byte directly in out, also can use start and offset rather than arr copy
    else // 0s > 1s
        arr[] = oneC.len == zeroC.len ? zeroC : oneC // lens will be same if a 0 is missing
    if arr[].len == 1
        return arr[0] // or built out


 */


/*
Array is not sorted
We only have bit-wise access to element
So, brute force will take O(n * 32) along with a O(n) boolean array for tracking

Can we do this in O(n)?

Features/Adv of bitwise we can use?
Adding numbers from 1 to n gives: n/2(1 + n) = n/2 + n^2/2

Adding all the bits everywhere and subtract from the above gives the number, but bool array is better

The advantage is to be taken by limiting access to all digits?

How about something like a radix sort?
Access only the last bit of each number? Find if it's 0 or 1 | The one matching the missing will have lesser count
Take those and access last-1  bit

We'll need n * log n attempts to find the missing number this way

If n = 2048, this needs 2048 * 11
But O(32 * n) needs = 32 * 2048 (i.e., 3x)

So, radix-like approach helps - because most of the time we don't need to go upto the 32nd bit

Wait! So can we do the same with brute force, but stop accessing bits after log (n) + 1 digits are checked

So, it will need O(n) * log n again

I think we can't go lower than this because we need to access all numbers O(n), then access all digits O(log n)

The former is essential, but the latter can be minimized. However without sort or some order, it's impossible

Alg:
maxDigits = Math.ceil(log(n))
for(int i = 0; i < n; i++)
    for(int j = maxDig; j >= 0; j--)
        int currBit = arr[i][j]
        numSoFar = numSoFar * 2 + currBit
    boolMarker[numSoFar] = true

for int i = 0; i < n; i++
    if(!boolMarker[i])
        return i
return -1;

Had a sneak peek at the book solution, and apparently the book went towards radix sort-ish solution probably because
it's slightly more complex. I'll implement the same.

if n is odd e.g., 13 (Assuming it's 1-index based, so 0-13) | count = 14
0s - 7
1s - 7

n is even e.g., 8 (0-8) | count = 9
0s - 5
1s - 4

If count is odd, we'll have 1 more 0s in LSB
If count is even, we'll have same no of 1s and 0s

Alg:
idx=0 // Start at LSB
init arr[] zeroC, oneC // not using a set because we can iterate inside this set
int out = 0
while(1)
    for int i = 0 to n // n or n+1 based on include/exclude
        int currBit = arr[i][j]
        if(currBit == 0)
            zeroC.add(currBit)
        else
            oneC.add(currBit)
    if(n % 2 == 0) // 1s = 0s
        arr[] = zeroC.len > oneC.len ? oneC : zeroC // Whichever is smaller has one missing, and needs to be pried further
        out = zeroC.len > oneC.len ? out + 1 << 1 : out << 0 // Can can set byte directly in out, also can use start and offset rather than arr copy
    else // 0s > 1s
        arr[] = oneC.len == zeroC.len ? zeroC : oneC // lens will be same if a 0 is missing
    if arr[].len == 1
        return arr[0] // or built out

e.g., n = 8
0000 | 0001 | 0010 | 0011 | 0100 | 0101 | 0110 (missing) | 0111 | 10000
n=9, so 5 0s and 41s
    4 0's and 4 1's => zeroc is missing | arr = 0000 | 0010 | 0100 | 1000

 */