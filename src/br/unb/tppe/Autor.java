package br.unb.tppe;

public class Autor {

    private final long id;
    private final String nome;

    public Autor(long id, String nome) {
        if (id <= 0)
            throw new AutorInvalidoException("ID deve ser um valor positivo");
        if (nome == null || nome.isBlank())
            throw new AutorInvalidoException("Nome não pode ser nulo ou vazio");

        this.id = id;
        this.nome = nome.trim();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", nome='" + nome + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor outro = (Autor) o;
        return id == outro.id && nome.equals(outro.nome);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id) * 31 + nome.hashCode();
    }
}
