package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 Towers of Hanoi: In the classic problem of the Towers of Hanoi, you have 3 towers and N disks of
 different sizes which can slide onto any tower. The puzzle starts with disks sorted in ascending order
 of size from top to bottom (i.e., each disk sits on top of an even larger one). You have the following
 constraints:
 (1) Only one disk can be moved at a time.
 (2) A disk is slid off the top of one tower onto another tower.
 (3) A disk cannot be placed on top of a smaller disk.
 Write a program to move the disks from the first tower to the last using stacks.
 */
public class Question8_6 {
    public static void main(String[] args) {
        towersOfHanoi(3);
    }

    static void towersOfHanoi(int disks) {
        List<Stack<Integer>> towerStacks = new ArrayList<>();
        towerStacks.add(new Stack<>());
        towerStacks.add(new Stack<>());
        towerStacks.add(new Stack<>());

        for(int i = disks - 1; i >= 0; i--) {
            towerStacks.get(0).push(i);
        }
        towersInt(disks, towerStacks);
        System.out.println(towerStacks);
    }

    static void towersInt(int disks, List<Stack<Integer>> towerStacks) {
        if(disks == 1) {
            return;
        }

        System.out.printf("Disks = %d\n", disks);

        int source;
        int intermediary;
        int target;
        if(disks % 2 == 0) {
            source = 0;
            intermediary = 1;
            target = 2;
        } else {
            source = 0;
            intermediary = 2;
            target = 1;
        }

        move(towerStacks, source, intermediary, target);
        move(towerStacks, source, target, intermediary);
        move(towerStacks, intermediary, target, source);

        System.out.println();
        
        towersInt(disks - 1, towerStacks);
    }

    static void move(List<Stack<Integer>> towerStack, int source, int target, int intermediary) {
        //System.out.printf("----- Before movement: %s | %s | %s\n", towerStack.get(0).toString(), towerStack.get(1).toString(), towerStack.get(2).toString());
        if(towerStack.get(source).empty()) {
            System.out.printf("Tower %d is empty. Skipping \n", source);
        } else {
            int popped = towerStack.get(source).pop();
            towerStack.get(target).push(popped);
        }

        System.out.printf("After movement: %s | %s | %s\n", towerStack.get(0).toString(), towerStack.get(1).toString(), towerStack.get(2).toString());
    }
}

/*
   -
  - -
 - - -      |      |

 1 2 3  []   []

 1 2 [] []
 s   i  t

 move(s, i, t)
 move(s, t, i)
 move(i, t, s)

if(n % 2 != 0)
 move(s, t, i)
 move(s, i, t)
 move(t, i, s)

 [123] [] []
 [23] [1] []  sit
 [3] [1] [2]  sti
 [3] [] [12] its


towersOfHanoi(int n)
  towersInt(n)
  print output of all 3 stacks

towersInt(n)
  if n == 1
    return

  if(n % 2 == 0)
     move(s, i, t)
     move(s, t, i)
     move(i, t, s)
  else
     move(s, t, i)
     move(s, i, t)
     move(t, i, s)
  towersInt(n-1)

move(t1, t2, t3)
  print Move from t1 to t2. t3 does nothing.

move(t1, t2, t3)
  t2.push(t1.pop)
  print Move from t1 to t2. t3 does nothing.


how do we use stack here?

 */