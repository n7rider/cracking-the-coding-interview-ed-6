package n7rider.ch16.moderate;

/**
 * Intersection: Given two straight line segments (represented as a start point and an end point),
 * compute the point of intersection, if any.
 */
public class Solution16_3 {
}

/*
Line a - x1, y1 to x2, y2
Line b - x3, y3 to x4, y4

Example:
0,0 to 5, 5

2,-1 to 2, 5

Might need that mathematical formula. A line from 0,0 to 6,7 will go through fractions, so finding point might be
difficult, still trying:

- Make an equation for every point. e.,g., 2, -1 = x - 2 + y + 1 = 0.

After comparing with the book solution:
Yeah, it's mathematical indeed. I doubt this will be needed for an interview.
But just for curiosity, here it goes:
slope m = (y2 - y1) / (x2 - x1)
y-intercept c = y2 - m * x2;

Switch around the points such as x1 < x2 and x3 < x4 for both lines.

If the lines are parallel, they intercept only if they have the same y intercept and start 2 is on line 1.
if (line1.slope == line2.slope)
   if (line1.y_intercept == line2.y_intercept &&  isBetween(start 1, start2, end1))
        return start2;

// Checks if middle 'b' is between ends 'a' and 'b'
isBetween (a, b, c):
    true if a <= b and b <= c OR
    true if a > c & a > b && b > c

Otherwise, find intersection co-ordinate:
  x = line1.c - line2.c / (line1.m - line2.m)
  y = x * line1.m + line1.c
  true if isBetween(x1, x, y1) && isBetween (x2, y, y2)

 */