package util;

import java.util.Scanner;

public class LeitorEntrada {
    private static Scanner scanner = new Scanner(System.in);
    
    public static int lerInteiro() {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            return valor;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return lerInteiro();
    }
    
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    public static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    public static void fechar() {
        scanner.close();
    }
}
