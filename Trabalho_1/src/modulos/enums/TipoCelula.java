package src.modulos.enums;

public enum TipoCelula {
    PAREDE("# ", Cores.PRETO),
    MOEDA("$ ", Cores.AMARELO),
    LIVRE(". ", Cores.BRANCO),
    ARMADILHA("* ", Cores.ROXO),
    BURACO("0 ", Cores.PRETO),
    MONSTRO("X ", Cores.VERMELHO),
    CAMINHO("^ ", Cores.VERDE),
    JOGADOR("@ ", Cores.CIANO);

    private final String simbolo;
    private final Cores cor;

    TipoCelula(String simbolo, Cores cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }

    public String render() {
        return cor.aplicar(simbolo); //retorna char colorido pra imprimir
    }
}