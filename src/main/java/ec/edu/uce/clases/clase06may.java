package ec.edu.uce.clases;

import java.io.File;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class clase06may {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 06 de Mayo - Histograma\n");

        File file = new File("src/main/resources/image/soleado.jpg");

        int height, width, pixel;
        int red, green, blue;

        int widthHistograma = 800;
        int heightHistograma = 600;

        int histogramaRed[] = new int[256];
        int histogramaGreen[] = new int[256];
        int histogramaBlue[] = new int[256];

        float escalaX;
        float escalaY;

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage histograma = new BufferedImage(widthHistograma, heightHistograma, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = histograma.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, widthHistograma, heightHistograma);
            graphics.setStroke(new java.awt.BasicStroke(2));

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el valor del pixel actual
                    pixel = image.getRGB(x, y);

                    // Extraer los componentes RGB del pixel
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = (pixel >> 0) & 0xFF;

                    histogramaRed[red]++;
                    histogramaGreen[green]++;
                    histogramaBlue[blue]++;
                }
            }

            escalaX = widthHistograma / 256.0f;
            int maximoGeneral = Math.max(maximoValorHistograma(histogramaRed),
                    Math.max(maximoValorHistograma(histogramaGreen), maximoValorHistograma(histogramaBlue)));
            escalaY = heightHistograma * 1.0f / maximoGeneral;

            graphics.setColor(Color.RED);
            for (int i = 1; i < histogramaRed.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histogramaRed[i - 1]);

                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histogramaRed[i]);

                graphics.drawLine(x1, y1, x2, y2);
            }

            graphics.setColor(Color.GREEN);
            for (int i = 1; i < histogramaGreen.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histogramaGreen[i - 1]);

                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histogramaGreen[i]);

                graphics.drawLine(x1, y1, x2, y2);
            }

            graphics.setColor(Color.BLUE);
            for (int i = 1; i < histogramaBlue.length; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histogramaBlue[i - 1]);

                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histogramaBlue[i]);

                graphics.drawLine(x1, y1, x2, y2);
            }
            graphics.dispose();

            // Imprimir el histograma en consola
            imprimirHistograma(histogramaRed);

            File outputFile = new File("src/main/resources/image/Histograma.png");
            ImageIO.write(histograma, "png", outputFile);
            System.out.println("Imagen generada con histograma.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void imprimirHistograma(int[] histograma) {
        int maxValor = 0;
        int aux = 0;

        maxValor = maximoValorHistograma(histograma);

        System.out.println("Histograma:");
        for (int i = 0; i < histograma.length; i++) {
            System.out.println(i + ": " + histograma[i]);

            if (histograma[i] == maxValor) {
                aux = i;
            }
        }
        System.out.println("Maximo valor del histograma: " + maxValor + " en la posición [" + aux + "]");
    }

    private static int maximoValorHistograma(int[] histograma) {
        int maxValor = 0;
        for (int valor : histograma) {
            if (valor > maxValor) {
                maxValor = valor;
            }
        }
        return maxValor;
    }

}
