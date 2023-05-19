import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Depei Yu
 * @version 1.0
 * @userid dyu79
 * @GTID 903312858
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph.");
        }

        // Initialize
        Queue<Vertex<T>> toVisit = new LinkedList<>();
        List<Vertex<T>> listToReturn = new ArrayList<>();
        Set<Vertex<T>> alreadyVisited = new HashSet<>();


        // Start at the start vertex
        toVisit.add(start);
        alreadyVisited.add(start);

        // Traverse the graph
        while (!toVisit.isEmpty()) {
            // Pop the top item of the queue of vertices to visit
            Vertex<T> tempVertex = toVisit.remove();

            // Add this list to the list to return
            listToReturn.add(tempVertex);

            // Loop through adjacent vertices
            for (VertexDistance<T> temp : graph.getAdjList().get(tempVertex)) {
                if (!alreadyVisited.contains(temp.getVertex())) {
                    toVisit.add(temp.getVertex());
                    alreadyVisited.add(temp.getVertex());
                }
            }
        }
        return listToReturn;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph.");
        }

        // Initialize
        List<Vertex<T>> listToReturn = new ArrayList<>();
        Set<Vertex<T>> alreadyVisited = new HashSet<>();

        // Helper method
        dfsHelper(start, graph, alreadyVisited, listToReturn);

        return listToReturn;
    }

    /**
     * Helper method for dfs().
     * @param vertex the vertex to start at
     * @param graph the graph to search
     * @param alreadyVisited the list of nodes that are already visited
     * @param listToReturn the list to return in the main method that calls this helper method
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> vertex,
                                      Graph<T> graph,
                                      Set<Vertex<T>> alreadyVisited,
                                      List<Vertex<T>> listToReturn) {

        // Start at the specified vertex
        alreadyVisited.add(vertex);
        listToReturn.add(vertex);

        // Recursively loop through adjacent vertices
        for (VertexDistance<T> temp : graph.getAdjList().get(vertex)) {
            if (!alreadyVisited.contains(temp.getVertex())) {
                dfsHelper(temp.getVertex(), graph, alreadyVisited, listToReturn);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start cannot be null.");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph.");
        }

        // Initialize VisitedSet
        Set<Vertex<T>> visitedSet = new HashSet<>();

        // Initialize DistanceMap
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();

        // Initialize PriorityQueue
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();

        // Initialize distances to INF for all nodes
        for (Vertex<T> temp : graph.getVertices()) {
            if (temp == start) {
                distanceMap.put(temp, 0);
            } else {
                distanceMap.put(temp, Integer.MAX_VALUE);
            }
        }

        // Enqueue start node to priorityQueue with a distance of 0
        priorityQueue.add(new VertexDistance<>(start, 0));

        // While PriorityQueue is not empty and VisitedSet is not full
        while (!priorityQueue.isEmpty() && (visitedSet.size() < graph.getVertices().size())) {
            // Dequeue from PriorityQueue
            VertexDistance<T> temp = priorityQueue.remove();

            // Extract vertex u and distance d from the dequeued object
            Vertex<T> u = temp.getVertex();
            int d = temp.getDistance();

            // If u is not visited in VisitedSet
            if (!visitedSet.contains(u)) {

                // Mark u as visited in VisitedSet
                visitedSet.add(u);

                // Update DistanceMap for u with new shortest path d
                distanceMap.replace(u, d);

                // for all adjacent to u and not visited in VisitedSet
                for (VertexDistance<T> vd : graph.getAdjList().get(u)) {
                    if (!visitedSet.contains(vd.getVertex())) {
                        // Enqueue
                        priorityQueue.add(new VertexDistance<>(vd.getVertex(), d + vd.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        // Initialize DisjointSet
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();

        // Initialize EdgeSet
        Set<Edge<T>> edgeSet = new HashSet<>();

        // Initialize PriorityQueue
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<Edge<T>>();

        // Add all edges in graph to PriorityQueue
        for (Edge<T> temp : graph.getEdges()) {
            priorityQueue.add(temp);
        }

        // While PriorityQueue is not empty and MST has fewer than n - 1 edges
        while (!priorityQueue.isEmpty() && edgeSet.size() != 2 * (graph.getVertices().size() - 1)) {
            // Dequeue from PriorityQueue
            Edge<T> tempEdge = priorityQueue.remove();

            // Get u and v
            Vertex<T> uTemp = tempEdge.getU();
            Vertex<T> vTemp = tempEdge.getV();

            // If u and v are not in the same cluster
            if (disjointSet.find(uTemp) != disjointSet.find(vTemp)) {
                // Add edge(u, v) to MST
                edgeSet.add(new Edge<>(uTemp, vTemp, tempEdge.getWeight()));

                // Also add edge(v, u) due to assumption that graph is undirected
                edgeSet.add(new Edge<>(vTemp, uTemp, tempEdge.getWeight()));

                // Merge u's cluster with v's cluster
                disjointSet.union(uTemp, vTemp);
            }
        }

        // Check that the MST is of valid size
        if (edgeSet.size() == 2 * (graph.getVertices().size() - 1)) {
            return edgeSet;
        } else {
            return null;
        }
    }
}
