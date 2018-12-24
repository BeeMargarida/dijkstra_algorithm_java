class Pair<F, S> {
    private F vertex;
    private S weight;

    public Pair(F vertex, S weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public F getVertex() {
        return vertex;
    }

    public void setVertex(F vertex) {
        this.vertex = vertex;
    }

    public S getWeight() {
        return weight;
    }

    public void setWeight(S weight) {
        this.weight = weight;
    }

    public String toString() {
        return vertex.toString() + " : " + weight;
    }
}