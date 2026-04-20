package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoRetro2 {
	/*
	 * 4. Efecto retro 2:
	 * Reducir los colores de la imagen a N colores para únicamente 2 canales (RG o
	 * RB o GB), manteniendo el canal Alpha intacto; considere
	 * N = (2, 4, 8, 64, 128, 255).
	 * Analice el resultado para cada valor de N, ¿qué puede concluir?
	 * Ejemplo:
	 * N = 2 colores
	 * Colores posibles:
	 * 
	 * R -> 0, 255 R -> 0 R -> 0, 255
	 * G -> 0, 255 G -> 0, 255 G -> 0
	 * B -> 0 b -> 0, 255 B -> 0, 255
	 */

	public static void aplicarFiltro() {
		System.out.println("\n🎨 Generando Efecto Retro 2 para todos los modos (RG, RB, GB) y valores de N...\n");

		int[] valoresN = { 2, 4, 8, 64, 128, 255 };
		String[] modos = { "RG", "RB", "GB" };

		File inputFile = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");

		// Iterar sobre todos los modos
		for (String modo : modos) {
			System.out.println("Modo: " + modo);
			// Iterar sobre todos los valores de N
			for (int N : valoresN) {
				generarRetro2(inputFile, modo, N);
			}
			System.out.println();
		}
	}

	private static void generarRetro2(File inputFile, String modo, int N) {
		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;
		int nivel1, nivel2;
		float paso;

		try {
			BufferedImage image = ImageIO.read(inputFile);
			width = image.getWidth();
			height = image.getHeight();

			BufferedImage filterImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			if (N < 2) {
				N = 2;
			}

			paso = 255f / (N - 1);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xFF;
					red = (pixel >> 16) & 0xFF;
					green = (pixel >> 8) & 0xFF;
					blue = (pixel >> 0) & 0xFF;

					if (modo.equalsIgnoreCase("RG")) {
						nivel1 = Math.round(red / paso);
						nivel2 = Math.round(green / paso);
						red = Math.round(nivel1 * paso);
						green = Math.round(nivel2 * paso);
						blue = 0;
					} else if (modo.equalsIgnoreCase("RB")) {
						nivel1 = Math.round(red / paso);
						nivel2 = Math.round(blue / paso);
						red = Math.round(nivel1 * paso);
						green = 0;
						blue = Math.round(nivel2 * paso);
					} else if (modo.equalsIgnoreCase("GB")) {
						nivel1 = Math.round(green / paso);
						nivel2 = Math.round(blue / paso);
						red = 0;
						green = Math.round(nivel1 * paso);
						blue = Math.round(nivel2 * paso);
					}

					if (red < 0)
						red = 0;
					if (red > 255)
						red = 255;
					if (green < 0)
						green = 0;
					if (green > 255)
						green = 255;
					if (blue < 0)
						blue = 0;
					if (blue > 255)
						blue = 255;

					pixelNuevo = (alpha << 24) | (red << 16) | (green << 8) | (blue << 0);
					filterImage.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File(
					"src/main/resources/image/Filtros por Colores/EfectoRetro2_" + modo + "_N" + N + ".png");
			ImageIO.write(filterImage, "png", outputFile);
			System.out.println("  ✓ Imagen con modo=" + modo + " N=" + N + " colores generada");

		} catch (Exception e) {
			System.out.println("  ✗ Error con modo=" + modo + " N=" + N + ": " + e.getMessage());
		}
	}
}