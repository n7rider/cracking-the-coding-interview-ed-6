package n7rider.ch8.recursion_and_dynamic_prog;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
public class Question8_6_Attempt2 {
    public static void main(String[] args) {
        towersOfHanoi(4);
    }

    static void towersOfHanoi(int disks) {
        List<Stack<Integer>> towers = new ArrayList<>();
        for(int i = 0; i< 3; i++) {
            towers.add(new Stack<>());
        }

        for(int i = disks - 1; i >= 0; i--) {
            towers.get(0).add(i);
        }

        towersRec(towers, disks, 0, 2, 1);
        System.out.println(towers);
    }

    static void towersRec(List<Stack<Integer>> towers, int disks, int source, int target, int inter) {
        if(disks == 0) {
            return;
        } else {
            towersRec(towers, disks - 1, source, inter, target);
            move(towers, source, target);
            towersRec(towers, disks - 1, inter, target, source);
        }
    }

    static void move(List<Stack<Integer>> towers, int source, int target) {
        if(towers.get(source).empty()) {
            System.out.printf("Skipped moving from %d to %d. Empty source stack\n", source, target);
        } else {
            int movedDisk = towers.get(source).pop();
            towers.get(target).push(movedDisk);
            System.out.printf("Moved from %d to %d. \n", source, target);
        }
    }


}

/*
   =        |      |
  ===       |      |
 =====      |      |

for n:
  move 1..n-1 disks from source to inter
  move nth disk from source to target
  move 1..n-1 disks from inter to target

for 1:
  ---
  move disk 1 from source to target
  ---

for 2:
  move disk 1 from source to inter
  move disk 2 from source to target
  move disk 1 from inter to target

for 3:
  move disks 1..2 from source to inter // 3
  move disk 3 from source to target    // 1
  move disk 1..2 from inter to target // 3


towersRec(int stacks, int n, int source, int target, int inter)
base case:
  if n==0
    return

rec case:
  towersRec(stacks, n-1, s, i, dummy = tar)
  move(stacks, n, s, t)
  towersRec(stacks, n-1, i, t, dummy = src)

move(stacks, n, s, t)
  if s.empty()
    print Skipped
  else
    t.push (s.pop)
    print log // Move from s to t

 */