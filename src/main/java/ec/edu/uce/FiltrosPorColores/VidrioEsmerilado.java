package ec.edu.uce.FiltrosPorColores;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class VidrioEsmerilado {

	/*
	 * Vidrio esmerilado: Crear una imagen donde la transparencia dependa del brillo
	 * del p�xel. Si el p�xel es muy brillante (cerca del blanco), debe ser m�s
	 * opaco (A ~ 255). Si el p�xel es oscuro, debe ser casi transparente (A ~ 50).
	 */

	public static void aplicarFiltro() {
		File file = new File("src/main/resources/image/Deber2.jpg");
		File file2 = new File("src/main/resources/image/imagen_vidrio_esmerilado.png");

		int ancho, alto, pixel, pixelNuevo;
		int a, r, g, b;
		int brillo;

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

					brillo = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

					// 50 = casi transparente, 255 = totalmente opaco
					a = 50 + (brillo * (255 - 50) / 255);

					pixelNuevo = (a << 24) | (r << 16) | (g << 8) | (b << 0);
					buffer2.setRGB(x, y, pixelNuevo);
				}
			}

			ImageIO.write(buffer2, "png", file2);
			System.out.println("✅ Vidrio Esmerilado aplicado exitosamente.");

		} catch (Exception e) {
			System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
		}
	}
}
