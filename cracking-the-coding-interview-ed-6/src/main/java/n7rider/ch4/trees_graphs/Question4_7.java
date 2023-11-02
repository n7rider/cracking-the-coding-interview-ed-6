package n7rider.ch4.trees_graphs;

import java.util.*;
import java.util.stream.Collectors;

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
 * Mine will not work with cycles. We can check with pathSoFar with current node to see if we have cycles
 * Is there a better way to track pathSoFar than to use String with _ as delimiters?
 * Mine has the right count of output but returns duplicates too. Output is [e, f, f, b, d, c]
 *
 * After comparing with solution:
 * Mine has the right count but won't work since this is not about finding a chain from no-dep node to final node. But
 *  * multiple paths can solve different pieces of a problem like a Psi symbol. So, there may not be one big path for DFS
 *  * to find.
 *  The solution uses topological sort (what I call as Civ tree). TODO Try this.
 *
 */
public class Question4_7 {
    public static void main(String[] args) {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");

        a.connectedNodes = List.of(d);
        f.connectedNodes = List.of(b, a);
        b.connectedNodes = List.of(d);
        d.connectedNodes = List.of(c);

        List<Node> nodes = List.of(a, b, c, d, e, f);
        Graph g = new Graph();
        g.nodes = nodes;

        System.out.println(g.findBuildOrder());
    }

    static class Graph {
        List<Node> nodes;
        // indicate all the projects dependent on current project
        Set<String> independentProjects;

        List<String> findBuildOrder() {

            independentProjects = findIndependentProjects();
            List<String> outputList = new ArrayList<>();
            for(Node node : nodes) {
                clearAllNodes();
                String output = performDfsForBuildPath(node, node.name, 1);
                if(output != null) {
                    outputList = convertToListAndAddUnconnNodes(output);
                    break;
                }
            }

            return outputList;
        }

        private List<String> convertToListAndAddUnconnNodes(String string) {
            List<String> output = new ArrayList<>(independentProjects);
            output.addAll(Arrays.asList(string.split("_")));
            return output;
        }

        private void clearAllNodes() {
            for(Node node : nodes) {
                node.visited = false;
            }
        }

        private Set<String> findIndependentProjects() {
            Set<String> independentProjects = nodes.stream().map(node -> node.name).collect(Collectors.toSet());
            for(Node node: nodes) {
                for(Node connNode : node.connectedNodes) {
                    independentProjects.remove(connNode.name);
                }
            }
            System.out.println("Independent projects: " + independentProjects);
            return independentProjects;
        }

//        private String performDfsForBuildPath(Node node, List<String> path, int pathLength) {
//            node.visited = true;
//            if(pathLength == nodes.size() - independentProjects.size()) {
//                System.out.println("Path found. Path = " + path);
//                return null;
//            }
//
//            System.out.println("Path so far - " + path + ". Length - " + pathLength);
//
//            String output = null;
//            for(Node connNode: node.connectedNodes) {
//                if(!connNode.visited) {
//                    path.add(connNode.name);
//                    output = performDfsForBuildPath(connNode, path, pathLength + 1);
//                    if(output != null) {
//                        break;
//                    }
//                }
//            }
//            return output;
//        }

        private String performDfsForBuildPath(Node node, String path, int pathLength) {
            node.visited = true;
            if(pathLength == nodes.size() - independentProjects.size()) {
                System.out.println("Path found. Path = " + path);
                return path;
            }

            System.out.println("Path so far - " + path + ". Length - " + pathLength);

            String output = null;
            for(Node connNode: node.connectedNodes) {
                if(!connNode.visited) {
                    output = performDfsForBuildPath(connNode, path + "_" + connNode.name, pathLength + 1);
                    if(output != null) {
                        break;
                    }
                }
            }
            return output;
        }


    }

    static class Node {
        String name;
        List<Node> connectedNodes;
        boolean visited;

        public Node(String name) {
            this.name = name;
            this.connectedNodes = new ArrayList<>();
        }
    }

}

/**
 * Create project with name, List<Project> deps
 * Construct a map of <Int, Project>
 * Create a list of builtProjects
 * for int i = 0 to n in map's key
 *   fix deps and check if all projects have zero dep
 *   keep adding to list
 * ---
 *
 * |----> a ---v
 * f --> b --> d ---> c
 *
 * Project {
 *     char name
 *     List<Project> deps
 * }
 *
 * init:
 *   create object for each project
 *   for each dep in depsList:
 *     add to deps
 *
 * construct_structures
 * for each proj in projects:
 *   add to map <int, projects> as deps.size, project
 *
 * determineOrder:
 * create list<Project> buildOrder
 * sort keySet in asc order
 * for each entry in keySet
 *   if entry == 0
 *     add to buildOrder
 *     return
 *   for each proj in mapVal list:
 *     for i = 0 to 1 // Do twice - first to remove deps, then to add
 *     for each dep in projDeps:
 *       if buildOrder.contains(dep)
 *         remove dep from project
 *
 * // This is similar to using adjacency matrix
 *
 * ---
 *
 * Graph way:
 * Create a list of unconnected node
 *
 * From each node in nodes
 *   Check if there's no cycle
 *   Do DFS from each node
 *      At each pathSoFar, check if length = total - unconnected
 *
 * DFS()
 *  for each node in allNodes
 *    setup visited = false for all nodes
 *    DFSinternal(n, '')
 *
 * DFSinternal(n, path)
 *  for each currNode in n.connNodes
 *    path += currNode
 *    if !currNode.isVisited
 *    DFSinternal(currNode, path)
 *
 *
 *
 *
 *
 */