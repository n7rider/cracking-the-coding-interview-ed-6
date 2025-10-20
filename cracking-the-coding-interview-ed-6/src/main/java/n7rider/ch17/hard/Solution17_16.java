package n7rider.ch17.hard;

/**
 * The Masseuse: A popular masseuse receives a sequence of back-to-back appointment requests
 * and is debating which ones to accept. She needs a 15-minute break between appointments and
 * therefore she cannot accept any adjacent requests. Given a sequence of back-to-back appointment
 * requests (all multiples of 15 minutes, none overlap, and none can be moved), find the optimal
 * (highest total booked minutes) set the masseuse can honor. Return the number of minutes.
 * EXAMPLE
 * Input: {30, 15, 60, 75, 45, 15, 15, 45}
 * Output:180 minutes ({30, 60, 45, 45}).
 *
 * After finishing:
 * Runs in O(n). It goes from first to last, and no obvious memoization, but I carry the sum forward
 * and don't repeat any calculations.
 *
 * After comparing with the solution:
 * A good way to try all combinations is to call method(n + 1) and also curr + method(n + 2), and then
 * find the maximum amongst these. Using 0000 to 1111 works, but this is more intuitive.
 *
 * The book says it tries to go from backwards to think about a recursive + memoization solution and reverse the logic.
 * I don't see this making a big difference for this problem.
 *
 * I was dividing between carrying the index, and number. Choosing to carry just one will simplify the logic.
 */
public class Solution17_16 {
}
/*
Simple solution: Try all combinations from 0000 0000 to 1111 1111
But two adjacent ones can't be selected, so max = 1010 1010

if n = 8, we'll have 2^8 = 256 combinations.
So runtime is O(2^n).

Algorithm:
int c = 0, temp = 0, sum = 0
temp = c
while c < n
  for i = 0 to n-1
    if(temp & 1 == 0)
        add to curr set
        i++ // if one is added, next should be ignored
    else
        ignore
    temp = temp >> 1
  if curr-set.sum > max
     save curr-set
  c = c + 1
  temp = c

Simplify runtime?
Can we keep finding local max and keep expanding its scope as we add new numbres?
We can because even if the next booking has more mins, we only remove the previous ones i.e., it means you
remove just the previous one, no need to collapse the entire set so far

e.g., 30, 15, 60 -> choose 30 and 60
30, 15, 60, 75 -> choose 30 and 75
30, 15, 60, 75, 45 -> 30, 60, 45 > 30 + 75

Consider adding 45 again, we need to consider newNo (45), currLast (75), prevLast (60)

Algorithm:
init prev = -2, lastDislodged = 0, sum = 0, lastInSet = 0, lastInSetIdx = -1, lastDislodgedIdx = -1
30 ->
    sum = sum + currNo
    lastInSet = currNo
    lastInSetIdx = currIdx

30, 15 ->
    if currIdx - lastInSetIdx == 1
        // Next num. Add only if it's bigger than prev
        if sum - currLast > sum + curr
            ignore // too little ignore
        else
            sum = sum - lastInSet + currNo
            lastDislodged = lastInSet // update set if needed, lastDislodgedIdx = lastInSetIdx
            lastInSet = currNo
            lastInSet = currIdx
    else
        // For scenarios like 30, 15, _60_
        sum = sum + curr
        lastInSet = currNo
        lastInSetIdx = currIdx

return sum

Runtime:
This wil take O(n) time, we do around 3-4 assignments everytime.


Can this be run from backwards?
    The performance will be the same. If you choose one, you skip the other, keep going backwards with the same logic.

Memoization?
    Not much since we already use the sum, and not the individual numbers.
    








 */