package n7rider.ch6.math_and_logic;

/**
 * 6.7
 * The Apocalypse: In the new post-apocalyptic world, the world queen is desperately concerned
 * about the birth rate. Therefore, she decrees that all families should ensure that they have one girl or
 * else they face massive fines. If all families abide by this policy-that is, they have continue to have
 * children until they have one girl, at which point they immediately stop-what will the gender ratio
 * of the new generation be? (Assume that the odds of someone having a boy or a girl on any given
 * pregnancy is equal.) Solve this out logically and then write a computer simulation of it.
 *
 * After finishing:
 * --
 *
 * After comparing:
 * --
 * Similar conclusion to mine i.e., the higher 'n' we have , the closer to 0.5 we'll get
 *
 */
public class Question6_7 {
}

/**
 * P(g) = 0.5
 *
 * If total = 1, ratio = 0.5
 * If total = 2, ratio = 0.25
 *
 * If there are 16 families,
 * T = 1 -> 8 8|8*0
 * T = 2 -> 4 4|4*1
 * T = 3 -> 2 2|2*2
 * T = 4 -> 1 1|1*3
 *
 * Ratio = 15:11
 *
 * No. of girls = n/2 * n/4 * .... 1
 * No. of boys = n/4 + 2*n/8 + 3*n/16 + ...
 *
 * Ratio  = g/b
 *
 * However, 1 + 2 + 4 + 8 + .... = 2^n - 1
 * Simplifies the no. of girls
 *
 * The same can be used for boys with the starting point moving from n/4, to n/8 and so on.
 * But this is needlessly complex.
 *
 *
 *
 */