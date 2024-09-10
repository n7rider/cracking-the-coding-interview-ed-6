package n7rider.ch9.system_design;

/*
Web Crawler: If you were designing a web crawler, how would you avoid getting into infinite loops?

After comparing:
The book also goes in detail about figuring how to determine if two URLs are the same or not e.g., a page
URI with different parameters. Such pages are assigned lower priorities to avoid flooding the queue but also
to avoid missing a page that might have new content.

 */
public class Solution9_3 {
}

/*
Design a web crawler:

Simplest approach - Graphs go DFS or BFS

DFS vs BFS:
We're not looking to find something - so probably no difference
If we're crunched for time, BFS can produce more related results together (because links will be from the same page)

Algorithm (Simple):

crawl(url, queue)
open page // HTTP GET call
parse html for links // Use Apache Poi or similar library
do action // whatever the crawler is meant to do - Save link, store all data, create a trie
collect all links and put them in the priority queue
While queue is NOT empty
    pop queue
    repeat

How to avoid getting into infinite loops?
- Have a HashMap of k,v OR just a hashSet of visited links
- Have in-memory kv DB like Redis

How to implement hashmap?
- Distributed S3 like system
- Each node can take over a range of urls e.g., a-d for 1, e-h for 2, etc.
- Have an API service to front this hashmap. It has redundancy, DB server to cache and store ranges
    SQL's consistency will be useful
- Nodes have redundancy

If new node wants to join, the front does this:
- find range it supports e.g., if we have 26 PKs and 5 nodes (with 5 each + 1), a 6th node means it can grab 26/6 = 4.5 ~ 4
- Find nodes that are supporting more ranges e.g., grab 2 from one with 6 ranges etc.
- Copy over
- While it's warming up (make it receive changefeed for both, and it can reject unwanted ones)

How to receive changefeed?
- The API front sends them

Bottlenecks:
How to make the PQ distributed:
- Out of  box solutions eg., Azure queue. Or implement a queue with SQL.
- Use auto-gen ID ASC (if supported) or index timestamp ASC to decide priority of items

 */