package src.modulos.graph;

import java.util.HashMap;
import java.util.Map;

public class No {
    private final String id;
    private final Map<No, Integer> vizinhos; // vizinho -> peso da aresta
    private Object informacao;

    public No(String id) {
        this.id = id;
        this.vizinhos = new HashMap<>();
    }

    public String getId() { return id; }
    public Map<No, Integer> getVizinhos() { return vizinhos; }
    public Object getInformacao() { return informacao; }
    public void setInformacao(Object informacao) { this.informacao = informacao; }

    public void adicionarVizinho(No no, int peso) {
        vizinhos.put(no, peso); //add uma aresta entre os nos
    }

    @Override public String toString() { return id; }
}