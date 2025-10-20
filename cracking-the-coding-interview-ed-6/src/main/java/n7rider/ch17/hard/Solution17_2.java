package n7rider.ch17.hard;

import java.util.Arrays;
import java.util.Random;

/**
 * Shuffle: Write a method to shuffle a deck of cards. It must be a perfect shuffle-in other words, each
 * of the 52! permutations of the deck has to be equally likely. Assume that you are given a random
 * number generator which is perfect.
 *
 * After finishing:
 * The crux of the method is just 4 lines in shuffleHelper().
 *
 * After comparing:
 * The book swaps arr[i] to a random position in the range 0-i.
 * My code gets a random card from range i+1 - n and puts it into arr[i].
 * Mine looks like it is more open but it becomes restricted later on. It can just be the simple version
 * in the solution if it's going to be the same either way.
 */
public class Solution17_2 {
    public static void main(String[] args) {
        Card[] cards = shuffle();
        System.out.println(Arrays.toString(cards));
    }

    static Card[] shuffle() {
        Card[] cards = getCards();
        shuffleHelper(cards, 52);
        return cards;
    }

    private static void shuffleHelper(Card[] cards, int rem) {
        if(rem != 0) {
            int offsetAtStart = cards.length - rem;
            int currCardIdx = offsetAtStart + random(rem);
            System.out.printf("offsetAtStart: %d | rem: %d | currCardIdx: %d \n", offsetAtStart, rem, currCardIdx);
            swap(cards, currCardIdx, offsetAtStart);
            shuffleHelper(cards, rem - 1);
        }
    }

    private static void swap(Card[] cards, int currIdx, int newIdx) {
        Card temp = cards[currIdx];
        cards[currIdx] = cards[newIdx];
        cards[newIdx] = temp;
    }

    static Random random = new Random();
    static int random(int n) {
        return random.nextInt(n);
    }

    static class Card {
        Suite suite;
        Value value;

        Card(Suite suite, Value value) {
            this.suite = suite;
            this.value = value;
        }

        @Override
        public String toString() {
            return suite.name() + " " + value.name();
        }
    }

    static enum Suite {
        Spade, Club, Heart, Diamond;

        static Suite getSuite(int n) {
            return switch (n) {
                case 0: yield Spade;
                case 1: yield Club;
                case 2: yield Heart;
                case 3: yield Diamond;
                default:
                    throw new IllegalStateException("Unexpected value: " + n);
            };
        }
    }

    static enum Value {
        N2, N3, N4, N5, N6, N7, N8, N9, N10, J, Q, K, A;

        static Value getValue(int n) {
            return switch (n) {
                case 0: yield A;
                case 1: yield N2;
                case 2: yield N3;
                case 3: yield N4;
                case 4: yield N5;
                case 5: yield N6;
                case 6: yield N7;
                case 7: yield N8;
                case 8: yield N9;
                case 9: yield N10;
                case 10: yield J;
                case 11: yield Q;
                case 12: yield K;
                default:
                    throw new IllegalStateException("Unexpected value: " + n);
            };
        }
    }

    static Card[] getCards() {
        Card[] cards = new Card[52];
        for(int i = 0; i < 52; i++) {
            Suite suite = Suite.getSuite(i / 13);
            Value value = Value.getValue(i % 13);
            cards[i] = new Card(suite, value);
        }
        return cards;
    }
}

/*
If we have 3 cards a, b, c. How many ways to arrange them?
a b c | a c b | b c a | b a c | c b a | c a b

So, 6 i.e., 3!
Logically, you have 3 choices first, 2 next, 1 next, so 3 * 2 * 1 = 3!

Similarly, 52! is the no. of ways to arrange a deck of cards

How about we do call random(1, 52), random(1, 51) and so on?

Algorithm (Simplest):
Get deck of cards
    Range is from deck.length - remaining.length to deck.length (i.e., 0, 52 -> 1, 52 -> 2, 52 ... )
Random (1, remaining.length)
Remove card from deck or exclude from mix
    Swap this card to idx deck.length - remaining.len // Swaps 0 with card first, then 1 with card etc. This ensures
         cards shuffled already are not moved
Repeat until remaining.length = 0

This is different from a human shuffling - In which we take a range of cards and shift their indexes.
Here, we take one unshuffled card every time and move it to the shuffled side. That's how it must be because
with 52! this is how it works. If we can shuffle a bunch of cards anytime, the problem is still 52! but we just
move a few and don't move some.

In a nutshell, we merely select elements randomly, and then move around

Pseudocode:
shuffle(Card[] cards, rem=52)
    while(rem != 0)
        int currCardIdx = random(rem)
        int newIdx = cards.len - rem
        swap(currCardIdx, newIdx)
        shuffle(cards, rem - 1)

print

*/