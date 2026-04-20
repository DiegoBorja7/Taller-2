package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoEscalasGrises {
	/*
	 * 6. Efecto escala de grises:
	 * Reducir los colores de la imagen a una escala de grises de
	 * N = (2, 4, 8, 64, 128, 255).
	 */

	public static void aplicarFiltro() {
		System.out.println("\n🎨 Generando Efecto Escalas de Grises para todos los valores de N...\n");

		int[] valoresN = { 2, 4, 8, 64, 128, 255 };
		File inputFile = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");

		for (int N : valoresN) {
			generarEscalasGrises(inputFile, N);
		}
	}

	private static void generarEscalasGrises(File inputFile, int N) {
		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;
		int gris, nivel;
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

					gris = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

					nivel = Math.round(gris / paso);
					gris = Math.round(nivel * paso);

					if (gris < 0)
						gris = 0;
					if (gris > 255)
						gris = 255;

					pixelNuevo = (alpha << 24) | (gris << 16) | (gris << 8) | (gris << 0);
					filterImage.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File("src/main/resources/image/Filtros por Colores/EscalasGrises_N" + N + ".png");
			ImageIO.write(filterImage, "png", outputFile);
			System.out.println("  ✓ Imagen con N=" + N + " niveles generada");

		} catch (Exception e) {
			System.out.println("  ✗ Error con N=" + N + ": " + e.getMessage());
		}
	}
}