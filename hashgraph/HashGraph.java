package kth.csc.inda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 * 
 * @author Adam Jacobs
 * @version Feb 2017
 */
public class HashGraph implements Graph {
    /**
     * The map edges[v] contains the key-value pair (w, c) if there is an edge
     * from v to w; c is the cost assigned to this edge. The maps may be null
     * and are allocated only when needed.
     */
    private final Map<Integer, Integer>[] edges;
    private final static int INITIAL_MAP_SIZE = 4;

    /**
     * Number of edges in the graph.
     */
    private int numEdges;

    /**
     * Constructs a HashGraph with n vertices and no edges. Time complexity:
     * O(n)
     *
     * @throws IllegalArgumentException if n < 0
     */
    public HashGraph(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n = " + n);

        // The array will contain only Map<Integer, Integer> instances created
        // in addEdge(). This is sufficient to ensure type safety.
        @SuppressWarnings("unchecked")
        Map<Integer, Integer>[] a = new HashMap[n];
        edges = a;

        for(int i = 0; i < a.length; i++) {
            edges[i] = new HashMap<>();
        }
    }

    /**
     * Add an edge without checking parameters.
     */
    private void addEdge(int from, int to, int cost) {
        if (edges[from] == null)
            edges[from] = new HashMap<Integer, Integer>(INITIAL_MAP_SIZE);
        if (edges[from].put(to, cost) == null)
            numEdges++;
    }

    /**
     * Remove an edge without checking parameters
     */
    private void removeEdge(int from, int to) {
        if(edges[from].remove(to) != null) {
            numEdges--;
        }
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public int numVertices() {
        return edges.length;
    }

    /**
     * {@inheritDoc Graph} Time complexity: O(1).
     */
    @Override
    public int numEdges() {
        return numEdges;
    }

    /**
     * Check if a vertex is out of bounds
     *
     * @param v the vertex
     * @return true if out of bounds, otherwise false
     */
    private boolean vertexOutOfBounds(int v) {
        return (v < 0 || v >= numVertices());
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public int degree(int v) throws IllegalArgumentException {
        if (vertexOutOfBounds(v)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        return edges[v].size();
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public VertexIterator neighbors(int v) {
        if (vertexOutOfBounds(v)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        return new neighborIterator(v);
    }

    private class neighborIterator implements VertexIterator {
        private Iterator<Map.Entry<Integer, Integer>> iteratorOfAdj;

        public neighborIterator(int v) {
            iteratorOfAdj = edges[v].entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return iteratorOfAdj.hasNext();
        }

        @Override
        public int next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("no such element");
            }

            return iteratorOfAdj.next().getKey();
        }
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public boolean hasEdge(int v, int w) {
        if (vertexOutOfBounds(v)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        return edges[v].containsKey(w);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public int cost(int v, int w) throws IllegalArgumentException {
        if (vertexOutOfBounds(v)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        Integer fetchedCost = edges[v].get(w);

        if (fetchedCost == null) {
            return NO_COST;
        }

        return fetchedCost;

    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void add(int from, int to) throws IllegalArgumentException {
        if (vertexOutOfBounds(from)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        addEdge(from, to, NO_COST);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void add(int from, int to, int c) throws IllegalArgumentException {
        if (vertexOutOfBounds(from) || c < 0) {
            throw new IllegalArgumentException("Illegal argument");
        }

        addEdge(from, to, c);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void addBi(int v, int w) {
        if (vertexOutOfBounds(v) || vertexOutOfBounds(w)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        addEdge(v, w, NO_COST);
        addEdge(w, v, NO_COST);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void addBi(int v, int w, int c) {
        if (vertexOutOfBounds(v) || vertexOutOfBounds(w) || c < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        addEdge(v, w, c);
        addEdge(w, v, c);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void remove(int from, int to) {
        if (vertexOutOfBounds(from)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        removeEdge(from, to);
    }

    /**
     * {@inheritDoc Graph}
     */
    @Override
    public void removeBi(int v, int w) {
        if (vertexOutOfBounds(v) || vertexOutOfBounds(w)) {
            throw new IllegalArgumentException("Vertex out of bounds!");
        }

        removeEdge(v, w);
        removeEdge(w, v);
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return a String representation of this graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(edges.length == 0) {
            return "{}";
        }

        sb.append("{");

        for(int i = 0; i < edges.length; i++) {
            for(Map.Entry e : edges[i].entrySet()) {
                if(e.getValue().equals(NO_COST)) {
                    sb.append("(" + i + "," + e.getKey() + "), ");
                } else {
                    sb.append("(" + i + "," + e.getKey() + "," + e.getValue() + "), ");
                }
            }
        }

        sb.delete(sb.length()-2, sb.length());
        sb.append("}");

        return sb.toString();
    }


}