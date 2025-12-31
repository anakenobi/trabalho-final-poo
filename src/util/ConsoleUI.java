package util;

import model.*;
import model.evento.*;
import model.usuario.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleUI {
    private static final DateTimeFormatter FMT_DATA = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    // ===== MENUS =====
    public static void exibirCabecalho() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("   SISTEMA DE GERENCIAMENTO DE EVENTOS UNIVERSITÁRIOS");
        System.out.println("═══════════════════════════════════════════════════════\n");
    }
    
    public static void exibirMenuPrincipal() {
        System.out.println("\n╔═════════════════════════════╗");
        System.out.println("║       MENU PRINCIPAL        ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar como Aluno");
        System.out.println("3. Cadastrar como Professor");
        System.out.println("4. Ver Eventos (sem login)");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    public static void exibirMenuAluno(String nome) {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║          MENU ALUNO                 ║");
        System.out.println("║  Bem-vindo(a), " + nome);
        System.out.println("╚═════════════════════════════════════╝");
        System.out.println("1. Ver Eventos Disponíveis");
        System.out.println("2. Inscrever-se em Evento");
        System.out.println("3. Minhas Inscrições");
        System.out.println("4. Cancelar Inscrição");
        System.out.println("5. Buscar Eventos");
        System.out.println("0. Logout");
        System.out.print("\nEscolha uma opção: ");
    }
    
    public static void exibirMenuProfessor(String nome) {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║        MENU PROFESSOR               ║");
        System.out.println("║  Bem-vindo(a), Prof. " + nome);
        System.out.println("╚═════════════════════════════════════╝");
        System.out.println("1. Criar Evento");
        System.out.println("2. Meus Eventos");
        System.out.println("3. Editar Evento");
        System.out.println("4. Ver Inscritos em Evento");
        System.out.println("5. Ver Todos os Eventos");
        System.out.println("0. Logout");
        System.out.print("\nEscolha uma opção: ");
    }
    
    public static void exibirMenuAdministrador(String nome) {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║      MENU ADMINISTRADOR             ║");
        System.out.println("║  Bem-vindo(a), " + nome);
        System.out.println("╚═════════════════════════════════════╝");
        System.out.println("1. Gerenciar Eventos");
        System.out.println("2. Gerenciar Usuários");
        System.out.println("3. Relatórios");
        System.out.println("4. Ver Todos os Eventos");
        System.out.println("0. Logout");
        System.out.print("\nEscolha uma opção: ");
    }
    
    // ===== EXIBIÇÃO DE DADOS =====
    public static void exibirEvento(Evento e) {
        System.out.println("\n┌─ ID: " + e.getId() + " ─────────────────────");
        System.out.println("│ Título: " + e.getTitulo());
        System.out.println("│ Tipo: " + e.getTipo().getDescricao());
        System.out.println("│ Palestrante: " + e.getPalestrante());
        System.out.println("│ Data: " + e.getDataHora().format(FMT_DATA));
        System.out.println("│ Descrição: " + e.getDescricao());
        System.out.println("│ Categoria: " + e.getCategoria());
        System.out.println("│ Vagas: " + e.getVagasDisponiveis() + "/" + e.getVagasTotais());
        System.out.println("└────────────────────────────────────");
    }
    
    public static void exibirInscricao(Inscricao i) {
        System.out.println("\n┌─ ID Inscrição: " + i.getId() + " ─────────────────");
        System.out.println("│ Evento: " + i.getEvento().getTitulo());
        System.out.println("│ Data do Evento: " + i.getEvento().getDataHora().format(FMT_DATA));
        System.out.println("│ Status: " + (i.isCancelada() ? "CANCELADA" : "ATIVA"));
        System.out.println("└────────────────────────────────────");
    }
    
    public static void exibirUsuario(Usuario u) {
        System.out.println("┌─ ID: " + u.getId());
        System.out.println("│ " + u.toString());
        System.out.println("└────────────────────────────────────\n");
    }
    
    // ===== MENSAGENS =====
    public static void exibirTitulo(String titulo) {
        System.out.println("\n─── " + titulo + " ───");
    }
    
    public static void exibirSucesso(String mensagem) {
        System.out.println("\n✓ " + mensagem);
    }
    
    public static void exibirErro(String mensagem) {
        System.out.println("\n✗ " + mensagem);
    }
    
    public static void exibirInfo(String mensagem) {
        System.out.println("✓ " + mensagem);
    }
}