package n7rider.ch17.hard;

/**
 * Smallest K: Design an algorithm to find the smallest K numbers in an array.
 *
 * After finishing:
 * The logic is straightforward other than a generic heapify for bubble up and down.
 *
 * After comparing:
 * The book does suggest heap sort, but there's an even efficient algorithm - selection rank
 * which can work in O(n) time. It's like quick sort but only cares about the K numbers.
 * The worst case can be O(n^2) time, but the average is O(n).
 */
public class Solution17_14 {
}

/*
Simplest idea:
Sort the idea, print the first K numbers
Runtime: O(n log n) // Quick sort or merge sort

Let's start improving:
Create queues using 2 max heaps - each of size k/2
Both heaps store their max at top
Let it fill up for K elements
For K+1th ele,
    if ele < min-heap-max
        take top out if size is full
        insert ele at the bottom of min-heap and bubble up
        insert top at the bottom of max-heap and bubble up
    else
        if ele < max-heap-max
            insert at the bottom of max-heap and bubble up
        else
            skip

Runtime O(n log k) // Can be much cheaper if k << n

Can we memoize things here?
The max heap kinda caches entries already


 Can we just use one heap? Yeah, but it results in more time e.g., if k = 128, with two heaps, it is
 at the most 6 levels every time.
 We can use more but mgmt becomes tricky

 Heap {
  Node head
  Node min
  int size
  Node[] data
  int nextParentIdx // find parent by using 2^n table e.g., if n = 3, prev2Divisor = 2, diff = 1, parent is prev2Divisor + (diff/2)
 }

 Node {
 int val
 Node parent // can skip. we can calc from array
 Node left, right // can skip. we can cal from array
 }

 findKsmallest(arr, k)
    validations // arr NOT null or empty, k <= arr.size
    init min heap // size = 0, head = MAX_INT, nextParentIdx = -1
    init max heap
    for(a : arr)
       insert(a, minHeap, maxHeap)
    getHeaps() // print heap ele

 insert(int ele)
    if ele < min-heap-min
        top = insertHelper(minHeap, ele)
        if top != null
            insertHelper(maxHeap, leftOver)
        return

    if ele < max-heap-max
        insertHelper(maxHeap, ele)

 insertHelper(heap, ele)
    int toReturn = null
    if nextIdx >= size
        toReturn = top
        heapify
            // remove top, take last and bubble it down
    bubble up the new ele
    return toReturn











 */

