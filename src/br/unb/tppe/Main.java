package br.unb.tppe;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        CuradorRepositorio repositorio = new CuradorRepositorio();

        // Caso 1: diferenças tipográficas
        repositorio.adicionarAutor(new Autor(554799, "Sergio Henrique Guaraldi"));
        repositorio.adicionarAutor(new Autor(243350, "Sérgio Henrique Guaraldi"));
        repositorio.adicionarAutor(new Autor(31299,  "Monica Hirata Sant`anna"));
        repositorio.adicionarAutor(new Autor(433095, "Mônica Hirata Sant'anna"));

        // Caso 2: sobrenome + iniciais
        repositorio.adicionarAutor(new Autor(28372,  "Ana de Mattos Seabra"));
        repositorio.adicionarAutor(new Autor(582585, "Seabra A. M."));
        repositorio.adicionarAutor(new Autor(28371,  "Cassius de Souza"));
        repositorio.adicionarAutor(new Autor(746936, "Souza C."));

        // Caso 3: partículas e pontos opcionais
        repositorio.adicionarAutor(new Autor(746937, "Luiz de Oliveira de Souza"));
        repositorio.adicionarAutor(new Autor(608296, "Luiz Oliveira Souza"));

        // Caso 4: iniciais agrupadas
        repositorio.adicionarAutor(new Autor(763027, "Vanilda Cristina Junior"));
        repositorio.adicionarAutor(new Autor(763028, "VC Junior"));

        // Caso 5: IDs diferentes, mesmo nome
        repositorio.adicionarAutor(new Autor(31298,  "Raphael Goncalves Viana"));
        repositorio.adicionarAutor(new Autor(433094, "Raphael Gonçalves Viana"));
        repositorio.adicionarAutor(new Autor(549243, "Raphael Gonçalves Viana"));

        System.out.println("=== Duplicatas encontradas ===\n");
        List<List<Autor>> grupos = repositorio.encontrarDuplicatas();

        if (grupos.isEmpty()) {
            System.out.println("Nenhuma duplicata encontrada.");
        } else {
            for (int i = 0; i < grupos.size(); i++) {
                System.out.println("Grupo " + (i + 1) + ":");
                for (Autor a : grupos.get(i)) {
                    System.out.println("  " + a);
                }
                System.out.println();
            }
        }

        System.out.println("Total de autores: " + repositorio.contarAutores());
    }
}
