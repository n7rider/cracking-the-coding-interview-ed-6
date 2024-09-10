package n7rider.ch9.system_design;

/*
Social Network: How would you design the data structures for a very large social network like Facebook
or Linked In? Describe how you would design an algorithm to show the shortest path between
two people (e.g., Me -> Bob -> Susan -> Jason -> You).

After comparing:
The book writes down code for bi-directional BFS, but I guess we don't need it for system design interviews.
 */
public class Solution9_2 {
}

/*
Functional requirements:
Very large Social network
Each user has hundreds of friends, millions of users
Need to find shortest path between any two people.

Non-Functional requirements:
Response must be quick, user is probably clicking on a person's page and waiting for it to load
Keep loading feed for user in the background
Modularity - One function's outage doesn't affect another e.g., chat outage can't affect feeds
Security

Design:

UI --> Service --> Cache --> DB

Service API:
E1 - GET /v1/{user-id}/fetch-feed (also is called async by UI when user is close to finish consuming current page)
E2 - POST /v1/{user-id}/create-post
E3 - GET /v1/{user-id}/profile (also finds shortest path)

Cache:
Caches all DB interactions while user is logged in.
Cache has an expiry time too.

DB:
Something that supports graph DB e.g., Neo4J? Not sure how it works 100%, so let's design something

Friends don't change frequently, so list of friends can be in a JSON document in NoSQL (friendsDoc)
    Has a PK and a ID. ID can be 0. PK is the user ID.
    Doc structure: userId, friend-ids[]
Profile details can be in SQL
Blob storage can hold binary data.
Feed data for the last 3 days are in another JSON doc in the same NoSQL DB (activityDoc)
    rest are moved to docs in same partition (or moved to cheaper storage?)
    Doc structure: userId, activity[] (sorted DESC, so add new entries to top)
        activity - ID, type: POST|COMMENT|REACTION
A NoSQL container for posts with PK as userid-post-ID
    Comments can have their own documents with ID as timestamp (Since we always sort by TS)
    Reaction can have its own document with ID as Reaction-Type1 (with a counter field too). The counter is updated
        with a patch update

BOTTLENECKS:
    To prevent hot partitions
    Like counter can be slow to increment itself

Deep dive design:
E1 -
  This is called during log-in.
  After auth, the auth service hands the JSON document.
  Endpoint fetches c.loggedInTime for all friends and sorts by top 50 // This needs a fast look-up, so cache this?
    For 1 million users, this is 10 KB of data, so 100 MB. Redis or any good hash-based look up can do this.
  In batches, fetch top 3-5 posts for these users
    Maybe use shell-sort like method to speed up batches e.g., Recent user 1, 4, 7 are sent in a call, 2,5,8 in one
        and 3,6,9 in another. So, we get top 3 at the same time.

E2
  Validate
  Create post in posts DBC
  Update users.activityDoc DBC (goes through cache, so cache is updated)
    Can do something like real update with a remote hook to active friends (maybe add that to the Redis look up)?
    Call the remote hook with this update and the user will see some live notification

E3
  Make expanding calls
    Check friends list of this person
    Check friends list of other person
    Then do BFS from each person in parallel (BFS has more chance - casting a wide net, rather than going down a path).
        Also cache friends of friends since people most often look at them.
    Stop when level 6 or 7 is reached. Research shows it's unlikely for connections to take more than that.

BOTTLENECKS - Addressing:
    Prevent hot partitions & Like counter can be slow to increment itself
        Allowing reads one by one might be slow for trending posts, so create a Kafka topic dynamically
            for each post when the partition exceeds 1000 entries (Topic name = Post ID) and rate of traffic is > 10/sec.
        Insert into a look up table - Posts (Post ID, Consumer ID - default: null)
        A consumer group include unassigned posts to itself and adds entries
        Dismantle this setup when traffic is < 1/sec for 1 hour.
        The rate-based monitoring can be done by Prometheus








 */