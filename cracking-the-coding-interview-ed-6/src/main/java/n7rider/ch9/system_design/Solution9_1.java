package n7rider.ch9.system_design;

/*
Stock Data: Imagine you are building some sort of service that will be called by up to 1,000 client
applications to get simple end-of-day stock price information (open, close, high, low). You may
assume that you already have the data, and you can store it in any format you wish. How would you
design the client-facing service that provides the information to client applications?You are respon-
sible for the development, rollout, and ongoing monitoring and maintenance of the feed. Describe
the different methods you considered and why you would recommend your approach. Your service
can use any technologies you wish, and can distribute the information to the client applications in
any mechanism you choose.

After comparing with solution:
The book also explains how the database/cache would work (using hashtable, keys). 
 */
public class Solution9_1 {
}

/*
Requirements:
Create a service called by clients for stock price information (open, close, high, low)
Number of clients = 1000

Development, rollout, ongoing monitoring, maintenance

Non-functional requirements:
Needs to connect to a service that gives stock prices to this service (an upstream service)
Write can be slow because we deal with end-of-day stock prices (Limited number of stocks, limited writes,
but best if reads can be fast)
Lots of users, so DB and API bandwidth should be high

High level design:
-----

Upstream --> Stock Service --> Downstream services

Upstream:
A Kafka service? Or an API which is called for stocks in alphabetical order?
If it's former, it's a Kafka consumer. For latter, it can be a batch service

DB:
SQL is okay since writes can be slow, number of stocks are limited, SQL can index data.
However, we don't need sort or any aggregations or joins, so NoSQL can be faster.
(If we're using the batch, then we need a SQL table to store a sorted list of stocks).

DB schema:
Stocks container - Partition Key: Exchange name | ID: Stock name
(There are just 2.2k stock tickers in NYSE, so partitioning by exchange is sufficient. No need to break down.
A Cosmos DB partition can hold 50GB data)

API for downstream:
1000 clients assuming 10 QPS = 10k queries/sec
If peak QPS = 50, then 50k queries/sec

If each JVM thread size is 10 MB, a 2 GB API instance can handle = 200 requests
So we need to scale up, and also use cache to avoid DB overload

Each API should have a cache that is built before it is ready to serve clients. Cache built can
read DB with pagination and build it, or we can create snapshot, upload it a blob storage like S3.
The API can extract this, and populate cache (The latter is faster since DB is not under load)

Question: How does the cache keep itself updated?

With a local cache like Memcache, we can read in ~ 10ms.

For 10k queries, we need 10,000 / 200 = 100 instances. Peak needs 500 instances.

Deep dive:
-----

Prevent stale caching:
Cache can be updated daily but at 4PM the data set needs to stop showing yesterday's data and start showing
today's data. So, the cache can have expiry at 4, and do DB call, and save result to DB.

DB limits:
DB will be overwhelmed until caches are filled. And APIs can start pinging the S3 server for snapshots at 4.01AM.
The objective is to bring down load at DB.

Cosmos can handle more than 50,000 RUs (1 KB read is 1 RU and 50k RUs mean it can handle 50k READs, so this design
should be able to handle).

Auto-scale:
Cosmos and API can be auto-scaled to higher volume for 4 PM volume and peak volume respectively. The load balancer in
front of the API can detect this.

The LB can be connected to Prometheus, and PromQL alerts are written, which will bump up these. Say, bump up
Cosmos by 5k RUs, or API with 25 more instances

Prom QL example for each read: inc_calls(region)

Resiliency:
The Cosmos DB can have a secondary in another region for resiliency. API instances can be spun up in a specific
region if volume is high from a specific country
The bump up logic can look up PromQL volume by region and go with the ones that are running high. However, this
will need the bump up logic to look up Kube pods and clusters by different tags, and attach tags when spinning
up instances. Complicated, but doable.

Scale down:
A PromQL alert keeps running that keeps track of server_utility. If overall usage goes below, say, 30%, it
can start scaling down instances. So the design looks this way now:

Consumer:

UPSTREAM
Kafka consumer --> No-SQL Cosmos DB
                              |
Snapshot uploader batch <---- |
  |
  |-------> Upload snapshot after 4 PM ---> Storage (blob) container

DOWNSTREAM
Client --> Load balancer --> API --> Memcache (Local) --> DB
            |
            |--------------> Prom QL counter -> Scale up/Scale down service -> Kubernetes clusters
                                                    |
                                                    |-------------------------> Azure query to scale Cosmos

We can leave auto-scaling to Kubernetes and to Cosmos too. Both have auto-scaling features.



 */