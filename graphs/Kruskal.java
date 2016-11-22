import java.util.*;

/** A class that runs Kruskal's algorithm on a Graph. Given a graph G, Kruskal's
 *  algorithm constructs a new graph T such T is a spanning tree of G and the
 *  sum of its edge weights is less than the sum of the edge weights for
 *  every possible spanning tree T* of G. This is called the Minimum Spanning
 *  Tree (MST).
 *
 */
public class Kruskal {

    /** Returns the MST of INPUT using a naive isConnected implementation. */
    public static Graph minSpanTree(Graph input) {
        TreeSet<Edge> edgesByWeight = input.getAllEdges();
        List<Edge> edges = new LinkedList<>();
        for (Edge y : edgesByWeight) {
            edges.add(y);
        }
        Collections.sort(edges, (Edge o1, Edge o2) -> o1.compareTo(o2));
        Graph newGraph = new Graph();
        ArrayList<Integer> visited = new ArrayList<>();
        for (Edge x : edgesByWeight) {
            if (!visited.contains(x.getSource()) || !visited.contains(x.getSource())
                    && (!isConnected(input, x.getDest(), x.getSource()))) {
                newGraph.addEdge(x);
            }
        }
        return newGraph;
    }

    /** Returns the MST of INPUT using the Union Find datastructure. */
    public static Graph minSpanTreeFast(Graph input) {
        TreeSet<Edge> edgesByWeight = input.getAllEdges();
        List<Edge> edges = new LinkedList<>();
        for (Edge y : edgesByWeight) {
            edges.add(y);
        }
        Collections.sort(edges, (Edge o1, Edge o2) -> o1.compareTo(o2));
        Graph newGraph = new Graph();
        ArrayList<Integer> visited = new ArrayList<>();
        for (Edge x : edgesByWeight) {
            if ((!visited.contains(x.getSource()) || !visited.contains(x.getSource()))
                    && !isConnected(input, x.getDest(), x.getSource())) {
                newGraph.addEdge(x);
            }
        }
        return newGraph;
    }

    /** A naive implementation of BFS to check if two nodes are connected. */
    public static boolean isConnected(Graph g, int v1, int v2) {
        // TODO implement!
        if (!g.containsVertex(v1) || !g.containsVertex(v2)) {
            return false;
        }
        if (g.isNeighbor(v1, v2)) {
            return true;
        }
        TreeSet<Integer> allVertices = g.getAllVertices();
        LinkedList<Integer> visited = new LinkedList<>();
        for (Integer z : allVertices) {
            visited.add(z);
        }
        while (visited.poll() != null) {
            int curr = visited.pop();
            if (curr == v1) {
                Set<Integer> neighbors = g.neighbors.get(curr);
                for (Integer y : neighbors) {
                    if (!visited.contains(y)) {
                        visited.add(y);
                    }
                }
                if (neighbors.contains(v2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
