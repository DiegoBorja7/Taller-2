package ec.edu.uce.FiltrosPorColores;

import java.util.Scanner;

public class Filtros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║         Filtros de Imagen - Efectos por Colores             ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println("\nElige un filtro para aplicar:\n");
            System.out.println("  1. Vidrio Esmerilado");
            System.out.println("  2. Desvanecimiento Circular");
            System.out.println("  3. Efecto Retro 1 (Reducción de colores)");
            System.out.println("  4. Efecto Retro 2 (Posterización)");
            System.out.println("  5. Blanco y Negro");
            System.out.println("  6. Escala de Grises");
            System.out.println("  0. Salir\n");
            System.out.print("Ingresa tu opción: ");

            try {
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        VidrioEsmerilado.aplicarFiltro();
                        break;
                    case 2:
                        DesvanecimientoCircular.aplicarFiltro();
                        break;
                    case 3:
                        EfectoRetro1.aplicarFiltro();
                        break;
                    case 4:
                        EfectoRetro2.aplicarFiltro();
                        break;
                    case 5:
                        EfectoBlancoNegro.aplicarFiltro();
                        break;
                    case 6:
                        EfectoEscalasGrises.aplicarFiltro();
                        break;
                    case 0:
                        System.out.println("\n¡Hasta luego! 👋");
                        scanner.close();
                        return;
                    default:
                        System.out.println("\n❌ Opción inválida. Por favor, intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: Debes ingresar un número válido.");
                scanner.nextLine();
            }
        }
    }
}
