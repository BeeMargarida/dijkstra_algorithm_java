import java.util.*;

public class Dijkstra {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public Dijkstra(Graph graph) {
        // create a copy of the array so that we can operate on this array

        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Vertex source) {

        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        //predecessors = new HashMap<Vertex, Vertex>();

        distance.put(source, 0);
        unSettledNodes.add(source);

        List<Pair<Vertex, Integer>> prev = null;

        while (unSettledNodes.size() > 0) {

            Vertex node = getMinimum(unSettledNodes);

            settledNodes.add(node);
            unSettledNodes.remove(node);

            findMinimalDistances(node, prev);

        }
    }

    /*
    public Vertex getFirst(Set<Vertex> nodes) {

        for(Vertex n : nodes) {
            return n;
        }

        return null;
    }*/

    private void findMinimalDistances(Vertex node, List<Pair<Vertex, Integer>> prev) {
        List<Vertex> adjacentNodes = getNeighbors(node);

        for (Vertex target : adjacentNodes) {

            prev = target.getPreviousNodes();

            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {

                distance.put(target, getShortestDistance(node) + getDistance(node, target));

                target.setPrevious(node);
                if(prev == null){
                    prev = new ArrayList<>();
                }
                Pair<Vertex, Integer> newPrev = new Pair<>(node, getShortestDistance(node) + getDistance(node, target));
                prev.add(newPrev);
                target.setPreviousNodes(prev);

                unSettledNodes.add(target);

            }
            else if(getShortestDistance(target) == getShortestDistance(node) + getDistance(node, target)) {

                if(prev != null) {

                    Pair<Vertex, Integer> newPrev = new Pair<>(node, getShortestDistance(node) + getDistance(node, target));
                    prev.add(newPrev);
                }
                else {

                    Pair<Vertex, Integer> newPrev = new Pair<>(node, getShortestDistance(node) + getDistance(node, target));
                    prev.add(newPrev);
                    target.setPreviousNodes(prev);

                }
            }
        }

    }


    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();

        for (Edge edge : edges) {

            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {

                neighbors.add(edge.getDestination());

            }
        }
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;

        for (Vertex vertex : vertexes) {

            if (minimum == null) {
                minimum = vertex;

            } else {

                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }

            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }


    /**
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public List<Vertex> getShortestPath(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex);


        Collections.reverse(path);
        return path;
    }

    /**
     * Get All paths
     * @param target
     * @return
     */
    public Set<PairPaths<List<Vertex>, Integer>> getAllShortestPathsTo(Vertex target) {
        Set<PairPaths<List<Vertex>, Integer>> allShortestPaths = new HashSet<PairPaths<List<Vertex>, Integer>>();

        getShortestPath(new PairPaths<List<Vertex>, Integer>(new ArrayList<Vertex>(), 0), new Pair<Vertex,Integer>(target,0), allShortestPaths);

        return allShortestPaths;
    }

    /**
     * Recursive function to get all the paths
     * @param shortestPath
     * @param target
     * @param allShortestPaths
     * @return
     */
    private PairPaths<List<Vertex>, Integer> getShortestPath(PairPaths<List<Vertex>, Integer> shortestPath, Pair<Vertex, Integer> target, Set<PairPaths<List<Vertex>, Integer>> allShortestPaths) {
        //System.out.println("TARGET: " + target.toString() + " PREV: " + target.getVertex().getPreviousNodes() + " FF: " + shortestPath.getVertexes().toString());
        List<Pair<Vertex, Integer>> prev = target.getVertex().getPreviousNodes();

        if (prev == null) {
            shortestPath.addVertexes(target.getVertex());

            Collections.reverse(shortestPath.getVertexes());
            allShortestPaths.add(shortestPath);

        } else {
            PairPaths<List<Vertex>, Integer>updatedPath = new PairPaths<>((ArrayList<Vertex>) ((ArrayList<Vertex>) shortestPath.getVertexes()).clone(), shortestPath.getWeight());
            updatedPath.addVertexes(target.getVertex());
            //TODO: PROBLEM WITH THE TOTAL CALCULATION OF THE WEIGHT
            //System.out.println("WEIGHT: " + target.getWeight());
            if(updatedPath.getWeight() < target.getWeight()) {
                updatedPath.setWeight(target.getWeight());
            }

            for (Iterator<Pair<Vertex, Integer>> iterator = prev.iterator(); iterator.hasNext();) {

                Pair<Vertex, Integer> vertex = iterator.next();
                getShortestPath(updatedPath, vertex, allShortestPaths);
            }
        }
        return shortestPath;
    }


    private static void addLane(String laneId, int sourceLocNo, int destLocNo,
                         int duration, List<Edge> edges, List<Vertex> nodes) {
        Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }

    public static void main(String[] args) {

        List<Vertex> nodes = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();

        for (int i = 0; i < 11; i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 85, edges, nodes);
        addLane("Edge_1", 0, 2, 217, edges, nodes);
        addLane("Edge_2", 0, 4, 173, edges, nodes);
        addLane("Edge_3", 2, 6, 186, edges, nodes);
        addLane("Edge_4", 2, 7, 103, edges, nodes);
        addLane("Edge_5", 3, 7, 183, edges, nodes);
        addLane("Edge_6", 7, 5, 80, edges, nodes);
        addLane("Edge_6", 5, 8, 250, edges, nodes);
        addLane("Edge_7", 8, 9, 84, edges, nodes);
        addLane("Edge_8", 7, 9, 167, edges, nodes);
        addLane("Edge_9", 4, 9, 502, edges, nodes);
        addLane("Edge_10", 9, 10, 40, edges, nodes);
        addLane("Edge_11", 1, 10, 600, edges, nodes);

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        Dijkstra dijkstra = new Dijkstra(graph);

        dijkstra.execute(nodes.get(0));
        Set<PairPaths<List<Vertex>, Integer>> paths = dijkstra.getAllShortestPathsTo(nodes.get(10));


        System.out.println("AFTER");
        Iterator<PairPaths<List<Vertex>, Integer>> it = paths.iterator();
        while(it.hasNext()){

            PairPaths<List<Vertex>, Integer> next = it.next();
            System.out.println("HERE");

            System.out.println("Nodes: " + next.getVertexes());
            System.out.println("Total weight: " + next.getWeight());

        }


        /*LinkedList<Vertex> path2 = dijkstra.getAllShortestPathsTo(nodes.get(2));
        System.out.println("AFTER2");
        for (Vertex vertex : path2) {
            System.out.println(vertex);
        }

        LinkedList<Vertex> path3 = dijkstra.getAllShortestPathsTo(nodes.get(7));
        System.out.println("AFTER3");
        for (Vertex vertex : path3) {
            System.out.println(vertex);
        }

        LinkedList<Vertex> path4 = dijkstra.getAllShortestPathsTo(nodes.get(8));
        System.out.println("AFTER4");
        for (Vertex vertex : path4) {
            System.out.println(vertex);
        }*/

    }

}