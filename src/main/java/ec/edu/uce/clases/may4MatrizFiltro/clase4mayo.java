package ec.edu.uce.clases.may4MatrizFiltro;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class clase4mayo {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - matriz - 4 de mayo\n");
        File file = new File("src/main/resources/image/LDU2.jpg");

        int height, width, pixel;
        int red, green, blue;

        try {
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();

            BufferedImage matriz = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Obtener el valor del pixel actual
                    pixel = image.getRGB(x, y);

                    // Extraer los componentes RGB del pixel
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = (pixel >> 0) & 0xFF;

                    int newRed = Math.min(255, (int) (Matrices.coloresFiltroVHS[0][0] * red + Matrices.coloresFiltroVHS[0][1] * green + Matrices.coloresFiltroVHS[0][2] * blue
                            + Matrices.coloresFiltroVHS[0][3] * 255));
                    int newGreen = Math.min(255, (int) (Matrices.coloresFiltroVHS[1][0] * red + Matrices.coloresFiltroVHS[1][1] * green + Matrices.coloresFiltroVHS[1][2] * blue
                            + Matrices.coloresFiltroVHS[1][3] * 255));
                    int newBlue = Math.min(255, (int) (Matrices.coloresFiltroVHS[2][0] * red + Matrices.coloresFiltroVHS[2][1] * green + Matrices.coloresFiltroVHS[2][2] * blue
                            + Matrices.coloresFiltroVHS[2][3] * 255));

                    pixel = (newRed << 16) | (newGreen << 8) | newBlue;
                    matriz.setRGB(x, y, pixel);
                }
            }
            File outputFile = new File("src/main/resources/image/LDU2_EfectoVHS.png");
            ImageIO.write(matriz, "png", outputFile);
            System.out.println("Imagen generada con filtro VHS.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
