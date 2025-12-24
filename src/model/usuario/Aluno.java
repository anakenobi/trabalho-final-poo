package model.usuario;

import interfaces.Notificavel;
import model.Inscricao;

import java.util.ArrayList;
import java.util.List;


public class Aluno extends Usuario implements Notificavel {

    private String matricula;
    private List<Inscricao> historicoEventos;

    public Aluno(String nome, String email, String senha, String matricula)
    {
        super(nome, email, senha);
        this.matricula = matricula;
        this.historicoEventos = new ArrayList<>();
    }

    public String getMatricula()
    {
        return matricula;
    }

    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }

    public List<Inscricao> getHistoricoEventos()
    {
        return new ArrayList<>(historicoEventos);
    }

    public void adicionarInscricao(Inscricao inscricao)
    {
        historicoEventos.add(inscricao);
    }

    @Override
    public String getTipo()
    {
        return "Aluno";
    }

    @Override
    public void receberNotificacao(String mensagem)
    {
        System.out.println("Notificação para " + getNome() + ": " + mensagem);
    }

}
