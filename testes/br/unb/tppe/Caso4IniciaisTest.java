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

            assertTrue(detector.nomesEquivalentes(
                "Ana de Mattos Seabra", 
                "AM Seabra"
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
        void grupoVanildaFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            
            repo.adicionarAutor(new Autor(763027, "Vanilda Cristina Junior")); 
            repo.adicionarAutor(new Autor(763027, "VC Junior")); 
            repo.adicionarAutor(new Autor(335284, "Vanilda Cristina Júnior")); 

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size(), "Deve agrupar todas as variações de Vanilda");
            assertEquals(3, grupos.get(0).size());
        }

        @Test
        void grupoSergioFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            
            repo.adicionarAutor(new Autor(554799, "Sergio Henrique Guaraldi"));
            repo.adicionarAutor(new Autor(243350, "Sérgio Henrique Guaraldi"));
            repo.adicionarAutor(new Autor(954057, "SH Guaraldi"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size(), "Deve agrupar todas as variações de Sérgio");
            assertEquals(3, grupos.get(0).size());
        }

        @Test
        void grupoAnaFormaUmUnicoGrupoCaso4() {
            CuradorRepositorio repo = new CuradorRepositorio();
            
            repo.adicionarAutor(new Autor(28372, "Ana de Mattos Seabra"));
            repo.adicionarAutor(new Autor(582585, "AM Seabra"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size(), "Deve agrupar as variações de Ana");
            assertEquals(2, grupos.get(0).size());
        }
    }
}