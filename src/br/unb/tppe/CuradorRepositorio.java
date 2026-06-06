package br.unb.tppe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuradorRepositorio {

    private final List<Autor> autores;
    private final DetectorDuplicatas detector;

    public CuradorRepositorio() {
        this.autores = new ArrayList<>();
        this.detector = new DetectorDuplicatas();
    }

    public void adicionarAutor(Autor autor) {
        if (autor == null)
            throw new AutorInvalidoException("Autor não pode ser nulo");
        autores.add(autor);
    }

    public List<Autor> listarAutores() {
        return Collections.unmodifiableList(autores);
    }

    public int contarAutores() {
        return autores.size();
    }

    // Agrupa autores que são possivelmente a mesma pessoa
    public List<List<Autor>> encontrarDuplicatas() {
        List<List<Autor>> grupos = new ArrayList<>();
        boolean[] processado = new boolean[autores.size()];

        for (int i = 0; i < autores.size(); i++) {
            if (processado[i]) continue;

            List<Autor> grupo = new ArrayList<>();
            grupo.add(autores.get(i));
            processado[i] = true;

            for (int j = i + 1; j < autores.size(); j++) {
                if (!processado[j] && detector.saoMesmaPessoa(autores.get(i), autores.get(j))) {
                    grupo.add(autores.get(j));
                    processado[j] = true;
                }
            }

            if (grupo.size() > 1) {
                grupos.add(grupo);
            }
        }

        return grupos;
    }

    // Remove entradas com ID e nome exatamente iguais, retorna quantas foram removidas
    public int removerDuplicatasExatas() {
        List<Autor> semDuplicatas = new ArrayList<>();
        int removidos = 0;

        for (Autor autor : autores) {
            if (!semDuplicatas.contains(autor)) {
                semDuplicatas.add(autor);
            } else {
                removidos++;
            }
        }

        autores.clear();
        autores.addAll(semDuplicatas);
        return removidos;
    }

    // Unifica os IDs de autores duplicados mapeando-os para o menor ID do grupo
    public void unificarIdsAutoresDuplicados() {
        List<List<Autor>> grupos = encontrarDuplicatas();

        for (List<Autor> grupo : grupos) {
            if (grupo.size() > 1) {
                long menorId = grupo.get(0).getId();
                for (Autor autor : grupo) {
                    if (autor.getId() < menorId) {
                        menorId = autor.getId();
                    }
                }

                for (Autor autorOriginal : grupo) {
                    if (autorOriginal.getId() != menorId) {
                        int index = autores.indexOf(autorOriginal);
                        if (index != -1) {
                            Autor autorUnificado = new Autor(menorId, autorOriginal.getNome());
                            autores.set(index, autorUnificado);
                        }
                    }
                }
            }
        }
    }
}
