package ec.edu.uce.clases;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class prueba1 {
    public static void main(String[] args) {
        // A traves de una matriz de convolucion aplicar un efecto de oscurecimiento a
        // una imagen y generar 10 imagenes mas
        // simulando un efecto de amanecer, para esto se debe crear una matriz de
        // convolucion que oscurezca la imagen y luego aplicar esta matriz a la imagen
        // original varias veces, cada vez aumentando el nivel de oscurecimiento.
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Prueba 1 - 29 de abril\n");

        try {

            for (int i = 1; i <= 10; i++) {
                float centro = 0.27f + (i - 1) * 0.1f;
                Aclarar(centro, i);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void Aclarar(float centro, int paso) {
        int height, width, pixel;
        int red, green, blue, index = 0;
        int mask = 0xFF;
        int radius = 1;

        float[] kernel = {
                0.01f, 0.01f, 0.01f,
                0.01f, centro, 0.01f,
                0.01f, 0.01f, 0.01f
        };

        float sumRed = 0;
        float sumGreen = 0;
        float sumBlue = 0;

        File file = new File("src/main/resources/image/Prueba/mundial.jpg");

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage imageConvolution = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Copiamos la imagen original para conservar los bordes.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    imageConvolution.setRGB(x, y, image.getRGB(x, y));
                }
            }

            for (int y = radius; y < height - radius; y++) {
                for (int x = radius; x < width - radius; x++) {
                    sumRed = 0;
                    sumGreen = 0;
                    sumBlue = 0;
                    index = 0;
                    for (int i = -radius; i <= radius; i++) {
                        for (int j = -radius; j <= radius; j++) {
                            pixel = image.getRGB(x + i, y + j);

                            red = (pixel >> 16) & mask;
                            green = (pixel >> 8) & mask;
                            blue = (pixel >> 0) & mask;

                            sumRed += red * kernel[index];
                            sumGreen += green * kernel[index];
                            sumBlue += blue * kernel[index];

                            index++;
                        }
                    }
                    red = Math.max(0, Math.min(255, (int) sumRed));
                    green = Math.max(0, Math.min(255, (int) sumGreen));
                    blue = Math.max(0, Math.min(255, (int) sumBlue));

                    pixel = (red << 16) | (green << 8) | blue;
                    imageConvolution.setRGB(x, y, pixel);
                }

            }
            File outputFile = new File("src/main/resources/image/Prueba/ImagenPrueba_" + paso + ".png");
            ImageIO.write(imageConvolution, "png", outputFile);
            float sumaKernel = 0.08f + centro;
            System.out.println("Imagen " + paso + " generada. SumaKernel=" + sumaKernel);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   
    }

}
