package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class VidrioEsmerilado {

	/*
	1. Vidrio esmerilado:
	Crear una imagen donde la transparencia dependa del brillo del píxel.
	Si el píxel es muy brillante (cerca del blanco), debe ser más opaco (A ≈ 255).
	Si el píxel es oscuro, debe ser casi transparente (A ≈ 50).
	 */

	public static void aplicarFiltro() {
		File file = new File("src/main/resources/image/Filtros por Colores/Deber2.jpg");
	
		int width, height, pixel, pixelNuevo;
		int alpha, red, green, blue;
		int brightness;

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

					// Calcular el brillo usando la fórmula de luminosidad percibida
					brightness = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

					// 50 = casi transparente, 255 = totalmente opaco
					alpha = 50 + (brightness * (255 - 50) / 255);

					pixelNuevo = (alpha << 24) | (red << 16) | (green << 8) | (blue << 0);
					filterImage.setRGB(x, y, pixelNuevo);
				}
			}

			File outputFile = new File("src/main/resources/image/Filtros por Colores/VidrioEsmerilado.png");
            ImageIO.write(filterImage, "png", outputFile);
            System.out.println("Imagen con filtro Vidrio Esmerilado generada exitosamente. ✅");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}
