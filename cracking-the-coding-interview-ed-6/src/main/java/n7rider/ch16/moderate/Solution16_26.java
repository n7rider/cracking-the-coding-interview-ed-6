package n7rider.ch16.moderate;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Calculator: Given an arithmetic equation consisting of positive integers, +, -, * and / (no parentheses), compute the result.
 * EXAMPLE
 * Input: 2 * 3 + 5 / 6 * 3 + 15
 * Output: 23.5
 *
 * After finishing:
 * The obvious solution is to use a stack to convert to post fix, and then look at the next operator
 * for * or / but that will take a lot of time for an interview problem.
 * My approach is not the most efficient but still O(n)
 *
 * After comparing with solution:
 * The book goes with the stack, and looks at the next operator in its other approach. The book also admits
 * that there is a lot of charAt and too many annoying String parsing if done that way.
 * Probably I should do the best approach and see how fast I can do it.
 */
public class Solution16_26 {
    public static void main(String[] args) throws Exception {
        //computeEquation("2 * 3 + 5 / 6 * 3 + 15");
        computeEquation("2 - 6 - 7 * 8 / 2 + 5");

    }

    public static double computeEquation(String input) throws Exception {
        // Inbuilt method to collect as LL with next and prev
        var inputArray = input.split(" ");
        // Skip validation - starts with, and ends with no., no.s are all ints
        // Skip validation - no.s and symbols alternate
        // Skip validation - len >= 3
        Node head = null;
        Node prev = null;
        for(String ele: inputArray) {
            if(head == null) {
                head = new Node(ele, null, null);
                prev = head;
            } else {
                Node curr = new Node(ele, prev, null);
                prev.next = curr;
                prev = curr;
            }
        }
        return compute(head, Set.of("/", "*"));
    }

    private static double compute(Node head, Set<String> opsSet) throws Exception {
        System.out.println("List beginning: " + head.getAllElements() + "\n");

        Node aNode = null, bNode = null, opNode = null;
        Node curr = head;
        // In 0-based array, curr is 0, 2, 4.... curr becomes null at the last one, or we'll have two available -
        // -- because numbers and symbols alternate
        while(curr.next != null) {
            System.out.println("\nCurrent is: " + curr.element);
            aNode = curr;
            opNode = curr.next;
            bNode = curr.next.next;

            String out = null;
            if(opsSet.contains(opNode.element)) {
                out = compute(aNode.element, opNode.element, bNode.element); // ....* 1_ + 3
                Node newNode = new Node(out, curr.prev, bNode.next);
                if(curr != head) {
                    curr.prev.next = newNode;
                } else {
                    head = newNode;
                }
                curr = newNode;
            } else {
                curr = bNode;
            }

            System.out.println("List so far: " + head.getAllElements() + "\n");
        }

        if(head.next != null) {
            return compute(head, Set.of("+", "-"));
        } else {
            return Double.parseDouble(head.element);
        }
    }

    private static String compute(String a, String op, String b) throws Exception {
        double op1 = Double.parseDouble(a);
        double op2 = Double.parseDouble(b);

        String out = null;
        switch (op) {
            case "+": out = String.valueOf(op1 + op2); break;
            case "-": out = String.valueOf(op1 - op2); break;
            case "*": out = String.valueOf(op1 * op2); break;
            case "/": out = String.valueOf((op1 * 1.0) / op2); break;
            default: throw new Exception("Unknown op: " + op);
        };
        System.out.printf("%s %s %s = %s\n", op1, op, op2, out);
        return out;
    }

    static class Node {
        String element;
        Node prev;
        Node next;

        Node(String element, Node prev, Node next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        String getAllElements() {
            StringBuilder out = new StringBuilder();
            out.append(this.element);
            Node curr = this;
            while(curr.next != null) {
                out.append(" ").append(curr.next.element);
                curr = curr.next;
            }
            return out.toString();
        }
    }
}

/*

validate LL starts and ends with no.
validate nos and symbols alternate
validate LL.len >= 3
a = LL[0], b=LL[2], op=LL[1] // use .next actually not idx
while (1)
    if op == * OR /
        out = calc(a, op, b)
    a.prev?.next = out
    if b.next == null // end of eqn
        break
    else
        out.next = b.next // a * b * c becomes out * c
        a = out
        op = b.next // if this is null, there will be another num
        b = op.next

next round:
    so the same but skip the first if check




This is a textbook definition of postfix expression + stack

Simplest solution:
// Validate no.s and symbols alternate and don't come after each other // because, no parantheses
First run - Solve high priority * / elements first
    get three eles first time, prev + 2 eles thereafter
    decompose if solving, else leave it
Second run - Solve low priority +- elements first



How will we approach the above with postfix? 2 3 * 5 6 / 3 * + 15 +

Algorithm:
Assume eq.'s ele come in String array

No parantheses, so * / have higher priority, and + - have lower and should wait

Pfout
cumulative no.s in stack = 0
while(ele != null)
  if numeric
    add to stack

  if symbol && high priority
    remove last 2 no.s from stack (Or stop with 1 with 2nd is a symbol) -> out

  if symbol && low priority
    remove from stack if stack has low priority -> out
  else
    curr -> out

After we get post fix we need to repeat the steps in the simplest solution

So, we don't need the post-fix logic if there are no parantheses.

Detailed algo for simplest solution:
validate LL starts and ends with no.
validate nos and symbols alternate
validate LL.len >= 3
a = LL[0], b=LL[2], op=LL[1] // use .next actually not idx
while (1)
    if op == * OR /
        out = calc(a, op, b)
    a.prev?.next = out
    if b.next == null // end of eqn
        break
    else
        out.next = b.next // a * b * c becomes out * c
        a = out
        op = b.next // if this is null, there will be another num
        b = op.next

next round:
    so the same but skip the first if check











 */