package src.modulos.graph;

import src.modulos.enums.Cores;

import java.util.HashMap;
import java.util.Map;

public class Grafo {
    private final Map<String, No> nos;
    public Map<String, No> getNos() { return nos; }
    public No getNo(String id) { return nos.get(id); }

    public Grafo() {
        this.nos = new HashMap<>();
    }

    public No adicionarNo(String id) {
        No no = new No(id);
        nos.put(id, no);
        return no;
    }

    public void removerNo(String id) {
        No no = this.getNo(id);
        if (no == null) return;
        for (No outro : nos.values())
            outro.getVizinhos().remove(no);
        nos.remove(id);
    }

    public void adicionarAresta(String origemId, String destinoId, int peso) {
        No origem = nos.get(origemId);
        No destino = nos.get(destinoId);

        if (origem == null || destino == null) throw new IllegalArgumentException(Cores.VERMELHO.aplicar("No inexistente"));


        origem.adicionarVizinho(destino, peso);
    }
}