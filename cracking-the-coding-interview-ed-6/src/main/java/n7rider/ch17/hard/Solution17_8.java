package n7rider.ch17.hard;

import java.util.List;

/**
 * Circus Tower: A circus is designing a tower routine consisting of people standing atop one another's
 * shoulders. For practical and aesthetic reasons, each person must be both shorter and lighter
 * than the person below him or her. Given the heights and weights of each person in the circus, write
 * a method to compute the largest possible number of people in such a tower.
 * EXAMPLE
 * Input (ht,wt): (65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)
 * Output: The longest tower is length 6 and includes from top to bottom:
 * (56, 90) (60,95) (65,100) (68,110) (70,150) (75,190)
 *
 * After finishing:
 * This runs in O(2^n) time, probably there's a trick like dynamic prog but the unknown-nature of whether adding the
 * current will result in max or not makes it difficult
 *
 * After comparing:
 * The book uses dynamic prog indeed. The trick is to find the max count up to this point while the result always includes the current
 * e.g., when i = 2 in 13, 14, 10, the answer is 10 for helper(idx=2). Now, we compare it with the max for helper(idx=1) and
 * check if ending with curr yields a higher count. Why would that work?
 * Look at i=2. If using 0, 1 is better than using just 2, (0, 1) would have already been the max when doing this for i=1
 * This works because at each idx, we try to add the curr idx to the solution no matter what, and finding if it results in a higher count
 * or not.
 * How can we know adding this won't affect future choices? e.g., in my example below the right answer is to ignore 70,90 | 80,120 and also 100|130.
 * Consider there are 10 counts of 70,90 but there are even more counts of 80,80 i.e., 20 there. 70,90 results in 11 at one point, but when we always
 * try to include the curr idx, we end up always considering the possibility of the branch that includes this. The branch that doesn't include this
 * will be considered if a future element is in-between.
 * That's the key - my solution branches out when things are unknown. This solution always considers current, but current is skipped when a future
 * run deems it to be unfit for a sequence including it. So, we do the same, but we just enable caching of the max thus far.
 * Thinking of solving the problem for up to n items is the key to memoization.
 */
public class Solution17_8 {

    public static void main(String[] args) {
        List<Attr> attrList = List.of(new Attr(50, 50), new Attr(70, 90), new Attr(80, 80),
                new Attr(80, 120), new Attr(100, 100), new Attr(100, 130),
                new Attr(110, 120), new Attr(150, 150) );
        System.out.println(findLargestTowerLength(attrList));
    }

    public static int findLargestTowerLength(List<Attr> attrList) {
        return helper(0, attrList, 0, new Attr(0, 0)); // currPath is just a tracker, and can be removed
    }

    // Use prevAttr(0,0) for fast comparison. We can have a getter but that will result in an extra 'if' check everytime to avoid OutOfBounds at 0
    private static int helper(int currCount, List<Attr> attrList, int currIdx, Attr prevAttr) {
        if(currIdx == attrList.size()) {
            return currCount;
        }

        var currAttr = attrList.get(currIdx);
        if(currAttr.height > prevAttr.height && currAttr.weight > prevAttr.weight) {
            return helper(currCount + 1, attrList, currIdx + 1, currAttr);
        } else {
            // Skip this one. It has a smaller weight
            int length1 = helper(currCount, attrList, currIdx + 1, prevAttr);
            // Skip prev one. Probably, it's way too big to be in the middle
            int length2 = helper(currCount, attrList, currIdx + 1, currAttr);
            return Math.max(length1, length2);
        }
    }

    static class Attr implements Comparable<Attr> {
        int height;
        int weight;

        Attr(int height, int weight) {
            this.height = height;
            this.weight = weight;
        }

        @Override
        public int compareTo(Attr attr2) {
            if(height == attr2.height && weight == attr2.weight) {
                return 0;
            }
            if(height < attr2.height) {
                return -1;
            } else {
                return 1;
            }
        }

    }


}

/*
Simplest way:
Sort by ht and then wt - O(n log n)
    e.g., 50,50 | 70,90 | 80,80 |

How to find the optimum combination? e.g., If all are 80,80 after the above 3, skipping 70,90 will give the most number

Possible strategies:
Try skipping 1...n
    Considering possibilities n for choosing 1, n-1 for 2 etc., we end up with n * n combinations
How about trying to find inflection points? i.e., points where one attribute is bigger, but one is smaller
    e..g, in the above, it is 0, 1, 0, 0, 0, 0, 0
    In this case, it is just profitable to drop these.
    How about a more complex example? e.g., 50,50 | 70,90 | 80,80 | 80,120 | 100,100 | 100,100 | 100,100 | 100, 100
    In this case, skipping 70,90 would actually backfire. Inflection looks like 0, 1, 0, 0, 1, 0, 0, 0
    This doesn't make sense. The inflection point is just in respect to each other e.g., both 70,90 and 80,80 can precede 100,100 but the inflection
        doesn't say that.
    So, everytime we find an inflection. We can figure that there are possibilities opening up and we can investigate.

    So, we can be recursive but branch off for inflection.

    Example:
    if ht-next > ht-curr && wt-next > wt-curr
        rec-method(count + 1, idx + 1) // just go to next one
    else
        rec-method(count + 1, idx + 1) // next one
        rec-method(count + 1, idx + 1) // skip next one

    With n branches and splitting into 2 at each branch, 2 branches times n/2 = O(2^n)

Is there a better way?
    Going backwards provides the same runtime




 */