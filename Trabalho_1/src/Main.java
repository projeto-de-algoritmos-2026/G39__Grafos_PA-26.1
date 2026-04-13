package src;

import java.util.Objects;
import java.util.Scanner;
import src.modulos.Mapa;
import src.modulos.enums.Cores;

class Main {
    boolean modoTeste = false;

    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.iniciar();
    }

    void iniciar() {
        if (modoTeste) {
            var mapaTeste = this.mapaTeste();
            jornada(mapaTeste);
            return;
        }

        Cores.VERDE.imprimeln("BEM VINDO LABIRINTO CUSTOMIZADO");
        var mapa = criaMapa();

        int opcao = -1;
        while (opcao != 0) {
            this.instrucoes();

            opcao = scan.nextInt();
            if (opcao < 1 || opcao > 7) opcao = 0;

            switch (opcao) {
                case 1 -> criaParede(mapa);
                case 2 -> colocaMoeda(mapa);
                case 3 -> ondeComeca(mapa);
                case 4 -> opcao = jornada(mapa);
                case 5 -> criaArmadilha(mapa);
                case 6 -> criaMonstro(mapa);
                case 7 -> mapa = criaMapa();
            }
        }
    }

    private Mapa mapaTeste() {
        //cria o mapa
        Mapa mapa = new Mapa(15, 15);
        mapa.imprimir("novo");

        //add paredes
        mapa.adicionarParede(1, 1);
        mapa.adicionarParede(1, 2);
        mapa.adicionarParede(1, 3);
        mapa.adicionarParede(1, 4);
        mapa.adicionarParede(1, 5);

        mapa.adicionarParede(2, 2);

        mapa.adicionarParede(5,  7);
        mapa.adicionarParede(5,  8);
        mapa.adicionarParede(5,  9);
        mapa.adicionarParede(5, 10);
        mapa.adicionarParede(5, 11);

        mapa.adicionarParede( 1, 13);
        mapa.adicionarParede( 2, 13);
        mapa.adicionarParede( 3, 13);
        mapa.adicionarParede( 4, 13);
        mapa.adicionarParede( 5, 13);
        mapa.adicionarParede( 6, 13);
        mapa.adicionarParede( 7, 13);
        mapa.adicionarParede( 8, 13);
        mapa.adicionarParede( 9, 13);
        mapa.adicionarParede(10, 13);
        mapa.adicionarParede(11, 13);
        mapa.adicionarParede(12, 13);
        mapa.adicionarParede(13, 13);
        mapa.adicionarParede(14, 13);

        mapa.adicionarParede(14, 1);
        mapa.adicionarParede(13, 0);
        mapa.adicionarParede(13, 1);
        mapa.imprimir("com novas paredes");

        //add monstros e armadilhas
        mapa.adicionarArmadilha(0,8);
        mapa.adicionarArmadilha(0,10);
        mapa.adicionarArmadilha(1,9);
        mapa.adicionarArmadilha(1,0);
        mapa.adicionarArmadilha(9,14);
        mapa.adicionarMonstros(0,9, 10);
        mapa.adicionarMonstros(0,14, 10);

        //add moedas
        mapa.adicionarMoeda(14, 0);
        mapa.adicionarMoeda(4, 4);
        mapa.adicionarMoeda(12, 4);
        mapa.adicionarMoeda(14, 14);
        mapa.adicionarMoeda(14, 12);
        mapa.imprimir("com novas moedas");

        //add player
        mapa.adicionarJogador(0, 0);
        mapa.imprimir("com jogador");

        return mapa;
    }

    private void instrucoes() {
        Cores.VERDE.imprimeln("DIGITE:" +
                "   1-para inserir paredes" +
                "   2-para inserir moedas" +
                "   3-para decidir onde começar" +
                "   4-para planejar a jornada" +
                "   5-para adicionar armadilha" +
                "   6-para adicionar armadilha monstros" +
                "   7-para resetar o mapa" +
                "   0-para sair");
    }

    private void instrucoes2(String elemento) {
        Cores.VERDE.imprime("Informe a ");
        if (Objects.equals(elemento, "do mapa")) {
            Cores.ROXO.imprime("altura");
        } else {
            Cores.ROXO.imprime("linha");
        }
        Cores.VERDE.imprime(" e ");
        if (Objects.equals(elemento, "do mapa")) {
            Cores.AZUL.imprime("largura");
        } else {
            Cores.AZUL.imprime("coluna");
        }
        Cores.VERDE.imprimeln(" " + elemento + ": ");
    }

    private Mapa criaMapa() {//cria o mapa
        instrucoes2("do mapa");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();

        Mapa mapa = new Mapa(linha, coluna);
        mapa.imprimir("novo");

        return mapa;
    }

    private void criaParede(Mapa mapa) {//add paredes
        instrucoes2("da parede");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();
        mapa.adicionarParede(linha, coluna);
        mapa.imprimir("com nova parede");
    }

    private void criaArmadilha(Mapa mapa) {//add paredes
        instrucoes2("da armadilha");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();
        mapa.adicionarArmadilha(linha, coluna);
        mapa.imprimir("com nova armadilha");
    }

    private void criaMonstro(Mapa mapa) {//add paredes
        instrucoes2("do(s) monstro(s)");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();
        Cores.VERDE.imprimeln("Quantos monstros: ");
        int quant = scan.nextInt();
        mapa.adicionarMonstros(linha, coluna, quant);
        mapa.imprimir("com novo(s) monstro(s)");
    }

    private void colocaMoeda(Mapa mapa) {//add moedas
        instrucoes2("da moeda");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();
        mapa.adicionarMoeda(linha, coluna);
        mapa.imprimir("com nova moeda");
    }

    private void ondeComeca(Mapa mapa) {//add player
        instrucoes2("do jogador");
        int linha = scan.nextInt();
        int coluna = scan.nextInt();
        mapa.adicionarJogador(linha, coluna);
        mapa.imprimir("com jogador");
    }

    private int jornada(Mapa mapa) {
        var resultados = mapa.coletaMoedas();
        Cores.VERDE.imprimeln("Salas visitadas: " + resultados[0]);
        Cores.VERDE.imprimeln("Moedas coletadas: " + ((int[])resultados[1])[0] + "/" + ((int[])resultados[1])[1]  );
        Cores.VERDE.imprimeln("Monstros eliminados: " + resultados[4]);
        Cores.VERDE.imprimeln("Armadilhas visitadas: " + resultados[3]);
        Cores.VERDE.imprimeln("Custo: " + resultados[2]);

        if (modoTeste) return 0;
        Cores.VERDE.imprimeln("\nDeseja jogar de novo? (S/N)");
        if (Objects.equals(scan.nextLine(), "S") || Objects.equals(scan.nextLine(), "s")) {
            criaMapa();
            return 1;
        }

        return 0;
    }
}