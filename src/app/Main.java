package app;

import model.*;
import model.evento.Evento;
import model.evento.TipoEvento;
import model.usuario.Administrador;
import model.usuario.Aluno;
import model.usuario.Professor;
import model.usuario.Usuario;
import service.*;
import exception.*;
import util.GeradorRelatorio;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static UsuarioService usuarioService = new UsuarioService();
    private static EventoService eventoService = new EventoService();
    private static InscricaoService inscricaoService = new InscricaoService();
    private static NotificacaoService notificacaoService = new NotificacaoService();
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {

        System.out.println("********************************************************");
        System.out.println("SISTEMA DE GERENCIAMENTO DE EVENTOS UNIVERSITÁRIOS ");
        System.out.println("********************************************************");

        boolean continuar = true;
        while (continuar) {
            if (usuarioLogado == null) {
                continuar = menuPrincipal();
            } else {
                continuar = menuUsuarioLogado();
            }
        }
        System.out.println("\n Obrigada por usar o sistema! ");
        sc.close();

    }



        private static boolean menuPrincipal()
        {
            System.out.println("-----MENU PRINCIPAL-----");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar como aluno");
            System.out.println("3. Cadastrar como Professor");
            System.out.println("4. Ver Eventos (sem login)");
            System.out.println("0. Sair");

            int opcao = lerInteiro();

            switch (opcao){
                case 1: realizarLogin();
                break;
                case 2: cadastrarAluno();
                break;
                case 3: cadastrarProfessor();
                break;
                case 4: listarEventosDisponiveis();
                break;
                case 0: return false;
                default: System.out.println("Opção inválida");
            }
            return true;

        }

        private static boolean menuUsuarioLogado()
        {
            if (usuarioLogado instanceof Aluno){
                return menuAluno();
            } else if (usuarioLogado instanceof Professor) {
                return menuProfessor();
            } else if (usuarioLogado instanceof Administrador) {
                return menuAdministrador();
            }
            return true;
        }

        private static boolean menuAluno(){
            System.out.println("***************************");
            System.out.println("MENU ALUNO");
            System.out.println("****************************");
            System.out.println("Bem vindo(a) " + usuarioLogado.getNome());
            System.out.println("1. Ver eventos disponíveis");
            System.out.println("2. Inscrever-se em evento");
            System.out.println("3. Minhas inscrições");
            System.out.println("4. Cancelar inscrição ");
            System.out.println("5. Buscar Eventos ");
            System.out.println("0. Logout");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1: listarEventosDisponiveis();
                break;
                case 2: inscreverEmEvento();
                break;
                case 3: verMinhasInscricoes();
                break;
                case 4: cancelarInscricao();
                break;
                case 5: buscarEventos();
                break;
                case 0:
                    usuarioLogado = null;
                    System.out.println("Logout realizado com sucesso!");
                    break;
                default: System.out.println("Opção inválida!");
            }
            return true;
        }

    private static boolean menuProfessor(){
        System.out.println("***************************");
        System.out.println("MENU Professor");
        System.out.println("****************************");
        System.out.println("Bem vindo(a) " + usuarioLogado.getNome());
        System.out.println("1. Criar evento");
        System.out.println("2. Meus eventos");
        System.out.println("3. Editar evento");
        System.out.println("4. Ver inscritos em evento");
        System.out.println("5. Ver todos os eventos");
        System.out.println("0. Logout");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1: criarEvento();
                break;
            case 2: verMeusEventos();
                break;
            case 3: editarEvento();
                break;
            case 4: verInscritosEvento();
                break;
            case 5: listarEventosDisponiveis();
                break;
            case 0:
                usuarioLogado = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default: System.out.println("Opção inválida!");
        }
        return true;
    }

    private static boolean menuAdministrador(){
        System.out.println("***************************");
        System.out.println("MENU Administrador");
        System.out.println("****************************");
        System.out.println("Bem vindo(a) " + usuarioLogado.getNome());
        System.out.println("1. Gerenciar eventos");
        System.out.println("2. Gerenciar usuários");
        System.out.println("3. Relatórios");
        System.out.println("4. Ver todos os eventos");
        System.out.println("0. Logout");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1: gerenciarEventos();
                break;
            case 2: gerenciarUsuarios();
                break;
            case 3: gerarRelatorios();
                break;
            case 4: listarEventosDisponiveis();
                break;
            case 0:
                usuarioLogado = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default: System.out.println("Opção inválida!");
        }
        return true;
    }

    private static void realizarLogin()
    {
        System.out.println("\n>>> LOGIN <<<");
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.println("Senha: ");
        String senha = sc.nextLine();

        try{
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario.getSenha().equals(senha)){
                if(usuario.isAtivo()){
                    usuarioLogado = usuario;
                    System.out.println("\n Login realizado com sucesso");
                    System.out.println("Bem vindo(a), " + usuario.getNome());
                } else {
                    System.out.println("\n Usuário desativado. Contate o administrador.")
                }
            }
        }


    }



        }







