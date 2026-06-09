package br.unb.tppe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Caso3ParticulasEAbreviacoesTest {

    private DetectorDuplicatas detector;
    private NormalizadorNome normalizador;

    @BeforeEach
    void setUp() {
        detector = new DetectorDuplicatas();
        normalizador = new NormalizadorNome();
    }

    @Nested
    class RemocaoDeParticulas {

        @Test
        void deveIgnorarParticulasDeDaDoDosDas() {
            var tokens = normalizador.tokenizarSemParticulas("Luiz de Oliveira da Silva dos Santos");
            assertEquals(4, tokens.size());
            assertFalse(tokens.contains("de"));
            assertFalse(tokens.contains("da"));
            assertFalse(tokens.contains("dos"));
        }
    }

    @Nested
    class DeteccaoDeDuplicatas {

        @Test
        void nomesComOuSemParticulasSaoEquivalentes() {
            assertTrue(detector.nomesEquivalentes(
                "Luiz de Oliveira de Souza", 
                "Luiz Oliveira Souza"
            ));

            assertTrue(detector.nomesEquivalentes(
                "Cassius de Souza", 
                "Cassius Souza"
            ));

            assertTrue(detector.nomesEquivalentes(
                "Ana de Mattos Seabra", 
                "Ana Mattos Seabra"
            ));
        }

        @Test
        void nomesComAbreviacoesOpcionaisEPontosSaoEquivalentes() {
            // Grupo Luiz
            assertTrue(detector.nomesEquivalentes(
                "Luiz de Oliveira de Souza", 
                "Luiz de O. de Souza"
            ));
            assertTrue(detector.nomesEquivalentes(
                "Luiz Oliveira Souza", 
                "Luiz de O. de Souza"
            ));

            // Grupo Cassius
            assertTrue(detector.nomesEquivalentes(
                "Cassius de Souza", 
                "C. Souza"
            ));

            // Grupo Veronica
            assertTrue(detector.nomesEquivalentes(
                "Veronica de Oliveira Moreira", 
                "V. de O. Moreira"
            ));

            // Grupo Ana
            assertTrue(detector.nomesEquivalentes(
                "Ana de Mattos Seabra", 
                "A. M. Seabra"
            ));

            // Grupo Monica
            assertTrue(detector.nomesEquivalentes(
                "Monica Hirata Sant`anna", 
                "M. H. Sant'anna"
            ));
        }

        @Test
        void nomesComAbreviacoesIncompativeisNaoSaoEquivalentes() {
            assertFalse(detector.nomesEquivalentes(
                "Luiz de Oliveira de Souza", 
                "Luiz de M. de Souza" 
            ));

            assertFalse(detector.nomesEquivalentes(
                "Cassius de Souza", 
                "K. Souza" 
            ));
        }
    }

    @Nested
    class IntegracaoRepositorio {

        @Test
        void grupoLuizFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(746937, "Luiz de Oliveira de Souza"));
            repo.adicionarAutor(new Autor(608296, "Luiz Oliveira Souza"));
            repo.adicionarAutor(new Autor(549242, "Luiz de O. de Souza"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size(), "Deve formar apenas 1 grupo de duplicatas");
            assertEquals(3, grupos.get(0).size(), "O grupo deve conter os 3 registros equivalentes");
        }

        @Test
        void grupoCassiusFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(28371, "Cassius de Souza"));
            repo.adicionarAutor(new Autor(746936, "Cassius Souza"));
            repo.adicionarAutor(new Autor(746936, "C. Souza")); 
            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size());
            assertEquals(3, grupos.get(0).size());
        }

        @Test
        void grupoAnaFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(28372, "Ana de Mattos Seabra"));
            repo.adicionarAutor(new Autor(582585, "A. M. Seabra"));
            repo.adicionarAutor(new Autor(582585, "Ana Mattos Seabra"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size());
            assertEquals(3, grupos.get(0).size());
        }

        @Test
        void grupoVeronicaFormaUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(31303, "Veronica de Oliveira Moreira"));
            repo.adicionarAutor(new Autor(608303, "V. de O. Moreira"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size());
            assertEquals(2, grupos.get(0).size());
        }
    }
}