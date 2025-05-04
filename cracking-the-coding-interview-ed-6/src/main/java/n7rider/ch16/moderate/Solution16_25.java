package n7rider.ch16.moderate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;

/**
 * LRU Cache: Design and build a "least recently used" cache, which evicts the least recently used
 * item. The cache should map from keys to values (allowing you to insert and retrieve a value associated
 * with a particular key) and be initialized with a max size. When it is full, it should evict the least
 * recently used item.
 *
 * After finishing:
 * I was distracted, but it's straightforward that it's a queue.
 *
 * It's a little easier/different from an actual circular queue because once we hit the max size we don't go back with
 * deleting the tail. The read and write stays at O(1) but there is O(n) additional storage. Seeing that it needs
 * some sort of sorting, O(n) sounds okay.
 *
 * There are little misses with updating the head or tail here or there or in the right sequence. If I am white-boarding
 * things, I should write examples and track the solution.
 *
 * After comparison:
 * The book reuses the same data type for the HashMap's value and the LL's entry. There's probably not much difference
 * storagewise since the value in my Hashmap just had value and the LL entry which has key, prev, next, idx.
 *
 * I never used the idx, so merging the types would have made it much simpler.
 *
 */
public class Solution16_25 {
    public static void main(String[] args) {
        LRU lru = new LRU(5);
        lru.add(4, "E");
        lru.print();

        lru.add(14, "F");
        lru.print();

        lru.add(24, "G");
        lru.print();

        lru.add(34, "H");
        lru.print();

        lru.get(4);
        lru.print();

        lru.add(44, "I");
        lru.add(54, "J"); // 14 should be removed

        lru.print();

        assertNull(lru.get(14));
    }


}

class LRU {
    // Can use template or object but using Int, string for ease of use.
    // Using Int as key to work with the concept of objectives, rather than primitives
    Map<Integer, MapEntry> dataMap;
    CDLL cdll;

    LRU(int size) {
        // Validate size > 0, < max-limit
        this.cdll = new CDLL(size);
        this.dataMap = new HashMap<>();
    }

    void add(Integer key, String value) {// Ca
        Integer oldestKey = cdll.deleteIfSizeExceeded();
        dataMap.remove(oldestKey);
        LLEntry llEntry = cdll.add(key);
        dataMap.put(key, new MapEntry(value, llEntry));
    }

    String get(Integer key) {
        MapEntry mapEntry = dataMap.get(key);
        if(mapEntry != null) {
            LLEntry newLlEntry = cdll.pushToTail(mapEntry.llEntry);
            mapEntry.llEntry = newLlEntry;
            return mapEntry.value;
        }
        return null;
    }

    void print() {
        for (var key : dataMap.keySet()) {
            System.out.printf("Key: %d | Val: %s | Prev: %s | Next: %s\n",
                    key, dataMap.get(key).value,
                    dataMap.get(key).llEntry.prev == null ? null : dataMap.get(key).llEntry.prev.key,
                    dataMap.get(key).llEntry.next == null ? null : dataMap.get(key).llEntry.next.key);
        }
        System.out.printf("Head: %s | Tail: %s\n",
                cdll.head == null ? null : cdll.head.key,
                cdll.tail == null ? null : cdll.tail.key);
        System.out.println();
    }
}

class MapEntry {
    String value;
    LLEntry llEntry;

    MapEntry(String value, LLEntry llEntry) {
        this.value = value;
        this.llEntry = llEntry;
    }
}

class CDLL {
    // Using indexed array because you need to randomly access elements and bump them to the
    // tail on every new access
    // Add size because empty and full CLL can look similar after a full circle
    // Is a quick check for LRU size too
    int currSize;
    int totalSize;
    LLEntry head;
    LLEntry tail;

    CDLL(int size) {
        this.currSize = 0;
        this.totalSize = size;
    }

    LLEntry add(Integer key) {
        if (currSize == 0) {
            int newIdx = 0;
            LLEntry currEntry = new LLEntry(key, null, null, newIdx);
            head = currEntry;
            tail = currEntry;

            currSize++;

            return currEntry;
        } else if (currSize < totalSize) {
            int newIdx = tail.idx + 1;
            LLEntry currEntry = new LLEntry(key, tail, null, newIdx);
            tail.next = currEntry;

            tail = currEntry;

            currSize++;

            return currEntry;

        } else { // Reached size limit
            throw new IllegalArgumentException("Reached limit");
        }
    }

    // Delete the head if size is exceeded
    Integer deleteIfSizeExceeded() {
        if (currSize == totalSize) {
            Integer oldestKey = head.key;
            head = head.next;
            currSize--;
            return oldestKey;
        } else {
            return null;
        }
    }

    // Pushes the last accessed entry to the end of the stack
    LLEntry pushToTail(LLEntry entry) {
        // If the last element is accessed, do nothing
        if (entry == tail) {
            return entry;
        }

        // There is no previous for the head.
        if(entry != head) {
            entry.prev.next = entry.next;
        } else {
            // This is head, make the next one as the head
            head = entry.next;
            head.prev = null;
        }

        entry.prev = tail;
        entry.next = null;

        tail.next = entry;
        tail = tail.next;

        return tail;
    }

}

class LLEntry {
    Integer key;
    LLEntry prev;
    LLEntry next;
    int idx;

    LLEntry(Integer key, LLEntry prev, LLEntry next, int idx) {
        this.key = key;
        this.prev = prev;
        this.next = next;
        this.idx = idx;
    }
}


/*
LRU char:
- get value for a key
- keep track of sequence (to kick oldest item)

Simplest way:
- Deal every purpose with the best solution for the purpose

- Create a hashmap (to store values)
- To store access sequence, we need:
    random access  to get an item
    insert first/last - to update the order of eviction

So we need fast access/removal to maintain sequence of access - Hashmap again?
    - A LL to store timestamp in order of removal (can insert at head/tail)
    - A hashmap to map timestamp to key?

Alg:
    Add k,v
        Add k, v, ts to datamap
        Add ts to LL -> tail
        Add ts, k to seqMap

    Add k, v - No space
        oldTs = Get LL.head
        Get key = seqMap.oldTs
        Remove datamap.key

        Call above add k,v

    Access k
        Get v, ts from datamap
        Get seqMap.ts, replace with seqMap.currTime
        Remove LL.oldTs // O(n) - bad, can replace LL with Tree for O(log n) but tree can be imbalanced

What if we just use the key in the LL
    - A LL to store key in order of removal/access
    - Remove LL.oldTs will still be slow

What if we replace LL with Circular-LL, and store k, v, LL idx in data map

    Add k,v
        Add ts to LL -> tail
        Add k, v, ts, LLidx to data map
        Add ts, k to seqMap //Prlly don't need it. We need seqMap usually to find oldest entry, and connect to it in datamap.
            // What if we find the oldest key with just LL.head?

    Add k, v - No space
        oldestKey = Get LL.head // Also check if we can store the key in LL.head to cut the middleman seqMap out
        Remove datamap.key

        Call above add k,v
        LL.head += 1

    Access k
        Get v, ts, LLidx from datamap
        Squeeze entries to remove LLidx, and add key to LLidx tail // Can take O(n) so not efficient
        Return v

Rather than moving O(n) entries with C-LL<Key> (OR)
finding through O(n) entries with LL<key>
    we have circular Doubly LL along with hashmap to find the entry

What diff does this make?
    Access k
        Get v, ts, LLidx from datamap
        Connect LL.prev to LL.next, add this to LL tail
        Update datamap with new LLidx // So ts doesn't matter
        Return v

Hashmap<K, { V, LLidx }
Circly-Double-LL Entry { Key, prev, next, idx } // Backed by an array of len l

Init (l)
    Create hashmap
    Create array of size l
    LL.head = null, tail = null, idx = 0

Add k, v
    if head == null
        Create new LLentry { key, prev=null, next=null}
            Calls internal method which inserts at 0
            head = 0, tail = 0
            Returns idx
    Else
        Create new LLentry { key, prev=tail, next =null }
        tail.next = this
        tail = this
        Return tail.idx + 1 // Use inc and never add/sub manually. inc/dec always use modulus

Access k
    Get v, LL-idx from TS
    Connect LL.prev to LL.next, add this to LL tail
    Update datamap with new LLidx // So ts doesn't matter
    Return v













- Create a LL - linking all items

Alg:
    Init LRU (size)
        Create hashmap
        Create LL

    Add (k, v)
        Insert to k, v
        LL.next = LLentry(k)

        If LL is empty, LL.head = the above LLentry

        If LL is full, LL.head = LL.head.next

    Get(k)
        Get from hashmap




 */