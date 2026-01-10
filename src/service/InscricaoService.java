package service;

import model.evento.Evento;
import model.usuario.Aluno;
import util.Constantes;
import java.time.Duration;
import java.time.LocalDateTime;
import model.*;
import exception.*;
import java.util.*;
import java.util.stream.Collectors;

public class InscricaoService {

    private List<Inscricao> inscricoes;

    public InscricaoService() {
        this.inscricoes = new ArrayList<>();
    }

    public Inscricao inscreverAluno(Aluno aluno, Evento evento)
            throws EventoLotadoException, InscricaoDuplicadaException {

        boolean jaInscrito = inscricoes.stream()
                .anyMatch(i -> i.getAluno().getId() == aluno.getId() &&
                        i.getEvento().getId() == evento.getId() &&
                        !i.isCancelada());

        if (jaInscrito) {
            throw new InscricaoDuplicadaException("Você já está inscrito neste evento.");
        }

        if (!evento.temVagasDsiponiveis()) {
            throw new EventoLotadoException("Evento lotado: " + evento.getTitulo());
        }

        Inscricao inscricao = new Inscricao(aluno, evento);
        inscricoes.add(inscricao);
        evento.adicionarInscricao(inscricao);
        aluno.adicionarInscricao(inscricao);

        return inscricao;
    }

    public Inscricao buscarPorId(int id) throws InscricaoNaoEncontradaException {
        return inscricoes.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InscricaoNaoEncontradaException("Inscrição não encontrada: ID" + id));
    }

    public void cancelarInscricao(int inscricaoId) throws InscricaoNaoEncontradaException, CancelamentoForaDoPrazoException {
        Inscricao inscricao = buscarPorId(inscricaoId);

        if (inscricao == null) {
            throw new InscricaoNaoEncontradaException("Não foi encontrada nenhuma inscrição com o ID: " + inscricaoId);
        }

        if(inscricao.isCancelada())
        {
            throw new IllegalStateException("Esta inscrição já está cancelada. ");
        }

        long horasAteEvento = Duration.between(LocalDateTime.now(), inscricao.getEvento().getDataHora()).toHours();

        if(horasAteEvento < Constantes.HORAS_MINIMAS_CANCELAMENTO)
        {
            throw new CancelamentoForaDoPrazoException("Cancelamento deve ser feito com " + Constantes.HORAS_MINIMAS_CANCELAMENTO + " horas de antecedência");
        }

        inscricao.setCancelada(true);
        inscricao.getEvento().removerInscricao(inscricao);

    }

    public List<Inscricao> listarPorAluno(Aluno aluno)
    {
        return inscricoes.stream()
                .filter(i -> i.getAluno().getId() == aluno.getId())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Inscricao> listarInscricoesAtivasFuturas(Aluno aluno) {
        LocalDateTime agora = LocalDateTime.now();
        return inscricoes.stream()
                .filter(i -> i.getAluno().getId() == aluno.getId())
                .filter(i -> !i.isCancelada())
                .filter(i -> i.getEvento().getDataHora().isAfter(agora)) // Apenas o que ainda vai ocorrer
                .collect(Collectors.toList());
    }

    public List<Inscricao> listarPorEvento(Evento evento)
    {
        return inscricoes.stream()
                .filter(i -> i.getEvento().getId() == evento.getId())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Inscricao> listarTodas()
    {
        return new ArrayList<>(inscricoes);
    }

    public List<Inscricao> listarHistoricoParticipacao(Aluno aluno) {
        LocalDateTime agora = LocalDateTime.now();

        return inscricoes.stream()
                .filter(i -> i.getAluno().getId() == aluno.getId())
                .filter(i -> !i.isCancelada())
                .filter(i -> i.getEvento().getDataHora().isBefore(agora))
                .sorted((i1, i2) -> i2.getEvento().getDataHora()
                        .compareTo(i1.getEvento().getDataHora()))
                .collect(Collectors.toList());
    }

}

