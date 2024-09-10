package n7rider.ch9.system_design;

/*
Cache: Imagine a web server for a simplified search engine. This system has 100 machines to
respond to search queries, which may then call out using processSearch ( string query) to
another cluster of machines to actually get the result. The machine which responds to a given query
is chosen at random, so you cannot guarantee that the same machine will always respond to the
same request. The method processSearch is very expensive. Design a caching mechanism for
the most recent queries. Be sure to explain how you would update the cache when data changes.

After comparing:
I'm using a HashMap and PQ to store items in a LRU cache. The book uses a HashMap<String, Node> where
Node is the head of a linked list which moves recently accessed items to the first every time.
The book uses distributed cache and updating results but, I was just more focused on HashMap and PQ
implementation.
The book uses the HashMap<String, Node> impl the book goes. I thought of it initially but, I didn't think of moving
items to the first. Even if we do that, we can't pop the oldest entry in the entire hashmap. We can only
pop the oldest entry within the list (Different understandings of the requirement I guess)
 */
public class Solution9_5 {
}

/*
Func req:
There's no guarantee that same machine will always respond to the same request
so, there's no hashing of the request or something of that sort. This means we haven't an
in-memory cache. The cache must be common cross all the servers.
Cache must be consistent and prevent unwanted calls to search (expensive)


Simple approach:
Assume we have 5 words - a, b, c, d, e
Create a centralized cache e.g., Gemfire or Redis
if cache.get() == null
    call server and add to cache
else
    return from cache

To reuse queries for words:
lee | eel - Same chars but different searches - So counting no. of chars won't work
mal | malt OR beside | besides -  Adding one letter makes searches different - So a trie may be sometimes wrong

We can have the same object in the cache for different word
To prevent duplicate, we can have a 2-level cache - query -> result hash -> result. So result is stored only once.
So we can use 2 hashmaps and need to call both to get the result.

Structure: resultHashMap<String, String> | resultMap<String, Result>
Get:
    if(resultHashMap.contains(key))
      return resultMap.get(resultHashMap.get(key))

Set:
    hash = computeMd5Hash(result)
    resultHashMap.set(key, hash)
    resultMap.set(hash, searchResult)

Deep dive:
MRU/LRU

Purge old units:
Simple ways:
- Use counter + timestamp in parallel to resultHashMap (We ideally shouldn't delete from one without deleting from the other map).
Uses k, v as key + Obj<counter + timestamp>
- Heap makes updates costly i.e., O(log(n)).

To incorporate this:
during get:
    if(resultHashMap.contains(key))
      // upsert counter & timestamp to counterMap
      return result.get(resultHashMap.get(key))

during set:
    if(resultMap.isAboveCapacity()) // Have a long field to update CacheSize everytime an entry is added or deleted. a custom hashmap
        findOldestEntry in counterMap -- costly O(n)

So, modify counterMap to store <timestamp-nano/milli, list<key>>. This makes set easier but get is expensive, we need to find
timestamp. So, we can modify resultHashMap to store k, v as key, obj<resultHash, timestamp>

So, finally:
during get:

    if(resultHashMap.contains(key))
      <Will explain this at the end of the block>
      return resultMap.get(resultHashMap.get(key))
    else
      find result
      if(resultMap.size + result.size < capacity)
        compute resultHash
        resultMap.setOnlyIfItExists(resultHash, result)
        resultHashMap.set(key, resultHash + timestamp)
        counterMap.upsert(timestamp, List.of(key)) // PQ.upsert()
      else
        // We need something that can give us the lowest timestamp value in counterMap - a heap or a min-max heap.
        // A heap would take O(log n) to store and read. A min/max heap would still take O(log(n)). Min/max is useful
        // only if you need to find top-n one time at the end of operations. For consecutive operations, it doesn't make much difference
        // So, just implement a PQ using a heap
        // And this means we can just replace counterMap with the PQ. counterMap has no value. Each PQ entry is timestamp, List<key>
        PQ.peek() // the oldest result entry but can be used by multiple keys, so we need another way of mapping hash to a key
        // maybe update resultMap to <resultHash, List<key> + result>
        // Sidebar - Or just a SQL like structure in Redis where all columns are indexed? Looked up. we can't. it is like columnar noSQl

        If resultMap.get(oldestEntry).listSize == 1
          delete
        else
          // get next level items // we don't want to remove something from the PQ, so pop and push is bad. Just get next
          level items
          find an entry with no usages and delete it.
          // OR if we will always update the PQ even if there is a cache hit, we can just delete the oldest timestamp always.


if(resultHashMap.contains(key))
  get result from resultMap
  PQ.find // Takes O(log(N))
  PQ.deleteKeyFromList // The List inside PQ should be synchronized list or thread-safe
  PQ.find new timestamp and update

  return resultMap.get(resultHashMap.get(key))






 */