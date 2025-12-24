package model;

import model.evento.Evento;
import model.usuario.Aluno;

import java.time.LocalDateTime;

public class Inscricao {

    private static int contadorId = 1;
    private int id;
    private Aluno aluno;
    private Evento evento;
    private LocalDateTime dataInscricao;
    private boolean cancelada;

    public Inscricao(Aluno aluno, Evento evento)
    {
        this.aluno = aluno;
        this.evento = evento;
        this.dataInscricao = LocalDateTime.now();
        this.cancelada = false;
        this.id = contadorId++;
    }

    public Aluno getAluno()
    {
        return aluno;
    }

    public Evento getEvento()
    {
        return evento;
    }

    public LocalDateTime getDataInscricao()
    {
        return dataInscricao;
    }

    public boolean isCancelada()
    {
        return cancelada;
    }

    public void setCancelada(boolean cancelada)
    {
        this.cancelada = cancelada;
    }

    public int getId()
    {
        return id;
    }


    @Override
    public String toString() {
        return String.format("Inscrição #%d - %s em '%s' (%s)",
                id, aluno.getNome(), evento.getTitulo(),
                cancelada ? "CANCELADA" : "ATIVA");
    }



}
