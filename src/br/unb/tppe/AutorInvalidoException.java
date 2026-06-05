package br.unb.tppe;

public class AutorInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AutorInvalidoException(String mensagem) {
        super(mensagem);
    }
}
