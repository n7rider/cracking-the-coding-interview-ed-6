package n7rider.ch4.trees_graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * 4.1
 * Route Between Nodes: Given a directed graph, design an algorithm to find out whether there is a
 * route between two nodes.
 * ---
 * Post-run observations:
 *
 *
 * After comparing with solution:
 * Similar
 * ChatGPT suggested starting the search only from the startNode, so added this. This means BFS will not be effective
 * for this. There's no use looking into neighbors at the start, both we know the result is in the child of the start node.
 *
 */
public class Question4_1 {

    public static void main(String[] args) {
        Graph g = new Graph();
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");

        a.connectedNodes = List.of(b);
        b.connectedNodes = List.of(c);
        c.connectedNodes = List.of(a);
        d.connectedNodes = List.of(c);

        g.nodes = List.of(a, b, c, d);

        System.out.println("Iteration 1");
        dfs(g, List.of("c", "d"));

        System.out.println("Iteration 2");
        dfs(g, List.of("d", "a"));
    }


    static class Node {
        String name;
        List<Node> connectedNodes;
        boolean visited;

        Node(String name) {
            this.name = name;
        }

    }

    static class Graph {
        List<Node> nodes;
    }

    static void dfs(Graph g, List<String> searchedNodes) {
        clearAllNodes(g);
        String pathSoFar = "";
        dfs(g, searchedNodes, pathSoFar);
    }

    private static void dfs(Graph g, List<String> searchedNodes, String pathSoFar) {

        for(Node n : g.nodes) {
            if(searchedNodes.get(0).equals(n.name)) {
                visit(n, searchedNodes, pathSoFar);
            }
        }
    }

    private static void visit(Node n, List<String> searchedNodes, String pathSoFar) {
        pathSoFar = pathSoFar.length() == 0 ? n.name : pathSoFar.concat("_").concat(n.name);
        n.visited = true;

        boolean isPathFound = foundPath(searchedNodes, pathSoFar);
        if(isPathFound) {
            System.out.println("Path found " + pathSoFar);
        }

        for(Node connNode : n.connectedNodes) {
            if(!connNode.visited) {
                visit(connNode, searchedNodes, pathSoFar);
            }
        }
    }

    private static boolean foundPath(List<String> searchedNodes, String pathSoFar) {
        String[] pathSoFarArr = pathSoFar.toString().split("_");
        if(pathSoFarArr.length < 2) {
            return false;
        } else {
            String firstNode = pathSoFarArr[0];
            String lastNode = pathSoFarArr[pathSoFarArr.length - 1];

            return firstNode.equals(searchedNodes.get(0)) &&
                    lastNode.equals(searchedNodes.get(1));
        }
    }

    private static void clearAllNodes(Graph g) {
        for(Node n : g.nodes) {
            n.visited = false;
        }
    }





}

/**
 * Algorithm
 * - Construct adjacency matrix
 *  - list is complex for this purpose, where we search and replace often
 *  - but each node should have a unique number id (Or we can initially have a hash map but more work)
 * - Construct adjacency list
 *   - can have enums as names or ids (or String, but more code)
 *   - look up is always O(n), but with directed graph, we'll go through and OR n rows for n times
 * Conclusion: Go with matrix (just create a hash map before then)
 * Conclusion changed: Go with list (it's not too difficult to use ids)
 * x 1 0 0
 * 0 x 1 0
 * 0 0 x 1
 * 0 1 0 x
 * - In each row of matrix, if val = 1, go to the corr row (e.g., go to row 2 if origin's[row][2]= 1
 * - Do OR of this row with every other row.
 * - Repeat this for n-1 times (all columns in origin with non-zero vals)
 * - We are figuring new connections, so repeat (go up to max of n-1 times, so O(n^2) complexity for each row)
 *
 * - Now check if origin has a connection towards destination in its row.
 *
 * Graph
 * Node[] nodes
 *
 * Node
 * String name
 * Node[] connNodes
 *
 * Adjacency list:
 * - The graph itself is an adjacency list (but create Set for ease of use)
 *
 * for each node in nodes   //  -- O(n) complexity
 *   for each connNode in node.connNodes    //  -- O(n) complexity
 *      find in nodes where node=connNode    //  -- O(n) complexity. A matrix would avoid this
 *      call it as otherNode
 *        for each connNode in otherNode:    //  -- O(n) complexity
 *           node.connNodes.add(otherNode.connNode)
 *
 * So, it is ideally O(n^3) in matrix, but is O(n^4) with list
 *
 * ---
 *
 * Looked up the book, and will try DFS and BFS
 *
 * DFS
 * for each node in nodes
 *   visit(node, path-so-far)
 *
 * visit node, path-so-far:
 *
 * for each connNode in connNodes
 *   node.visited = true
 *   path-so-far += node
 *
 *   if(path-so-far == expectedPath) return
 *
 *   if(!connNode.visited)
 *      visit (node, path-so-far)
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
