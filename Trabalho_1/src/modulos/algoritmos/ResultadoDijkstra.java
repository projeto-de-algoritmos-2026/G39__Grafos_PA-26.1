package src.modulos.algoritmos;

import src.modulos.graph.No;

import java.util.List;

public class ResultadoDijkstra {
    private final List<No> caminho;
    private final int custo;

    public ResultadoDijkstra(List<No> caminho, int custo) {
        this.caminho = caminho;
        this.custo = custo;
    }

    public List<No> getCaminho() {
        return caminho;
    }

    public int getCusto() {
        return custo;
    }
}