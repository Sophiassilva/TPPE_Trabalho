package br.unb.tppe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Caso4IniciaisTest {

    private DetectorDuplicatas detector;

    @BeforeEach
    void setUp() {
        detector = new DetectorDuplicatas();
    }

    @Nested
    class DeteccaoDeDuplicatas {

        @Test
        void iniciaisAgrupadasComSobrenomeSaoEquivalentesAoNomeCompleto() {
            assertTrue(detector.nomesEquivalentes(
                "Vanilda Cristina Junior", 
                "VC Junior"
            ));

            assertTrue(detector.nomesEquivalentes(
                "Sérgio Henrique Guaraldi", 
                "SH Guaraldi"
            ));
        }

        @Test
        void ignorarDiferencaDeMaiusculasEMinusculasNasIniciais() {
            assertTrue(detector.nomesEquivalentes(
                "Sérgio Henrique Guaraldi", 
                "sh Guaraldi"
            ));
        }

        @Test
        void iniciaisIncorretasNaoSaoEquivalentes() {
            assertFalse(detector.nomesEquivalentes(
                "Vanilda Cristina Junior", 
                "VR Junior" 
            ));
        }

        @Test
        void ultimoSobrenomeDiferenteNaoEhEquivalente() {
            assertFalse(detector.nomesEquivalentes(
                "Sérgio Henrique Guaraldi", 
                "SH Silva"
            ));
        }
        
        @Test
        void agrupamentoDeIniciaisNaoSeAplicaAMesmaQuantidadeDeNomes() {
            assertFalse(detector.nomesEquivalentes(
                "Vanilda Cristina Junior", 
                "V Junior" 
            ));
        }
    }

    @Nested
    class IntegracaoRepositorio {

        @Test
        void duplicatasDoCaso4FormamGruposCorretos() {
            CuradorRepositorio repo = new CuradorRepositorio();
            
            // Grupo 1
            repo.adicionarAutor(new Autor(763027, "Vanilda Cristina Junior")); 
            repo.adicionarAutor(new Autor(763028, "VC Junior")); 
            
            // Grupo 2
            repo.adicionarAutor(new Autor(243350, "Sérgio Henrique Guaraldi"));
            repo.adicionarAutor(new Autor(954057, "SH Guaraldi"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(2, grupos.size(), "Devem ser encontrados 2 grupos distintos de duplicatas");
            
            // Verifica se cada grupo encontrou o par corretamente
            assertEquals(2, grupos.get(0).size());
            assertEquals(2, grupos.get(1).size());
        }
    }
}