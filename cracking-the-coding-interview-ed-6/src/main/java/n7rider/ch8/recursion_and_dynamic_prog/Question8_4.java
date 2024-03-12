package n7rider.ch8.recursion_and_dynamic_prog;

/**
 Power Set: Write a method to return all subsets of a set.

 After finishing algorithm:
 I vaguely remember we can just inject a new character to an existing set to get these combinations, but if
 this is all about recursions and being effective (memoize only when we can), this solution does the job.

 After comparing with solution:
 I knew injecting new char is a possibility. But just didn't explore it entirely. Should do it the next time.
 */
public class Question8_4 {
}

/*
 cat - cat | cta | tca | tac | atc | act

allSubset(char[] set)
  for i = 0 to set.length - 1 // length of subset
    // e.g., [0..12], i 5, j can go up to 12-5+1 = 8. So j can start at 8, grab 4 more to reach 12.
    for j = 0 to set.length - i + 1
      currSubset = subset(start at j, grab j + i more items) // If we flip i and j around we can cache here
         e.g., ca becomes cat becomes catc becomes catch
      allSubsetInternal(currSubset, currSubset.length - 1) // Simply create a new subset for each length
      allSubsetInternal(j, j+i-1 currSubset, i) // Use the same set but will specify limits

allSubsetInternal(int start, int end, set, int idx)
  if idx == 0
    print (start, end) // print from start to end
  return

  allSubsetInternal(start, end, set, idx-1)
  newSet = rotate(set, idx) // Move char at idx to start e.g., 1, 3, gator, 2 becomes gtaor
  allSubsetInternal(start, end, newSet, idx-1)


allSubsetInternal(char[] set, int idx):
  if idx == 0
    print set
    return

  allSubsetInternal(set, idx - 1)
  newSet = rotate(set, idx) // Move char at idx to first e.g., c a t, idx 2 becomes t c a.
  allSubsetInternal(newSet, idx - 1)



 */