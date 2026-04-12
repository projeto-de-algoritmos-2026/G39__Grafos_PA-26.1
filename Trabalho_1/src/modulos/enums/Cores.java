package src.modulos.enums;

public enum Cores {
    RESET("\u001B[0m"),
    PRETO("\u001B[30m"),
    VERMELHO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARELO("\u001B[33m"),
    AZUL("\u001B[34m"),
    ROXO("\u001B[35m"),
    CIANO("\u001B[36m"),
    BRANCO("\u001B[37m");

    private final String codigo;

    Cores(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String aplicar(String texto) {
        return codigo + texto + RESET.codigo;
    }

    public void imprimeln(String texto) { System.out.println(this.aplicar(texto)); }
    public void imprimef(String texto, Object... args) { System.out.printf(this.aplicar(texto), args); }
    public void imprime(String texto) { System.out.print(this.aplicar(texto)); }
}