package n7rider.ch16.moderate;

/**
 * Living People: Given a list of people with their birth and death years, implement a method to
 * compute the year with the most number of people alive. You may assume that all people were born
 * between 1900 and 2000 (inclusive). If a person was alive during any portion of that year, they should
 * be included in that year's count. For example, Person (birth= 1908, death= 1909) is included in the
 * counts for both 1908 and 1909.
 *
 * After finishing:
 * No, that wouldn't work. It doesn't update new entries with info from existing entries e.g., 7 and 49 will get update for 44, 46
 * but 44 and 46 will not get updates of 7. You can just add everything under 7, but that won't be right. It could be 100 or 0.
 * I guess it's a kind of dynamic programming, and would like to see how the solution is reached.
 *
 * After comparing with solution:
 * Yeah, the solution adds +1 to every birth year's array idx, and -1 to every death year's array idx, and maintains a global
 * max value like I did.
 */
public class Solution16_10 {
    public static void main(String[] args) {
        int[][] peopleTimeline = new int[][] {{1908, 1909}, {1907, 1949}, {1944, 1946}, {1939, 1944}};
        System.out.println(PopAnalyics.findPopulousYear(peopleTimeline));
    }

    static class Node {
        int year;
        int count;
        Node left;
        Node right;

        Node(int year, int count) {
            this.year = year;
            this.count = count;
        }
    }

    static class PopAnalyics {

    static int maxYear = 0;
    static int maxCount = 0;
    static Node root;

    public static int findPopulousYear(int[][] peopleTimeline) {
        for(int[] person: peopleTimeline) {
            if(person == null || person.length != 2) {
                throw new IllegalArgumentException("Array length is not 2");
            }
            ingestPersonData(person);
        }
        return maxYear;
    }

    private static void ingestPersonData(int[] person) {
        upsert(root, person[0]);
        upsert(root, person[1]);
        update(root, person[0], person[1]);

    }

    private static void upsert(Node currNode, int year) {
        if(root == null) {
            root = new Node(year, 1);
            checkAndUpdateGlobal(year, 1);
        } else {
            upsertInternal(currNode, year);
        }
    }

    private static void upsertInternal(Node currNode, int year) {
        if(currNode.year == year) {
            currNode.count++;
            checkAndUpdateGlobal(year, currNode.count);
            return;
        }
        if(year < currNode.year) {
            if(currNode.left == null) {
                currNode.left = new Node(year, 1);
            } else {
                upsertInternal(currNode.left, year);
            }
        } else {
            if(currNode.right == null) {
                currNode.right = new Node(year, 1);
            } else {
                upsertInternal(currNode.right, year);
            }
        }
        }


    private static void checkAndUpdateGlobal(int year, int count) {
        if(count > maxCount) {
            maxYear = year;
            maxCount = count;
        }
    }

    private static void update(Node currNode, int birth, int death) {
        if(currNode == null) {
            return;
        }

        if(currNode.year > birth || currNode.year < death) {
            currNode.count++;
            checkAndUpdateGlobal(currNode.year, currNode.count);
        }

        update(currNode.left, birth, death);
        update(currNode.right, birth, death);
    }
    }



}

/*
Simplest solution:
Create a hashmap<int: year, int: pop>
For each person
    for each year
        add year:1 in hash map
        check and update global maxYear, maxPop if needed

1908-1909
1907-1949
1944-1946
1939-1944
Answer: 1944

19-29, 28-35, 23-29,
Answer: I can't make 28 or 29 the max without mentioning them
So, the biggest will always be mentioned?

0-50, 45-55
50 is the biggest, but all are just once
So, the biggest is one of the mentioned, but no. of mentions is not the factor, something else is.

Check and add if mentions are included?
0-50, 45-55, Update 50 when adding 45, 55. Will it work inverted though?
45-55, 0-50, Yeah, can update 45 and return that as the result. But do we find what prev ones are affected?
    Do we maintain a sorted list? Or use a tree and update everything in between? Can use a heap too.

So, brute force is O(n) - list of people. runtime O(n * x) - n - people, x - life expectancy
using bookends method - O(y) - no. of years with events. runtime O(n log y) - y - years with events 0-100

Other datatypes:
    map - no because, we can add 0, 50 but difficcult to find 45, have to comb through all values
    sorted set - we need to sort everytime
    skip list - will work, but with a limited dataset, we don't want to maintain and update various skip levels

Algorithm:
Class: year, count

Insert(birth, death):
    Upsert birth, death
        count += 1
        checkAndUpdateGlobal
    Update entries such that birth < entry < death
        count += 1
        checkAndUpdateGlobal


    Best not to merge these because upserts, while the next just updates.

Pseudocode:
class Node
    int year
    int count

insert(birth, death)
    upsert(tree, birth)
    upsert(tree, death)
    update(tree, birth, death)

upsert(tree, year)
    if tree.root == empty()
        root = new Node (year, count = 1)
        return

    if tree.root.year == year
        root.count++
        checkAndUpdateGlobal(root.count, year)

checkAndUpdateGlobal(count, year)
    if count > maxCount
        maxYear = year
        maxCount = count

update(tree, birth, death)
    updateInt(tree.root, birth, death)

updateInt(currNode, birth, death)
    if currNode is NULL
        return

    if currNode IN_BETWEEN birth, death
        currNode.count++

    updateInt(currNode.left, birth, death)
    updateInt(currNode.right, birth, death)

After implementation, I found the first example I have returns 1908, not 1944. So the other option is to maintain ranges
 */