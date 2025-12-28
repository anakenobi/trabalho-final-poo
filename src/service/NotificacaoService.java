package service;

import interfaces.Notificavel;
import model.*;
import model.evento.Evento;
import model.usuario.Usuario;

import java.util.List;

public class NotificacaoService {

    public void notificarNovoEvento(Evento evento, List<Usuario> usuarios)
    {
        String mensagem = "Novo evento disponível: " + evento.getTitulo();

        for(Usuario usuario : usuarios)
        {
            if (usuario instanceof Notificavel && usuario.isAtivo())
            {
                ((Notificavel) usuario).receberNotificacao(mensagem);
            }
        }
    }

    public void notificarAlteracaoEvento(Evento evento)
    {
        String mensagem = "O evento " + evento.getTitulo() + " foi alterado. Verifique os detalhes.";

        for(Inscricao inscricao : evento.getInscricoes())
        {
            if(!inscricao.isCancelada())
            {
                inscricao.getAluno().receberNotificacao(mensagem);
            }
        }
    }

    public void notificarCancelamentoEvento(Evento evento)
    {
        String mensagem = "Atenção: O evento " + evento.getTitulo() + " foi cancelado";

        for(Inscricao inscricao : evento.getInscricoes())
        {
            if(!inscricao.isCancelada())
            {
                inscricao.getAluno().receberNotificacao(mensagem);
            }
        }
    }

    public void enviarLembreteEvento(Evento evento)
    {
        String mensagem = "Lembrente: O evento " + evento.getTitulo() + " acontecerá em breve!";

        for(Inscricao inscricao : evento.getInscricoes())
        {
            if(!inscricao.isCancelada())
            {
                inscricao.getAluno().receberNotificacao(mensagem);
            }
        }
    }


}
