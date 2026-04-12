package src.modulos;

import src.modulos.algoritmos.Dijkstra;
import src.modulos.algoritmos.ResultadoDijkstra;
import src.modulos.enums.Cores;
import src.modulos.enums.TipoCelula;
import src.modulos.graph.Grafo;
import src.modulos.graph.No;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private static final String SEPARA_CORDS = ",";
    private final int altura;
    private final int largura;
    private final int alturaCord;
    private final int larguraCord;
    private final Grafo grafo;
    private int[] posJogador;
    private final List<String> moedas = new ArrayList<>();
    private int armadilhasPassadas = 0;
    private int monstrosMortos = 0;

    public Mapa(int altura, int largura) {
        this.altura = Math.min(altura, 100);
        this.largura = Math.min(largura, 100);

        this.alturaCord = (altura + "").length();
        this.larguraCord = (largura + "").length();

        this.grafo = new Grafo();

        criarGrade();
        conectarVizinhos();
    }

    public Grafo getGrafo() { return grafo; }
    public int[] getPosJogador() { return  posJogador; }

    private String id(int linha, int coluna) { return linha + SEPARA_CORDS + coluna; }

    private void criarGrade() { //cria um grid altura X largura para servir de mapa
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                No no = grafo.adicionarNo(id(i, j));
                no.setInformacao(TipoCelula.LIVRE);
            }
        }
    }

    private void conectarVizinhos() { //conecta cada no com os de cima baixo direita e esquerda
        int[][] direcoes = {
                {-1, 0}, // cima
                {1, 0},  // baixo
                {0, -1}, // esquerda
                {0, 1}   // direita
        };

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                No atual = grafo.getNo(id(i, j));

                for (int[] d : direcoes) {
                    int ni = i + d[0];
                    int nj = j + d[1];

                    if (dentroLimite(ni, nj)) {
                        grafo.adicionarAresta(
                                id(i, j),
                                id(ni, nj),
                                1
                        );
                    }
                }
            }
        }
    }

    private boolean dentroLimite(int i, int j) { //verifica se ta no limite
        return i >= 0 && i < altura && j >= 0 && j < largura;
    }

    private int adicionarTipoCelula(int linha, int coluna, TipoCelula tipo) {
        No no = grafo.getNo(id(linha, coluna));
        if (no != null) {
            no.setInformacao(tipo);
        } else {
            Cores.VERMELHO.imprimeln("Existe uma parede aqui!");
            return 1;
        }
        return 0;
    }

    public void adicionarParede(int linha, int coluna) {
        grafo.removerNo(id(linha, coluna));
    }

    public void adicionarArmadilha(int linha, int coluna) { //add uma armadilha na celula
        this.adicionarTipoCelula(linha, coluna, TipoCelula.ARMADILHA);
        alteraCustoChegar(grafo.getNo(id(linha, coluna)), 10);
    }

    public void adicionarMonstros(int linha, int coluna, int quantidade) { //add n monstros na celula
        this.adicionarTipoCelula(linha, coluna, TipoCelula.MONSTRO);
        alteraCustoChegar(grafo.getNo(id(linha, coluna)), quantidade * 5);
    }

    public void adicionarMoeda(int linha, int coluna) {
       if (this.adicionarTipoCelula(linha, coluna, TipoCelula.MOEDA) == 0) moedas.add(id(linha, coluna));
    }

    public void adicionarJogador(int linha, int coluna) {
        if (posJogador == null) {
            if (this.adicionarTipoCelula(linha, coluna, TipoCelula.JOGADOR) == 0) posJogador = new int[]{linha, coluna};
        } else {
            Cores.VERMELHO.imprimeln("Já existe um jogador neste mapa!");
        }
    }

    private void alteraCustoChegar(No no, int novoCusto) {// atualiza os custos de chegar em uma celula
        if (no == null) return;
        for (No outro : grafo.getNos().values()) { // percorre as celulas
            if (outro.getVizinhos().containsKey(no)) //se existe aresta outro -> no
                outro.getVizinhos().put(no, novoCusto);//att o custo da aresta
        }
    }

    public Object[] coletaMoedas() {
        var resultado = new Object[5];
        int distancia = 0;
        int custo = 0;
        int moedasAntes = moedas.size();

        //faz Dijkstra ate cada moeda, escolhe mennor caminho e remove a moeda da lista
        ResultadoDijkstra caminho;
        do {
            String moedaEscolhida = null;
            caminho = new ResultadoDijkstra(new ArrayList<>(), Integer.MAX_VALUE);
            for (var moeda : moedas) { //faz um Dijkstra pra cada moeda
                var novoCaminho = Dijkstra.menorCaminho(getGrafo(), id(getPosJogador()[0], getPosJogador()[1]), moeda);

                if (novoCaminho.getCusto() < caminho.getCusto()) {
                    caminho = novoCaminho;
                    moedaEscolhida = moeda;
                } else if ( novoCaminho.getCusto() == caminho.getCusto() &&  novoCaminho.getCaminho().size() < caminho.getCaminho().size() ) {
                    caminho = novoCaminho; //caso empate custo, escolhe menor caminho
                    moedaEscolhida = moeda;
                }
            }

            if (moedaEscolhida != null) { //caso tenha achado um caminho att os dados
                moedas.remove(moedaEscolhida);
                colorirCaminho(caminho.getCaminho());
                distancia = distancia + caminho.getCaminho().size();
                custo = custo + caminho.getCusto();
            }
        } while (caminho.getCusto() != Integer.MAX_VALUE && !moedas.isEmpty()); // executa ate o menor caminho ser max int ou n ter mais moedas

        //finalizando
        System.out.println("\n\n\n");
        this.imprimir("final");

        resultado[0] = distancia;
        resultado[1] = new int[]{moedasAntes - moedas.size(), moedasAntes};
        resultado[2] = custo;
        resultado[3] = armadilhasPassadas;
        resultado[4] = monstrosMortos;
        return resultado;
    }

    public void colorirCaminho(List<No> caminho) { //colore o caminho tomado e seta a ultima celula como o jogador
        for (No celula : caminho) {
            if (celula.getInformacao() == TipoCelula.MONSTRO) {//mata o monstro
                alteraCustoChegar(celula, 1);
                monstrosMortos = monstrosMortos + 1;
            }
            if (celula.getInformacao() == TipoCelula.ARMADILHA) armadilhasPassadas = armadilhasPassadas + 1;//marca uma armadilha
            celula.setInformacao(TipoCelula.CAMINHO);
        }
        caminho.getLast().setInformacao(TipoCelula.JOGADOR);
        var cords = caminho.getLast().getId().split(SEPARA_CORDS);
        posJogador = new int[]{Integer.parseInt(cords[0]), Integer.parseInt(cords[1])};

        imprimir("andando");
    }

    public void imprimir(String titulo) {
        Cores.VERDE.imprimeln("\nMapa " + titulo + ":");;

        imprimirCordsCol(largura);

        imprimirEapacamento();
        for (int j = 0; j < largura + 2; j++) //parede cima
            System.out.print(TipoCelula.PAREDE.render());
        System.out.println();

        for (int i = 0; i < altura; i++) {
            imprimirCordsLin(i);
            System.out.print(TipoCelula.PAREDE.render());//parede esq

            for (int j = 0; j < largura; j++) {
                No no = grafo.getNo(id(i, j));
                if (no == null) {
                    System.out.print(TipoCelula.PAREDE.render());//se n tem no ent é parede
                } else {
                    System.out.print(((TipoCelula) no.getInformacao()).render());//se tem ent renderiza a celula
                }
            }

            System.out.print(TipoCelula.PAREDE.render());//parede dir
            System.out.println();
        }

        imprimirEapacamento();
        for (int j = 0; j < largura + 2; j++) //parede baixo
            System.out.print(TipoCelula.PAREDE.render());
        System.out.println("\n");
    }
    private void imprimirCordsCol(int largura) {
        for (int d = 0; d < alturaCord; d++) {
            imprimirEapacamento();
            System.out.print("  ");

            for (int j = 0; j < largura; j++) {
                String num = String.format("%" + alturaCord + "d", j);
                Cores.AZUL.imprime(num.charAt(d) + " ");
            }
            System.out.println();
        }
    }
    private void imprimirCordsLin(int linha) {
        Cores.ROXO.imprimef("%" + larguraCord + "d ", linha);
    }
    private void imprimirEapacamento() {
        for (int i = -1; i < larguraCord; i++) System.out.print(" ");
    }
}