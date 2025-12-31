package service;

import model.evento.Evento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import exception.*;
import model.evento.TipoEvento;
import model.usuario.Professor;

import java.util.*;

public class EventoService {

    private List<Evento> eventos;

    public EventoService()
    {
        this.eventos = new ArrayList<>();
    }

    public Evento criarEvento(String titulo, String descricao, String palestrante, LocalDateTime datahora,
                              int vagas, TipoEvento tipo, Professor criador, String categoria)
    {
        Evento evento = new Evento(titulo, descricao, palestrante, datahora, vagas, tipo, criador, categoria);

        eventos.add(evento);
        criador.adicionarEvento(evento);
        return evento;
    }

    public void editarEvento(int id, String titulo, String descricao, String palestrante, LocalDateTime dataHora,
                             int vagas, String categoria)
            throws EventoNaoEncontradoException
    {
        Evento evento = buscarPorId(id);
        evento.setTitulo(titulo);
        evento.setDescricao(descricao);
        evento.setPalestrante(palestrante);
        evento.setDataHora(dataHora);
        evento.setVagasTotais(vagas);
        evento.setCategoria(categoria);

    }

    public void removerEvento(int id) throws EventoNaoEncontradoException
    {
        Evento evento = buscarPorId(id);
        eventos.remove(evento);
    }

    public Evento buscarPorId(int id) throws EventoNaoEncontradoException
    {
        return eventos.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado: ID " + id));
    }

    public List<Evento> listarTodos()
    {
        return new ArrayList<>(eventos);
    }

    public List<Evento> buscarPorTipo(TipoEvento tipo)
    {
        return eventos.stream()
                .filter(e -> e.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    public List<Evento> buscarPorPalestrante(String palestrante)
    {
        return eventos.stream()
                .filter(e -> e.getPalestrante().toLowerCase().contains(palestrante.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Evento> buscarPorCategoria(String categoria)
    {
        return eventos.stream()
                .filter(e -> e.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .collect(Collectors.toList());
    }




}
