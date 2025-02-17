package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

public class Solution10_0_QuickSort_1 {

    // Function to partition the array
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];  // Choose the last element as pivot
        int i = (low - 1); // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] with arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // The main function that implements QuickSort
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the partition index
            int pi = partition(arr, low, high);

            // Recursively sort the elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Function to print the array
    private static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // Main function to test the quicksort implementation
    public static void main(String[] args) {
//        int[] arr = {10, 7, 8, 9, 1, 5};
        int[] arr = {1, 0, 2, 8, 5, 1, 3, 7, 6, 4 };
        System.out.println("Unsorted array: ");
        printArray(arr);

        quickSort(arr, 0, arr.length - 1);

        System.out.println("Sorted array: ");
        printArray(arr);
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
