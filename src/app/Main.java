import model.*;
import model.evento.*;
import model.usuario.*;
import service.*;
import exception.*;
import util.GeradorRelatorio;
import util.ConsoleUI;
import util.LeitorEntrada;
import java.time.LocalDateTime;
import java.util.List;

import static util.LeitorEntrada.pausar;

public class Main {
    private static UsuarioService usuarioService = new UsuarioService();
    private static EventoService eventoService = new EventoService();
    private static InscricaoService inscricaoService = new InscricaoService();
    private static NotificacaoService notificacaoService = new NotificacaoService();
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        inicializarDadosDemo();

        ConsoleUI.exibirCabecalho();

        boolean continuar = true;
        while (continuar) {
            if (usuarioLogado == null) {
                continuar = menuPrincipal();
            } else {
                continuar = menuUsuarioLogado();
            }
        }

        ConsoleUI.exibirSucesso("Obrigado por usar o sistema!");
        LeitorEntrada.fechar();
    }

    private static void inicializarDadosDemo() {
        usuarioService.cadastrarAluno("João Silva", "joao@email.com", "123", "20231001");
        usuarioService.cadastrarAluno("Maria Santos", "maria@email.com", "123", "20231002");
        Professor prof1 = usuarioService.cadastrarProfessor("Dr. Carlos", "carlos@email.com", "123", "Computação");
        usuarioService.cadastrarProfessor("Dra. Ana", "ana@email.com", "123", "Matemática");
        usuarioService.cadastrarAdministrador("Admin Sistema", "admin@email.com", "123", "Coordenador");

        eventoService.criarEvento(
                "Introdução à Inteligência Artificial",
                "Palestra sobre os fundamentos de IA",
                "Dr. Carlos",
                LocalDateTime.now().plusDays(7),
                30,
                TipoEvento.PALESTRA,
                prof1,
                "Tecnologia"
        );

        eventoService.criarEvento(
                "Workshop de Python",
                "Aprenda Python do zero",
                "Dra. Ana",
                LocalDateTime.now().plusDays(10),
                20,
                TipoEvento.WORKSHOP,
                prof1,
                "Programação"
        );

        eventoService.criarEvento(
                "Minicurso de Machine Learning",
                "Conceitos práticos de ML",
                "Dr. Carlos",
                LocalDateTime.now().plusDays(15),
                25,
                TipoEvento.MINICURSO,
                prof1,
                "Tecnologia"
        );
    }

    private static boolean menuPrincipal() {
        ConsoleUI.exibirMenuPrincipal();
        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1:
                realizarLogin();
                break;
            case 2:
                cadastrarAluno();
                break;
            case 3:
                cadastrarProfessor();
                break;
            case 4:
                listarEventosDisponiveis();
                break;
            case 0:
                return false;
            default:
                ConsoleUI.exibirErro("Opção inválida!");
        }
        return true;
    }

    private static boolean menuUsuarioLogado() {
        if (usuarioLogado instanceof Aluno) {
            return menuAluno();
        } else if (usuarioLogado instanceof Professor) {
            return menuProfessor();
        } else if (usuarioLogado instanceof Administrador) {
            return menuAdministrador();
        }
        return true;
    }

    private static boolean menuAluno() {
        ConsoleUI.exibirMenuAluno(usuarioLogado.getNome());
        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1:
                listarEventosDisponiveis();
                break;
            case 2:
                inscreverEmEvento();
                break;
            case 3:
                verMinhasInscricoes();
                break;
            case 4:
                cancelarInscricao();
                break;
            case 5:
                buscarEventos();
                break;
            case 6:
                verHistoricoParticipacao();
                break;
            case 0:
                usuarioLogado = null;
                ConsoleUI.exibirSucesso("Logout realizado com sucesso!");
                break;
            default:
                ConsoleUI.exibirErro("Opção inválida!");
        }
        return true;
    }

    private static boolean menuProfessor() {
        ConsoleUI.exibirMenuProfessor(usuarioLogado.getNome());
        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1:
                criarEvento();
                break;
            case 2:
                verMeusEventos();
                break;
            case 3:
                editarEvento();
                break;
            case 4:
                verInscritosEvento();
                break;
            case 5:
                listarEventosDisponiveis();
                break;
            case 0:
                usuarioLogado = null;
                ConsoleUI.exibirSucesso("Logout realizado com sucesso!");
                break;
            default:
                ConsoleUI.exibirErro("Opção inválida!");
        }
        return true;
    }

    private static boolean menuAdministrador() {
        ConsoleUI.exibirMenuAdministrador(usuarioLogado.getNome());
        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1:
                gerenciarEventos();
                break;
            case 2:
                gerenciarUsuarios();
                break;
            case 3:
                gerarRelatorios();
                break;
            case 4:
                listarEventosDisponiveis();
                break;
            case 5:
                verInscritosEvento();
                break;
            case 0:
                usuarioLogado = null;
                ConsoleUI.exibirSucesso("Logout realizado com sucesso!");
                break;
            default:
                ConsoleUI.exibirErro("Opção inválida!");
        }
        return true;
    }

    private static void realizarLogin() {
        ConsoleUI.exibirTitulo("LOGIN");
        String email = LeitorEntrada.lerString("Email: ");
        String senha = LeitorEntrada.lerString("Senha: ");

        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario.getSenha().equals(senha)) {
                if (usuario.isAtivo()) {
                    usuarioLogado = usuario;
                    ConsoleUI.exibirSucesso("Login realizado com sucesso!");
                    ConsoleUI.exibirInfo("Bem-vindo(a), " + usuario.getNome() + "!");
                } else {
                    ConsoleUI.exibirErro("Usuário desativado. Contate o administrador.");
                }
            } else {
                ConsoleUI.exibirErro("Senha incorreta!");
            }
        } catch (UsuarioNaoEncontradoException e) {
            ConsoleUI.exibirErro("Usuário não encontrado!");
        }
    }

    private static void cadastrarAluno() {
        ConsoleUI.exibirTitulo("CADASTRO DE ALUNO");

        String nome = LeitorEntrada.lerString("Nome completo: ");
        String email = LeitorEntrada.lerString("Email: ");
        String senha = LeitorEntrada.lerString("Senha: ");
        String matricula = LeitorEntrada.lerString("Matrícula: ");

        usuarioService.cadastrarAluno(nome, email, senha, matricula);
        ConsoleUI.exibirSucesso("Aluno cadastrado com sucesso!");
        ConsoleUI.exibirInfo("Você já pode fazer login!");
    }

    private static void cadastrarProfessor() {
        ConsoleUI.exibirTitulo("CADASTRO DE PROFESSOR");

        String nome = LeitorEntrada.lerString("Nome completo: ");
        String email = LeitorEntrada.lerString("Email: ");
        String senha = LeitorEntrada.lerString("Senha: ");
        String departamento = LeitorEntrada.lerString("Departamento: ");

        usuarioService.cadastrarProfessor(nome, email, senha, departamento);
        ConsoleUI.exibirSucesso("Professor cadastrado com sucesso!");
        ConsoleUI.exibirInfo("Você já pode fazer login!");
    }

    private static void listarEventosDisponiveis() {
        List<Evento> eventos = eventoService.listarTodos();

        if (eventos.isEmpty()) {
            ConsoleUI.exibirErro("Nenhum evento disponível no momento.");
            return;
        }

        ConsoleUI.exibirTitulo("EVENTOS DISPONÍVEIS");
        eventos.forEach(ConsoleUI::exibirEvento);
        pausar();
    }

    private static void inscreverEmEvento() {
            if (!(usuarioLogado instanceof Aluno)) return;

            ConsoleUI.exibirTitulo("INSCRIÇÃO EM EVENTO");
            listarEventosDisponiveis();

            int id = LeitorEntrada.lerInteiro("\nDigite o ID do evento: ");

            try {
                Evento evento = eventoService.buscarPorId(id);
                Aluno aluno = (Aluno) usuarioLogado;

                inscricaoService.inscreverAluno(aluno, evento);

                ConsoleUI.exibirSucesso("Inscrição realizada com sucesso!");
                ConsoleUI.exibirInfo("Evento: " + evento.getTitulo());

            } catch (EventoNaoEncontradoException e) {
                ConsoleUI.exibirErro("Erro: " + e.getMessage());

            } catch (EventoLotadoException e) {
                ConsoleUI.exibirErro("Desculpe: " + e.getMessage());

            } catch (InscricaoDuplicadaException e) {
                // Captura a nova exceção e mostra a mensagem amigável definida no Service
                ConsoleUI.exibirErro("Atenção: " + e.getMessage());
            }

            pausar();
        }

    private static void verMinhasInscricoes() {
            if (!(usuarioLogado instanceof Aluno)) return;

            Aluno aluno = (Aluno) usuarioLogado;
            LocalDateTime agora = LocalDateTime.now();

            List<Inscricao> futuras = inscricaoService.listarPorAluno(aluno).stream()
                    .filter(i -> !i.isCancelada())
                    .filter(i -> i.getEvento().getDataHora().isAfter(agora))
                    .collect(java.util.stream.Collectors.toList());

            if (futuras.isEmpty()) {
                ConsoleUI.exibirInfo("Você não tem eventos pendentes para participar.");
                pausar();
                return;
            }

            ConsoleUI.exibirTitulo("Minhas próximas inscrições");
            futuras.forEach(ConsoleUI::exibirInscricao);
            pausar();
        }


        private static void cancelarInscricao() {
        if (!(usuarioLogado instanceof Aluno)) return;

        verMinhasInscricoes();

        int id = LeitorEntrada.lerInteiro("\nDigite o ID da inscrição a cancelar (0 para voltar): ");

        if (id == 0) return;

        try {
            inscricaoService.cancelarInscricao(id);
            ConsoleUI.exibirSucesso("Inscrição cancelada com sucesso!");
        } catch (InscricaoNaoEncontradaException | CancelamentoForaDoPrazoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }

        pausar();
    }

    private static void verHistoricoParticipacao() {
        if (!(usuarioLogado instanceof Aluno)) return;

        Aluno aluno = (Aluno) usuarioLogado;
        List<Inscricao> historico = inscricaoService.listarHistoricoParticipacao(aluno);

        if (historico.isEmpty()) {
            ConsoleUI.exibirInfo("Você ainda não participou de eventos encerrados.");
            pausar();
            return;
        }

        ConsoleUI.exibirTitulo("Histórico de Participação (Eventos Encerrados)");
        System.out.println("Aluno: " + aluno.getNome());

        for (Inscricao i : historico) {
            Evento e = i.getEvento();
            System.out.println("[FINALIZADO] " + e.getTitulo());
            System.out.println("Data: " + e.getDataHora().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("Tipo: " + e.getTipo().getDescricao());
            System.out.println("Palestrante: " + e.getPalestrante());
        }

        pausar();
    }

    private static void buscarEventos() {
        ConsoleUI.exibirTitulo("BUSCAR EVENTOS");
        System.out.println("1. Por Tipo");
        System.out.println("2. Por Palestrante");
        System.out.println("3. Por Categoria");
        System.out.print("\nEscolha: ");

        int opcao = LeitorEntrada.lerInteiro();
        List<Evento> resultados = null;

        switch (opcao) {
            case 1:
                System.out.println("\nTipos disponíveis:");
                TipoEvento[] tipos = TipoEvento.values();
                for (int i = 0; i < tipos.length; i++) {
                    System.out.println((i + 1) + ". " + tipos[i].getDescricao());
                }
                System.out.print("Escolha: ");
                int escolhaTipo = LeitorEntrada.lerInteiro() - 1;
                if (escolhaTipo >= 0 && escolhaTipo < tipos.length) {
                    resultados = eventoService.buscarPorTipo(tipos[escolhaTipo]);
                }
                break;
            case 2:
                String palestrante = LeitorEntrada.lerString("Nome do palestrante: ");
                resultados = eventoService.buscarPorPalestrante(palestrante);
                break;
            case 3:
                String categoria = LeitorEntrada.lerString("Categoria: ");
                resultados = eventoService.buscarPorCategoria(categoria);
                break;
        }

        if (resultados != null) {
            if (resultados.isEmpty()) {
                ConsoleUI.exibirErro("Nenhum evento encontrado.");
            } else {
                ConsoleUI.exibirSucesso(resultados.size() + " evento(s) encontrado(s):");
                resultados.forEach(ConsoleUI::exibirEvento);
            }
        }

        pausar();
    }

    private static void criarEvento() {
        if (!(usuarioLogado instanceof Professor)) return;

        ConsoleUI.exibirTitulo("CRIAR EVENTO");

        String titulo = LeitorEntrada.lerString("Título do evento: ");
        String descricao = LeitorEntrada.lerString("Descrição: ");
        String palestrante = LeitorEntrada.lerString("Palestrante: ");
        int dias = LeitorEntrada.lerInteiro("Daqui a quantos dias será o evento? ");
        int vagas = LeitorEntrada.lerInteiro("Número de vagas: ");

        System.out.println("\nTipos de evento:");
        TipoEvento[] tipos = TipoEvento.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].getDescricao());
        }
        int escolhaTipo = LeitorEntrada.lerInteiro("Escolha o tipo: ") - 1;

        if (escolhaTipo < 0 || escolhaTipo >= tipos.length) {
            ConsoleUI.exibirErro("Tipo inválido!");
            return;
        }

        String categoria = LeitorEntrada.lerString("Categoria: ");

        LocalDateTime dataHora = LocalDateTime.now().plusDays(dias);
        Professor prof = (Professor) usuarioLogado;

        Evento evento = eventoService.criarEvento(titulo, descricao, palestrante,
                dataHora, vagas, tipos[escolhaTipo], prof, categoria);

        notificacaoService.notificarNovoEvento(evento, usuarioService.listarTodos());

        ConsoleUI.exibirSucesso("Evento criado com sucesso!");
        ConsoleUI.exibirInfo("ID do evento: " + evento.getId());

        pausar();
    }

    private static void verMeusEventos() {
        if (!(usuarioLogado instanceof Professor)) return;

        Professor prof = (Professor) usuarioLogado;
        List<Evento> eventos = prof.getEventosCriados();

        if (eventos.isEmpty()) {
            ConsoleUI.exibirErro("Você ainda não criou eventos.");
            pausar();
            return;
        }

        ConsoleUI.exibirTitulo("MEUS EVENTOS");
        eventos.forEach(ConsoleUI::exibirEvento);
        pausar();
    }

    private static void editarEvento() {
        if (!(usuarioLogado instanceof Professor)) return;

        verMeusEventos();
        int id = LeitorEntrada.lerInteiro("\nDigite o ID do evento a editar (0 para voltar): ");
        if (id == 0) return;

        try {
            Evento evento = eventoService.buscarPorId(id);
            Professor prof = (Professor) usuarioLogado;

            if (evento.getCriador().getId() != prof.getId()) {
                ConsoleUI.exibirErro("Você só pode editar seus próprios eventos!");
            } else {
                processarEdicao(evento); // Chama o motor de edição
            }
        } catch (EventoNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }
        pausar();
    }

    private static void verInscritosEvento() {
        if (!(usuarioLogado instanceof Professor) && !(usuarioLogado instanceof Administrador)){
            ConsoleUI.exibirErro("Acesso negado");
            return;
        }

        if(usuarioLogado instanceof Professor){
            verMeusEventos();
        } else {
            listarEventosDisponiveis(); // administrador ve os eventos
        }

        int id = LeitorEntrada.lerInteiro("\nDigite o ID do evento para ver os inscritos no eventos: ");

        try {
            Evento evento = eventoService.buscarPorId(id);
            if(usuarioLogado instanceof Professor){
                Professor prof = (Professor) usuarioLogado;
                if(evento.getCriador().getId() != prof.getId()){
                    ConsoleUI.exibirErro("Você só pode acessar seus eventos");
                    pausar();
                    return;
                }
            }

            List<Inscricao> inscricoes = evento.getInscricoes();

            if (inscricoes.isEmpty()) {
                ConsoleUI.exibirErro("Nenhum inscrito neste evento.");
            } else {
                ConsoleUI.exibirTitulo("Inscritos em: " + evento.getTitulo());
                System.out.println("Vagas ocupadas: " + inscricoes.size() + "/" + evento.getVagasTotais());

            }

            ConsoleUI.exibirTitulo("INSCRITOS EM: " + evento.getTitulo());

            int contador = 1;
            for (Inscricao i : inscricoes) {
                if (!i.isCancelada()) {
                    System.out.println(contador++ + ". " + i.getAluno().getNome() +
                            " (" + i.getAluno().getEmail() + ")" +
                            " (" + i.getAluno().getMatricula() + ")" );
                }
            }
        } catch (EventoNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }

        pausar();
    }

    private static void gerenciarEventos() {
        ConsoleUI.exibirTitulo("GERENCIAR EVENTOS");
        System.out.println("1. Cadastrar Novo Evento");
        System.out.println("2. Editar Evento");
        System.out.println("3. Remover Evento");
        System.out.println("4. Listar Todos os Eventos");
        System.out.println("5. Ver inscritos nos Eventos");
        System.out.print("\nEscolha: ");

        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1:
                adminCadastrarEvento();
                break;
            case 2:
                adminEditarEvento();
                break;
            case 3:
                removerEvento();
                break;
            case 4:
                listarEventosDisponiveis();
                break;
            case 5:
                verInscritosEvento();
                break;

        }
    }

    private static void adminCadastrarEvento() {
        ConsoleUI.exibirTitulo("CADASTRAR EVENTO (ADMIN)");

        // Listar professores disponíveis
        List<Usuario> professores = usuarioService.listarTodos().stream()
                .filter(u -> u instanceof Professor)
                .collect(java.util.stream.Collectors.toList());

        if (professores.isEmpty()) {
            ConsoleUI.exibirErro("Nenhum professor cadastrado no sistema!");
            LeitorEntrada.pausar();
            return;
        }

        System.out.println("\nProfessores disponíveis:");
        for (int i = 0; i < professores.size(); i++) {
            System.out.println((i + 1) + ". " + professores.get(i).getNome());
        }

        int escolhaProfessor = LeitorEntrada.lerInteiro("\nEscolha o professor responsável: ") - 1;

        if (escolhaProfessor < 0 || escolhaProfessor >= professores.size()) {
            ConsoleUI.exibirErro("Professor inválido!");
            LeitorEntrada.pausar();
            return;
        }

        Professor prof = (Professor) professores.get(escolhaProfessor);

        String titulo = LeitorEntrada.lerString("Título do evento: ");
        String descricao = LeitorEntrada.lerString("Descrição: ");
        String palestrante = LeitorEntrada.lerString("Palestrante: ");
        int dias = LeitorEntrada.lerInteiro("Daqui a quantos dias será o evento? ");
        int vagas = LeitorEntrada.lerInteiro("Número de vagas: ");

        System.out.println("\nTipos de evento:");
        TipoEvento[] tipos = TipoEvento.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].getDescricao());
        }
        int escolhaTipo = LeitorEntrada.lerInteiro("Escolha o tipo: ") - 1;

        if (escolhaTipo < 0 || escolhaTipo >= tipos.length) {
            ConsoleUI.exibirErro("Tipo inválido!");
            LeitorEntrada.pausar();
            return;
        }

        String categoria = LeitorEntrada.lerString("Categoria: ");
        LocalDateTime dataHora = LocalDateTime.now().plusDays(dias);

        Evento evento = eventoService.criarEvento(titulo, descricao, palestrante,
                dataHora, vagas, tipos[escolhaTipo], prof, categoria);

        notificacaoService.notificarNovoEvento(evento, usuarioService.listarTodos());

        ConsoleUI.exibirSucesso("Evento cadastrado com sucesso pelo administrador!");
        ConsoleUI.exibirInfo("ID do evento: " + evento.getId());
        ConsoleUI.exibirInfo("Professor responsável: " + prof.getNome());

        LeitorEntrada.pausar();
    }

    public static void adminEditarEvento() {
        ConsoleUI.exibirTitulo("Editar Evento (Admin)");

        List<Evento> eventos = eventoService.listarTodos();
        if (eventos.isEmpty()) {
            ConsoleUI.exibirErro("Não há eventos cadastrados.");
            pausar();
            return;
        }
        eventos.forEach(ConsoleUI::exibirEvento);
        int id = LeitorEntrada.lerInteiro("\nDigite o ID do evento a editar (0 para voltar): ");
        if (id == 0) return;

        try {
            Evento evento = eventoService.buscarPorId(id);
            // O Admin tem permissão total, então não checamos o criador aqui
            processarEdicao(evento);
        } catch (EventoNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }
        pausar();
    }

    private static void processarEdicao(Evento evento) {
        ConsoleUI.exibirTitulo("EDITANDO: " + evento.getTitulo());
        System.out.println("(Pressione Enter para manter o valor atual)\n");

        String titulo = LeitorEntrada.lerString("Título [" + evento.getTitulo() + "]: ");
        if (titulo.isEmpty()) titulo = evento.getTitulo();

        String descricao = LeitorEntrada.lerString("Descrição [" + evento.getDescricao() + "]: ");
        if (descricao.isEmpty()) descricao = evento.getDescricao();

        String palestrante = LeitorEntrada.lerString("Palestrante [" + evento.getPalestrante() + "]: ");
        if (palestrante.isEmpty()) palestrante = evento.getPalestrante();

        String vagasStr = LeitorEntrada.lerString("Número de vagas [" + evento.getVagasTotais() + "]: ");
        int vagas = vagasStr.isEmpty() ? evento.getVagasTotais() : Integer.parseInt(vagasStr);

        String categoria = LeitorEntrada.lerString("Categoria [" + evento.getCategoria() + "]: ");
        if (categoria.isEmpty()) categoria = evento.getCategoria();

        try {
            eventoService.editarEvento(evento.getId(), titulo, descricao, palestrante,
                    evento.getDataHora(), vagas, categoria);

            notificacaoService.notificarAlteracaoEvento(evento);
            ConsoleUI.exibirSucesso("Evento atualizado com sucesso!");
        } catch (Exception e) {
            ConsoleUI.exibirErro("Erro ao atualizar: " + e.getMessage());
        }
    }


    private static void removerEvento() {
        listarEventosDisponiveis();
        
        int id = LeitorEntrada.lerInteiro("\nDigite o ID do evento a remover (0 para cancelar): ");
        
        if (id == 0) return;

        try {
            Evento evento = eventoService.buscarPorId(id);
            
            String confirmacao = LeitorEntrada.lerString("\nTem certeza que deseja remover '" + evento.getTitulo() + "'? (S/N): ");

            if (confirmacao.equalsIgnoreCase("S")) {
                notificacaoService.notificarCancelamentoEvento(evento);
                eventoService.removerEvento(id);
                ConsoleUI.exibirSucesso("Evento removido com sucesso!");
            } else {
                ConsoleUI.exibirErro("Operação cancelada.");
            }
        } catch (EventoNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }
        
        pausar();
    }

    private static void gerenciarUsuarios() {
        ConsoleUI.exibirTitulo("GERENCIAR USUÁRIOS");
        System.out.println("1. Listar Usuários");
        System.out.println("2. Ativar/Desativar Usuário");
        System.out.println("3. Remover Usuário");
        System.out.print("\nEscolha: ");

        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1: listarUsuarios(); break;
            case 2: ativarDesativarUsuario(); break;
            case 3: removerUsuario(); break;
        }
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();

        ConsoleUI.exibirTitulo("USUÁRIOS CADASTRADOS");
        usuarios.forEach(ConsoleUI::exibirUsuario);
        pausar();
    }

    private static void ativarDesativarUsuario() {
        listarUsuarios();
        
        int id = LeitorEntrada.lerInteiro("Digite o ID do usuário: ");

        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            String acao = usuario.isAtivo() ? "desativar" : "ativar";
            
            String confirmacao = LeitorEntrada.lerString("\nDeseja " + acao + " o usuário " + usuario.getNome() + "? (S/N): ");

            if (confirmacao.equalsIgnoreCase("S")) {
                usuario.setAtivo(!usuario.isAtivo());
                ConsoleUI.exibirSucesso("Usuário " + (usuario.isAtivo() ? "ativado" : "desativado") + " com sucesso!");
            }
        } catch (UsuarioNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }
        
        pausar();
    }

    private static void removerUsuario() {
        listarUsuarios();
        
        int id = LeitorEntrada.lerInteiro("Digite o ID do usuário a remover (0 para cancelar): ");
        
        if (id == 0) return;

        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            
            String confirmacao = LeitorEntrada.lerString("\nTem certeza que deseja remover " + usuario.getNome() + "? (S/N): ");

            if (confirmacao.equalsIgnoreCase("S")) {
                usuarioService.removerUsuario(id);
                ConsoleUI.exibirSucesso("Usuário removido com sucesso!");
            } else {
                ConsoleUI.exibirErro("Operação cancelada.");
            }
        } catch (UsuarioNaoEncontradoException e) {
            ConsoleUI.exibirErro(e.getMessage());
        }
        
        pausar();
    }

    private static void gerarRelatorios() {
        ConsoleUI.exibirTitulo("RELATÓRIOS");
        System.out.println("1. Eventos Mais Populares");
        System.out.println("2. Inscrições por Aluno");
        System.out.println("3. Estatísticas Gerais");
        System.out.print("\nEscolha: ");

        int opcao = LeitorEntrada.lerInteiro();

        switch (opcao) {
            case 1: relatorioEventosPopulares(); break;
            case 2: relatorioInscricoesPorAluno(); break;
            case 3: relatorioEstatisticasGerais(); break;
        }
    }

    private static void relatorioEventosPopulares() {
        List<Evento> eventos = eventoService.listarTodos();
        eventos.sort((e1, e2) -> Integer.compare(
            e2.getInscricoes().size(),
            e1.getInscricoes().size()));

        ConsoleUI.exibirTitulo("EVENTOS MAIS POPULARES");

        int limite = Math.min(5, eventos.size());
        for (int i = 0; i < limite; i++) {
            Evento e = eventos.get(i);
            System.out.println((i + 1) + "º - " + e.getTitulo());
            System.out.println("    Inscrições: " + e.getInscricoes().size());
            System.out.println("    Vagas totais: " + e.getVagasTotais());
            System.out.println();
        }
        
        pausar();
    }

    private static void relatorioInscricoesPorAluno() {
        List<Aluno> alunos = usuarioService.listarAlunos();
        alunos.sort((a1, a2) -> Integer.compare(
            a2.getHistoricoEventos().size(),
            a1.getHistoricoEventos().size()));

        ConsoleUI.exibirTitulo("INSCRIÇÕES POR ALUNO");

        for (Aluno a : alunos) {
            long inscricoesAtivas = a.getHistoricoEventos().stream()
                .filter(i -> !i.isCancelada()).count();
            System.out.println("• " + a.getNome());
            System.out.println("  Inscrições ativas: " + inscricoesAtivas);
            System.out.println("  Total de inscrições: " + a.getHistoricoEventos().size());
            System.out.println();
        }
        
        pausar();
    }

    private static void relatorioEstatisticasGerais() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<Evento> eventos = eventoService.listarTodos();
        List<Inscricao> inscricoes = inscricaoService.listarTodas();

        long usuariosAtivos = usuarios.stream().filter(Usuario::isAtivo).count();
        long inscricoesAtivas = inscricoes.stream()
            .filter(i -> !i.isCancelada()).count();

        long totalAlunos = usuarios.stream()
            .filter(u -> u instanceof Aluno).count();
        long totalProfessores = usuarios.stream()
            .filter(u -> u instanceof Professor).count();
        long totalAdmins = usuarios.stream()
            .filter(u -> u instanceof Administrador).count();

        ConsoleUI.exibirTitulo("ESTATÍSTICAS GERAIS DO SISTEMA");

        System.out.println("┌─── USUÁRIOS ─────────────────────────────────");
        System.out.println("│ Total de usuários: " + usuarios.size());
        System.out.println("│ Usuários ativos: " + usuariosAtivos);
        System.out.println("│ Alunos: " + totalAlunos);
        System.out.println("│ Professores: " + totalProfessores);
        System.out.println("│ Administradores: " + totalAdmins);
        System.out.println("└──────────────────────────────────────────────\n");

        System.out.println("┌─── EVENTOS ──────────────────────────────────");
        System.out.println("│ Total de eventos: " + eventos.size());
        System.out.println("│ Palestras: " + 
            eventos.stream().filter(e -> e.getTipo() == TipoEvento.PALESTRA).count());
        System.out.println("│ Workshops: " + 
            eventos.stream().filter(e -> e.getTipo() == TipoEvento.WORKSHOP).count());
        System.out.println("│ Minicursos: " + 
            eventos.stream().filter(e -> e.getTipo() == TipoEvento.MINICURSO).count());
        System.out.println("│ Seminários: " + 
            eventos.stream().filter(e -> e.getTipo() == TipoEvento.SEMINARIO).count());
        System.out.println("└──────────────────────────────────────────────\n");

        System.out.println("┌─── INSCRIÇÕES ───────────────────────────────");
        System.out.println("│ Total de inscrições: " + inscricoes.size());
        System.out.println("│ Inscrições ativas: " + inscricoesAtivas);
        System.out.println("│ Inscrições canceladas: " + (inscricoes.size() - inscricoesAtivas));
        System.out.println("└──────────────────────────────────────────────\n");
        
        pausar();
    }
}