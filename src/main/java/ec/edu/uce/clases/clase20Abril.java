package ec.edu.uce.clases;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.imageio.ImageIO;

public class clase20Abril {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Recorte de Bits Dinamico\n");
        File file = new File("src/main/resources/image/LDU2.jpg");

        try {
            int width;
            int height;
            int pixel, pixelAux;

            System.out.println("Recorte de bits dinámico: Permite ajustar el número de bits a recortar por canal, lo que ofrece flexibilidad para lograr diferentes niveles de reducción de color y efectos visuales. En este ejemplo, se recortan 4 bits por canal, lo que reduce la cantidad de colores disponibles pero mantiene una buena calidad visual.");
            System.out.print("Ingrese el número de bits a recortar por canal (1 a 7): ");
            int bits = scanner.nextInt(); // Número de bits a recortar por canal
            if (bits < 1 || bits > 7) {
                System.out.println("Error: valor invalido. Debe estar entre 1 y 7.");
                return;
            }

            int bitsConservados = 8 - bits;
            int mascaraRecorteBit = (1 << bitsConservados) - 1; // Máscara para los bits conservados
            int maxValor = mascaraRecorteBit; // Valor máximo según bits conservados

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int alpha = (pixel >> 24) & 0xFF;
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // recorte dinámico de bits por canal
                    red = (red >> bits) & mascaraRecorteBit;
                    green = (green >> bits) & mascaraRecorteBit;
                    blue = (blue >> bits) & mascaraRecorteBit;

                    // Reescalar los valores recortados a 8 bits para mantener la intensidad visual
                    red = (red * 255) / maxValor; // red = (red << 4) | red; forma alternativa para escalar a 8 bits
                    green = (green * 255) / maxValor;
                    blue = (blue * 255) / maxValor;

                    pixelAux = (alpha << 24) | (red << 16) | (green << 8) | blue; // Asegurar que el canal alpha esté en
                                                                                 // el valor calculado
                    buffer.setRGB(x, y, pixelAux);
                }
            }

            File outputFile = new File("src/main/resources/image/RecorteBitsDinamico.png");
            ImageIO.write(buffer, "png", outputFile);
            System.out.println("Imagen con recorte de bits generada exitosamente.");
            System.out.println("Bits recortados: " + bits);
            System.out.println("Bits conservados: " + bitsConservados);
			System.out.println("Máscara decimal: " + mascaraRecorteBit);
			System.out.println("Máscara binaria: " + Integer.toBinaryString(mascaraRecorteBit));
			System.out.println("Máscara hexadecimal: 0x" + Integer.toHexString(mascaraRecorteBit).toUpperCase());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void recorteBits() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 20 de Abril > Recorte de Bits\n");
        File file = new File("src/main/resources/image/LDU2.jpg");
        try {
            int width;
            int height;
            int pixel, pixelAux;

            int mascaraRecorteBit = 0b1111; // Máscara para recortar los bits > Máscara para 4 bits (0-15)
            int maxValor = (1 << 4) - 1; // Valor máximo para 4 bits (2^4 - 1)

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int alpha = (pixel >> 24) & 0xFF;
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // recorte a 4 bits por canal, no es necesario reducir el alfa porque es
                    // transparencia
                    red = (red >> 4) & mascaraRecorteBit; // Recortar a 4
                    green = (green >> 4) & mascaraRecorteBit; // Recortar a 4
                    blue = (blue >> 4) & mascaraRecorteBit; // Recortar a 4

                    // Reescalar los valores recortados a 8 bits para mantener la intensidad visual
                    red = (red * 255) / maxValor; // red = (red << 4) | red; forma alternativa para escalar a 8 bits
                    green = (green * 255) / maxValor;
                    blue = (blue * 255) / maxValor;

                    pixelAux = (alpha << 24) | (red << 16) | (green << 8) | blue; // Asegurar que el canal alpha esté en
                                                                                  // el valor calculado

                    buffer.setRGB(x, y, pixelAux);
                }
            }

            File outputFile = new File("src/main/resources/image/Recorte4Bits.png");
            ImageIO.write(buffer, "png", outputFile);
            System.out.println("Imagen con recorte de bits generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void recorteBitsOptimizado() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nRecorte de Bits optimizado\n");
        File file = new File("src/main/resources/image/LDU2.jpg");

        try {
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
            int[] output = new int[pixels.length];

            int bits = 4;
            int maxValor = (1 << bits) - 1;

            for (int i = 0; i < pixels.length; i++) {

                int pixel = pixels[i];

                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // reducción
                red = (red >> (8 - bits)) & maxValor;
                green = (green >> (8 - bits)) & maxValor;
                blue = (blue >> (8 - bits)) & maxValor;

                // reescalado (genérico)
                red = (red * 255) / maxValor;
                green = (green * 255) / maxValor;
                blue = (blue * 255) / maxValor;

                output[i] = (alpha << 24) | (red << 16) | (green << 8) | blue;
            }

            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            result.setRGB(0, 0, width, height, output, 0, width);

            ImageIO.write(result, "png", new File("src/main/resources/image/vectorizado.png"));

            System.out.println("Procesamiento optimizado completado.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void HSVTransparencia() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 20 de Abril > HSV y Transparencia\n");
        File file = new File("src/main/resources/image/LDU2.jpg");
        try {
            int width;
            int height;
            int pixel, pixelAux;
            float[] hsv = new float[3];
            float hue, saturation, value;
            float factorSaturacion = 1.5f, factorBrillo = 1.5f, factorTransparencia = 0.8f; // Factor para reducir la
                                                                                            // saturación (ajústalo
                                                                                            // según tus
            // preferencias)

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int alpha = (pixel >> 24) & 0xFF;
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    alpha = (int) (alpha * factorTransparencia);

                    // Convertir RGB a HSV
                    hsv = Color.RGBtoHSB(red, green, blue, null);
                    hue = hsv[0];
                    saturation = hsv[1];
                    value = hsv[2];

                    saturation = Math.min(1, saturation * factorSaturacion);
                    value = Math.min(1, value * factorBrillo);

                    pixelAux = Color.HSBtoRGB(hue, saturation, value);

                    pixelAux = (pixelAux & 0x00FFFFFF) | (alpha << 24); // Asegurar que el canal alpha esté en el valor
                                                                        // calculado

                    grayImage.setRGB(x, y, pixelAux);
                }
            }

            File outputFile = new File("src/main/resources/image/filtrosHSVTransparencia.png");
            ImageIO.write(grayImage, "png", outputFile);
            System.out.println("Imagen en formato HSV con transparencia generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
