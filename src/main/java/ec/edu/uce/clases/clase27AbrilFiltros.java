package ec.edu.uce.clases;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class clase27AbrilFiltros {
    public static void main(String[] args) {
       filtroRojo();        
    }

    public static void filtroRojo() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 27 de Abril - Filtros\n");

        File file = new File("src/main/resources/image/balon2.jpg");

        int height, width, pixel;
        int red, green, blue;

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage imageConvolution = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el valor del pixel actual
                    pixel = image.getRGB(x, y);

                    // Extraer los componentes RGB del pixel
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = (pixel >> 0) & 0xFF;
                    
                    // Convertir a escala de grises
                    int gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

                    gray = Math.min(255, (int) (gray * 1.05));

                    red = Math.min(255, (int) (gray * 1.20));
                    green = Math.min(255, (int) (gray * 0.35));
                    blue = Math.min(255, (int) (gray * 0.65));

                    pixel = (red << 16) | (green << 8) | blue;
                    imageConvolution.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/balon2_filtroRed.png");
            ImageIO.write(imageConvolution, "png", outputFile);
            System.out.println("Imagen con filtro rojo generada exitosamente. ✅");

        } catch (Exception e) {
            System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
        }
    }

    public static void filtroAzul() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 27 de Abril - Filtros\n");

        File file = new File("src/main/resources/image/balon2.jpg");

        int height, width, pixel;
        int red, green, blue;

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage imageConvolution = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el valor del pixel actual
                    pixel = image.getRGB(x, y);

                    // Extraer los componentes RGB del pixel
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = (pixel >> 0) & 0xFF;
                    
                    // Convertir a escala de grises
                    int gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

                    gray = Math.min(255, (int) (gray * 1.05));

                    red = (int) (gray * 0.18);
                    green = (int) (gray * 0.28);
                    blue = Math.min(255, (int) (gray * 1.25));

                    red = Math.min(255, red);
                    green = Math.min(255, green);
                    blue = Math.min(255, blue);

                    pixel = (red << 16) | (green << 8) | blue;
                    imageConvolution.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/balon2_filtroBlue.png");
            ImageIO.write(imageConvolution, "png", outputFile);
            System.out.println("Imagen con filtro azul generada exitosamente. ✅");

        } catch (Exception e) {
            System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
        }
    }

    public static void filtroVerde() {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 27 de Abril - Filtros\n");

        File file = new File("src/main/resources/image/balon2.jpg");

        int height, width, pixel;
        int red, green, blue;

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage imageConvolution = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el valor del pixel actual
                    pixel = image.getRGB(x, y);

                    // Extraer los componentes RGB del pixel
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = (pixel >> 0) & 0xFF;
                    
                    // Convertir a escala de grises
                    int gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

                    gray = Math.min(255, (int) (gray * 1.05));

                    red = (int) (gray * 0.28);
                    green = Math.min(255, (int) (gray * 1.15));
                    blue = (int) (gray * 0.28);

                    red = Math.min(255, red);
                    green = Math.min(255, green);
                    blue = Math.min(255, blue);

                    pixel = (red << 16) | (green << 8) | blue;
                    imageConvolution.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File("src/main/resources/image/balon2_filtrogreen.png");
            ImageIO.write(imageConvolution, "png", outputFile);
            System.out.println("Imagen con filtro verde generada exitosamente. ✅");

        } catch (Exception e) {
            System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
        }
    }

}
