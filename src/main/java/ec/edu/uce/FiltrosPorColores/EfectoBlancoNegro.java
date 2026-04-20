package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoBlancoNegro {

	/*
	 * 5. Efecto blanco y negro:
	 * Reducir los colores de la imagen a únicamente blanco y negro.
	 */

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");

		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;
		int gray, blackWhite;

		try {
			BufferedImage image = ImageIO.read(file);
			width = image.getWidth();
			height = image.getHeight();

			BufferedImage filterImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					pixel = image.getRGB(x, y);

					alpha = (pixel >> 24) & 0xFF;
					red = (pixel >> 16) & 0xFF;
					green = (pixel >> 8) & 0xFF;
					blue = (pixel >> 0) & 0xFF;

					// Calcular la luminosidad en escala de grises
					gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

					// Aplicar el filtro blanco y negro
					if (gray >= 128) {
						blackWhite = 255;
					} else {
						blackWhite = 0;
					}

					pixelNuevo = (alpha << 24) | (blackWhite << 16) | (blackWhite << 8) | (blackWhite << 0);
					filterImage.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File("src/main/resources/image/Filtros por Colores/BlancoNegro.png");
            ImageIO.write(filterImage, "png", outputFile);
            System.out.println("Imagen con filtro Blanco y Negro generada exitosamente. ✅");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}