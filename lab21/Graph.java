import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.IOException;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }


    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }
    /*
    public Graph prims(int start) {
        HashMap<Integer, Edge> distFromTree = new HashMap<Integer, Edge>(); // vertices to distance from start vertex
        int[] predecessors = new int[vertexCount];
        predecessors[start] = start;
        PriorityQueue<PriorityItem> pq = new PriorityQueue<>();
        ArrayList<Integer> visitedVerts = new ArrayList<>();
        vertexDistToStartVertex.put(start, 0);
        pq.add(new PriorityItem(start, vertexDistToStartVertex));
        int closestVertex = start; //ensures this int is global, as in it does not "disappear" after the while loop is terminated
        while (!pq.isEmpty()) { //loop until fringe is empty
            closestVertex = pq.poll().node; // closestVertex is the vertex in fringe w/shortest distance to start (or highest priority). Remove and hold onto v.
            visitedVerts.add(closestVertex); //
            if (closestVertex == stop) { //exit loop if the closest vertex is the stop node
                break;
            }
            for (int neighbor : neighbors(closestVertex)) { // get edge for each neighbor
                Edge e = getEdge(closestVertex, neighbor);
                // int distance = adjLists[closestVertex].get(i).weight;
                // neighbor
                if (!visitedVerts.contains(neighbor)) { //ensures we dont visit the same node twice
                    if (!vertexDistToStartVertex.containsKey(neighbor)){ //same as above, but for the PriorityQueue data structure
                        vertexDistToStartVertex.put(neighbor, e.weight + vertexDistToStartVertex.get(closestVertex));
                        pq.add(new PriorityItem(neighbor, vertexDistToStartVertex)); //adds neighbor to PriorityQueue (it is not there already)
                        predecessors[neighbor] = closestVertex;

                    } else {
                        int newDistance = e.weight + vertexDistToStartVertex.get(closestVertex);
                        if (newDistance < vertexDistToStartVertex.get(neighbor)){
                            vertexDistToStartVertex.put(neighbor, newDistance);
                            predecessors[neighbor] = closestVertex;
                            pq.add(new PriorityItem(neighbor,vertexDistToStartVertex));
                        }
                    }
                }
            }
        }
     */
    public static class PriorityItem implements Comparable<PriorityItem> {
        public int node;
        public int distance;
        private PriorityItem(int node, HashMap<Integer, Edge> distFromTree){
            this.node = node;
            this.distance = distFromTree.get(node).getWeight();
        }

        @Override
        public int compareTo(PriorityItem n){
            if (this.distance < n.distance) {
                return -1;
            }
            if (this.distance > n.distance){
                return 1;
            }
            return 0;
        }
    }
    public Graph prims(int start) {
        Graph t = new Graph();
        HashMap<Integer, Edge> distFromTree = new HashMap<Integer, Edge>(); // maps weight to edge
        t.addVertex(start);
        //PriorityQueue<Integer> pq = new PriorityQueue<>();
        // distFromTree.put(start, null);
        // take all edges that connect vertex to surrounding vertices, add weights to priority queue. pop lowest weight and put into hash map, which gives us specific edge w/weight

        // pq.add()); // when we add to pq, map to distFromTree
        // for (Edge e : edges.getValues(node)
        // add the weights of the neighboring edges of the weights' nodes that you have popped off
        // take off 2, 2--> edge 0-3, graph.contains the source or dest vertices and add hwihc one isnt there
        //while (t.allEdges.size() < this.getAllVertices().size() - 1) {
        //while (!pq.isEmpty()){

        while (t.allEdges.size() < this.getAllVertices().size() - 1) {
            // Add smallest edge of G that has one vertex inside of T to T, called edge E.
            for (Edge i : this.allEdges) { //allEdges is a sorted set of edges
                if (t.containsVertex(i.getSource()) && !t.containsVertex(i.getDest())) {
                    // Contains source, does NOT contain destination
                    t.addEdge(i);
                    t.addVertex(i.getDest());
                    break;
                } else if (!t.containsVertex(i.getSource()) && t.containsVertex(i.getDest())) {
                    // Contains destination, does NOT contain source
                    t.addEdge(i);
                    t.addVertex(i.getSource());
                    break;
                }
            }
        }
            // After adding E, add vertex on E not in T to T.


        return t;
    }

    public Graph kruskals() {
        List<Edge> MST = new ArrayList<>();

        DisjointSet ds = new DisjointSet();
        










        /*
        Graph T = new Graph();
        ArrayList alreadyVisited = new ArrayList();
        for (int i: this.getAllVertices()){
            T.addVertex(i);
        }
        ArrayList<Edge> listOfEdges = new ArrayList(this.allEdges);
        Collections.sort(listOfEdges);
            for (Edge e : listOfEdges) {
                if (!T.isNeighbor(e.getSource(), e.getDest())) {
                    if (!alreadyVisited.contains(e.getDest())) {
                        T.addEdge(e);
                        alreadyVisited.add(e.getSource());
                    }
                }
            }
        return T;

         */
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}