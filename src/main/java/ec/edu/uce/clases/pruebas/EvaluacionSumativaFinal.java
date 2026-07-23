
package ec.edu.uce.clases.pruebas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class EvaluacionSumativaFinal {
    public static void main(String[] args) {
        /*
        Matriz de convolucion para el filtro de bordes (sharpen)
        double[] sharpen = {
                0f, -1f, 0f,
                -1f, -5f, -1f,
                0f, -1f, 0f,
        };
        */

        // Al multiplicar este vector consigo mismo (Horizontal * Vertical) genera una matriz 2D de Sharpen.
        double[] kernelGaussiano = {
                -0.5,
                2.0,
                -0.5
        };

        File file = new File("src/main/resources/image/image01.jpg");
        try {
            int iteraciones = 3; 
            BufferedImage filtro = ImageIO.read(file);

            for (int i = 0; i < iteraciones; i++) {
                filtro = AplicarFiltroSeparable(filtro, kernelGaussiano);
            }

            File outputFile = new File("src/main/resources/image/FiltroSeparableBordesExamen.png");
            ImageIO.write(filtro, "png", outputFile);
            System.out.println("Imagen generada con filtro de bordes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage AplicarFiltroSeparable(BufferedImage image, double[] kernel) {
        BufferedImage convolucionHorizontal = convolucionHorizontal(image, kernel);

        return convolucionVertical(convolucionHorizontal, kernel);
    }

    public static BufferedImage convolucionHorizontal(BufferedImage image, double[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] base = new int[width * height];
        resultado.setRGB(0, 0, width, height, base, 0, width);

        int radius = kernel.length / 2;
        int mask = 0xFF;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                float sumaA= 0f,sumaR= 0f,sumaG= 0f,sumaB = 0f;

                for (int k = -radius; k <= radius; k++) {
                    int pixelX = Math.min(Math.max(x + k, 0), width - 1);
                    int pixelColor = image.getRGB(pixelX, y);
                    sumaA += (float) (((pixelColor >> 24) & mask) * kernel[k + radius]);
                    sumaR += (float) (((pixelColor >> 16) & mask) * kernel[k + radius]);
                    sumaG += (float) (((pixelColor >> 8) & mask) * kernel[k + radius]);
                    sumaB += (float) ((pixelColor & mask) * kernel[k + radius]);

                }
                int a = clamp(Math.round(sumaA));
                int r = clamp(Math.round(sumaR));
                int g = clamp(Math.round(sumaG));
                int b = clamp(Math.round(sumaB));

                int pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                resultado.setRGB(x, y, pixelNuevo);
            }
        }

        return resultado;
    }

    public static BufferedImage convolucionVertical(BufferedImage image, double[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] base = new int[width * height];
        resultado.setRGB(0, 0, width, height, base, 0, width);

        int radius = kernel.length / 2;
        int mask = 0xFF;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                float sumaA= 0f,sumaR= 0f,sumaG= 0f,sumaB = 0f;

                for (int k = -radius; k <= radius; k++) {
                    int pixelY = Math.min(Math.max(y + k, 0), height - 1);
                    int pixelColor = image.getRGB(x, pixelY);

                    sumaA += (float) (((pixelColor >> 24) & mask) * kernel[k + radius]);
                    sumaR += (float) (((pixelColor >> 16) & mask) * kernel[k + radius]);
                    sumaG += (float) (((pixelColor >> 8) & mask) * kernel[k + radius]);
                    sumaB += (float) ((pixelColor & mask) * kernel[k + radius]);

                }
                int a = clamp(Math.round(sumaA));
                int r = clamp(Math.round(sumaR));
                int g = clamp(Math.round(sumaG));
                int b = clamp(Math.round(sumaB));

                int pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                resultado.setRGB(x, y, pixelNuevo);
            }
        }

        return resultado;
    }

    private static int clamp(int value) {
        if (value < 0) {
            return 0;
        }

        return Math.min(value, 255);
    }
}