package ec.edu.uce.clases;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class clase13julio {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Filtro de convolucion separable\n");

        File file = new File("src/main/resources/image/mundial.jpg");

        double[] kernelGaussiano = {
                1.0 / 4.0,
                2.0 / 4.0,
                1.0 / 4.0
        };

        try {
            BufferedImage image = ImageIO.read(file);

            BufferedImage filtro = AplicarFiltroSeparable(image, kernelGaussiano);

            File outputFile = new File("src/main/resources/image/FiltroSeparable.png");
            ImageIO.write(filtro, "png", outputFile);
            System.out.println("Imagen generada con filtro separable.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static BufferedImage AplicarFiltroSeparable(BufferedImage image, double[] kernel) {
        BufferedImage convolucionHorizontal = convolucionHorizontal(image, kernel);
        BufferedImage convolucionVertical = convolucionVertical(convolucionHorizontal, kernel);

        return convolucionVertical;
    }

    public static BufferedImage convolucionHorizontal(BufferedImage image, double[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int kernelRadius = kernel.length / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0;

                for (int k = -kernelRadius; k <= kernelRadius; k++) {
                    int pixelX = Math.min(Math.max(x + k, 0), width - 1);
                    int pixelColor = image.getRGB(pixelX, y);

                    r += ((pixelColor >> 16) & 0xFF) * kernel[k + kernelRadius];
                    g += ((pixelColor >> 8) & 0xFF) * kernel[k + kernelRadius];
                    b += (pixelColor & 0xFF) * kernel[k + kernelRadius];
                }

                int newPixelColor = ((int) r << 16) | ((int) g << 8) | (int) b;
                result.setRGB(x, y, newPixelColor);
            }
        }

        return result;
    }

    public static BufferedImage convolucionVertical(BufferedImage image, double[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int kernelRadius = kernel.length / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0;

                for (int k = -kernelRadius; k <= kernelRadius; k++) {
                    int pixelY = Math.min(Math.max(y + k, 0), height - 1);
                    int pixelColor = image.getRGB(x, pixelY);

                    r += ((pixelColor >> 16) & 0xFF) * kernel[k + kernelRadius];
                    g += ((pixelColor >> 8) & 0xFF) * kernel[k + kernelRadius];
                    b += (pixelColor & 0xFF) * kernel[k + kernelRadius];
                }

                int newPixelColor = ((int) r << 16) | ((int) g << 8) | (int) b;
                result.setRGB(x, y, newPixelColor);
            }
        }

        return result;
    }

}
