package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoRetro2 {
	/*
	 * Reducir los colores de la imagen a N colores para �nicamente 2 canales (RG o
	 * RB o GB), manteniendo el canal Alpha intacto; considere N = (2, 4, 8, 64,
	 * 128, 255). Analice el resultado para cada valor de N, �qu� puede concluir?
	 * Ejemplo: N = 2 coloresColores posibles: R -> 0, 255 R -> 0 R-> 0, 255 G -> 0,
	 * 255 G -> 0, 255 G-> 0 B -> 0 B -> 0, 255 B-> 0, 255
	 */

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Deber2.jpg");

		int N = 255;

		String modo = "GB";

		File file2 = new File("src/main/resources/image/imagen_retro2_" + modo + "_N" + N + ".png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;
		int nivel1, nivel2;
		float paso;

		try {
			BufferedImage buffer = ImageIO.read(file);
			ancho = buffer.getWidth();
			alto = buffer.getHeight();

			BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

			if (N < 2) {
				N = 2;
			}

			paso = 255f / (N - 1);

			for (int y = 0; y < alto; y++) {
				for (int x = 0; x < ancho; x++) {

					pixel = buffer.getRGB(x, y);

					a = (pixel >> 24) & 0xFF;
					r = (pixel >> 16) & 0xFF;
					g = (pixel >> 8) & 0xFF;
					b = (pixel >> 0) & 0xFF;

					if (modo.equalsIgnoreCase("RG")) {
						nivel1 = Math.round(r / paso);
						nivel2 = Math.round(g / paso);
						r = Math.round(nivel1 * paso);
						g = Math.round(nivel2 * paso);
						b = 0;
					} else if (modo.equalsIgnoreCase("RB")) {
						nivel1 = Math.round(r / paso);
						nivel2 = Math.round(b / paso);
						r = Math.round(nivel1 * paso);
						g = 0;
						b = Math.round(nivel2 * paso);
					} else if (modo.equalsIgnoreCase("GB")) {
						nivel1 = Math.round(g / paso);
						nivel2 = Math.round(b / paso);
						r = 0;
						g = Math.round(nivel1 * paso);
						b = Math.round(nivel2 * paso);
					} else {
						System.out.println("Modo inv�lido. Use RG, RB o GB.");
						return;
					}

					if (r < 0)
						r = 0;
					if (r > 255)
						r = 255;
					if (g < 0)
						g = 0;
					if (g > 255)
						g = 255;
					if (b < 0)
						b = 0;
					if (b > 255)
						b = 255;

					pixelNuevo = (a << 24) | (r << 16) | (g << 8) | (b << 0);
					buffer2.setRGB(x, y, pixelNuevo);
				}
			}

			ImageIO.write(buffer2, "png", file2);
			System.out.println("✅ Efecto Retro 2 aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}