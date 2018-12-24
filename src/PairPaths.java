import java.util.List;

class PairPaths<F, S> {
    private List<Vertex> vertexes;
    private Integer weight;

    public PairPaths(List<Vertex> vertexes, Integer weight) {
        this.vertexes = vertexes;
        this.weight = weight;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void addVertexes(Vertex vertex) {
        this.vertexes.add(vertex);
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void addWeight(Integer weight) {
        this.weight += weight;
    }

    public String toString() {
        return this.vertexes.toString();
    }

}