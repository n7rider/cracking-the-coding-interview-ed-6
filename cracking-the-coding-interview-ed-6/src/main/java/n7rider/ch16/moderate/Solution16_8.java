package n7rider.ch16.moderate;

import static org.junit.Assert.assertEquals;

/**
 * English Int: Given any integer, print an English phrase that describes the integer (e.g., "One Thousand,
 * Two Hundred Thirty Four").
 *
 * After finishing:
 * The tricks were - you go up and down at times e.g., look for 100, 200, x11-x19 first, but go for hundreds, tens, ones.
 * And then, make this work for all - thousands, millions etc
 * I could have modularized updateHundredsThruOnes earlier - while writing the code
 *
 * After comparing with solution:
 * The book uses LinkedList for the output parts, and an array rather than a map (because values retrieved with idx match the number's value)
 * The solution is similar, but rather than using if(mod2Dig != 0), the books uses a "" entry in the lookup array. Neat
 */
public class Solution16_8 {
    public static void main(String[] args) {
        assertEquals("One Thousand, Two Hundred Thirty Four", intToWord(1234));
        assertEquals("Zero", intToWord(0));
        assertEquals("Minus Ten", intToWord(-10));
        assertEquals("One Million, One Hundred One Thousand, Two Hundred Thirty Four", intToWord(1101234));
    }

    static String intToWord(int n) {
        StringBuilder out = new StringBuilder();

        String prefix = "";
        if (n < 0) {
            prefix = "Minus ";
            n = n * -1;
        }

        if(n == 0) {
            return "Zero";
        }

        int count = 0;
        do {
            StringBuilder currString = new StringBuilder();
            updateHundredsThruOnes(currString, n);
            currString.append(lookupSep(count));
            if(count > 0) {
                out = currString.append(", ").append(out);
            } else {
                out = currString.append(" ").append(out);
            }

            System.out.printf("Out is now: %s\n", out);
            count++;
            n = n / 1000;
        } while (n > 0);

        if(n < 0) {
            return prefix + lookup(-1) + out.toString().trim();
        } else {
            return prefix + out.toString().trim();
        }
    }

    private static void updateHundredsThruOnes(StringBuilder currString, int n) {
        int mod3Dig = n % 1000;
        if(mod3Dig != 0) {
            int hundredDig = mod3Dig / 100;
            if (hundredDig > 0) {
                currString.append(lookup(hundredDig));
                currString.append(lookup(100));
            }

            int mod2Dig = mod3Dig % 100;
            if (mod2Dig != 0) {
                if (mod2Dig >= 11 && mod2Dig <= 19 || mod2Dig % 10 == 0) {
                    currString.append(lookup(mod2Dig));
                } else {
                    currString.append(lookup((mod2Dig / 10) * 10));
                    currString.append(lookup(mod2Dig % 10));
                }
            }
        }
    }

    private static String lookup(int n) {
        switch (n) {
            case -1: return "Minus ";

            // Needed to print nothing for tens digit if n < 10.
            // Ideally use default fort his, but here I'm using default to fill up numbers I'm not writing
            case 0: return "";

            case 1: return "One ";
            case 2: return "Two ";
            case 4: return "Four ";
            case 7: return "Seven ";

            case 10: return "Ten ";

            case 11: return "Eleven ";

            case 30: return "Thirty ";

            case 100: return "Hundred "; // Just 100, for rest add num + hundred

            default: return "Blah-" + n + " ";
        }
    }

    private static String lookupSep(int n) {
        switch (n) {
            // Space at the end added in the calling method
            case 1: return "Thousand";
            case 2: return "Million";
            case 3: return "Billion";
            default: return "";
        }
    }
}

/*
intToWord(n)
// init val
  if n == 0
    print 'Zero'
    return
  int c = 0
  find-first-non-zero-triad (e.g., look in triples. in 10 mil, you get it only on the 3rd attempt)

  do
    int mod3dig = n % 1000;
    if mod3dig != 0 // do nothing if mod3Dig = 0 e.g., first 000 digits for 1 mil.
      int hundredDig = mod3dig / 100;
      if(hundredDig > 0)
        out += Lookup Dict (hundredDig) // 100, 200 ... 900

      int mod2Dig = mod3dig % 100
      if(mod2Dig != 0) // do nothing if mod2Dig = 0 e.g.,00 in 300
        if 11 <= mod2Dig <= 19
          out += Lookup Dict (mod) // 11, 12, ..19
        else
          if mod2Dig % 10 == 0
              out += Lookup Dict (mod) // 10, 20, 30,...90
          else
              int ones = mod % 10 // 1, 2, ..., 9
              out += Lookup dict (ones)
              int tens = (mod / 10) * 10 // 10, 20, 30, ..90
              out += Lookup dict (mod)
     addQualifier(count) // nothing for 0, thou for 1, mil for 2,....
     count++
     n = n / 1000
  while n > 0
  return out
 */

/*
Int -> Word
4 -> Four
24 -> Twenty Four (Exceptions Eleven - Ninteen, 0 in tens digit)
123 - One hundred ...
100,232 - Hundred thousand blah (Reuse logic from earlier)
.... - Million (Reuse logic of 1s, 10s, 100s)

int -> word for 0 -> 999

1. take mod by int (quotient is stored too)
2. find word
3. add to the left of existing word
REPEAT until quotient > 0

edge cases
0 - just print zero (do at start)
minus - add minus at the last (do at end)

ones:
switch(mod)
  case 0: // ignore, have an edge case
  case 1: ONE
  ....

look for teens first:
11-19

So basic algorithm
 : if mod % 100 = 11-19
     teens(mod)
   else
     if(mod % 10) == 0
       tens(mod)
   else
     ones(mod % 10)
     tens(mod / 10)

call algorithm again for mod % 1000
then out = new-out + 'thousand, ' + out

call again for mod % 1000 -- million
call again for mod % 1000 -- billion


-----

intToWord(n)
// init val
  if n == 0
    print 'Zero'
    return
  int c = 0
  find-first-non-zero-triad (e.g., look in triples. in 10 mil, you get it only on the 3rd attempt)

  do
    int mod3dig = n % 1000;
    if mod3dig != 0 // do nothing if mod3Dig = 0 e.g., first 000 digits for 1 mil.
      int hundredDig = mod3dig / 100;
      if(hundredDig > 0)
        out += Lookup Dict (hundredDig) // 100, 200 ... 900
      else
        int mod2Dig = mod3dig % 100
        if(mod2Dig != 0) // do nothing if mod2Dig = 0 e.g.,00 in 300
          if 11 <= mod2Dig <= 19
            out += Lookup Dict (mod) // 11, 12, ..19
          else
            if mod2Dig % 10 == 0
                out += Lookup Dict (mod) // 10, 20, 30,...90
            else
                int ones = mod % 10 // 1, 2, ..., 9
                out += Lookup dict (ones)
                int tens = (mod / 10) * 10 // 10, 20, 30, ..90
                out += Lookup dict (mod)
     addQualifier(count) // nothing for 0, thou for 1, mil for 2,....
     count++
     n = n / 1000
  while n > 0
  return out

intToWord2Dig(n)
StringBuilder out = '';
int mod = n % 100;
if 11 <= mod <= 19
  out += Lookup Dict (mod) // 11, 12, ..19
else
  if mod % 10 == 0
    out += Lookup Dict (mod) // 10, 20, 30,...90
  else
    int ones = mod % 10 // 1, 2, ..., 9
    out += Lookup dict (ones)
    int tens = (mod / 10) * 10 // 10, 20, 30, ..90
    out += Lookup dict (mod)
return out




 */