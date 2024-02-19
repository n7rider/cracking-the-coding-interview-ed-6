package n7rider.ch7.object_oriented_design;

import java.util.LinkedList;

/**
 * Hash Table: Design and implement a hash table which uses chaining (linked lists) to handle
 * collisions.
 *
 * After comparing with solution:
 * Similar. An additional point to discuss - You can use BST too as the backing mechanism. Search
 * can end up taking O(log(n)) time, but we'll not need a huge chunk of bytes that the array needs.
 * Usually, to store n items, we create an array of size 2n. Also, rahter than n, it's good to choose a prime
 * number closer to it.
 */
public class Question7_12 {
    abstract class HashTable {
        LinkedList<KeyAndValue>[] data;
        HashTable(String hashType, int size) {
            // ...
        }

        abstract void add(Object key, Object value);
        abstract Object get(Object key);
        abstract boolean hasEntry(Object key);
        abstract boolean remove(Object key);
        abstract boolean resize(int newSize);

    }

    abstract class HashCalculator {
        abstract HashCalculator getHashCalculator(String hashType);
    }

    class KeyAndValue {
        Object key;
        Object value;
    }


}

/**
 * Components:
 * Hash table
 * Hash calculator
 * Hashing type
 * Array of type LinkedList<KV> // KV has two fields - key, value
 *
 * Fields and methods:
 * Hash table
 *   Hash calculator
 *   init(hash type, size)
 *     init hash calculator
 *     creates an array of size n
 *
 *   add(k, v)
 *     hk = generate hash for object
 *     idx = hk % n // or some logic like that
 *     upsert k, v to array[idx]
 *
 *   get(K)
 *     hk = generate hash for object
 *     idx = hk % n // or some logic like that
 *     lookup the LL in array[idx] for key k
 *     return value
 *
 *   doesExist(K)
 *     same as get() but return boolean
 *
 *
 *   remove(currKey)
 *     same as get()
 *     remove entry from LL
 *       if this.next == currKey
 *         this.next = this.next.next
 *
 *   resize(newSize)
 *     create LL<KV>[]
 *     for i = to n
 *       for each ele in LL
 *         recalculate hash
 *         insert to new array
 *
 * Hash calculator
 *   init(hash type)
 *     set hash type
 *
 *   generateHash(object)
 *     ...
 *
 *
 *
 */