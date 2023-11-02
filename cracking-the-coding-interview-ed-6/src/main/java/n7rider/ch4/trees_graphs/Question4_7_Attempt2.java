package n7rider.ch4.trees_graphs;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * 4.7
 * Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of
 * projects, where the second project is dependent on the first project). All of a project's dependencies
 * must be built before the project is. Find a build order that will allow the projects to be built. If there
 * is no valid build order, return an error.
 * EXAMPLE
 * Input:
 * projects: a, b, c, d, e, f
 * dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
 * Output: f, e, a, b, d, c
 *
 * ---
 * Post-run observations:
 * Runs in O(N^2) time (Actually, it's O(n), then finding a zero dep node from the remaining entries)
 *
 * After comparing with solution:
 * Similar solution.
 * Runs in O(N^2) time since I run a 'for' loop to hunt for a no-dep project. The book example says it's O(N+M) time because
 *  it replaces the 'for' loop hunting with a while loop that retroactively looks for nodes that have zero-deps after a change.
 *  This will be also like mine - O(N^2)
 * I use CopyOnWriteArrayList for concurrent modification. This is costly since it creates a new copy everytime it's modified.
 * The book uses arrays and modifies a counter instead of a list.
 * I should have an elegant method to feed data. Too many calls and snippets in main doesn't look good.
 * DFS will also work, but instead of finding if there is a single path that can give the build order. Find each path, then keep
 * removing the path just like the other approach. It has the same runtime.
 * Be open to consider variations to regular flows of DFS, flipping directions for nodes for specific use cases.
 *
 */
public class Question4_7_Attempt2 {
    public static void main(String[] args) {
        Graph g = new Graph();

        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");

        List<List<Node>> dependencyList = List.of(List.of(a, d), List.of(f, b), List.of(b, d), List.of(f, a), List.of(d, c));
        addDependencies(dependencyList);

        CopyOnWriteArrayList<Node> nodeList = new CopyOnWriteArrayList<>();
        nodeList.add(a);
        nodeList.add(b);
        nodeList.add(c);
        nodeList.add(d);
        nodeList.add(e);
        nodeList.add(f);

        g.nodes = nodeList;

        assertEquals("[e, f, a, b, d, c]", g.findBuildOrder().toString());

        Graph g2 = new Graph();

        Node x = new Node("x");
        Node y = new Node("y");
        Node z = new Node("z");
        Node w = new Node("w");
        List<List<Node>> dependencyList2 = List.of(List.of(x, y), List.of(y, x), List.of(y, z));
        addDependencies(dependencyList2);

        CopyOnWriteArrayList<Node> nodeList2 = new CopyOnWriteArrayList<>();
        nodeList2.add(x);
        nodeList2.add(y);
        nodeList2.add(z);
        nodeList2.add(w);

        g2.nodes = nodeList2;
        assertEquals("[]", g2.findBuildOrder().toString());
    }

    static void addDependencies(List<List<Node>> dependencyList) {
        // Add validation - throw error if not a pair
        for(List<Node> pair: dependencyList) {
            pair.get(1).connectedNodes.add(pair.get(0));
        }
    }

    static class Node {
        String name;
        CopyOnWriteArrayList<Node> connectedNodes;

        public Node(String name) {
            this.name = name;
            this.connectedNodes = new CopyOnWriteArrayList<>();
        }
    }

    static class Graph {
        CopyOnWriteArrayList<Node> nodes;

        List<String> findBuildOrder() {
            List<String> buildOrder = removeIndepNode();
            if(buildOrder.size() < nodes.size()) {
                return List.of(); // Error. Circular dependency found.
            } else {
                return buildOrder;
            }
        }

        private List<String> removeIndepNode() {
            List<String> buildOrder = new CopyOnWriteArrayList<>();
            for(Node node : nodes) {
                if(node.connectedNodes.isEmpty()) {
                    removeNodeFromDepsEverywhere(node);
                    nodes.remove(node);

                    buildOrder.add(node.name);
                    System.out.println("Node added to build order: " + node.name);
                    buildOrder.addAll(removeIndepNode());
                    break;
                }
            }


            return buildOrder;
        }

        private void removeNodeFromDepsEverywhere(Node nodeToRemove) {
            for(Node node: nodes) {
               for(Node connNode: node.connectedNodes) {
                   if(connNode == nodeToRemove) {
                       node.connectedNodes.remove(connNode);
                       System.out.println(String.format("Removed %s from %s's connected nodes: ", connNode.name, node.name));
                   }
               }
            }
        }
    }
}

/**
 * The solution suggests topological sort. Trying it without peeking at solution:
 *
 * removeIndepNode(order):
 * iterate through graph's nodes
 *   find a node with no deps i.e., no incoming nodes
 *      remove node from graph, remove its mentions everywhere
 *      add it to order
 *      removeIndepNode(order)
 *      break
 *
 * return order
 *
 *  result = removeIndepNode()
 *
 *  Node:
 *  name
 *  incoming nodes ==> deps
 *
 *  e.g., Node api. api.deps = [lib1]
 *
 *  api <--- lib1
 */