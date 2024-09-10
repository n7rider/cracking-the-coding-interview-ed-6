package n7rider.ch9.system_design;

/*
Personal Financial Manager: Explain how you would design a personal financial manager (like
Mint.com). This system would connect to your bank accounts, analyze your spending habits, and
make recommendations

After comparing:
I didn't think of not generating alerts for users who rarely login, sending email/notification alerts
for users who exceed budget,
but I guess these are additional performance optimizations or features.
The book was probably written at a time when NoSQL was not popular, so problems like this rather use flat
text files, but the idea is similar I guess.
 */
public class Solution9_7 {
}

/*
Gather requirements?
No. of users - 10 million?
Aggregation allowed, how frequent updates? - Yes, once per day

Should be secure with data, links
Feature to delete accounts/data

Estimates:
Each user has 3 accounts, 10 cards, 5 inv/saving = ~20 accounts
Each account has 10 txns per month, so for each user 20 * 10 = 200 per month

For 10 m users, this is 2000 mil txns. Each txn length < 10 KB
Size = 2000 mil * 10 KB = 20 TB - A NoSQL with partitions can handle this


System:

UI to connect accounts -> Import data -> Run recommendation engine --> Show/save results

Use NoSQL - with:
- unique partitionId for each user
- create snapshots to avoid re-running all the calculations every day

Import data:
 Redirect users to bank website to login, and return valid token. Use the aggregator token
 to import data
 Save to user's partition, with a standard prefix e.g., bankName_yyyy_mm_dd
 Also let users add cash calculations manually

Analysis & Recommendation engine:
 A job running for the list of users

 10 million / 10^6 seconds per day = Job runs in < 1 sec (Or go parallel)


Steps:
Find last known txn from DB
Go through each new txn
Calculate income, expenditure while putting them in categories (categories from merchant code)
Compare their pattern with recommendation pattern
    Have a NoSQL that has recommendations by income-ranges & zip (for COL)
    With 10 ranges per zip, 100,000 zips * 50 KB = 5 GB

Create report for results (Doing good or doing bad) - Use in-memory cache to hold data (max: 10 MB per user)
Save report to user snapshot

For delete:
Delete account, re-run report (Or if regulations allow < 1 day operation, just set delete marker to the user account)


Deep dive/breaking points:
Heavy txn users - Spin up new instances if a user's analysis takes more than

    With 20 accounts, and a conn. of 3 seconds per account, + 10 seconds for the recs. engine, we need = 70 seconds
    Call all accounts in parallel, then run compute engine
    Even then, we need at least 10 seconds per user. 30 is a safe number.

With 30s per user, we need to run 30 instances to finish all reports in a single day

DB Structure:
UserID - PK
BankAccountID_0 - Doc config (Just add delete indicator if user wants to delete, we'll delete in the next run)
BankAccountID_yyyy_mm_dd - txn documents
    After 1 years - Compress document into monthly granularity
    After 10 years - Compress document into yearly granularity (or whatever the regulations allow)
Compression can run after report generation

For enterprise, high volume accounts, we can have a separate PK per account and have this list in the UserID PK config
    A transition from normal to this account is unlikely, so we can ask this during sign up

Analytics?
  Have a separate DB with ranges (age, income levels) configured. Upsert an entry for each user there
  Use this to spend average spend on rent, dining etc.


 */