package model.evento;

import model.Inscricao;
import model.usuario.Professor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private static int contadorId = 1;
    private int id;
    private String titulo;
    private String descricao;
    private String palestrante;
    private LocalDateTime dataHora;
    private int vagasDisponiveis;
    private int vagasTotais;
    private TipoEvento tipo;
    private Professor criador;
    private List<Inscricao> inscricoes;
    private String categoria;

    public Evento(String titulo, String descricao, String palestrante, LocalDateTime dataHora, int vagas,
                  TipoEvento tipo, Professor criador, String categoria)
    {
        this.titulo = titulo;
        this.descricao = descricao;
        this.palestrante = palestrante;
        this.dataHora = dataHora;
        this.vagasDisponiveis = vagas;
        this.vagasTotais = vagas;
        this.tipo = tipo;
        this.criador = criador;
        this.inscricoes = new ArrayList<>();
        this.categoria = categoria;
        this.id = contadorId++;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getPalestrante()
    {
        return palestrante;
    }

    public void setPalestrante(String palestrante)
    {
        this.palestrante = palestrante;
    }

    public LocalDateTime getDataHora()
    {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora)
    {
        this.dataHora = dataHora;
    }

    public int getVagasDisponiveis()
    {
        return vagasDisponiveis;
    }

    public int getVagasTotais()
    {
        return vagasTotais;
    }

    public void setVagasTotais(int vagas)
    {
        this.vagasTotais = vagas;
        this.vagasDisponiveis = vagas - inscricoes.size();
    }
    public int getId()
    {
        return id;
    }

    public TipoEvento getTipo()
    {
        return tipo;
    }

    public void setTipo(TipoEvento tipo)
    {
        this.tipo = tipo;
    }

    public Professor getCriador()
    {
        return criador;
    }

    public List<Inscricao> getInscricoes()
    {
        return new ArrayList<>(inscricoes);
    }

    public String getCategoria()
    {
        return  categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public void adicionarInscricao(Inscricao inscricao)
    {
        inscricoes.add(inscricao);
        vagasDisponiveis--;
    }

    public void removerInscricao(Inscricao inscricao)
    {
        inscricoes.remove(inscricao);
        vagasDisponiveis++;
    }

    public boolean temVagasDsiponiveis()
    {
        return vagasDisponiveis > 0;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s\nPalestrante: %s | Data: %s | Vagas: %d/%d\nCategoria: %s",
                tipo.getDescricao(), titulo, palestrante, dataHora.format(f),
                vagasDisponiveis, vagasTotais, categoria);
    }





}
