package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoRetro1 {

	/*
	 * 3. Efecto retro 1:
	 * Reducir los colores de la imagen a N colores por canal RGB (N para R, N para
	 * G y N para B), manteniendo el canal Alpha intacto; considere
	 * N = (2, 4, 8, 64, 128, 255).
	 * Analice el resultado para cada valor de N, ¿qué puede concluir?
	 * Ejemplo:
	 * N = 2 colores
	 * Colores posibles:
	 * 
	 * R -> 0, 255
	 * G -> 0, 255
	 * B -> 0, 255
	 * 
	 * N = 4 colores
	 * Colores posibles:
	 * 
	 * R -> 0, 85, 170, 255
	 * G -> 0, 85, 170, 255
	 * B -> 0, 85, 170, 255
	 */

	public static void aplicarFiltro() {
		System.out.println("\n🎨 Generando Efecto Retro 1 para todos los valores de N...\n");

		int[] valoresN = { 2, 4, 8, 64, 128, 255 };
		File inputFile = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");

		for (int N : valoresN) {
			generarRetro1(inputFile, N);
		}
	}

	private static void generarRetro1(File inputFile, int N) {
		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;
		int nivelR, nivelG, nivelB;
		float paso;

		try {
			BufferedImage image = ImageIO.read(inputFile);
			width = image.getWidth();
			height = image.getHeight();

			BufferedImage imageRetro = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			if (N < 2) {
				N = 2;
			}

			paso = 255f / (N - 1); // Cálculo del paso entre colores

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xFF;
					red = (pixel >> 16) & 0xFF;
					green = (pixel >> 8) & 0xFF;
					blue = (pixel >> 0) & 0xFF;

					nivelR = Math.round(red / paso);
					nivelG = Math.round(green / paso);
					nivelB = Math.round(blue / paso);

					red = Math.round(nivelR * paso);
					green = Math.round(nivelG * paso);
					blue = Math.round(nivelB * paso);

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
					imageRetro.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File("src/main/resources/image/Filtros por Colores/EfectoRetro1_N" + N + ".png");
			ImageIO.write(imageRetro, "png", outputFile);
			System.out.println("  ✓ Imagen con N=" + N + " colores generada");

		} catch (Exception e) {
			System.out.println("  ✗ Error con N=" + N + ": " + e.getMessage());
		}
	}
}