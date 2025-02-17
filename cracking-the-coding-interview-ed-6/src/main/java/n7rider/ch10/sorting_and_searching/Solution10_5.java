package n7rider.ch10.sorting_and_searching;

/**
 * Sparse Search: Given a sorted array of strings that is interspersed with empty strings, write a
 * method to find the location of a given string.
 * EXAMPLE
 * Input: ball, {"at", "" , "" , "" , "ball", "" , "" , "car", "" , "" , "dad", "", ""}
 * <p>
 * After finishing:
 * Tried to throw in a hashmap to reduce future look ups, but it brings you back to the same place
 * where you started, so this won't be helpful.
 *
 * After comparing:
 * Same as my solution.
 */
public class Solution10_5 {
    public static void main(String[] args) {
        String[] arr = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        System.out.println(sparseSearch(arr, "ball"));
        System.out.println(sparseSearch(arr, "balla"));
        System.out.println(sparseSearch(arr, "dad"));
        System.out.println(sparseSearch(arr, "at"));


    }

    public static int sparseSearch(String[] arr, String x) {
        // Skip null validations
        return binarysearch(arr, x, 0, arr.length - 1);
    }

    private static int binarysearch(String[] arr, String x, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;
        int leftC = mid;
        int rightC = mid;
        int nonEmptyMid = -1;
        if (arr[mid] != "") {
            nonEmptyMid = mid;
        } else {
            while (leftC >= left || rightC <= right) {
                if (leftC >= left && arr[leftC] != "") {
                    nonEmptyMid = leftC;
                    break;
                }
                if (rightC <= right && arr[rightC] != "") {
                    nonEmptyMid = rightC;
                    break;
                }
                leftC--;
                rightC++;
            }
        }

        if (nonEmptyMid == -1) {
            return -1;
        }
        if (x.equals(arr[nonEmptyMid])) {
            return nonEmptyMid;
        }
        for (int i = Math.max(leftC, left); i < Math.min(rightC, right); i++) {
            System.out.printf("Cache added: key = %d | value = %d \n", i, nonEmptyMid);
        }
        if (x.compareTo(arr[nonEmptyMid]) < 0) {
            return binarysearch(arr, x, left, nonEmptyMid - 1);
        } else {
            return binarysearch(arr, x, nonEmptyMid + 1, right);
        }
    }
}

/*
Binary search works but if data is sparse, even O(log(n)) can be a lot.

Approach 1:
We can do ternary search with left, mid1, mid2, right and branch somewhere.
Still O(log(n))

Approach 2:
Run through array at create a hashmap<int, int> storing next element (Like using LL but with random access)
or even a new trimmed array if memory is not an issue but speed is paramount.
Can also create hashmap<int, string> and run binary search on the key

GLANCED the book. The problem is if mid is empty, we'll not know which direction to go.
So approach 1 won't work.
We can go in either direction until we find a word but the map<int, int> can make it safer.
it mid is empty, we simply look up the map to find the next ele.

We can fill up the hashmap as we go too. Thus run time will stay O(log n) but can go to O(n) on worst case.

binarySearch(arr, x, 0, n-1)

HashMap<int, Pair<L,R>> linksMap; // Pair<L, R> - next non-empty idx on left side, right side
binarySearch(arr, x, left, right):
  if left > right
    return = -1
  newMid = -1 // default value for a mid position that's not blank
  mid = mid(left, right)
  if mid != ""
    newMid = mid
  else
    rightInc = mid
    leftInc = mid
    while leftInc > = left && rightInc <= right
      if(arr[leftInc] != "")
        newMid = leftInc
        break
      if(arr[rightInc] != "")
        newMid = rightInc
        break
      leftInc--
      rightInc++

    if newMid == -1
      return -1 // Item not found

    if arr[newMid] == x  // assumes x != ""
      return mid
    // Not found yet, populate hash map
    sideFound = arr[leftInc] != "" ? LEFT : RIGHT
    populateHashMap(mid, sideFound, leftInc, rightInc)

    if x < newMid
      binarySearch(arr, x, left, newMid - 1)
    else
      binarySearch(arr, x, newMid + 1, right)

populateHashMap(mid, sideFound, leftInc, rightInc)
    if side == LEFT // i.e., non-empty value is on left side
      value = getValue().set (or) new Pair<Left = leftInc, Right= null>
      for j = mid  to leftInc - 1 (step - 1)
        linksMap.put(j, value)
    .. similar for right

After writing code, I realize that the closest number is enough for HashMap and no need for both left and right
Because whichever side we go, the mid will be different now, and we'll be closer to the target anyway
e.g., In 20...60, mid = 40, let's say newMid=42 even though req. value is 24. After newMid=42, we'll take range 20..42,
so the mid is 31, so we inevitably get closer to the target.

 */