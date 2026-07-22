package ec.edu.uce.clases.ejerciciospropuestos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class G7BufferAcumulacion {
    public static void main(String[] args) {
        File archivoOriginal = new File("src\\main\\resources\\image\\liga.jpg");
        try {
            BufferedImage imagen = ImageIO.read(archivoOriginal);

            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();

            // Factores de iluminación
            float[] factores = { 1.0f, 0.75f, 0.50f, 0.25f };

            for (int f = 0; f < factores.length; f++) {
                float factor = factores[f];
                float[] bufferR = new float[ancho * alto];
                float[] bufferG = new float[ancho * alto];
                float[] bufferB = new float[ancho * alto];

                // GL_LOAD
                for (int y = 0; y < alto; y++) {
                    for (int x = 0; x < ancho; x++) {

                        int index = y * ancho + x;

                        int pixel = imagen.getRGB(x, y);

                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = pixel & 0xFF;

                        bufferR[index] = r;
                        bufferG[index] = g;
                        bufferB[index] = b;
                    }
                }

                // GL_MULT
                for (int i = 0; i < bufferR.length; i++) {
                    bufferR[i] *= factor;
                    bufferG[i] *= factor;
                    bufferB[i] *= factor;
                }

                BufferedImage resultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
                // GL_RETURN
                for (int y = 0; y < alto; y++) {
                    for (int x = 0; x < ancho; x++) {
                        int index = y * ancho + x;

                        int r = Math.max(0, Math.min(255, (int) bufferR[index]));
                        int g = Math.max(0, Math.min(255, (int) bufferG[index]));
                        int b = Math.max(0, Math.min(255, (int) bufferB[index]));

                        int pixelNuevo = (r << 16) | (g << 8) | b;

                        resultado.setRGB(x, y, pixelNuevo);
                    }
                }
                String nombreSalida = String.format("src\\main\\resources\\image\\resultado_iluminacion_%.2f%%.png", factor);
                ImageIO.write(resultado, "png", new File(nombreSalida));
                System.out.println("Imagen generada con factor de iluminación correcta: " + nombreSalida);
            }

        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
        }
    }
}
