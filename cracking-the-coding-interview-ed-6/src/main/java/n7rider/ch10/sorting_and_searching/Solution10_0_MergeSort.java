package n7rider.ch10.sorting_and_searching;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Solution10_0_MergeSort {

    public static void main(String[] args) {

        int[] a = {9, 5, 2, 8, 0, 1, 3, 7, 6, 4 };
        System.out.println(Arrays.toString(mergeSort(a)));

        int[] b = {4, 6, 7, 3, 1, 0, 8, 2, 5, 9};
        System.out.println(Arrays.toString(mergeSort(b)));
    }

    public static int[] mergeSort(int[] a) {
        if(a == null || a.length == 0) {
            return new int[] {};
        }

        int[] c = new int[a.length];
        mergeSort(a, c, 0, a.length - 1);
        return c;
    }

    private static void mergeSort(int[] a, int[] c, int start, int end) {
        if(start >= end) {
            return;
        }

        int mid = (start + end) / 2;

        mergeSort(a, c, start, mid);
        mergeSort(a, c, mid + 1, end);

        int idxL = start;
        int idxR = mid + 1;
        int idxC = start;

        System.out.println("Start - " + start + ". End - " + end);

        while(idxC <= end && (idxL < mid + 1 || idxR <= end)) {
//            if(idxL >= mid + 1) {
//                c[idxC++] = a[idxR++];
//                continue;
//            }
//            if(idxR > end) {
//                c[idxC++] = a[idxL++];
//                continue;
//            }
//            if(a[idxL] < a[idxR]) {
//                c[idxC++] = a[idxL++];
//            } else {
//                c[idxC++] = a[idxR++];
//            }

            System.out.println("idxL: " + idxL + ". idxR : " + idxR + ". idxC : " + idxC);

            if(idxR <= end && (idxL >= mid + 1 || a[idxR] < a[idxL])) {
                c[idxC++] = a[idxR++];
                continue;
            }
            if(idxR > end || a[idxL] < a[idxR]) {
                c[idxC++] = a[idxL++];
                continue;
            }
        }

        for(int i = start; i <= end; i++) {
            a[i] = c[i];
        }

        System.out.println("Curr state: " + Arrays.toString(c));
    }


}

/*
mergeSort

a = 9 5 2 8 0 1 3 7 6 4
5 9 | 2 0 8
5 9 | 0 2 8

write merge results to copy array. Otherwise, you'll need to compare two no.s of the same sides. Complex logic.
copy results back to orig array after merge

Sort left half
Sort right half

For the whole, merge both halves

mergeSort(a, c, 0, l-1)

mergeSort(arr, copy, start, end)
  if(start >= end) // 1 ele array doesn't need sorting
    return
  mid = (start + end) / 2
  mergeSort(arr, copy, start, mid) . // Mid of 0, 1 is 0. Give 1 to the other half
  mergeSort(arr, copy, mid + 1, end)

  2, 5 9 && 0, 8

  idx1 = start
  idx2 = mid + 1
  idxC = start // goes till end

  while(idx1 < mid + 1 || idx2 <= end)
    // if 1 is above its bounds, just use ele from other
    if(idx1 >= mid + 1)
      c[idxC++] = a[idx2++]
      continue
    if(idx2 >= end)
      c[idxC++] = a[idx1++]
      continue

    if(a[idx1] < a[idx2])
      c[idxC++] = a[idx1++]
    else
      c[idxC++] = a[idx2++]

    for i = start to end
       a[i] = c[i]
 */
