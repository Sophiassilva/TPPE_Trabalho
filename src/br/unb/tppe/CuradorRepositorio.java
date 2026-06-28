package br.unb.tppe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuradorRepositorio {

    private final List<Autor> autores;
    private final GestorDuplicatas gestor;

    public CuradorRepositorio() {
        this.autores = new ArrayList<>();
        this.gestor = new GestorDuplicatas();
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

    public List<List<Autor>> encontrarDuplicatas() {
        return gestor.encontrarDuplicatas(autores);
    }

    public int removerDuplicatasExatas() {
        return gestor.removerDuplicatasExatas(autores);
    }

    public void unificarIdsAutoresDuplicados() {
        gestor.unificarIdsAutoresDuplicados(autores);
    }
}