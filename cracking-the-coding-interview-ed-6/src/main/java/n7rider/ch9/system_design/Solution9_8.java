package n7rider.ch9.system_design;

/*
Pastebin: Design a system like Pastebin, where a user can enter a piece of text and get a randomly
generated URL to access it.

After comparing:
Forgot to talk about how views are calculated (added now), UUID may not be pretty for the user
 */
public class Solution9_8 {
}

/*
Requirements (asked Chat-GPT):
Max length allowed - 10k char?
Type of text allowed - Plain text unicode
Coding or text formatter - Maybe (additional feature, discuss at the end)

Number of users - 1 million per day
Lifetime - 10 years

High consistency - Not immediate, but still high

Estimates:
Storage -
10k char = 40 KB
1 mil / day * 10k char = 40 GB per day
For 10 years, 40 GB * 10 * 400 days = 16 TB

Can be handled by a cloud offered NoSQL DB or S3 like bucket storage
Let's go with bucket storage. We don't deal with this in terms of partitions and doc IDs, so bucket is more apt.
The book might go with flat files with UUID names

Design:

Create -> Validator -> Store
POST /v1/entry

GET -> Formatter -> Store
GET /v1/{id}

Formatter can be implemented with JS to reduce server load. Logic to discuss later


Doc structure:
Key - UUID
Tags
 User-name
 Views
 Formatter
Content

Create-flow:
    Validator
        Regex for banned phrases
    Generate UUID
    Find hash for UUID, and send to the bucket instance
    Change feed consumed by a batch processor for formatting

Read-flow:
    Find hash for UUID, and read from the bucket instance

Bucket rebalancing:
    If instance 2 drops, its secondary can come online.
    If we want to shut it altogether, we'll split its ranges between i1 and i3
        For live traffic, calculate hash and split data between i1 and i3 at the midpoint
        For existing data, walk through each doc, calculate UUID hash and split data

Formatter
    Look for regex e.g., look for keywords eg., psv main, public class, stdio.h etc. find count of {, }, :, ; etc
        Arrive at conclusion (Checked with ChatGPT - this is the same appraoch it uses, but also adds scores to choose the
        highest)
        If user answers the question, just go with this formatter
    Apply formatting
        Use something like .editorconfig and apply
        Basically, there are a bunch of rules - check for regex and replace with formatted strings in all of these
        e.g., /s.*+/s.* - replace with /s+/s . This replaces both 'a+b' and ' a   + b' with 'a + b'
    Reupload to bucket - Delete and re-upload

Views calc
    We can just append count in tag during each read. This won't be expensive but can cause consistency issues if too
    many updates are done simultaneously.
    We can export view data to Prometheus and have a source poll counter for the last 1 minute and update views. This
    decouples the mechanism from rest of the flows

UUID may not be pretty for user
    Counter is simple to use, but anyone can predict the sequences
    A counter that generates alphadecimal values with arithmetic might work
        e.g., Use 64 encoding to generate a phrase e.g., 'aa' encoded in Base64 is YWE=
        Add ASCII shift of a random prime number to each char and set that as the doc ID

 */
