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
            int iteraciones = 5; // Puedes cambiar esto (10, 20) para hacerlo más borroso
            BufferedImage image = ImageIO.read(file);
            BufferedImage filtro = image;
            for (int i = 0; i < iteraciones; i++) {
                filtro = AplicarFiltroSeparable(filtro, kernelGaussiano);
            }
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
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int kernelRadius = kernel.length / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0, a = 0;

                for (int k = -kernelRadius; k <= kernelRadius; k++) {
                    int pixelX = Math.min(Math.max(x + k, 0), width - 1);
                    int pixelColor = image.getRGB(pixelX, y);

                    a += ((pixelColor >> 24) & 0xFF) * kernel[k + kernelRadius];
                    r += ((pixelColor >> 16) & 0xFF) * kernel[k + kernelRadius];
                    g += ((pixelColor >> 8) & 0xFF) * kernel[k + kernelRadius];
                    b += (pixelColor & 0xFF) * kernel[k + kernelRadius];
                }

                // Clamping
                int finalA = Math.max(0, Math.min(255, (int) a));
                int finalR = Math.max(0, Math.min(255, (int) r));
                int finalG = Math.max(0, Math.min(255, (int) g));
                int finalB = Math.max(0, Math.min(255, (int) b));

                int newPixelColor = (finalA << 24) | (finalR << 16) | (finalG << 8) | finalB;
                result.setRGB(x, y, newPixelColor);
            }
        }

        return result;
    }

    public static BufferedImage convolucionVertical(BufferedImage image, double[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int kernelRadius = kernel.length / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double a = 0, r = 0, g = 0, b = 0;

                for (int k = -kernelRadius; k <= kernelRadius; k++) {
                    int pixelY = Math.min(Math.max(y + k, 0), height - 1);
                    int pixelColor = image.getRGB(x, pixelY);

                    a += ((pixelColor >> 24) & 0xFF) * kernel[k + kernelRadius];
                    r += ((pixelColor >> 16) & 0xFF) * kernel[k + kernelRadius];
                    g += ((pixelColor >> 8) & 0xFF) * kernel[k + kernelRadius];
                    b += (pixelColor & 0xFF) * kernel[k + kernelRadius];
                }

                int finalA = Math.max(0, Math.min(255, (int) a));
                int finalR = Math.max(0, Math.min(255, (int) r));
                int finalG = Math.max(0, Math.min(255, (int) g));
                int finalB = Math.max(0, Math.min(255, (int) b));

                int newPixelColor = (finalA << 24) | (finalR << 16) | (finalG << 8) | finalB;
                result.setRGB(x, y, newPixelColor);
            }
        }

        return result;
    }

}
