package ec.edu.uce.clases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;

import javax.imageio.ImageIO;

public class Imagen {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("\n╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║    Ejercicios - Efectos de Degradado y Generación de Imágenes   ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            System.out.println("\nElige un ejercicio para ejecutar:\n");
            System.out.println("  1. Copiar imagen");
            System.out.println("  2. Generar imagen aleatoria");
            System.out.println("  3. Degradado vertical 1");
            System.out.println("  4. Degradado vertical 2 (invertido)");
            System.out.println("  5. Degradado horizontal 1");
            System.out.println("  6. Degradado horizontal 2 (invertido)");
            System.out.println("  7. Degradado radial");
            System.out.println("  8. Escala de grises");
            System.out.println("  9. Filtro negativo");
            System.out.println("  10. Brillo por canal");
            System.out.println("  11. Escala de grises usando HSV");
            System.out.println("  12. Filtros HSV");
            System.out.println("  13. Canal Alpha");
            
            System.out.println("  0. Salir\n");
            System.out.print("Ingresa tu opción: ");

            try {
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        copiarImagen();
                        break;
                    case 2:
                        imagenAleatoria();
                        break;
                    case 3:
                        degradadoVertical1();
                        break;
                    case 4:
                        degradadoVertical2();
                        break;
                    case 5:
                        degradadoHorizontal1();
                        break;
                    case 6:
                        degradadoHorizontal2();
                        break;
                    case 7:
                        degradadoRadial();
                        break;
                    case 8:
                        escalaDeGrises();
                        break;
                    case 9:
                        filtroNegativo();
                        break;
                    case 10:
                        brilloPorCanal();
                        break;
                    case 11:
                        escalaDeGrisesHSV();
                        break;
                    case 12:
                        flitrosHSV();
                        break;
                    case 13:
                        canalAlpha();
                        break;
                    case 0:
                        System.out.println("\n¡Hasta luego!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("\n❌ Opción inválida. Por favor, intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: Debes ingresar un número válido.");
                scanner.nextLine();
            }
        }
    }

    public static void canalAlpha() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 13 > Alpha Channel");
                File file = new File("src/main/resources/image/trionda.png");
        try {
            int width;
            int height;
            int pixel;
            float factorTransparencia = 1.5f;
           
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage argbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int alpha = (pixel >> 24) & 0xFF; // Obtener el canal alpha
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;
                    
                    alpha = ((int) (alpha * factorTransparencia)); // Aplicar el factor de transparencia al canal alpha
                    pixel = (alpha << 24) | (red << 16) | (green << 8) | blue; // Combinar el canal alpha con RGB


                    argbImage.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/argbImage.png");
            ImageIO.write(argbImage, "png", outputFile);
            System.out.println("Imagen en formato ARGB generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void flitrosHSV() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 12 > Filtros HSV");
        File file = new File("src/main/resources/image/LDU.jpg");
        try {
            int width;
            int height;
            int pixel, pixelAux;
            float[] hsv = new float[3];
            float hue, saturation, value;
            float factorSaturacion = 1.5f, factorBrillo = 1.5f; // Factor para reducir la saturación (ajústalo según tus
                                                                // preferencias)

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Convertir RGB a HSV
                    hsv = Color.RGBtoHSB(red, green, blue, null);
                    hue = hsv[0];
                    saturation = hsv[1];
                    value = hsv[2];

                    saturation = Math.min(1, saturation * factorSaturacion);
                    value = Math.min(1, value * factorBrillo);

                    pixelAux = Color.HSBtoRGB(hue, saturation, value);

                    grayImage.setRGB(x, y, pixelAux);
                }
            }

            File outputFile = new File("src/main/resources/image/filtrosHSV.png");
            ImageIO.write(grayImage, "png", outputFile);
            System.out.println("Imagen en formato HSV generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void escalaDeGrisesHSV() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 11 > Escala de grises HSV");
        File file = new File("src/main/resources/image/LDU.jpg");
        try {
            int width;
            int height;
            int pixel, pixelAux;
            float[] hsv = new float[3];
            float hue, saturation, value; 

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el color del pixel en la posición (x, y)
                    pixel = image.getRGB(x, y);

                    // Obtener los componentes RGB del color
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Convertir RGB a HSV
                    hsv = Color.RGBtoHSB(red, green, blue, null);
                    hue = hsv[0];
                    saturation = 0; // Para escala de grises, la saturación se establece en 0
                    value = hsv[2];

                    pixelAux = Color.HSBtoRGB(hue, saturation, value);

                    grayImage.setRGB(x, y, pixelAux);
                }
            }

            File outputFile = new File("src/main/resources/image/escala_grisesHSV.png");
            ImageIO.write(grayImage, "png", outputFile);
            System.out.println("Imagen en escala de grises HSV generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void brilloPorCanal() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 10 > Brillo por canal");
        File file = new File("src/main/resources/image/LDU.jpg");
        try {
            int width;
            int height;
            int pixel;
            int brillo = 25; // Valor de brillo a sumar (puedes ajustar este valor)

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage brightnessImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Aplicar brillo
                    red = Math.min(255, red + brillo);
                    green = Math.min(255, green + brillo);
                    blue = Math.min(255, blue + brillo);

                    brightnessImage.setRGB(x, y, (red << 16) | (green << 8) | blue);
                }
            }

            File outputFile = new File("src/main/resources/image/brillo.png");
            ImageIO.write(brightnessImage, "png", outputFile);
            System.out.println("Imagen con brillo generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void filtroNegativo() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 9 > Filtro negativo");
        File file = new File("src/main/resources/image/ayuwoki.jpg");
        try {
            int width;
            int height;
            int pixel;

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage negativeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Aplicar el filtro negativo
                    int negRed = 255 - red;
                    int negGreen = 255 - green;
                    int negBlue = 255 - blue;

                    negativeImage.setRGB(x, y, (negRed << 16) | (negGreen << 8) | negBlue);
                }
            }

            File outputFile = new File("src/main/resources/image/filtro_negativo.png");
            ImageIO.write(negativeImage, "png", outputFile);
            System.out.println("Imagen con filtro negativo generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void escalaDeGrises() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 8 > Escala de grises");
        File file = new File("src/main/resources/image/trionda.jpg");
        try {
            int width;
            int height;
            int pixel;

            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    // Método simple: Promedio de los componentes RGB
                    // > int gray = (red + green + blue) / 3;

                    // Convertir a escala de grises usando la fórmula de luminosidad
                    int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                    grayImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
                }
            }

            File outputFile = new File("src/main/resources/image/escala_grises2.png");
            ImageIO.write(grayImage, "png", outputFile);
            System.out.println("Imagen en escala de grises generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void degradadoRadial() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 7 > Degradado Radial");
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Colores: Del centro hacia afuera
            Color colorCentro = new Color(147, 112, 219); // Morado
            Color colorExtremo = new Color(255, 105, 180); // Rosado Fuerte

            // Definimos el centro de la imagen
            double centroX = width / 2.0;
            double centroY = height / 2.0;

            // La distancia máxima es desde el centro hasta una esquina
            double distanciaMaxima = Math.sqrt(Math.pow(centroX, 2) + Math.pow(centroY, 2));

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // 1. Calcular distancia del píxel actual al centro
                    double distanciaActual = Math.sqrt(Math.pow(x - centroX, 2) + Math.pow(y - centroY, 2));

                    // 2. Normalizar el ratio (0.0 en el centro, 1.0 en la esquina más lejana)
                    float ratio = (float) (distanciaActual / distanciaMaxima);

                    // Aseguramos que el ratio no pase de 1 por precisión decimal
                    if (ratio > 1.0f)
                        ratio = 1.0f;

                    // 3. Interpolación LERP (la misma que ya aprendiste)
                    int r = (int) (colorCentro.getRed() * (1 - ratio) + colorExtremo.getRed() * ratio);
                    int g = (int) (colorCentro.getGreen() * (1 - ratio) + colorExtremo.getGreen() * ratio);
                    int b = (int) (colorCentro.getBlue() * (1 - ratio) + colorExtremo.getBlue() * ratio);

                    int pixel = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/degradado_radial.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Degradado radial generado exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void degradadoHorizontal1() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 5 > Degradado Horizontal 1");
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Definimos los 3 colores
            Color c1 = new Color(255, 255, 0); // Amarillo
            Color c2 = new Color(0, 0, 255); // Azul
            Color c3 = new Color(255, 0, 0); // Rojo

            for (int x = 0; x < width; x++) {
                float ratio;
                Color inicio, fin;

                // Dividimos la lógica en dos tramos
                if (x < width / 2) {
                    // TRAMO 1: De Color 1 a Color 2
                    // El ratio debe ir de 0 a 1 dentro de la primera mitad
                    ratio = (float) x / (width / 2f);
                    inicio = c1;
                    fin = c2;
                } else {
                    // TRAMO 2: De Color 2 a Color 3
                    // Restamos la mitad para que el ratio vuelva a empezar en 0
                    ratio = (float) (x - (width / 2)) / (width / 2f);
                    inicio = c2;
                    fin = c3;
                }

                // Aplicamos la misma fórmula de interpolación (LERP)
                int r = (int) (inicio.getRed() * (1 - ratio) + fin.getRed() * ratio);
                int g = (int) (inicio.getGreen() * (1 - ratio) + fin.getGreen() * ratio);
                int b = (int) (inicio.getBlue() * (1 - ratio) + fin.getBlue() * ratio);

                int pixel = (r << 16) | (g << 8) | b;

                // Aplicamos el color a toda la columna (y)
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/degradado_horizontal_1.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagen de 3 colores generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void degradadoHorizontal2() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 6 > Degradado Horizontal 2");
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Definimos los 3 colores
            Color c1 = new Color(255, 255, 0); // Amarillo
            Color c2 = new Color(0, 0, 255); // Azul
            Color c3 = new Color(255, 0, 0); // Rojo

            for (int x = 0; x < width; x++) {
                float ratio;
                Color inicio, fin;

                // Dividimos la lógica en dos tramos
                if (x < width / 2) {
                    // TRAMO 1: De Color 1 a Color 2
                    // El ratio debe ir de 0 a 1 dentro de la primera mitad
                    ratio = (float) x / (width / 2f);
                    inicio = c3;
                    fin = c2;
                } else {
                    // TRAMO 2: De Color 2 a Color 3
                    // Restamos la mitad para que el ratio vuelva a empezar en 0
                    ratio = (float) (x - (width / 2)) / (width / 2f);
                    inicio = c2;
                    fin = c1;
                }

                // Aplicamos la misma fórmula de interpolación (LERP)
                int r = (int) (inicio.getRed() * (1 - ratio) + fin.getRed() * ratio);
                int g = (int) (inicio.getGreen() * (1 - ratio) + fin.getGreen() * ratio);
                int b = (int) (inicio.getBlue() * (1 - ratio) + fin.getBlue() * ratio);

                int pixel = (r << 16) | (g << 8) | b;

                // Aplicamos el color a toda la columna (y)
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/degradado_horizontal_2.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagen de 3 colores generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void degradadoVertical1() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 3 > Degradado vertical 1");
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Definimos los dos colores del degradado
            Color startColor = new Color(255, 0, 0); // Rojo (Arriba)
            Color finColor = new Color(255, 255, 255); // Blanco (Abajo)

            for (int y = 0; y < height; y++) {
                // 1. Calcular el factor de mezcla (de 0 a 1) según la altura
                float ratio = (float) y / (float) (height - 1);

                // 2. Interpolar los componentes RGB
                int red = (int) (startColor.getRed() * (1 - ratio) + finColor.getRed() * ratio);
                int green = (int) (startColor.getGreen() * (1 - ratio) + finColor.getGreen() * ratio);
                int blue = (int) (startColor.getBlue() * (1 - ratio) + finColor.getBlue() * ratio);

                // 3. Combinar en un entero (Shift bit a bit)
                int pixel = (red << 16) | (green << 8) | blue;

                for (int x = 0; x < width; x++) {
                    image.setRGB(x, y, pixel); // Aplicamos el mismo color a toda la fila
                }
            }

            File outputFile = new File("src/main/resources/image/degradado_vertical.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagen degradado vertical generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void degradadoVertical2() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 4 > Degradado vertical 2");
        try {
            int width = 900;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Definimos los dos colores del degradado
            Color startColor = new Color(255, 0, 0); // Rojo (Arriba)
            Color finColor = new Color(255, 255, 255); // Blanco (Abajo)

            for (int y = 0; y < height; y++) {
                // 1. Calcular el factor de mezcla (de 0 a 1) según la altura
                float ratio = (float) y / (float) (height - 1);

                // 2. Interpolar los componentes RGB
                int red = (int) (finColor.getRed() * (1 - ratio) + startColor.getRed() * ratio);
                int green = (int) (finColor.getGreen() * (1 - ratio) + startColor.getGreen() * ratio);
                int blue = (int) (finColor.getBlue() * (1 - ratio) + startColor.getBlue() * ratio);

                // 3. Combinar en un entero (Shift bit a bit)
                int pixel = (red << 16) | (green << 8) | blue;

                for (int x = 0; x < width; x++) {
                    image.setRGB(x, y, pixel); // Aplicamos el mismo color a toda la fila
                }
            }

            File outputFile = new File("src/main/resources/image/degradado_vertical2.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagen degradado vertical generada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void imagenAleatoria() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 2 > Generar imagen");
        try {
            BufferedImage image;
            int width = 900;
            int height = 600;

            // Obtener los componentes RGB del color
            int red;
            int green;
            int blue;
            int pixel;

            Random random = new Random();

            // Crear una nueva imagen
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Generar valores aleatorios para los componentes RGB
                    red = random.nextInt(256);
                    green = random.nextInt(256);
                    blue = random.nextInt(256);

                    pixel = (red << 16) | (green << 8) | blue; // Combinar los componentes RGB en un solo valor
                    image.setRGB(x, y, pixel); // Establecer el color del pixel en la nueva imagen

                }
                System.out.println();
            }
            // Guardar la nueva imagen
            File outputFile = new File("src/main/resources/image/imagen_aleatoria.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Imagen aleatoria generada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al generar la imagen: " + e.getMessage());
        }
    }

    public static void copiarImagen() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Ejercicio 1 > Copiar imagen");

        File file = new File("src/main/resources/image/trionda.jpg");

        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();

            // Obtener los componentes RGB del color
            int red;
            int green;
            int blue;
            int mask = 0xFF; // Máscara para obtener los últimos 8 bits

            // Obtener el color del pixel en la posición (200, 200)
            int pixel = image.getRGB(200, 200);
            int pixel2;

            System.out.println("Dimensiones de la imagen: " + width + "x" + height);
            System.out.println("Color del pixel (200,200): " + Integer.toHexString(pixel));
            red = (pixel >> 16) & mask;
            green = (pixel >> 8) & mask;
            blue = pixel & mask;
            System.out.println("Componentes RGB: R=" + red + ", G=" + green + ", B=" + blue);

            // Crear una nueva imagen
            BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixel = image.getRGB(x, y);
                    // Obtener los componentes RGB del color
                    red = (pixel >> 16) & mask;
                    green = (pixel >> 8) & mask;
                    blue = pixel & mask;
                    System.out.print(Integer.toHexString(pixel) + " ");
                    System.out.println("Componentes RGB: R=" + red + ", G=" + green + ", B=" + blue);

                    pixel2 = (red << 16) | (green << 8) | blue; // Combinar los componentes RGB en un solo valor
                    image2.setRGB(x, y, pixel2); // Establecer el color del pixel en la nueva imagen
                }
                System.out.println();
            }
            // Guardar la nueva imagen
            File outputFile = new File("src/main/resources/image/trionda_copy.jpg");
            ImageIO.write(image2, "jpg", outputFile);
            System.out.println("Imagen copiada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al copiar la imagen: " + e.getMessage());
        }
    }
}
