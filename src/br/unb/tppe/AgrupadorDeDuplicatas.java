package br.unb.tppe;

import java.util.ArrayList;
import java.util.List;

public class AgrupadorDeDuplicatas {
    private final List<Autor> autores;
    private final DetectorDuplicatas detector;
    private final boolean[] processado;
    private final List<List<Autor>> grupos;

    public AgrupadorDeDuplicatas(List<Autor> autores, DetectorDuplicatas detector) {
        this.autores = autores;
        this.detector = detector;
        this.processado = new boolean[autores.size()];
        this.grupos = new ArrayList<>();
    }

    public List<List<Autor>> executar() {
        for (int i = 0; i < autores.size(); i++) {
            if (processado[i]) continue;
            
            List<Autor> grupo = formarGrupo(i); 
            
            if (grupo.size() > 1) {
                grupos.add(grupo);
            }
        }
        return grupos;
    }

    private List<Autor> formarGrupo(int i) {
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
}