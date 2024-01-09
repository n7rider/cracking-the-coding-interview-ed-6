package n7rider.ch6.math_and_logic;

/**
 * 6.6
 * Blue-Eyed Island: A bunch of people are living on an island, when a visitor comes with a strange
 * order: all blue-eyed people must leave the island as soon as possible. There will be a flight out at
 * 8:00 pm every evening. Each person can see everyone else's eye color, but they do not know their
 * own (nor is anyone allowed to tell them). Additionally, they do not know how many people have
 * blue eyes, although they do know that at least one person does. How many days will it take the
 * blue-eyed people to leave?
 *
 * After finishing:
 * Had to look up for hints, but could have solved myself if I had
 * - tried to flesh it out/write more
 * - didn't think of the look at others and figure out yourself part. Maybe it'll come if I speak out
 * (Was trying to solve silently).
 *
 * After checking solution:
 *
 */
public class Question6_6 {
}

/**
 * Easiest case: 50 blue, 50 green
 *
 * 50 green tell 50 blue to leave.
 * Days = 1
 *
 * 100 green (Can't happen, given)
 * Days = 0
 *
 * 100 blue
 * Still all 100 can leave the same day. If a person can tell others to leave after being told to leave
 * Days = 1
 *
 * 100 blue, can't tell others to leave if you're told to leave
 * Day 1: 50 leave
 * Day 2: 25 leave
 * log 2 (100) ?
 *
 * Problem says: can't tell others about their color, how are they told to go then?
 *
 * Looked at a hint from the book: No one can tell others to leave.
 * If c(blue) = 1, the person will look at everyone else's eyes, realize no one else has blue,
 * then will leave (since they can know that at least one person does).
 *
 * If c(blue) = 2, it'll be a deadlock. P1 will think P2 will leave, P2 will think P1 will leave.
 * So no one leaves on Day 1
 * On day 2, both can leave.
 * What if a green sees a blue on day 1? What
 *
 * Looked at hint again, but derived it myself:
 *
 * If c = 1,
 * day 1:
 *   green - sees 1 blue, doesn't go.
 *   blue - sees 0 blue & no one leaves at 8, so leaves after that. (Need two conditions)
 *   BLUE GOES
 *
 * If c = 2,
 * day 1:
 *   green - sees 2 blue (even? or > 1? What's the general case?), doesn't go
 *   blue - sees 1 blue and thinks they'll go. But doesn't himself go
 *   NO ONE GOES
 *
 * day 2:
 *   green - sees 2 blue, doesn't go
 *   blue - sees 1 blue, knows the one is not the only one (Else would have left due to c=1), and goes
 *   Both blue go
 *
 * If c = 3,
 *  day 1:
 *  blue - sees 2 blue, thinks they'll both go but they won't
 *  green - sees 3 blue
 *
 *  day 2:
 *  blue - sees 2 blue. checks if they go.
 *  They  won't
 *
 *  day 3:
 *  blue - realizes it's becaus we hve 3. so goes
 *
 *  So, day 1,
 *  if c = 0, go
 *  if c >= 1, don't go.
 *
 *  day 2,
 *  if c = 0 or 1, go
 *  c >=2, don't go
 *
 *  day 3:
 *  c >=3 don't go
 *  c = 2 or 1, go
 *
 *  So, days taken = c days
 *
 *
 *
 *
 *
 */