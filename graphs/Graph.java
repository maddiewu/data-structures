/**
 * Created by madelinewu on 7/31/16.
 * Implementation of a graph that uses Dijkstra's Algorithm (can find the shortest path between two vertices),
 * can determine if a path exists between two given vertices, determine the distance between two edges, 
 * can topologically sort, and can iterate through the graph using depth first search.
 */

import java.util.*;
import java.util.HashMap;

public class Graph implements Iterable<Integer>{

    private LinkedList<Edge>[] adjLists; //linked list of adjacent edges
    private int vertexCount;
    private int startVertex;

    // Initialize a graph with the given number of vertices and no edges.
    public Graph(int numVertices) {
        adjLists = new LinkedList[numVertices];
        startVertex = 0;
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    // Change the vertex the iterator will start DFS from
    public void setStartVertex(int v){
        if (v < vertexCount && v >= 0){
            startVertex = v;
        } else {
            throw new IllegalArgumentException("Cannot set iteration start vertex to " + v + ".");
        }
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2.
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, null);
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2.
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, null);
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2,
    // with the given edge information.
    public void addEdge(int v1, int v2, Object edgeInfo) {
        adjLists[v1].add(new Edge(v1, v2, edgeInfo));
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2,
    // with the given edge information.
    public void addUndirectedEdge(int v1, int v2, Object edgeInfo) {
        adjLists[v1].add(new Edge(v1, v2, edgeInfo));
        adjLists[v2].add(new Edge(v2, v1, edgeInfo));
    }

    // Return true if there is an edge from vertex "from" to vertex "to";
    // return false otherwise.
    public boolean isAdjacent(int from, int to) {
        for (Edge x: adjLists[from]) {
            if (x.to == to) {
                return true;
            }
        }
        return false;
    }

    // Returns a list of all the neighboring  vertices 'u'
    // such that the edge (VERTEX, 'u') exists in this graph.
    public List neighbors(int vertex) {
        LinkedList<Integer> neighbors = new LinkedList<>();
        for (Edge x: adjLists[vertex]) {
            neighbors.add(x.to);
        }
        return neighbors;
    }

    // Return the number of incoming vertices for the given vertex,
    // i.e. the number of vertices v such that (v, vertex) is an edge.
    public int inDegree(int vertex) {
        int count = 0;
        for (int i = 0; i < adjLists.length; i++) {
            for (Edge x: adjLists[i]) {
                if (x.to == vertex) {
                    count++;
                }
            }
        }
        return count;
    }

    public Iterator<Integer> iterator(){
        return new TopologicalIterator();
    }

    // A class that iterates through the vertices of this graph, starting with a given vertex.
    // Does not necessarily iterate through all vertices in the graph: if the iteration starts
    // at a vertex v, and there is no path from v to a vertex w, then the iteration will not
    // include w
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;
        Edge startEdge;
        int listIndex = 1;
        int arrayIndex = 0;

        public DFSIterator(Integer start) {
            visited = new HashSet<>();
            fringe = new Stack<>();
            fringe.add(start);
            visited.add(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                return true;
            }
            return false;
        }

        public Integer next() {
            if (hasNext()) {
                Integer next = fringe.pop();

                while (visited.contains(next)) {
                    if (!fringe.isEmpty()) {
                        next = fringe.pop();
                        if (!visited.contains(next)) {
                            visited.add(next);
                            return next;
                        }
                    }
                    break;
                }
                if (!visited.contains(next)) {
                    visited.add(next);

                }
                LinkedList<Integer> neighbors = (LinkedList) neighbors(next);
                Collections.sort(neighbors, (obj1, obj2) -> Integer.compare(obj1, obj2));
                while (!neighbors.isEmpty()) {
                    Integer temp = neighbors.removeLast();
                    if (!visited.contains(temp)) {
                        if (!fringe.contains(temp)) {
                            fringe.push(temp);
                        }
                    }
                }
                return next;
            }
            return null;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    // Return the collected result of iterating through this graph's
    // vertices as an ArrayList.
    public ArrayList<Integer> visitAll(int startVertex) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(startVertex);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    // Returns true iff there exists a path from STARVETEX to
    // STOPVERTEX. Assumes both STARTVERTEX and STOPVERTEX are
    // in this graph. If STARVERTEX == STOPVERTEX, returns true.
    public boolean pathExists(int startVertex, int stopVertex) {
        ArrayList<Integer> check = visitAll(startVertex);
        if (check.contains(stopVertex)) {
            return true;
        }
        return false;
    }


    // Returns the path from startVertex to stopVertex.
    // If no path exists, returns an empty arrayList.
    // If startVertex == stopVertex, returns a one element arrayList.

    /*Pattern your method on visitAll, with the following differences.
    First, add code to stop calling next when you encounter the finish vertex.
    Then, trace back from the finish vertex to the start,
    by first finding a visited vertex u for which (u, finish) is an edge,
    then a vertex v visited earlier than u for which (v, u) is an edge,
    and so on, finally finding a vertex w for which (start, w) is an edge.
    Collecting all these vertices in the correct sequence produces the desired path.
    We recommend that you try this by hand with a graph or two to see that it works.*/
    public ArrayList<Integer> path(int startVertex, int stopVertex) {
        ArrayList<Integer> finalPath = new ArrayList<>();

        if (!pathExists(startVertex, stopVertex)) {
            return finalPath;
        } else if (isAdjacent(startVertex, stopVertex)) {
            finalPath.add(startVertex);
            finalPath.add(stopVertex);
            return finalPath;
        } else {
            Iterator<Integer> iter1= new DFSIterator(startVertex);
            while (iter1.hasNext()) {
                Integer add = iter1.next();
                finalPath.add(add);
                if (add == stopVertex) {
                    break;
                }
            }
            for (int x = finalPath.size() - 1; x > 0; x--) {
                if (!neighbors(finalPath.get(x - 1)).contains(finalPath.get(x))) {
                    finalPath.remove(x - 1);
                }
            }
            return finalPath;
        }
    }

    /*Topological sort. */
    public ArrayList<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /*Topological iterator. */
    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private Integer[] inDegreeArray;

        // more instance variables go here

        public TopologicalIterator() {
            fringe = new Stack<Integer>();
            inDegreeArray = new Integer[adjLists.length];
            for (int i = 0; i < inDegreeArray.length; i++) {
                inDegreeArray[i] = inDegree(i);
                if (inDegreeArray[i] == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            Integer next = fringe.pop();
            for (Edge x : adjLists[next]) {
                inDegreeArray[x.to]--;
                if (inDegreeArray[x.to] == 0) {
                    fringe.push(x.to);
                }
            }
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /*Shortest path (DIKKSTRA'S ALGORITHM). */
    public ArrayList<Integer> shortestPath(int startVertex, int endVertex) {
        ArrayList<Integer> finalPath = new ArrayList<>();
        ArrayList<Integer> vertices = visitAll(startVertex);
        ArrayList<Edge> nodeSet = new ArrayList<>();
        int shortestDistance = 1000;
        int counter = 0;

        /*The fringe is a stack. */
        Stack<Edge> fringe = new Stack<>();

        fringe.add(new Edge(startVertex, startVertex, 0));
        if (!vertices.contains(endVertex)) {
            return finalPath;
        } else if (startVertex == endVertex) {
            finalPath.add(startVertex);
            return finalPath;
        } else {
            while (!fringe.isEmpty()) {
                Edge curr = fringe.pop();
                if (curr.from == endVertex) {
                    break;
                } else if (curr.to != endVertex && counter == inDegree(endVertex)) {
                    break;
                }
                else {
                    PriorityQueue<Edge> neighbors = new PriorityQueue<Edge>(
                        (obj1, obj2) -> ((Integer) obj2.info() - (Integer) obj1.info()));
                    for (Edge x: adjLists[curr.to()]) {
                        if (x.from != endVertex) {
                            Edge addNeighbor = new Edge(x.from, x.to(), (Integer) x.info() + (Integer) curr.info());
                            neighbors.add(addNeighbor);
                        }
                    }
                    if (neighbors != null) {
                        for (Edge y : neighbors) {
                            boolean add = true;
                            if (!fringe.isEmpty()) {
                                for (Edge x : fringe) {
                                    if (Objects.equals(x, y)) {
                                        add = false;
                                    } else if (y.to() == x.from) {
                                        add = false;
                                    }
                                }
                            }

                            if (y.to() != startVertex && add && y.from != endVertex) {
                                if (!(counter >= inDegree(endVertex))) {

                                    Edge addEdge = new Edge (y.from, y.to(), y.info());
                                    if (!nodeSet.contains(addEdge)) {
                                        fringe.add(addEdge);
                                    }
                                    if (addEdge.to() == endVertex) {
                                        if ((Integer) addEdge.info() < shortestDistance) {
                                            shortestDistance = (Integer) addEdge.info();
                                            counter++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!fringe.isEmpty()) {
                    nodeSet.add(fringe.peek());
                }
            }
            /*After whole graph has been traversed through. */
            ArrayList<Edge> nodeSet2 = new ArrayList<>();
            for (int i = 0; i < nodeSet.size(); i++) {
                if ((Integer) nodeSet.get(i).info() <= shortestDistance) {
                    nodeSet2.add(nodeSet.get(i));
                }
            }

            for (int j = 0; j < nodeSet2.size(); j++) {
                if (nodeSet2.get(j).from == startVertex || nodeSet2.get(j).from == endVertex) {
                    if (!finalPath.contains(nodeSet2.get(j).from)) {
                        finalPath.add(nodeSet2.get(j).from);
                    }
                } else {
                    for (int k = 0; k < nodeSet2.size(); k++) {
                        if (nodeSet2.get(j).to() == nodeSet2.get(k).from || nodeSet2.get(j).to == endVertex) {
                            if (!finalPath.contains(nodeSet2.get(j).from)) {
                                finalPath.add(nodeSet2.get(j).from);
                            }
                        }
                    }
                }
            }
            finalPath.add(endVertex);
        }
        return finalPath;
    }

    /*Determine the distance between two edges. */
    public int distance(int n, int neighbor) {
        for (Edge x : adjLists[n]) {
            if (x.to == neighbor) {
                return (Integer) x.info();
            }
        }
        return 0;
    }

    private class Edge {

        private Integer from;
        private Integer to;
        private Object edgeInfo;

        public Edge(int from, int to, Object info) {

            this.from = from;
            this.to = to;
            edgeInfo = info;
        }

        public Integer to() {
            return to;
        }

        public Object info() {
            return edgeInfo;
        }

        public String toString() {
            return "(" + from + "," + to + ",dist=" + edgeInfo + ")";
        }

    }

    public static void main(String[] args) {
        ArrayList<Integer> result;

        Graph g6 = new Graph(5);
        g6.addEdge(0,1);
        g6.addEdge(1,2);
        g6.addEdge(2,3);
        g6.addEdge(3,4);
        g6.addEdge(4,0);
        result = g6.visitAll(0);
        Iterator<Integer> iter;
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }

        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(0, 4);
        g1.addEdge(1, 2);
        g1.addEdge(2, 0);
        g1.addEdge(2, 3);
        g1.addEdge(4, 3);
        System.out.println("Traversal starting at 0");
        result = g1.visitAll(0);
        /*Iterator<Integer> iter;*/
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 2");
        result = g1.visitAll(2);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 3");
        result = g1.visitAll(3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 4");
        result = g1.visitAll(4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 3");
        result = g1.path(0, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 4");
        result = g1.path(0, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 3");
        result = g1.path(1, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 4");
        result = g1.path(1, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 4 to 0");
        result = g1.path(4, 0);
        if (result.size() != 0) {
            System.out.println("*** should be no path!");
        }

        Graph g2 = new Graph(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(0, 4);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(4, 3);
        System.out.println();
        System.out.println();
        System.out.println("Topological sort");
        result = g2.topologicalSort();
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

}
