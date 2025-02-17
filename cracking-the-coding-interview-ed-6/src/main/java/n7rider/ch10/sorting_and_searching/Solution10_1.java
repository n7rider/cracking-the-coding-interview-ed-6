package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

/**
 * Sorted Merge: You are given two sorted arrays, A and B, where A has a large enough buffer at the
 * end to hold B. Write a method to merge B into A in sorted order.
 *
 * After finishing:
 * I figured we don't need a merge sort, and we need some kind of insertion sort.
 *
 * After comparing:
 * Doing insertion sort backwards has no shifting since A is big enough to hold both array's elements.
 * The logic is much simpler too.
 */
public class Solution10_1 {

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9, 0, 0, 0, 0};
        int[] b = {2, 4, 6, 8};

        System.out.println(Arrays.toString(mergeAtoB(a, b)));
    }

    static int[] mergeAtoB(int[] a, int[] b) {

        // Skip validations: a.len >= a.ele.size + b.size
        int idxA = 0;
        int idxB = 0;

        int elementsInA = a.length - b.length;
        int counterForEleInA = 0;
        while (idxA < a.length && idxB < b.length && counterForEleInA < elementsInA) {
            if (a[idxA] <= b[idxB]) {
                idxA++;
                counterForEleInA++;
            } else {
                insertionSort(idxA, a, b[idxB]);
                idxA++;
                idxB++;
            }
        }

        return a;
    }

    private static void insertionSort(int idxA, int[] a, int nextEle) {
        int temp = 0;

        // Can add other short-circuit conditions to skip null elements if provided e.g., a[idxA] != 0
        while (idxA < a.length) {
            temp = a[idxA];
            a[idxA] = nextEle;
            nextEle = temp;

            idxA++;
        }
    }
}

/*
We need merge sort of A into B

A 1 3 5 7 9
B 2 4 6 8

Large enough buffer - Looks like the data can be large, so best to do it in-place rather than creating
a new array and do the merge required for merge sort.

Have pointers in both arrays
If B has a smaller element
  Do insertion sort
  Do increments
Else
  Do increments

// Assuming len[A] = exactly A.ele.size + B.size
idxA = 0
idxB = 0
while(idxA < idxA.length && idxB < idxB.length)
  if(a[idxA] <= b[idxB])
    idxA++;
  else
    insertionSort(idxA, A, B[idxB]);
    idxA++;
    idxB++;

insertionSort(idxA, idxB, toInsertLater)
  int temp = 0;

  while(idxA < A.length) //Can check for 0s or nulls or special values indicating end of array too.
    temp = A[idxA];
    A[idxA] = toInsertLater;
    toInsertLater = temp;
    idxA++;




 */
