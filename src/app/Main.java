package app;

import model.*;
import model.evento.Evento;
import model.evento.TipoEvento;
import model.usuario.Administrador;
import model.usuario.Aluno;
import model.usuario.Professor;
import service.*;
import exception.*;
import util.GeradorRelatorio;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GERENCIAMENTO DE EVENTOS UNIVERSITÁRIOS ===\n");

        UsuarioService usuarioService = new UsuarioService();
        EventoService eventoService = new EventoService();
        InscricaoService inscricaoService = new InscricaoService();
        NotificacaoService notificacaoService = new NotificacaoService();

        Inscricao insc2 = null;
        try {
            // Cadastro de usuários
            System.out.println("📝 Cadastrando usuários...");
            Aluno aluno1 = usuarioService.cadastrarAluno("João Silva", "joao@email.com", "123", "20231001");
            Aluno aluno2 = usuarioService.cadastrarAluno("Maria Santos", "maria@email.com", "123", "20231002");
            Professor prof1 = usuarioService.cadastrarProfessor("Dr. Carlos", "carlos@email.com", "123", "Computação");
            Professor prof2 = usuarioService.cadastrarProfessor("Dra. Ana", "ana@email.com", "123", "Matemática");
            Administrador admin = usuarioService.cadastrarAdministrador("Admin Sistema", "admin@email.com", "123", "Coordenador");
            System.out.println("✅ Usuários cadastrados com sucesso!\n");

            // Criação de eventos
            System.out.println("🎯 Criando eventos...");
            Evento evento1 = eventoService.criarEvento(
                    "Introdução à Inteligência Artificial",
                    "Palestra sobre os fundamentos de IA",
                    "Dr. Carlos",
                    LocalDateTime.now().plusDays(7),
                    30,
                    TipoEvento.PALESTRA,
                    prof1,
                    "Tecnologia"
            );

            Evento evento2 = eventoService.criarEvento(
                    "Workshop de Python",
                    "Aprenda Python do zero",
                    "Dra. Ana",
                    LocalDateTime.now().plusDays(10),
                    20,
                    TipoEvento.WORKSHOP,
                    prof2,
                    "Programação"
            );

            Evento evento3 = eventoService.criarEvento(
                    "Minicurso de Machine Learning",
                    "Conceitos práticos de ML",
                    "Dr. Carlos",
                    LocalDateTime.now().plusDays(15),
                    25,
                    TipoEvento.MINICURSO,
                    prof1,
                    "Tecnologia"
            );
            System.out.println("✅ Eventos criados com sucesso!\n");

            // Notificar novo evento
            System.out.println("📢 Enviando notificações de novos eventos...");
            notificacaoService.notificarNovoEvento(evento1, usuarioService.listarTodos());
            System.out.println();

            // Inscrições
            System.out.println("✍️ Realizando inscrições...");
            try {
                Inscricao insc1 = inscricaoService.inscreverAluno(aluno1, evento1);


                Inscricao insc3 = inscricaoService.inscreverAluno(aluno2, evento1);
                Inscricao insc4 = inscricaoService.inscreverAluno(aluno2, evento3);
                System.out.println("✅ Inscrições realizadas com sucesso!\n");
            } catch (EventoLotado e) {
                System.out.println("Erro: Não há vagas disponíveis para esse evento " + e.getMessage());
            } catch (InscricaoNaoEncontrada e) {
                System.out.println("Erro de dados: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado ao realizar inscrição: " + e.getMessage());
            }
            // Listar eventos disponíveis
            System.out.println("📋 EVENTOS DISPONÍVEIS:");
            for (Evento e : eventoService.listarTodos()) {
                System.out.println(e);
                System.out.println("Inscritos: " + e.getInscricoes().size());
                System.out.println("---");
            }
            System.out.println();

            // Buscar eventos por categoria
            System.out.println("🔍 Buscando eventos de 'Tecnologia':");
            for (Evento e : eventoService.buscarPorCategoria("Tecnologia")) {
                System.out.println("- " + e.getTitulo());
            }
            System.out.println();

            // Listar inscritos em um evento
            System.out.println("👥 Inscritos no evento '" + evento1.getTitulo() + "':");
            for (Inscricao i : evento1.getInscricoes()) {
                System.out.println("- " + i.getAluno().getNome());
            }
            System.out.println();

            // Editar evento
            System.out.println("✏️ Editando evento...");
            try {
                eventoService.editarEvento(evento1.getId(),
                        "Introdução à IA e ML",
                        "Palestra sobre IA e Machine Learning",
                        "Dr. Carlos",
                        evento1.getDataHora(),
                        35,
                        "Tecnologia"
                );
                notificacaoService.notificarAlteracaoEvento(evento1);
                System.out.println("Evento atualizado e usuários notificados! ");
            } catch (EventoNaoEcontrado e) {
                System.out.println("erro ao editar: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }

            notificacaoService.notificarAlteracaoEvento(evento1);
            System.out.println();

            // Cancelar inscrição (vai falhar por estar fora do prazo em produção real)
            System.out.println("❌ Tentando cancelar inscrição...");
            try {
                if (insc2 != null) {
                    inscricaoService.cancelarInscricao(insc2.getId());
                    System.out.println("Inscrição cancelada com sucesso!");
                }
            } catch (InscricaoNaoEncontrada e) {
                System.out.println("❌ Erro de busca:" + e.getMessage());
            } catch (CancelamentoForaDoPrazo e) {
                System.out.println("Erro de prazo: " + e.getMessage());
            }

            System.out.println();

            // Relatórios
            System.out.println("📊 GERANDO RELATÓRIOS:");
            GeradorRelatorio.gerarRelatorioEventosPopulares(eventoService.listarTodos());
            GeradorRelatorio.gerarRelatorioPorAluno(usuarioService.listarAlunos());
            GeradorRelatorio.gerarEstatisticasGerais(
                    usuarioService.listarTodos(),
                    eventoService.listarTodos(),
                    inscricaoService.listarTodas()
            );

            // Teste de exceção - evento lotado
            System.out.println("\n🧪 Testando evento lotado...");
            Evento eventoLotado = eventoService.criarEvento(
                    "Evento Teste Lotado",
                    "Teste",
                    "Teste",
                    LocalDateTime.now().plusDays(5),
                    0,
                    TipoEvento.PALESTRA,
                    prof1,
                    "Teste"
            );
        } catch (Exception e){
            System.out.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
