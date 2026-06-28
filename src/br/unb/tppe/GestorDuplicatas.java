package br.unb.tppe;

import java.util.ArrayList;
import java.util.List;

public class GestorDuplicatas {

    private final DetectorDuplicatas detector;

    public GestorDuplicatas() {
        this.detector = new DetectorDuplicatas();
    }

    public List<List<Autor>> encontrarDuplicatas(List<Autor> autores) {
        AgrupadorDeDuplicatas agrupador = new AgrupadorDeDuplicatas(autores, this.detector);
        return agrupador.executar();
    }

    public int removerDuplicatasExatas(List<Autor> autores) {
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

    public void unificarIdsAutoresDuplicados(List<Autor> autores) {
        List<List<Autor>> grupos = encontrarDuplicatas(autores);
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
                            autores.set(index, new Autor(menorId, autorOriginal.getNome()));
                        }
                    }
                }
            }
        }
    }
}