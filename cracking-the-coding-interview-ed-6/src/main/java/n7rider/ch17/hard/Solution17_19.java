package n7rider.ch17.hard;

/**
 * Missing Two: You are given an array with all the numbers from 1 to N appearing exactly once,
 * except for one number that is missing. How can you find the missing number in O(N) time and
 * 0(1) space? What if there were two numbers missing?
 *
 * After finishing:
 * I don't think there is a non-math solution here.
 *
 * After comparing:
 * Same. The book calls it n * (n + 1) / 2. That's the more popular form.
 */
public class Solution17_19 {

}

/*
Simple solution:
Run through the list and Using a bit representation for each number? This will take O(n) bits and O(n) time

The time is fine, how about reducing space?

- Can we go over numbers few at a time while storing only limited bits at a time?
e.g., have 72 bits in total, use 64 for actual storage, use 8 bits each to indicate 1-8, 9-16th bits are all there
This can use  O(1) but how will be know which 8 bits are used for what values? (especially when one in the middle
is done).

How about we run through the array multiple times, each time processing only the nth set of a batch of items?

e.g., if batch size = 64, it'll take 8 runs to go through 512 items to find the missing item. But the runtime is
O(N log N) now.

The simplest way to run in O(1) space is to find each number - one at a time. But the runtime is O(N^2) then.

So, using the batch size seems to be the best bet.

Since decomposition of one item to one bit is too big, how about decomposing to something even smaller? Like a sum for
each bit or number?

We know in arithmetic progression, sum = n/2 (a + 1)

We add all numbers, and we subtract expected_sum - actual_sum to find the missing number.

That should work with O(1) space and O(n) time.

Exploring another case, how about radix sort. e.g., if we have numbers 0 to 99

It means we have in units digit, we need 10 9's in units digit, 10 8's and so on, then 9 9's in tens digit etc.
So calculate this and find the missing one?
e.g., if missing is 67. we'll have only 8 7's in units digit, 9 6's in tens digit.
For 0-99, we need digit * 10, so O(10 * log10 n) space. So AP solution is the best


 */