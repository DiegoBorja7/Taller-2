package ec.edu.uce.clases.pruebas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class CorreccionEvaluacionSumativa1 {
    private static final float[] KERNEL_SHARPEN_5X5 = {
            0f, 0f, -1f, 0f, 0f,
            0f, -1f, -2f, -1f, 0f,
            -1f, -2f, 16f, -2f, -1f,
            0f, -1f, -2f, -1f, 0f,
            0f, 0f, -1f, 0f, 0f
    };

    public static void main(String[] args) {
        String ruta = "src/main/resources/image/Prueba/";
        File file = new File(ruta + "imagen.jpg");
        int veces = 1;

        if (args.length > 0) {
            try {
                veces = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
                veces = 1;
            }
        }

        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage resultado = aplicarFiltro(image, veces);

            File salida = new File(ruta + "resultado_correccion_prueba2.png");
            ImageIO.write(resultado, "png", salida);
            System.out.println("Imagen generada con realce de bordes (canal G), veces=" + veces);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static BufferedImage aplicarFiltro(BufferedImage image, int veces) {
        BufferedImage actual = image;
        int repeticiones = Math.max(1, veces);

        for (int i = 0; i < repeticiones; i++) {
            actual = convolucionCanalVerde(actual, KERNEL_SHARPEN_5X5);
        }

        return actual;
    }

    private static BufferedImage convolucionCanalVerde(BufferedImage image, float[] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultado = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] base = new int[width * height];
        for (int i = 0; i < base.length; i++) {
            base[i] = 0xFF000000; // alpha opaco, RGB negro
        }
        resultado.setRGB(0, 0, width, height, base, 0, width);

        int radius = 2;
        int mask = 0xFF;

        for (int y = radius; y < height - radius; y++) {
            for (int x = radius; x < width - radius; x++) {
                float sumaG = 0f;
                int index = 0;

                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                        int pixel = image.getRGB(x + kx, y + ky);
                        int g = (pixel >> 8) & mask;
                        sumaG += g * kernel[index];
                        index++;
                    }
                }

                int g = clamp(Math.round(sumaG));
                int pixelNuevo = 0xFF000000 | (g << 8);
                resultado.setRGB(x, y, pixelNuevo);
            }
        }

        return resultado;
    }

    private static int clamp(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
    }
}
