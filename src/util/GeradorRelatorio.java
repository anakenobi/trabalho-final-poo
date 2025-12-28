package util;

import model.*;
import model.evento.Evento;
import model.usuario.Aluno;
import model.usuario.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class GeradorRelatorio {

    public static void gerarRelatorioEventosPopulares(List<Evento> eventos) {
        System.out.println("\n ---- EVENTOS MAIS POPULARES ----");
        eventos.stream()
                .sorted((e1, e2) -> Integer.compare(
                        e2.getInscricoes().size(),
                        e1.getInscricoes().size()))
                .limit(5)
                .forEach(e -> System.out.printf("%s - %d inscrições\n",
                        e.getTitulo(), e.getInscricoes().size()));
    }

    public static void gerarRelatorioPorAluno(List<Aluno> alunos)
    {
        System.out.println("\n---- INSCRIÇÕES POR ALUNO ----");
        alunos.stream()
                .sorted((a1, a2) -> Integer.compare(
                     a2.getHistoricoEventos().size(),
                     a1.getHistoricoEventos().size()))
                .forEach(a -> System.out.printf("%s - %d inscrições\n",
                        a.getNome(), a.getHistoricoEventos().size()));

    }

    public static void gerarEstatisticasGerais(List<Usuario> usuarios, List<Evento> eventos, List<Inscricao> inscricoes)
    {
        System.out.println("\n ---- ESTATÍSTICAS GERAIS ----");
        long usuariosAtivos = usuarios.stream().filter(Usuario::isAtivo).count();
        long totalInscricoesAtivas = inscricoes.stream()
                .filter(i -> !i.isCancelada()).count();


        System.out.println("Total de usuários: " + usuarios.size());
        System.out.println("Usuários ativos: " + usuariosAtivos);
        System.out.println("Total de eventos: " + eventos.size());
        System.out.println("Total de inscrições ativas: " + totalInscricoesAtivas);

    }





}
