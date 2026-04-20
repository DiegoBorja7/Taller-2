package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoRetro1 {

	/*
	 * Efecto Retro 1: Reducir los colores de la imagen a una cantidad N de niveles
	 * por canal (R, G, B). Por ejemplo, si N = 4, cada canal solo puede tomar
	 * los valores 0, 85, 170 y 255. Si N = 8, cada canal solo puede tomar los
	 * valores 0, 36, 73, 109, 146, 182, 219 y 255.
	 */
	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Deber2.jpg");

		int N = 4;

		File file2 = new File("src/main/resources/image/imagen_retro1_N" + N + ".png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;
		int nivelR, nivelG, nivelB;
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

					nivelR = Math.round(r / paso);
					nivelG = Math.round(g / paso);
					nivelB = Math.round(b / paso);

					r = Math.round(nivelR * paso);
					g = Math.round(nivelG * paso);
					b = Math.round(nivelB * paso);

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
			System.out.println("✅ Efecto Retro 1 aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}