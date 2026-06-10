package br.unb.tppe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Caso5IdsDiferentesTest {

    private CuradorRepositorio repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new CuradorRepositorio();
    }

    @Nested
    class ConjuntoDeDados1_RaphaelViana {

        @Test
        void deveMapearTodosOsRegistrosParaOId_31298() {
            repositorio.adicionarAutor(new Autor(31298, "Raphael Goncalves Viana"));
            repositorio.adicionarAutor(new Autor(433094, "Raphael Gonçalves Viana"));
            repositorio.adicionarAutor(new Autor(549243, "Raphael Gonçalves Viana"));
            repositorio.adicionarAutor(new Autor(608297, "Raphael Gonçalves Viana"));
            repositorio.adicionarAutor(new Autor(746938, "Raphael Gonçalves Viana"));

            repositorio.unificarIdsAutoresDuplicados();
            List<Autor> autores = repositorio.listarAutores();

            assertEquals(5, autores.size(), "Nenhum registro deve ser excluído nesta etapa");
            for (Autor autor : autores) {
                assertEquals(31298, autor.getId(),
                        "O ID falhou ao ser unificado no registro: " + autor.getNome());
            }
        }
    }

    @Nested
    class ConjuntoDeDados2_SergioGuaraldi {

        @Test
        void deveMapearTodosOsRegistrosParaOId_243350() {
            repositorio.adicionarAutor(new Autor(554799, "Sergio Henrique Guaraldi"));
            repositorio.adicionarAutor(new Autor(243350, "Sérgio Henrique Guaraldi"));
            repositorio.adicionarAutor(new Autor(954057, "SH Guaraldi"));
            repositorio.adicionarAutor(new Autor(954057, "Sérgio Henrique Guaraldi"));

            repositorio.unificarIdsAutoresDuplicados();
            List<Autor> autores = repositorio.listarAutores();

            assertEquals(4, autores.size());
            for (Autor autor : autores) {
                assertEquals(243350, autor.getId(),
                        "O ID falhou ao ser unificado no registro: " + autor.getNome());
            }
        }
    }

    @Nested
    class ConjuntoDeDados3_VanildaCristina {

        @Test
        void deveMapearTodosOsRegistrosParaOId_335284() {
            repositorio.adicionarAutor(new Autor(763027, "Vanilda Cristina Junior"));
            repositorio.adicionarAutor(new Autor(763027, "Vanilda Cristina Junior"));
            repositorio.adicionarAutor(new Autor(335284, "Vanilda Cristina Júnior"));
            repositorio.adicionarAutor(new Autor(335284, "Vanilda Cristina Júnior"));

            repositorio.unificarIdsAutoresDuplicados();
            List<Autor> autores = repositorio.listarAutores();

            assertEquals(4, autores.size());
            for (Autor autor : autores) {
                assertEquals(335284, autor.getId(),
                        "O ID falhou ao ser unificado no registro: " + autor.getNome());
            }
        }
    }
}