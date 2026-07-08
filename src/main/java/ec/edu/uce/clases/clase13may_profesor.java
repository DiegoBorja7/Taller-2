package ec.edu.uce.clases;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class clase13may_profesor {
    public static void main(String[] args) {

        File file1 = new File("imagenes/grande.jpg");
        File file2 = new File("imagenes/pequenia.jpg");
        File file3 = new File("blending/imgBlendMulti.jpg");

        int ancho, alto, pixel1, pixel2, pixelBlend;
        int r1, g1, b1, r2, g2, b2, r, g, b;
        int mascara = 0xFF;
        float alpha = 0.5f;

        try {

            BufferedImage buffer1 = ImageIO.read(file1);
            BufferedImage buffer2 = ImageIO.read(file2);

            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();

            // Escalar una imagen para usar
            Image imgTemp = buffer2.getScaledInstance(ancho, alto, Image.SCALE_FAST);
            BufferedImage bufferTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            Graphics2D grTemp = bufferTemp.createGraphics();

            grTemp.drawImage(imgTemp, 0, 0, null);

            grTemp.dispose();

            buffer2 = bufferTemp;

            BufferedImage bufferBlend = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {

                    pixel1 = buffer1.getRGB(x, y);
                    pixel2 = buffer2.getRGB(x, y);

                    r1 = (pixel1 >> 16) & mascara;
                    g1 = (pixel1 >> 8) & mascara;
                    b1 = (pixel1) & mascara;

                    r2 = (pixel2 >> 16) & mascara;
                    g2 = (pixel2 >> 8) & mascara;
                    b2 = (pixel2) & mascara;

                    // Alpha Blendiing
                    /*
                     * r = (int)((1-alpha)*r1+alpha*r2);
                     * g = (int)((1-alpha)*g1+alpha*g2);
                     * b = (int)((1-alpha)*b1+alpha*b2);
                     */

                    // sumativa
                    /*
                     * r = Math.min(255, r1+r2);
                     * g = Math.min(255, g1+g2);
                     * b = Math.min(255, b1+b2);
                     */

                    // Multiplicativa
                    r = (r1 * r2) / 255;
                    g = (g1 * g2) / 255;
                    b = (b1 * b2) / 255;

                    pixelBlend = (r << 16) | (g << 8) | (b << 0);

                    bufferBlend.setRGB(x, y, pixelBlend);
                }
            }

            ImageIO.write(bufferBlend, "jpg", file3);
            System.out.println("Imagen Creada Correctamente");

        } catch (Exception e) {
            System.out.println("Error al crear la imagen" + e.getMessage());
        }
    }
}
