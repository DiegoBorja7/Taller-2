package ec.edu.uce.clases.abril27Convolucion;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

import javax.imageio.ImageIO;

public class clase27Abril {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 27 de Abril - Convolucion Parte 2\n");

        File file = new File("src/main/resources/image/balon2.jpg");

        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage imageConvolution = convolucion(image);

            File outputFile = new File("src/main/resources/image/balon2_convolution2.png");
            ImageIO.write(imageConvolution, "png", outputFile);
            System.out.println("Imagen convolucional generada exitosamente. ✅");

        } catch (Exception e) {
            System.out.println("❌ Error al aplicar filtro: " + e.getMessage());
        }
    }

    public static BufferedImage convolucion(BufferedImage image) {
        float[] matriz = Kernels.kernelOscurecer;

        int kernelSize = (int) Math.sqrt(matriz.length);

        // Crear el kernel de convolución
        Kernel kernel = new Kernel(kernelSize, kernelSize, matriz);

        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage imageConvolution = convolveOp.filter(image, null);

        return imageConvolution;

    }
}
