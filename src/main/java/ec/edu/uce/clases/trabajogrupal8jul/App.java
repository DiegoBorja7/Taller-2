package ec.edu.uce.clases.trabajogrupal8jul;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class App {

    public static void main(String[] args) {
        // Rutas de las imagenes de entrada y salida
        String inputPath = "src/main/resources/image/mundial.jpg";
        String outDir = "src/main/resources/image/Trabajo Grupal_Operacion_Puntos/";

        System.out.println("=== Ejercicios operación puntos - Grupo 2  ===");

        // 1. Aumentar canal rojo
        aumentarRojo(inputPath, outDir + "1_rojo_aumentado.jpg", 40);

        // 2. Escala de grises por luminancia
        escalaDeGrises(inputPath, outDir + "2_escala_grises.jpg");

        // 3. Umbralizacion
        umbralizacion(inputPath, outDir + "3_umbral_100.jpg", 100);
        umbralizacion(inputPath, outDir + "3_umbral_50.jpg", 50);
        umbralizacion(inputPath, outDir + "3_umbral_200.jpg", 200);

        // 4. Modificar saturacion
        modificarSaturacion(inputPath, outDir + "4_sat_1.20.jpg", 1.20);
        modificarSaturacion(inputPath, outDir + "4_sat_0.60.jpg", 0.60);
        modificarSaturacion(inputPath, outDir + "4_sat_1.0.jpg", 1.0);
        modificarSaturacion(inputPath, outDir + "4_sat_0.0.jpg", 0.0);

        // 5. Rotar Hue
        rotarHue(inputPath, outDir + "5_hue_60.jpg", 60f);
        rotarHue(inputPath, outDir + "5_hue_150.jpg", 150f);
        rotarHue(inputPath, outDir + "5_hue_360.jpg", 360f);
        rotarHue(inputPath, outDir + "5_hue_0.jpg", 0.0f);

        // 6. Modificar brillo (escala 1, bias variable)
        modificarBrillo(inputPath, outDir + "6_brillo_40.jpg", 40);
        modificarBrillo(inputPath, outDir + "6_brillo_menos40.jpg", -40);

        // 7. Interpolacion blanco (probamos con t = 0.5)
        interpolarBlanco(inputPath, outDir + "7_interpolar_blanco.jpg", 0.5f);

        // 8. Interpolacion negro (probamos con t = 0.5)
        interpolarNegro(inputPath, outDir + "8_interpolar_negro.jpg", 0.5f);

        // 9. Alto contraste (factor 1.8)
        altoContraste(inputPath, outDir + "9_contraste_1.8.jpg", 1.8);

        // 10. Convertir a CMYK
        convertirCMYK(inputPath);
    }

    public static int clamp(int valor) {
        return Math.max(0, Math.min(255, valor));
    }

    // Ejercicio 1
    public static void aumentarRojo(String input, String output, int incremento) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    r = clamp(r + incremento);

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 2
    public static void escalaDeGrises(String input, String output) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    int yLuminancia = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    r = g = b = yLuminancia;

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 3
    public static void umbralizacion(String input, String output, int umbral) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    int yLuminancia = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                    int color = yLuminancia >= umbral ? 255 : 0;
                    r = g = b = color;

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 4
    public static void modificarSaturacion(String input, String output, double saturacion) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    int gris = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                    r = clamp((int) (gris + saturacion * (r - gris)));
                    g = clamp((int) (gris + saturacion * (g - gris)));
                    b = clamp((int) (gris + saturacion * (b - gris)));

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 5
    public static void rotarHue(String input, String output, float grados) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    float[] hsb = Color.RGBtoHSB(r, g, b, null);
                    hsb[0] = (hsb[0] + grados / 360f) % 1.0f;

                    int nuevoRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                    r = (nuevoRGB >> 16) & mascara;
                    g = (nuevoRGB >> 8) & mascara;
                    b = nuevoRGB & mascara;

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 6
    public static void modificarBrillo(String input, String output, int bias) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    // formula: (escala * original) + bias, escala = 1
                    r = clamp(r + bias);
                    g = clamp(g + bias);
                    b = clamp(b + bias);

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 7
    public static void interpolarBlanco(String input, String output, float t) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    // A = original, B = blanco (255)
                    r = clamp((int) ((1 - t) * r + t * 255));
                    g = clamp((int) ((1 - t) * g + t * 255));
                    b = clamp((int) ((1 - t) * b + t * 255));

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 8
    public static void interpolarNegro(String input, String output, float t) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    // A = original, B = negro (0)
                    r = clamp((int) ((1 - t) * r + t * 0));
                    g = clamp((int) ((1 - t) * g + t * 0));
                    b = clamp((int) ((1 - t) * b + t * 0));

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 9
    public static void altoContraste(String input, String output, double escala) {
        File file1 = new File(input);
        File file2 = new File(output);
        int ancho, alto, pixel, pixelNuevo;
        int a, r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer1 = ImageIO.read(file1);
            ancho = buffer1.getWidth();
            alto = buffer1.getHeight();
            BufferedImage bufferSalida = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer1.getRGB(x, y);

                    a = (pixel >> 24) & mascara;
                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    r = clamp((int) (r * escala));
                    g = clamp((int) (g * escala));
                    b = clamp((int) (b * escala));

                    pixelNuevo = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferSalida.setRGB(x, y, pixelNuevo);
                }
            }
            ImageIO.write(bufferSalida, "jpg", file2);
            System.out.println("Imagen Creada Correctamente: " + file2.getName());
        } catch (Exception e) {
            System.out.println("Error al crear la imagen " + file2.getName() + ": " + e.getMessage());
        }
    }

    // Ejercicio 10
    public static void convertirCMYK(String input) {
        File file = new File(input);
        int ancho, alto, pixel;
        int r, g, b;
        int mascara = 0xFF;

        try {
            BufferedImage buffer = ImageIO.read(file);
            ancho = buffer.getWidth();
            alto = buffer.getHeight();

            int totalPixeles = ancho * alto;
            int step = Math.max(1, totalPixeles / 25);
            int pixelesImpresos = 0;
            int contadorPixel = 0;

            System.out.println("\n=== Ejercicio 10 > Muestra de 25 pixeles RGB a CMYK ===");

            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    pixel = buffer.getRGB(x, y);

                    r = (pixel >> 16) & mascara;
                    g = (pixel >> 8) & mascara;
                    b = (pixel) & mascara;

                    // Calculamos los valores CMYK (0.0 a 1.0)
                    double rPrima = r / 255.0;
                    double gPrima = g / 255.0;
                    double bPrima = b / 255.0;

                    double maxRGB = Math.max(rPrima, Math.max(gPrima, bPrima));
                    double k = 1.0 - maxRGB;

                    double c = 0, m = 0, yellow = 0;
                    if (k < 1.0) {
                        c = (1.0 - rPrima - k) / (1.0 - k);
                        m = (1.0 - gPrima - k) / (1.0 - k);
                        yellow = (1.0 - bPrima - k) / (1.0 - k);
                    }

                    // Imprimimos distribuidamente hasta 25 pixeles
                    if (contadorPixel % step == 0 && pixelesImpresos < 25) {
                        System.out.printf("Pixel [%4d, %4d] - RGB: (%3d, %3d, %3d) -> CMYK: (%.2f, %.2f, %.2f, %.2f)%n",
                                x, y, r, g, b, c, m, yellow, k);
                        pixelesImpresos++;
                    }
                    contadorPixel++;
                }
            }
            System.out.println("========================================");
        } catch (Exception e) {
            System.out.println("Error al procesar CMYK: " + e.getMessage());
        }
    }
}