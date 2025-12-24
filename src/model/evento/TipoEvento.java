package model.evento;

public enum TipoEvento {

    PALESTRA("Palestra"),
    MINICURSO("Minicurso"),
    WORKSHOP("Workshop"),
    SEMINARIO("Seminário");

    private String descricao;

    TipoEvento(String descricao)
    {
        this.descricao = descricao;
    }

    public String getDescricao()
    {
        return descricao;
    }



}
