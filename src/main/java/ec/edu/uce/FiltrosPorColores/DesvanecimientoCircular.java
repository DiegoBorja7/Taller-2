package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class DesvanecimientoCircular {

	/*
	 * 2. Desvanecimiento circular:
	 * Crear una imagen que sea totalmente opaca en el centro y se vuelva
	 * transparente hacia las esquinas.
	 * Calcular la distancia de cada píxel (x, y) al centro de la imagen y usar esa
	 * distancia para modificar el Alpha:
	 * a más distancia, menos Alpha.
	 */

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");
		
		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;

		double centroX, centroY, maxDistancia, distancia;

		try {
			BufferedImage image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();

			BufferedImage filterImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			centroX = (width - 1) / 2.0;
			centroY = (height - 1) / 2.0;
			maxDistancia = Math.sqrt((centroX * centroX) + (centroY * centroY));

			if (maxDistancia == 0) {
				maxDistancia = 1;
			}

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xFF;
					red = (pixel >> 16) & 0xFF;
					green = (pixel >> 8) & 0xFF;
					blue = (pixel >> 0) & 0xFF;

					distancia = Math.sqrt(((x - centroX) * (x - centroX)) + ((y - centroY) * (y - centroY)));

					alpha = (int) (255 * (1.0 - (distancia / maxDistancia)));

					if (alpha < 0) {
						alpha = 0;
					}
					if (alpha > 255) {
						alpha = 255;
					}

					pixelNuevo = (alpha << 24) | (red << 16) | (green << 8) | (blue << 0);
					filterImage.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File("src/main/resources/image/Filtros por Colores/DesvanecimientoCircular.png");
            ImageIO.write(filterImage, "png", outputFile);
            System.out.println("Imagen con filtro Desvanecimiento Circular generada exitosamente. ✅");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}