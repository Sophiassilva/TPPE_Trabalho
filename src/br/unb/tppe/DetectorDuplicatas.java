package br.unb.tppe;

import java.util.List;

public class DetectorDuplicatas {

    private final NormalizadorNome normalizador;

    public DetectorDuplicatas() {
        this.normalizador = new NormalizadorNome();
    }

    // Determina se dois autores são possivelmente a mesma pessoa (ignora IDs)
    public boolean saoMesmaPessoa(Autor a1, Autor a2) {
        return nomesEquivalentes(a1.getNome(), a2.getNome());
    }

    // Compara dois nomes cobrindo os 5 casos do enunciado
    public boolean nomesEquivalentes(String nome1, String nome2) {
        if (nome1 == null || nome2 == null) return false;

        // Caso 1: igualdade após normalização tipográfica
        String n1 = normalizador.normalizarParaComparacao(nome1);
        String n2 = normalizador.normalizarParaComparacao(nome2);
        if (n1.equals(n2)) return true;

        // Casos 2, 3, 4: comparar sobrenome + iniciais compatíveis
        String sobrenome1 = normalizador.extrairSobrenome(nome1);
        String sobrenome2 = normalizador.extrairSobrenome(nome2);

        if (!sobrenome1.equals(sobrenome2)) return false;

        List<String> iniciais1 = normalizador.extrairIniciais(nome1);
        List<String> iniciais2 = normalizador.extrairIniciais(nome2);

        return iniciaisCompativeis(iniciais1, iniciais2);
    }

    // Uma lista pode ser prefixo da outra (nome abreviado vs nome completo)
    private boolean iniciaisCompativeis(List<String> iniciais1, List<String> iniciais2) {
        if (iniciais1.isEmpty() || iniciais2.isEmpty()) return true;

        int tamanhoMinimo = Math.min(iniciais1.size(), iniciais2.size());
        for (int i = 0; i < tamanhoMinimo; i++) {
            if (!iniciais1.get(i).equals(iniciais2.get(i))) return false;
        }
        return true;
    }
}
