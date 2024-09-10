package n7rider.ch9.system_design;

/*
Sales Rank: A large eCommerce company wishes to list the best-selling products, overall and by
category. For example, one product might be the #1056th best-selling product overall but the #13th
best-selling product under "Sports Equipment" and the #24th best-selling product under "Safety."
Describe how you would design this system.

After comparing with solution:

The book considers we have millions of records, so it went to SQL rightaway. We can implement a
HashMap like mine using a bucket storage strategy.
However, mine doesn't consider that the rankings of the oldest entry needs to go away eventually.
Maybe, we can collapse it every week to a simple structure of weekly, monthly, yearly stats.
The book also assumes an hourly snapshot is enough, and there's no need to run it on-demand. I took
a different path, but it's good to remember that I need to ask a lot of questions in interviews
like this.
 */
public class Solution9_6 {
}

/*
Simplest way - In an orders database, sort by purchases count for overall rank.
Sort by purchase count & category for category rank.
And we can take a snapshot of the results every 1 hour or so.

SQL or Redis is best for sorting

The table structure - Item | Category[] tags | Count

Redis uses hash table + skip list for ranking. The hash table does product -> count mapping.
The skip list does count -> product mapping.
The skip list first connects count from 1 to n, its second level skips one, so 1, 3, ... n,
third level skips 3 etc. We can skip in powers of 2 maybe.

Skip List's structure
    List<Node> heads; // list of linked lists - 0 skips to n skips
    find(val, list-index) // in what list we want to find this value in
    add(val)

Each Node has List<String> - Product or List<long> if we use item id

For let's say, 100 products and 10 categories, here is how it works:

POST /add-purchase (item, count)
GET /get-ranking ()

add-purchase:
    Update the hash map
        A simple put
    Update the skip list
        find(oldCount, list n) // skip by a lot and arrive soon at the required value
            if(currVal >  oldCount)
              find(oldCount, list n - 1) // we jumped ahead, so be granular, look up a list with smaller skips
            if currVal == oldCount
               stop
            DO THIS on a loop
        Remove curr item from oldCount
        find(newCount, list-n) // skip by a lot and arrive soon at the required value
            if doesn't exist in skip list 0,
                create an entry in list 0
                from old-count's entry, all other list's entries go + 1
            else
                just add to List<String>

    How do find the same entry in all lists if one is found?
        Change node structure to
        Node:
            int val
            Set<Long> - item ids
            Node next;
            Map<Int, Node> - navigationMap <skip level, next node> // each level looks up map to decide which node to go next
                But what if entry doesn't exist? Just keep going back? No, we can make the find simpler

What if the skip-list's structure is made List<Node> heads & Map<int, Node>
    The Map creates an entry per value of count but helps arrive at a node easily and makes backward iteration possibl
    Without it, we always have to sort from the beginning to search any entry

So the new algorithm is:
    Find old entry in each list
        If has an entry in navigation map, change it to node = node.next
        If has no entry, keep going in levels of 1 until you find a node with an entry in navigation map
            Update that entry
    Delete entry from Map<>

get-ranking:
    Find item's purchaseCount
    We know the map size, so do binary search on the big list and keep track of the rank e.g., if map size = 256,
        biggest rank = 64, we can increase rank by 64 for each iteration on it.
    If we go beyond the product's purchase count, we go back to the previous number, and use  a smaller list to jump
    We'll reach the entry in log n. log n time

get-ranking in category:
    we can create maps per category
    or if we change the Set<Long> in each node to Map<String, Long> of Map<Category Id, Item Id>, then we can go
        through the over all list one by one to find the rank. This is bad, so separate maps is practical, but it takes
        a lot of memory.


 */                                                                 
