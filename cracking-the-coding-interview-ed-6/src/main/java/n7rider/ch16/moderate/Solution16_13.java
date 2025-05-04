package n7rider.ch16.moderate;

/**
 * Bisect Squares: Given two squares on a two-dimensional plane, find a line that would cut these two
 * squares in half. Assume that the top and the bottom sides of the square run parallel to the x-axis.
 *
 * After finishing:
 * I don't remember the equation for the intercept form, but I realize that connecting the midpoints and
 * interpolating it to the sides is where the answer lies.
 * Just need to convert the vertical sides of the squares to equations, and solve with the intercept formula.
 *
 * After comparing:
 * The eureka moment is the same - realizing connecting the centres is the key.
 * The book also considers and covers if square1 is to the right or bottom of square2.
 *
 * A logical understanding of slope helps you find the offset 'c'. Slope of 1 means for every 1 unit you go in dir x, you
 * climb 1 unit in dir y. If slope is too steep (~ inf), you climb too many units in dir y. So in the following triangle,
 * y1 = (m * (x1 - midx1)) + midy1
 * |--- |
 * | .  |   // Centre . is midx1, midy1.
 * |--- |
 *
 * The lines connecting the centres hit this square at x1, y1. x1 is simple to find, it's on one of the vertical sides
 * based on where the other square is. y1 is close to midy1 for slope < 1, but the exact answer is y1 = m * (x1 - midx1) + midy1
 * Here, x1-midx1 is the distance you go in dir x. Apply the formula to find how much you climb in dir y.
 *
 * The book finds the cutting points for both sides of both squares, and finds which one to use based on x1 < x2 && y1 < y2 etc.
 * I'd have found that out first and found the points just once.
 */
public class Solution16_13 {
}

/*
Input - top left & bottom right of both squares
Output - top left & bottom right of line cutting them in half

Squares on top of each other - A vertical slice
Squares next to each other - A horizontal slice
Squares diagonal to each other - From top left of square 1 to bottom right of square 2
|---|
|   |
|---|


   |---|
   |---|
   |---|


|---|
|   |
|---|


                                |---|
                                |---|
                                |---|

Line when squares are almost on top of each other (ex 1) - from len 'c' to the right of s1-top-left to 'c' left of s2-bottom-right
Line when squares are too far - Line starts at length 'c' below top left of s1 and starts at 'c' above bottom right of s2

Check if s1-top-left and s2-top-left meet at 45 deg i.e., slope = 1

m = (y2-y1)/(x2-x1)

Slope is close to inf for ex 1 (and > 1)
Slope is close to 0 for ex 2 (and < 1)

If m < 1, line starts below s1-top-left
else line starts to right of s1-top-left

How to find its spot?
If they are next to each other, line starts at mid below s1-top-left

Wait, so however this line is drawn, a line that divides a squre always goes through square' mis
So we should look at all of this from the midpoint of the square


slope between centres = inf
m=inf => s1-t-l + 0.5 right -> s2-b-r - 0.5 right //sq. one below other
m =0 => s1-t-1 + 0.5 bottom -> s2-b-r - 0.5 bottom //sq. next to each other
m = 1 => s1-t-1             -> s2-b-r              // on diagonal
m ~= 0 => s1-t-l + 'c' bottom -> s2-b-r - c bottom // far diagonal from each other
m ~inf => s1-t-l + 'c' right -> s2-b-r - c right // almost one below each other

So, c is a function of slope

We can just extend the line connecting the centres to each other

I don't remember the intercept equation y = mx + bc ?? and you substitute zero for x, y one after other?


 */