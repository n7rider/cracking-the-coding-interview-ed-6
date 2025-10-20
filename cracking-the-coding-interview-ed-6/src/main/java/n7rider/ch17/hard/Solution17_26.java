package n7rider.ch17.hard;

/**
 * Sparse Similarity: The similarity of two documents (each with distinct words) is defined to be the
 * size of the intersection divided by the size of the union. For example, if the documents consist of
 * integers, the similarity of { 1, 5, 3} and { 1, 7, 2, 3} is 0. 4, because the intersection has size
 * 2 and the union has size 5.
 * 190
 * We have a long list of documents (with distinct values and each with an associated ID) where the
 * similarity is believed to be "sparse:'That is, any two arbitrarily selected documents are very likely to
 * have similarity 0. Design an algorithm that returns a list of pairs of document IDs and the associated
 * similarity.
 * Print only the pairs with similarity greater than 0. Empty documents should not be printed at all. For
 * simplicity, you may assume each document is represented as an array of distinct integers.
 *
 * After finishing:
 * This was not that complex, so probably there's room for more optimization. The runtime is O(n^2 * k).
 *
 * After comparing:
 * The solution goes one step after what I've done. Rather than repeating testing all documents from the hashmap list,
 * it takes each combination and increment a new hashmap of <pairs, count>. This saves O(n) for every hit in pairs. The runtime
 * is O(nk + pk) now where 'p' is the number of pairs in the hashmap list.
 */
public class Solution17_26 {
}

/*
Similarity = ratio of intersection / union

Similarity is most likely 0 i.e., union >> intersection i.e., very few common elements

>Design an algorithm that returns a list of pairs of document IDs and the associated similarity.
I'm guessing this returns a list of all combinations in the list

Req: Print only the pairs with similarity > 0

Brute-force solution:
List<PairInfo> out
for i = 0 to in.size - 2
   for j = i + 1 to in.size - 1
      doc {smaller, bigger} = in[i].size < in[j].size == {in[i], in[j]} : {in[j], in[i]};
      Set<int> currContents.addAll(bigger)
      bool intersection = 0
      for k = 0 to smaller.size - 1
        intersection = intersection + currContents.add(smaller[k]) ? 0 : 1;
      if intersection > 0
        out.add(smaller, bigger, union, intersection) // union is set size

Runtime is O(n * n * k) // k = avg size of smaller set

How do we optimize this?
A linked hashmap may not be efficient on 1v1 comparison -- we need to look up the entire hashmap, or we need a reverse hashmap -- which is the
same as iterating through the smaller set.
However, a linked hashmap can tell us what needs be looked up each comparison i.e., we don't look all combinations O(n * n). We can just
look the combinations that are present.

Create linked hashmap
for i = 0 to in.size - 1
    for k = 0 to in[i].size - 1
        map.put(in[i][k], List.of(in[i]) // create a list or add to existing list

Lookup linked hashmap
for(Int key: map.keySet())
    for(List<Int> docIds: map.get(key))
        call the brute force solution -- but replace in with docIds

Run time is:
    O(n * k) to create hash map +
    O(n * k) to look up hashmap * O(n) for each map entry
    This is still O(n * n * k) but the second O(n) is much smaller because we know similarity is mostly 0.
So runtime is closer than O(n * k) than to O(n * n * k).

What if we have an int[] where each index acts as a placeholder for each ele? This is same as using hashmap but with
contiguous memory.



 */