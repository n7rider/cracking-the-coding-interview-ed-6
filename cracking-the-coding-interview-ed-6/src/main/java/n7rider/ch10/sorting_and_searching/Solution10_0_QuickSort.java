package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

public class Solution10_0_QuickSort {

    public static void main(String[] args) {

//        int[] a = {9, 5, 2, 8, 0, 1, 3, 7, 6, 4 };
        int[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a) {
        // Skip null check
        quickSortRec(a, 0, a.length - 1);
    }

    private static void quickSortRec(int[] a, int left, int right) {
        System.out.println(String.format("left : %d, right :% d", left, right));
        if(left < right) {
            int pivot = partition(a, left, right);

            quickSortRec(a, left, pivot - 1);
            quickSortRec(a, pivot, right);
        }
    }

    private static int partition(int[] a, int left, int right) {
        int pivotVal = a[right];
        System.out.println("Init pivot val: " + pivotVal);

        // This is the partition index i.e., all elements smaller than pivot are to the left of this.
        // It starts at 0, but otherwise stays at an element bigger than pivot. Then, when a smaller element is found, we swap
        int partitionIdx = left;
        for(int i = left; i < right; i++) {

            // If curr ele is smaller, swap with partitionIdx
            if(a[i] < pivotVal) {

                // Every time after a swap, i = partitionIdx
                // So if a smaller number is encountered, check before needlessly swapping
                if(a[i] != a[partitionIdx]) {
                    System.out.println("Before swap: " + Arrays.toString(a));
                    int temp = a[i];
                    a[i] = a[partitionIdx];
                    a[partitionIdx] = temp;
                    System.out.println("After swap: " + Arrays.toString(a));
                }

                partitionIdx++;
            }
        }

        // Swap pivot and partitionIdx. If all numbers are smaller than pivot, both will be at the same index.
        // Else, if all numbers are larger than pivot, partitionIdx will be stuck somewhere. We'll swap pivot there, and
        // call that as the partition.
        int temp = a[partitionIdx];
        a[partitionIdx] = a[right];
        a[right] = temp;

        System.out.println("Pivot val found: " + pivotVal);
        System.out.println("After setting pivot: " + Arrays.toString(a) + "\n");

        return partitionIdx;
    }






}

/*
quickSort

a = 9 3 7 2 4 8 2 1 5 0


init:
  int mid = (start + end)/2;
  quicksort(a, start, mid)
  quicksort(a, mid + 1, end)

quicksort(int[] a, start, end)
  if(start >= end)
    return

  int idxL = start;
  int idxR = end;
  int pivot = mid;
  while(idxL <= idxR)
    while(a[idxL] < a[pivot]) // <= will reduce swaps, but < acts as a boundary condition a[pivot] < a[pivot] = false
      idxL++;
    while(a[idxR] > a[pivot])
      idxR++;

    if(left <= right)
      swap a[idxL] a[idxR]
      left++
      right--




 */
