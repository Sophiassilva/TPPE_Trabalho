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
        }

        @Test
        void nomesComAbreviacoesOpcionaisEPontosSaoEquivalentes() {
            assertTrue(detector.nomesEquivalentes(
                "Luiz de Oliveira de Souza", 
                "Luiz de O. de Souza"
            ));
            
            assertTrue(detector.nomesEquivalentes(
                "Luiz Oliveira Souza", 
                "Luiz de O. de Souza"
            ));
        }

        @Test
        void nomesComAbreviacoesIncompativeisNaoSaoEquivalentes() {
            assertFalse(detector.nomesEquivalentes(
                "Luiz de Oliveira de Souza", 
                "Luiz de M. de Souza" 
            ));
        }
    }

    @Nested
    class IntegracaoRepositorio {

        @Test
        void duplicatasDoCaso3FormamUmUnicoGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(746937, "Luiz de Oliveira de Souza"));
            repo.adicionarAutor(new Autor(608296, "Luiz Oliveira Souza"));
            repo.adicionarAutor(new Autor(549242, "Luiz de O. de Souza"));

            var grupos = repo.encontrarDuplicatas();
            
            assertEquals(1, grupos.size(), "Deve formar apenas 1 grupo de duplicatas");
            assertEquals(3, grupos.get(0).size(), "O grupo deve conter os 3 registros equivalentes");
        }
    }
}