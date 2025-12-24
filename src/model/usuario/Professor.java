package model.usuario;
import interfaces.Notificavel;
import model.evento.Evento;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario implements Notificavel {

    private String departamento;
    private List<Evento> eventosCriados;

    public Professor(String nome, String email, String senha, String departamento)
    {
        super(nome, email, senha);
        this.departamento = departamento;
        this.eventosCriados = new ArrayList<>();
    }

    public String getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(String departamento)
    {
        this.departamento = departamento;
    }

    public List<Evento> getEventosCriados()
    {
        return new ArrayList<>(eventosCriados);
    }

    public void adicionarEvento(Evento evento)
    {
        eventosCriados.add(evento);
    }

    @Override
    public String getTipo()
    {
        return "Professor";
    }

    @Override
    public void receberNotificacao(String mensagem)
    {
        System.out.println("Notificação para Prof. " + getNome() + ": " + mensagem);
    }



}
