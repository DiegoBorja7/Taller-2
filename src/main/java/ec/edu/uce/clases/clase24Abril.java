package ec.edu.uce.clases;

import java.io.File;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class clase24Abril {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 24 de Abril - Convolucion\n");

        File file = new File("src/main/resources/image/balon2.jpg");

        int width;
        int height;
        int pixel;
        int red, green, blue;
        int mask = 0xFF;

        float sumRed = 0;
        float sumGreen = 0;
        float sumBlue = 0;

        // crear matriz de convolucion (kernel 9x9)
        int kernelSize = 9;
        int radius = kernelSize / 2;
        float[][] kernel = new float[kernelSize][kernelSize];
        for (int ky = 0; ky < kernelSize; ky++) {
            for (int kx = 0; kx < kernelSize; kx++) {
                kernel[ky][kx] = 1f / (kernelSize * kernelSize);
            }
        }

        /*
         * float[][] kernel = {
         * { 1, 0, -1 },
         * { 0, 0, 0 },
         * { -1, 0, 1 }
         * };
         */
        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage imageConvolution = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = radius; y < height - radius; y++) {
                for (int x = radius; x < width - radius; x++) {
                    sumRed = 0;
                    sumGreen = 0;
                    sumBlue = 0;

                    for (int i = -radius; i <= radius; i++) {
                        for (int j = -radius; j <= radius; j++) {
                            pixel = image.getRGB(x + i, y + j);

                            red = (pixel >> 16) & mask;
                            green = (pixel >> 8) & mask;
                            blue = (pixel >> 0) & mask;

                            sumRed += red * kernel[j + radius][i + radius];
                            sumGreen += green * kernel[j + radius][i + radius];
                            sumBlue += blue * kernel[j + radius][i + radius];
                        }
                    }

                    int newRed = Math.min(Math.max((int) sumRed, 0), 255);
                    int newGreen = Math.min(Math.max((int) sumGreen, 0), 255);
                    int newBlue = Math.min(Math.max((int) sumBlue, 0), 255);

                    pixel = (newRed << 16) | (newGreen << 8) | newBlue;
                    imageConvolution.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/ImagenConvolucion.png");
            ImageIO.write(imageConvolution, "png", outputFile);
            System.out.println("Imagen con convolución generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error al verificar el archivo: " + e.getMessage());
        }
    }
}
