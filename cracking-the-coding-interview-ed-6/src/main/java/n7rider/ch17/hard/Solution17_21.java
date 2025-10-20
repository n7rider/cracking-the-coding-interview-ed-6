package n7rider.ch17.hard;

/**
 * Volume of Histogram: Imagine a histogram (bar graph). Design an algorithm to compute the
 * volume of water it could hold if someone poured water across the top. You can assume that each
 * histogram bar has width 1.
 * EXAMPLE (Black bars are the histogram. Gray is water.)
 * Input:{0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0}
 * 0 0 4 0 0 6 0 0 3 0 5 0 1 0 0 0
 * Output: 26
 *
 * After solution:
 * Started with a simple solution, and that looks like the best solution.
 *
 * After comparing with the book solution:
 * My algorithm doesn't cover the scenario when everything after current bar is smaller. You need to find the biggest on
 * the right side and then use that to fill up everything between it and the current bar. This logic can actually cover
 * all scenarios, so the book essentially finds the max so far from left side and right side. Then, it starts from the highest
 * in the entire array, and goes (next-max-on-left-side TO this) and (this TO next-max-on-right-side), and calculates the volume,
 * and keeps repeat the method on left-max and right-max until the end is reached on both sides.
 */
public class Solution17_21 {
}

/*
Simplest solution:
From 0 to 4
    Skip till first entry is reached
From 4 to 6
    Keep counting, then do vol = count * smaller number (We get 8)
From 6 to 3, and 5
    Keep counting
    When 3 is reached, it is smaller than 6 so push to stack and keep going
    Keep counting
    When 5 is reached, it is bigger than prev i.e., 3 so stop. vol = count * smaller number (We get 4 * 5 = 20)
    Subtract each number from the stack (20-3 = 17)
From 5 to 1
    Keep counting
    When 1 is reached, it is smaller than 5, so keep going
    EOL
    So, vol = count * smaller number (We get 1 * 1 = 1)

Total 8 + 17 + 1 = 26

In algorithm:

// startIdx indicates where the current segment started.
// It can be 0. If graph starts at 4, it's fine. If it starts at 0, -1,0 can be considered to end there.
// and a new one starts at 0.
int startIdx = 0, prevIdx = 0
list temp
for int i = 0 to arr.len - 1
    if arr[i] != 0
        if arr[i] >= arr[startIdx]
            calcVol(startIdx, i, temp)
            clear temp
        else
            add i to temp
        prevIdx = i

calcVol(startIdx, currIdx)
    int smaller = arr[prev] < arr[currIdx] ? arr[prev] : arr[currIdx];
    vol = smaller * (currIdx - prev)
    for j = 0 to temp.len - 1
        vol = vol - arr[temp[j]]
    return j

Runtime:
O(n) iteration, additional storage of O(n) for temp (but will be half by average)

Alternate solutions:
We can consider a rectangle of the entire area and then remove blocks where water can't stay.
But this will require going back and forth in the histogram and won't be efficient

 */