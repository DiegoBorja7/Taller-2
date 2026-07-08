package ec.edu.uce.clases.pruebas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

//Prueba grupal - Grupo 2 - Blending de dos imagenes con saturacion y brillo
public class Grupo2 {
    public static void main(String[] args) {
        File file = new File("src/prueba/imagenes/imagen.png");
        File file2 = new File("src/prueba/imagenes/imagen3.png");
        File salida = new File("src/prueba/imagenes/blending.png");

        int pixel, pixel2, pixelNuevo, alto, ancho;
        int r, g, b, r1, r2, g1, g2, b1, b2;
        int mask = 0xFF;
        float alpha;

        int bits = 6;
        int niveles = 1 << bits;
        int salto = 256 / niveles;

        try {
            BufferedImage buffer = ImageIO.read(file);
            BufferedImage buffer2 = ImageIO.read(file2);

            ancho = buffer2.getWidth();
            alto = buffer2.getHeight();

            Image imgTemporal = buffer2.getScaledInstance(ancho, alto, Image.SCALE_FAST);

            BufferedImage temporal = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = temporal.createGraphics();
            graphics.drawImage(imgTemporal, 0, 0, null);
            graphics.dispose();
            buffer2 = temporal;

            BufferedImage blending = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer.getRGB(x, y);
                    pixel2 = buffer2.getRGB(x, y);

                    r1 = (pixel >> 16) & mask;
                    g1 = (pixel >> 8) & mask;
                    b1 = (pixel) & mask;

                    r2 = (pixel2 >> 16) & mask;
                    g2 = (pixel2 >> 8) & mask;
                    b2 = pixel2 & mask;
                    
                    //saturacion 20%
                    r2 = (int)(r2 * 1.2);
                    g2 = (int)(g2 * 1.2);
                    b2 = (int)(b2 * 1.2);

                    if (r2 > 255) r = 255;
                    if (g2 > 255) g = 255;
                    if (b2 > 255) b = 255;

                    int gris = (r2 + g2+ b2) / 3;

                    //brillo -10%
                    r2 = (int)(gris + (r2 - gris) * 0.9);
                    g2 = (int)(gris + (g2 - gris) * 0.9);
                    b2 = (int)(gris + (b2 - gris) * 0.9);

                    r2 = (r2 / salto) * salto;
                    g2 = (g2 / salto) * salto;
                    b2= (b2 / salto) * salto;

                    alpha = 255.f;
                    r = Math.min(255,((r1 * r1) / 255) + ((r2 * (255 - r1)) / 255));
                    g = Math.min(255,((g1 * g1) / 255) + ((g2 * (255 - g1)) / 255));
                    b = Math.min(255,((b1 * b1) / 255) + ((b2 * (255 - b1)) / 255));

                    pixelNuevo = ((int)alpha << 24)|(r << 16) | (g << 8) | b ;

                    blending.setRGB(x, y, pixelNuevo);
                }
            }

            ImageIO.write(blending, "png", salida);
            System.out.println("Imagen generada con exito.");

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

