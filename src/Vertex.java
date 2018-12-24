import java.util.*;

public class Vertex {
    final private String id;
    final private String name;
    private Vertex previous;
    private List<Pair<Vertex, Integer>> previousNodes;


    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public void setPreviousNodes(List<Pair<Vertex, Integer>> previousNodes) {
        Collections.sort(previousNodes, new Comparator<Pair<Vertex, Integer>>() {
            @Override
            public int compare(final Pair<Vertex, Integer> o1, final Pair<Vertex, Integer> o2) {
                if(o1.getWeight() < o2.getWeight())
                    return -1;
                else if(o1.getWeight() == o2.getWeight())
                    return 0;
                else
                    return 1;
            }
        });
        this.previousNodes = previousNodes;

    }

    public List<Pair<Vertex, Integer>> getPreviousNodes() {
        return previousNodes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}


