package src.modulos.algoritmos;

import src.modulos.enums.Cores;
import src.modulos.graph.Grafo;
import src.modulos.graph.No;

import java.util.*;

public class Dijkstra {
    public static ResultadoDijkstra menorCaminho(Grafo grafo, String origemId, String destinoId) {
        No origem = grafo.getNo(origemId);
        No destino = grafo.getNo(destinoId);

        if (origem == null || destino == null)
            throw new IllegalArgumentException(Cores.VERMELHO.aplicar("Origem ou destino inexistente"));

        Map<No, Integer> distancias = new HashMap<>();
        Map<No, No> anteriores = new HashMap<>();
        PriorityQueue<No> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        //iniciando
        for (No no : grafo.getNos().values()) {
            distancias.put(no, Integer.MAX_VALUE);
            anteriores.put(no, null);
        }

        distancias.put(origem, 0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            if (atual.equals(destino)) break;
            for (Map.Entry<No, Integer> vizinhoEntry : atual.getVizinhos().entrySet()) {
                No vizinho = vizinhoEntry.getKey();
                int peso = vizinhoEntry.getValue();
                int novaDistancia = distancias.get(atual) + peso;//soma peso atual com peso do vizinho

                if (novaDistancia < distancias.get(vizinho)) {//se a distancia for menor
                    distancias.put(vizinho, novaDistancia);
                    anteriores.put(vizinho, atual);

                    fila.remove(vizinho); //atualiza prioridade
                    fila.add(vizinho);
                }
            }
        }

        //faz lista do caminho
        List<No> caminho = new ArrayList<>();
        No atual = destino;

        if (anteriores.get(atual) == null && !atual.equals(origem)) //se n achou caminho
            return new ResultadoDijkstra(Collections.emptyList(), Integer.MAX_VALUE);

        while (atual != null) {
            caminho.add(atual);
            atual = anteriores.get(atual);
        }

        Collections.reverse(caminho);

        return new ResultadoDijkstra(caminho, distancias.get(destino));
    }
}