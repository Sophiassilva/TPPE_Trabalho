package br.unb.tppe;

import java.util.ArrayList;
import java.util.List;

public class GestorDuplicatas {

    private final DetectorDuplicatas detector;

    public GestorDuplicatas() {
        this.detector = new DetectorDuplicatas();
    }

    public List<List<Autor>> encontrarDuplicatas(List<Autor> autores) {
        List<List<Autor>> grupos = new ArrayList<>();
        boolean[] processado = new boolean[autores.size()];
        for (int i = 0; i < autores.size(); i++) {
            if (processado[i]) continue;
            List<Autor> grupo = formarGrupo(i, processado, autores);
            if (grupo.size() > 1) {
                grupos.add(grupo);
            }
        }
        return grupos;
    }

    private List<Autor> formarGrupo(int i, boolean[] processado, List<Autor> autores) {
        List<Autor> grupo = new ArrayList<>();
        grupo.add(autores.get(i));
        processado[i] = true;
        for (int j = i + 1; j < autores.size(); j++) {
            if (!processado[j] && detector.saoMesmaPessoa(autores.get(i), autores.get(j))) {
                grupo.add(autores.get(j));
                processado[j] = true;
            }
        }
        return grupo;
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