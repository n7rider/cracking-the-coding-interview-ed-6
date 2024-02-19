package n7rider.ch7.object_oriented_design;

/**
 * Circular Array: Implement a CircularArray class that supports an array-like data structure which
 * can be efficiently rotated. If possible, the class should use a generic type (also called a template), and
 * should support iteration via the standard f or (Obj o : circularArray) notation.
 *
 * After comparing:
 * I didn't get exactly the requirements for Circular array. It sounds like something which often marks node as null,
 * and starts a new head. It also needs to be iterable.
 * However, it just increments until it finds a value. So my smaller solution of next(idx) and add(idx, val) is similar.
 *
 * The latter solution I worked resembles a ring for S3-like storage which looks it out of the problem's scope.
 */
public class Question7_9 {
}

/**
 * Looked at book to understand what the second sentence means, but also found a trick that you don't need
 * a next() to all elements like LL. Rather, always expose a next() and vary its behavior only for the last element
 *
 * Components:
 * CircularArray
 * T[];
 *
 *
 * Fields and methods:
 *
 * CircularArray
 *   T[] elements
 *   int last;
 *
 *   next(idx)
 *     if idx > last
 *       throw ex
 *     if idx == last
 *       return elements[0]
 *     else
 *       return elements[idx]
 *
 *   add(idx, val)
 *     elements[idx] = val;
 *     if idx > last
 *       last = idx
 *
 *
 * How do we expand this for ele in the middle too?
 * Make an array or map of stored idx
 *
 *   int usedIdx[]; // O(log n) read time, but O(1) for input. O(n) storage. Have to use costly sort or start with exactly O(n) storage.
 *   Map<int, bool> //  O(1) read time, and O(1) for input. O(n) storage. Can slowly increase storage but lookup prev, next is not possible.
 *   // TreeMap allows looking prev, next but sorting is too costly for that.
 *   // Better to use a  set of hashmap to find range
 *   // Assuming storage is in range 10^6, l5Map has <range-of-10^5, int[] >, l4Map uses range of 10^4 etc..., l0 map
 *   So we end up with around 10 * 10^5 + 100 * 10^4 + .... = < 2 * 10^6
 *
 *   This will make insert O(1)
 *   read at unused index or used index will also be O(1)
 *     for used index, we'll find the item in the last map
 *     for unused index, we'll recursively go inside the last level and zoom out until we find a closer element in one of the maps.
 *     Can take O(n) time.
 *     The downside is storage is O(2n)
 *
 *   Any other setup might require a sort during insert slowing down inserts.
 *
 *   What about min-max heap?
 *
 *
 *   next(idx)
 *     look up 10^5
 *     if idx exists, return it
 *
 *
 *
 */