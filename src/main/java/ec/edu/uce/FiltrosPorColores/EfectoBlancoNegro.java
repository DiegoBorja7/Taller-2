package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EfectoBlancoNegro {

	/* Reducir los colores de la imagen a �nicamente blanco y negro. */

	public static void aplicarFiltro() {

		File file = new File("src/main/resources/image/Deber2.jpg");
		File file2 = new File("src/main/resources/image/imagen_blanco_negro.png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;
		int gris, bw;

		try {
			BufferedImage buffer = ImageIO.read(file);
			ancho = buffer.getWidth();
			alto = buffer.getHeight();

			BufferedImage buffer2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

			for (int y = 0; y < alto; y++) {
				for (int x = 0; x < ancho; x++) {

					pixel = buffer.getRGB(x, y);

					a = (pixel >> 24) & 0xFF;
					r = (pixel >> 16) & 0xFF;
					g = (pixel >> 8) & 0xFF;
					b = (pixel >> 0) & 0xFF;

					gris = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

					if (gris >= 128) {
						bw = 255;
					} else {
						bw = 0;
					}

					pixelNuevo = (a << 24) | (bw << 16) | (bw << 8) | (bw << 0);
					buffer2.setRGB(x, y, pixelNuevo);
				}
			}

			ImageIO.write(buffer2, "png", file2);
			System.out.println("✅ Efecto Blanco y Negro aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}