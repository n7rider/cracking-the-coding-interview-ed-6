package n7rider.ch4.trees_graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * 4.9
 * BST Sequences: A binary search tree was created by traversing through an array from left to right
 * and inserting each element. Given a binary search tree with distinct elements, print all possible
 * arrays that could have led to this tree.
 * Example input: 1 <-- 2 --> 3
 * Example output: {2, 1, 3}, {2, 3, 1}
 *
 * ---
 * Post-run observations:
 * Array is the perfect candidate for these operations, not string or immutable types
 * On the other hand if you don't recursion to mutate original state e.g., path in DFS, use strings.
 * Took me 3+ hours to solve this though.
 *
 * After comparing with solution:
 * The solution uses a concept called weaving for permutations. This takes two lists and recursively pushes one list's head to a third
 * one at the end of both. I'll note this down, but will stick to my method.
 * Performance-wise, both will be O(n!), but mine uses no additional data structure.
 */
public class Question4_9 {


/**
 * Given ex: 2 levels = 2 possibilities (both on level 1)
 *      13
 *   2     24
 * 1  3  22    35
 *
 * Level 0 - 2^0 = 1
 * Level 1 - 2^1 = 2
 * Level 2 - 2^2 = 4. 2 + 4 = 6. Not dependent on levels, but dependent on count
 *
 * {13, 2, 24, 1, 3, 22, 35}
 * {13, 24, 2, 1, 3, 22, 35}
 * 1 2 3 4 - Already done
 * 2 3 4 1
 * 3 4 1 2
 * 4 1 2 3
 * and so on
 *
 * 1 2 and 2 1. Count = 2 | length = 2
 * 1 2 3. Count = 6 i.e., 3 * 2. So 3!
 * n = 4, 4! = 24
 *
 * cat
 *
 * cat > cta > cat
 * Rotate >> atc
 * atc > act > atc
 *
 * 13 2 24 1 3 22 35
 * 13 2 24 1 3 35 22
 * 13 2 24 1 3 22 35 ---// Rest
 *
 * permutate(string)
 *  permutateUtil(string, string.length)
 *
 * permutateUtil(string, index):
 *  while(index > 1)
 *    for i=0 i<index i++
 *      permutateUtil(string, index-1)
 *
 *  if(index == 1)
 *      print string
 *
 *  push char at index to string right
 *
 * OUTPUT:
 * First call:
 * cat(cat, 3)
 *  cat(cat, 2) |
 *      cat(cat, 1) | print cat | cta
 *      cat(cta, 1) | print cta | cat
 *
 *      Rotate to atc
 *  cat(cat, 2) |
 *      cat(cat, 1) | print cat | cta
 *      cat(cta, 1) | print cta | cat
 *  cat(cat, 2) |
 *
 * We went from string.length to index == 1
 * Change: limits passed in arg
 *
 * We passed string.length once
 * Change: Run a GP to find limits each time
 *
 * arrayPerm(array)
 *   limits[] = calcPossibleRightPos(array)
 *   arrayPermUtil(array, array.length)
 *
 * arrayPermUtil(array, index):
 *   int rightPos = findRightPos(index)
 *   while(index > 1):
 *     for i = index ; i <= rightPos; i++
 *       arrayPermUtil(array, index-1)
 *   if(index == 1)
 *     print array
 *
 *   push char at index to right-pos
 *
 * calcPossibleRightPos(array):
 *   int[] limits = int[math.ceil(log base 2 of array)]; // 0, 2, 6
 *   int c = 0
 *   while(1)
 *     if(i < array.length)
 *       limits[c++] = i-1;
 *     else
 *       limits[c++] = array.length - 1;
 *     i = i + i *2;
 *
 * findRightPos(index)
 *   int c  = 0;
 *   while(limits[c] < index)
 *     c++
 *   return c
 *
 */

static class TreeAsArray {

    int[] array;
    List<Integer> limits;
    int printCounter;

    TreeAsArray(int[] array) {
        this.array = array;
    }

    private void printArrayPermUtil(int[] array, int index) {
        int stopIndex = findStopIndex(index);
        if(index < array.length - 1) { // Go all the way to the end of array, else you'll not get all combinations
            for(int i = index; i <= stopIndex; i++) { // Iterate only until stopIndex, Use <= and not <. This ensures you trigger elements beyond stop index too
                printArrayPermUtil(array, index + 1);

                if(index < stopIndex) { // Limit flipping chars within stopIndex
                    int temp = array[index];
                    for (int j = index; j < stopIndex; j++) {
                        array[j] = array[j + 1];
                    }
                    array[stopIndex] = temp;
                }
            }
        } else {
            printArray(array, index, stopIndex);
        }
    }

    void printArray(int[] array, int index, int stopIndex) {
        printCounter++; 
        System.out.print(String.format("%d. Index %d stopIndex %d | {", printCounter, index, stopIndex));
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("}");
    }

    void printArrayPerm() {
        this.limits = calculatePossibleRightPositions(array);
        printArrayPermUtil(array, 1);
    }

    List<Integer> calculatePossibleRightPositions(int[] array) {
        List<Integer> limits = new ArrayList<>();
        int i = 1;
        while(true) {
            if(i < array.length) {
                limits.add(i - 1);
            } else {
                limits.add(array.length - 1);
                break;
            }
            i = i + (i * 2);
        }
//        System.out.println("Limits found are: " + limits);
        return limits;
    }

    int findStopIndex(int index) {
       int c = 0;
       while(limits.get(c) < index) {
//           System.out.println(String.format("Passing limit %d for index %d", limits.get(c), index));
           c++;
       }
//       System.out.println(String.format("Right position for %d is: %d", index, limits.get(c)));
       return limits.get(c);
    }
}

public static void main(String[] args) {
    int[] array = { 13, 2, 24, 1, 3, 22, 35 };
//    int[] array = { 2, 1, 3 };
//    int[] array = { 13, 2, 24, 1, 3 };

    TreeAsArray tree = new TreeAsArray(array);
    tree.printArrayPerm();
}
}

/**
 * 1, 2 3, 4, 5
 *
 * m(arr, 0):
 *
 *
 */