package ec.edu.uce.EcualizadorHistograma;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ProcesadorImagen {

    /**
     * Aplica el ajuste de brillo (offset lineal) y opcionalmente convierte a escala
     * de grises.
     * 
     * @param original  Imagen base
     * @param brillo    Valor a sumar/restar a cada pixel
     * @param grayscale Si es true, se aplica la formula de luminancia antes del
     *                  brillo
     * @return BufferedImage resultante
     */
    public static BufferedImage ajustarBrillo(BufferedImage original, int brillo, boolean grayscale) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);

                // Extraccion mediante bit shifting
                int a = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (grayscale) {
                    int yLuma = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    r = yLuma;
                    g = yLuma;
                    b = yLuma;
                }

                int newR = r + brillo;
                int newG = g + brillo;
                int newB = b + brillo;

                // Clamping matematico
                newR = Math.max(0, Math.min(255, newR));
                newG = Math.max(0, Math.min(255, newG));
                newB = Math.max(0, Math.min(255, newB));

                // Reensamblado
                int finalRgb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                tempImage.setRGB(x, y, finalRgb);
            }
        }
        return tempImage;
    }

    /**
     * Ecualiza el histograma de una imagen utilizando la Función de Distribución
     * Acumulada (CDF).
     */
    public static BufferedImage ecualizarCDF(BufferedImage original, boolean grayscale) {
        int width = original.getWidth();
        int height = original.getHeight();
        int totalPixels = width * height;
        BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        if (grayscale) {
            int[] hist = new int[256];
            int[][] lumaPixels = new int[height][width];
            int[][] alphas = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = original.getRGB(x, y);
                    int a = (rgb >> 24) & 0xFF;
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    int yLuma = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    lumaPixels[y][x] = yLuma;
                    alphas[y][x] = a;
                    hist[yLuma]++;
                }
            }

            int[] cdf = new int[256];
            cdf[0] = hist[0];
            for (int i = 1; i < 256; i++) {
                cdf[i] = cdf[i - 1] + hist[i];
            }

            int cdfMin = 0;
            for (int i = 0; i < 256; i++) {
                if (cdf[i] > 0) {
                    cdfMin = cdf[i];
                    break;
                }
            }

            int[] map = new int[256];
            for (int i = 0; i < 256; i++) {
                map[i] = Math.round(((float) (cdf[i] - cdfMin) / (totalPixels - cdfMin)) * 255.0f);
                map[i] = Math.max(0, Math.min(255, map[i]));
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int newLuma = map[lumaPixels[y][x]];
                    int a = alphas[y][x];
                    int finalRgb = (a << 24) | (newLuma << 16) | (newLuma << 8) | newLuma;
                    tempImage.setRGB(x, y, finalRgb);
                }
            }
        } else {
            int[] histR = new int[256];
            int[] histG = new int[256];
            int[] histB = new int[256];
            int[][] rPixels = new int[height][width];
            int[][] gPixels = new int[height][width];
            int[][] bPixels = new int[height][width];
            int[][] alphas = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = original.getRGB(x, y);
                    int a = (rgb >> 24) & 0xFF;
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;

                    alphas[y][x] = a;
                    rPixels[y][x] = r;
                    gPixels[y][x] = g;
                    bPixels[y][x] = b;

                    histR[r]++;
                    histG[g]++;
                    histB[b]++;
                }
            }

            int[] cdfR = new int[256];
            int[] cdfG = new int[256];
            int[] cdfB = new int[256];
            cdfR[0] = histR[0];
            cdfG[0] = histG[0];
            cdfB[0] = histB[0];
            
            for (int i = 1; i < 256; i++) {
                cdfR[i] = cdfR[i - 1] + histR[i];
                cdfG[i] = cdfG[i - 1] + histG[i];
                cdfB[i] = cdfB[i - 1] + histB[i];
            }

            int cdfMinR = 0, cdfMinG = 0, cdfMinB = 0;
            for (int i = 0; i < 256; i++) {
                if (cdfR[i] > 0) {
                    cdfMinR = cdfR[i];
                    break;
                }
            }
            for (int i = 0; i < 256; i++) {
                if (cdfG[i] > 0) {
                    cdfMinG = cdfG[i];
                    break;
                }
            }
            for (int i = 0; i < 256; i++) {
                if (cdfB[i] > 0) {
                    cdfMinB = cdfB[i];
                    break;
                }
            }

            int[] mapR = new int[256];
            int[] mapG = new int[256];
            int[] mapB = new int[256];

            for (int i = 0; i < 256; i++) {
                mapR[i] = Math.max(0,
                        Math.min(255, Math.round(((float) (cdfR[i] - cdfMinR) / (totalPixels - cdfMinR)) * 255.0f)));
                mapG[i] = Math.max(0,
                        Math.min(255, Math.round(((float) (cdfG[i] - cdfMinG) / (totalPixels - cdfMinG)) * 255.0f)));
                mapB[i] = Math.max(0,
                        Math.min(255, Math.round(((float) (cdfB[i] - cdfMinB) / (totalPixels - cdfMinB)) * 255.0f)));
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int a = alphas[y][x];
                    int newR = mapR[rPixels[y][x]];
                    int newG = mapG[gPixels[y][x]];
                    int newB = mapB[bPixels[y][x]];
                    int finalRgb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                    tempImage.setRGB(x, y, finalRgb);
                }
            }
        }
        return tempImage;
    }

    /**
     * Genera la imagen del gráfico del histograma a partir de una imagen ya
     * procesada.
     * 
     * @param imagenProcesada Imagen de la cual extraeremos los picos RGB
     * @param grayscale       Si es true, pinta una sola linea blanca. Si es false,
     *                        pinta R,G,B.
     * @return BufferedImage que contiene el grafico dibujado
     */
    public static BufferedImage generarGraficoHistograma(BufferedImage imagenProcesada, boolean grayscale) {
        int width = imagenProcesada.getWidth();
        int height = imagenProcesada.getHeight();

        int[] histR = new int[256];
        int[] histG = new int[256];
        int[] histB = new int[256];

        // 1. Contar frecuencias
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = imagenProcesada.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                histR[r]++;
                histG[g]++;
                histB[b]++;
            }
        }

        // 2. Dibujar grafico
        int widthHistograma = 400;
        int heightHistograma = 400;

        BufferedImage histogramaImg = new BufferedImage(widthHistograma, heightHistograma, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = histogramaImg.createGraphics();

        graphics.setColor(new Color(24, 26, 56)); // Fondo Azul profundo de la nueva paleta
        graphics.fillRect(0, 0, widthHistograma, heightHistograma);
        graphics.setStroke(new BasicStroke(2));

        float escalaX = widthHistograma / 256.0f;
        int maximoGeneral = Math.max(maximoValorHistograma(histR),
                Math.max(maximoValorHistograma(histG), maximoValorHistograma(histB)));

        // Prevenir division por cero en caso de imagen vacia
        if (maximoGeneral == 0)
            maximoGeneral = 1;
        float escalaY = heightHistograma * 1.0f / maximoGeneral;

        if (grayscale) {
            // Histograma en Blanco y Negro (Gris)
            graphics.setColor(Color.WHITE);
            for (int i = 1; i < 256; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histR[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histR[i]);
                graphics.drawLine(x1, y1, x2, y2);
            }
        } else {
            // Histograma a Color (RGB)
            graphics.setColor(Color.RED);
            for (int i = 1; i < 256; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histR[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histR[i]);
                graphics.drawLine(x1, y1, x2, y2);
            }

            graphics.setColor(Color.GREEN);
            for (int i = 1; i < 256; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histG[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histG[i]);
                graphics.drawLine(x1, y1, x2, y2);
            }

            graphics.setColor(Color.BLUE);
            for (int i = 1; i < 256; i++) {
                int x1 = (int) (escalaX * (i - 1));
                int y1 = heightHistograma - (int) (escalaY * histB[i - 1]);
                int x2 = (int) (escalaX * i);
                int y2 = heightHistograma - (int) (escalaY * histB[i]);
                graphics.drawLine(x1, y1, x2, y2);
            }
        }

        graphics.dispose();
        return histogramaImg;
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
