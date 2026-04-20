package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoEscalasGrises {
	/*
	 * Reducir los colores de la imagen a una escala de grises de N = (2, 4, 8, 64,
	 * 128, 255).
	 */

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Deber2.jpg");

		int N = 255;

		File file2 = new File("src/main/resources/image/imagen_escalas_grises_N" + N + ".png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;
		int gris, nivel;
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

					gris = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

					nivel = Math.round(gris / paso);
					gris = Math.round(nivel * paso);

					if (gris < 0)
						gris = 0;
					if (gris > 255)
						gris = 255;

					pixelNuevo = (a << 24) | (gris << 16) | (gris << 8) | (gris << 0);
					buffer2.setRGB(x, y, pixelNuevo);
				}
			}

			ImageIO.write(buffer2, "png", file2);
			System.out.println("✅ Efecto Escalas de Grises aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}