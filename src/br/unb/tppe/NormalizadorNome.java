package br.unb.tppe;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NormalizadorNome {

    private static final Set<String> PARTICULAS = Set.of("de", "da", "do", "dos", "das");

    // Caso 1: remove acentos e normaliza apóstrofos/backticks
    public String normalizarGrafia(String nome) {
        if (nome == null || nome.isBlank()) return "";

        String resultado = nome.trim().replace('`', '\'');
        resultado = Normalizer.normalize(resultado, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return resultado;
    }

    public String normalizarParaComparacao(String nome) {
        return normalizarGrafia(nome).toLowerCase();
    }

    // Divide em tokens, trata formato "Sobrenome, Nome" e remove pontos de abreviações
    public List<String> tokenizar(String nome) {
        String base = normalizarParaComparacao(nome);

        if (base.contains(",")) {
            String[] partes = base.split(",", 2);
            base = partes[1].trim() + " " + partes[0].trim();
        }

        String[] brutos = base.trim().split("\\s+");
        List<String> tokens = new ArrayList<>();
        for (String token : brutos) {
            String limpo = token.replaceAll("\\.+$", "").replaceAll("^\\.+", "");
            if (!limpo.isEmpty()) {
                tokens.add(limpo);
            }
        }
        return tokens;
    }

    // Casos 2, 3 e 4: remove partículas (de, da, do, dos, das)
    public List<String> tokenizarSemParticulas(String nome) {
        List<String> resultado = new ArrayList<>();
        for (String token : tokenizar(nome)) {
            if (!PARTICULAS.contains(token)) {
                resultado.add(token);
            }
        }
        return resultado;
    }

    // Extrai o sobrenome: token antes da vírgula ou último token com mais de 1 caractere
    public String extrairSobrenome(String nome) {
        String base = normalizarParaComparacao(nome);

        if (base.contains(",")) {
            return base.split(",")[0].trim();
        }

        List<String> tokens = tokenizarSemParticulas(nome);
        for (int i = tokens.size() - 1; i >= 0; i--) {
            if (tokens.get(i).length() > 1) {
                return tokens.get(i);
            }
        }
        return tokens.isEmpty() ? "" : tokens.get(tokens.size() - 1);
    }

    // Extrai iniciais dos nomes que precedem o sobrenome
    // Caso 4: iniciais agrupadas ("vc") são expandidas em ['v', 'c']
    public List<String> extrairIniciais(String nome) {
        String sobrenome = extrairSobrenome(nome);
        List<String> iniciais = new ArrayList<>();

        for (String token : tokenizarSemParticulas(nome)) {
            if (token.equals(sobrenome)) continue;

            if (token.matches("[a-z]{2,3}")) {
                for (char c : token.toCharArray()) {
                    iniciais.add(String.valueOf(c));
                }
            } else {
                iniciais.add(String.valueOf(token.charAt(0)));
            }
        }
        return iniciais;
    }
}
