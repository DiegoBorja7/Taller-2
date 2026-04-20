package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class DesvanecimientoCircular {

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Deber2.jpg");
		File file2 = new File("src/main/resources/image/imagen_desvanecimiento_circular.png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;

		double centroX, centroY, maxDistancia, distancia;

		try {
			BufferedImage buffer = ImageIO.read(file);
			ancho = buffer.getWidth();
			alto = buffer.getHeight();

			BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

			centroX = (ancho - 1) / 2.0;
			centroY = (alto - 1) / 2.0;
			maxDistancia = Math.sqrt((centroX * centroX) + (centroY * centroY));

			if (maxDistancia == 0) {
				maxDistancia = 1;
			}

			for (int y = 0; y < alto; y++) {
				for (int x = 0; x < ancho; x++) {

					pixel = buffer.getRGB(x, y);

					a = (pixel >> 24) & 0xFF;
					r = (pixel >> 16) & 0xFF;
					g = (pixel >> 8) & 0xFF;
					b = (pixel >> 0) & 0xFF;

					distancia = Math.sqrt(((x - centroX) * (x - centroX)) + ((y - centroY) * (y - centroY)));

					a = (int) (255 * (1.0 - (distancia / maxDistancia)));

					if (a < 0) {
						a = 0;
					}
					if (a > 255) {
						a = 255;
					}

					pixelNuevo = (a << 24) | (r << 16) | (g << 8) | (b << 0);
					buffer2.setRGB(x, y, pixelNuevo);
				}
			}

			ImageIO.write(buffer2, "png", file2);
			System.out.println("✅ Desvanecimiento circular aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}