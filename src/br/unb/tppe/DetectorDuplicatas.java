package br.unb.tppe;


public class DetectorDuplicatas {

    private final NormalizadorNome normalizador;

    public DetectorDuplicatas() {
        this.normalizador = new NormalizadorNome();
    }

    // Determina se dois autores são possivelmente a mesma pessoa (ignora IDs)
    public boolean saoMesmaPessoa(Autor a1, Autor a2) {
        return nomesEquivalentes(a1.getNome(), a2.getNome());
    }

    // Compara dois nomes cobrindo os 5 casos do enunciado delegando para o Objeto-Método
    public boolean nomesEquivalentes(String nome1, String nome2) {
        ComparadorDeNomes comparador = new ComparadorDeNomes(nome1, nome2, this.normalizador);
        return comparador.executar();
    }
}