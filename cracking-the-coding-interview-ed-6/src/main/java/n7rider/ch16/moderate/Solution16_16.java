package n7rider.ch16.moderate;

/**
 * Sub Sort: Given an array of integers, write a method to find indices m and n such that if you sorted
 * elements m through n, the entire array would be sorted. Minimize n - m (that is, find the smallest
 * such sequence).
 * EXAMPLE
 * lnput:1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19
 * Output: (3, 9)
 * Hints: #482, #553, #
 *
 * After finishing:
 * I do it in 3 steps - find where the sequence is broken in both sides, find the min and max item in the middle,
 * then find their right places using insertion sort
 *
 * After comparing:
 * The book does the same - find where the sequence is broken, find the min and max item's indices (but uses a single `for`
 * loop to do them together), then fit their places using insertion sort (it is called shrinking the middle though)
 * However, the book starts insertion sort from right-pool-start for finding m's position. For 'n', it starts from
 * right-pool-start-1, because left and right are already sorted among themselves and with respect to each other (but it won't
 * work if the seq. is 97, 98, 99,  ,....., 1, 2, 3.
 */
public class Solution16_16 {
    public static void main(String[] args) {
        // Can implement equals for assertEquals check
        System.out.println(findMinimalSortIndices(new int[] {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19}));

        System.out.println(findMinimalSortIndices(new int[] {99, 98, 10, 1, 2, 100}));

    }

    static class MinimalSortIndices {
        int m;
        int n;

        @Override
        public String toString() {
            return String.format("m = %d | n = %d \n", m, n);
        }
    }

    static MinimalSortIndices findMinimalSortIndices(int[] a) {
        MinimalSortIndices out = new MinimalSortIndices();
        int i = 0;
        for(; i < a.length - 1; i++) {
            if(a[i + 1] < (a[i])) {
                break;
            }
        }

        int smallIdx = i + 1;
        for(i = i + 2; i < a.length; i++) {
            if(a[i] < a[smallIdx]) {
                smallIdx = i;
            }
        }
        System.out.println("m's original index is " + smallIdx);

        for(i = 0; i < smallIdx; i++) {
            if(a[i] > a[smallIdx]) {
                out.m = i;
                break;
            }
        }
        System.out.println("m is " + smallIdx);

        // Finding n
        for(i = a.length - 1; i >= 1; i--) {
            if(a[i - 1] > (a[i])) {
                break;
            }
        }

        int largeIdx = i - 1;
        for(i = i - 2; i >= 0; i--) {
            if(a[i] > a[largeIdx]) {
                largeIdx = i;
            }
        }
        System.out.println("n's original index is " + largeIdx);

        for(i = a.length - 1; i >= 0; i--) {
            if(a[i] < a[largeIdx]) {
                out.n = i;
                break;
            }
        }
        System.out.println("n is " + largeIdx);
        return out;

    }
}

/*
The structure of the array is
[a - m - n - l]
length of a-m, m-n, n-l - each can be 0+

Easiest solution:
Start from beginning - Stop when asc order is broken
Start from end - Stop when desc order is broken
Are these the indices of m-n?

In given input, yes
Are there other possibilities?

How about 10, 12, 14, 1, 2, 98, 99?
Same.

99, 98, 10, 1, 2, 100?
Expected answer - 99-2
Above alg - 98-10

Reason: Can't assume the seq is always [smallest-sorted][mid-jumbled][high-sorted]

Assuming I can do either ascending or descending?

Look how to find the seq that made the prev solution work?

Alg:
Find smallest (idx = 3)
Find largest (idx = 5)
n = max(small, large)
m = min(small, large)
Ans: 1-100
No, small and large don't really help

How about finding the place of inversion? No, that's the same as the easiest soln


How about finding the smallest after an inversion?
e.g., in given, the smallest after an inversion is 6 (at idx 8)
Do insertion sort until we put it in place.
It will stop at idx 3

From the end, find the biggest after an inversion. It is 12 (at idx 7)
Do insertion sort until we put it in place
It will stop at idx 9

So, 3-9 is got!

With my inputs:
Smallest after an inversion is 1 (Need to go all the way to the end, not stop at the first inversion)
Largest after an inversion is 99
Keep pushing it to the left, and we get the right answer
So that's the approach

Complexity is O(2n + 2n)

Can we do better?
Considering the starting point is somewhere in the deep end from left, we need to start from beginning and go n points
We can find the largest meanwhile, but can we keep checking if that's after an inversion?

Alg:
for 0 to len-2
    Go until a[i] < a[i+1]
    int smIdx = i + 1
    break

for smIdx + 1 to len - 1
        smIdx = math.min (smIdx, a[i]

for len-1 to 1
    Go until a[i] > a[i-1]
    int lgIdx = i - 1
    break

for lgIdx -1 1 to 0
        lgIdx = math.max (lgIdx, a[i])

To simplify:
We can try to merge for1 and for3 together
for 1 - remains the same
for 3 -
    lgIdx = 0 // init
    then lgIdx = i if (a[i+1] < a[i])) // This is simple, but will be inefficient if m is to the left, and n is to the very right
    So sticking to these four for loops

You can find the first inflection of the biggest from left, first inflection of the smallest from right but that won't work
because if the former is very small, it goes to the very beginning, and can't be just swapped with the latter.

 */