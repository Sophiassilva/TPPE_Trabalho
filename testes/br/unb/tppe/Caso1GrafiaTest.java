package br.unb.tppe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Caso1GrafiaTest {

    private DetectorDuplicatas detector;
    private NormalizadorNome normalizador;

    @BeforeEach
    void setUp() {
        detector = new DetectorDuplicatas();
        normalizador = new NormalizadorNome();
    }

    @Nested
    class NormalizacaoGrafia {

        @Test
        void removeAcentosEDiacriticos() {
            assertEquals("Monica",    normalizador.normalizarGrafia("Mônica"));
            assertEquals("Sergio",    normalizador.normalizarGrafia("Sérgio"));
            assertEquals("Goncalves", normalizador.normalizarGrafia("Gonçalves"));
            assertEquals("Junior",    normalizador.normalizarGrafia("Júnior"));
        }

        @Test
        void normalizaBacktickEApostrofoComoEquivalentes() {
            assertEquals(
                normalizador.normalizarGrafia("Sant'anna"),
                normalizador.normalizarGrafia("Sant`anna")
            );
        }
    }

    @Nested
    class DeteccaoDeDuplicatas {

        @Test
        void nomesDiferendoApenasNaAcentuacaoSaoDuplicatas() {
            assertTrue(detector.nomesEquivalentes("Monica Hirata Sant`anna",      "Mônica Hirata Sant'anna"));
            assertTrue(detector.nomesEquivalentes("Sergio Henrique Guaraldi",     "Sérgio Henrique Guaraldi"));
            assertTrue(detector.nomesEquivalentes("Veronica de Oliveira Moreira", "Verônica de Oliveira Moreira"));
            assertTrue(detector.nomesEquivalentes("Raphael Goncalves Viana",      "Raphael Gonçalves Viana"));
        }

        @Test
        void nomesCompletamenteDiferentesNaoSaoDuplicatas() {
            assertFalse(detector.nomesEquivalentes("Monica Hirata Sant'anna",     "Sergio Henrique Guaraldi"));
            assertFalse(detector.nomesEquivalentes("Sérgio Henrique Guaraldi",    "Raphael Gonçalves Viana"));
        }

        @Test
        void sobrenomeDiferenteNaoEhDuplicataMesmoComGrafiaParecida() {
            assertFalse(detector.nomesEquivalentes(
                "Verônica de Oliveira Moreira",
                "Verônica de Oliveira Noreira"
            ));
        }
    }

    @Nested
    class ValidacaoDeAutor {

        @Test
        void idInvalidoLancaExcecao() {
            assertThrows(AutorInvalidoException.class, () -> new Autor(0,  "Monica Hirata"));
            assertThrows(AutorInvalidoException.class, () -> new Autor(-1, "Monica Hirata"));
        }

        @Test
        void nomeInvalidoLancaExcecao() {
            assertThrows(AutorInvalidoException.class, () -> new Autor(31299, null));
            assertThrows(AutorInvalidoException.class, () -> new Autor(31299, ""));
            assertThrows(AutorInvalidoException.class, () -> new Autor(31299, "   "));
        }
    }

    @Nested
    class IntegracaoRepositorio {

        @Test
        void duplicatasComGrafiaVarianteFormamUmGrupo() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(31299,  "Monica Hirata Sant`anna"));
            repo.adicionarAutor(new Autor(433095, "Mônica Hirata Sant'anna"));
            repo.adicionarAutor(new Autor(554799, "Sergio Henrique Guaraldi"));
            repo.adicionarAutor(new Autor(243350, "Sérgio Henrique Guaraldi"));

            assertEquals(2, repo.encontrarDuplicatas().size());
        }

        @Test
        void pessoasDistintasNaoSaoAgrupadasJuntas() {
            CuradorRepositorio repo = new CuradorRepositorio();
            repo.adicionarAutor(new Autor(31299,  "Monica Hirata Sant`anna"));
            repo.adicionarAutor(new Autor(554799, "Sergio Henrique Guaraldi"));

            assertEquals(0, repo.encontrarDuplicatas().size());
        }
    }
}
