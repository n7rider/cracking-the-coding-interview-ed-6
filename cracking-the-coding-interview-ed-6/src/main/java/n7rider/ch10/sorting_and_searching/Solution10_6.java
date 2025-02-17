package n7rider.ch10.sorting_and_searching;

/**
 * Sort Big File: Imagine you have a 20 GB file with one string per line. Explain how you would sort
 * the file.
 *
 * After finishing:
 * This is a routine shell sort. Not implementing
 *
 * On checking solution:
 * Similar to mine.
 *
 */
public class Solution10_6 {
}


/*
File is too big, so something that takes O(2n) space like merge sort is not preferable

In-place sort like shell sort, quick sort are better.

The number of lines can be very huge. If one line = 1 KB, 20 GB = 20 * 1000 * 1000 = 2,000,000

But with array and in-place sort, 20GB is manageable. For even larger content, we might want to divide
and conquer in batches

E.g., we use quick sort to sort things in batches of 1 GB (or whatever memory our computer can handle), then
use merge sort to merge them all which gets complex without a O(2n) space. If CPU memory limit = l, we need 1 / 20 GB
chunks out of each chunk, merge them all, but can't insert it rightaway. We need to push or move items from each chunk.

Therefore, a shell sort is the best for this use case.

 */