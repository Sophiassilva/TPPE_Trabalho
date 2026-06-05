package br.unb.tppe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Caso2SobrenomeIniciaisTest {

    private DetectorDuplicatas detector;
    private NormalizadorNome normalizador;

    @BeforeEach
    void setUp() {
        detector = new DetectorDuplicatas();
        normalizador = new NormalizadorNome();
    }

    @Nested
    class ExtracaoDeSobrenome {

        @Test
        void extraiUltimoTokenComoSobrenome() {
            assertEquals("seabra", normalizador.extrairSobrenome("Ana de Mattos Seabra"));
            assertEquals("seabra", normalizador.extrairSobrenome("A. M. Seabra"));
            assertEquals("seabra", normalizador.extrairSobrenome("Seabra A. M."));
            assertEquals("souza",  normalizador.extrairSobrenome("Cassius de Souza"));
            assertEquals("souza",  normalizador.extrairSobrenome("Souza C."));
        }

        @Test
        void extraiSobrenomeDeFormatoComVirgula() {
            assertEquals("souza", normalizador.extrairSobrenome("Souza, Cassius de"));
        }

        @Test
        void particulasNaoSaoConfundidasComSobrenome() {
            String sobrenome = normalizador.extrairSobrenome("Ana de Mattos Seabra");
            assertNotEquals("de", sobrenome);
        }
    }

    @Nested
    class ExtracaoDeIniciais {

        @Test
        void extraiIniciaisIndependenteDeOrdemOuAbreviacao() {
            assertEquals(List.of("a", "m"), normalizador.extrairIniciais("Ana de Mattos Seabra"));
            assertEquals(List.of("a", "m"), normalizador.extrairIniciais("Seabra A. M."));
            assertEquals(List.of("a", "m"), normalizador.extrairIniciais("A. M. Seabra"));
        }

        @Test
        void iniciaisAgrupadasSaoExpandidas() {
            assertEquals(List.of("a", "m"), normalizador.extrairIniciais("AM Seabra"));
        }

        @Test
        void pontosNaAbreviacaoNaoMudamInicial() {
            assertEquals(
                normalizador.extrairIniciais("A. M. Seabra"),
                normalizador.extrairIniciais("A M Seabra")
            );
        }
    }

    @Nested
    class DeteccaoDeDuplicatas {

        @Test
        void nomeCompletoENomesAbreviadosSaoDuplicatas() {
            assertTrue(detector.nomesEquivalentes("Ana de Mattos Seabra", "Seabra A. M."));
            assertTrue(detector.nomesEquivalentes("Ana de Mattos Seabra", "A. M. Seabra"));
            assertTrue(detector.nomesEquivalentes("Ana de Mattos Seabra", "AM Seabra"));
            assertTrue(detector.nomesEquivalentes("Ana de Mattos Seabra", "Ana Mattos Seabra"));
        }

        @Test
        void nomeCompletoEFormatosVariantesDeCassiusSaoDuplicatas() {
            assertTrue(detector.nomesEquivalentes("Cassius de Souza", "Souza C."));
            assertTrue(detector.nomesEquivalentes("Cassius de Souza", "C. Souza"));
            assertTrue(detector.nomesEquivalentes("Cassius de Souza", "Cassius Souza"));
            assertTrue(detector.nomesEquivalentes("Cassius de Souza", "Souza, Cassius de"));
        }

        @Test
        void sobrenomeDiferenteNaoEhDuplicata() {
            assertFalse(detector.nomesEquivalentes("Ana de Mattos Seabra", "Ana de Mattos Pereira"));
        }

        @Test
        void primeiraInicialDiferenteNaoEhDuplicata() {
            assertFalse(detector.nomesEquivalentes("Ana de Mattos Seabra", "B. M. Seabra"));
        }

        @Test
        void pessoasDeGruposDistintosNaoSaoDuplicatas() {
            assertFalse(detector.nomesEquivalentes("Ana de Mattos Seabra", "Cassius de Souza"));
        }
    }

    @Nested
    class IntegracaoRepositorio {

        @Test
        void variantesDoMesmoAutorFormamUmGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(28372,  "Ana de Mattos Seabra"));
            repo.adicionarAutor(new Autor(582585, "Seabra A. M."));
            repo.adicionarAutor(new Autor(28371,  "Cassius de Souza"));
            repo.adicionarAutor(new Autor(746936, "Souza C."));

            assertEquals(2, repo.encontrarDuplicatas().size());
        }

        @Test
        void pessoasDistintasNaoSaoAgrupadasJuntas() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(28372, "Ana de Mattos Seabra"));
            repo.adicionarAutor(new Autor(28371, "Cassius de Souza"));

            assertEquals(0, repo.encontrarDuplicatas().size());
        }
    }
}
