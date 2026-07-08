package ec.edu.uce.clases;

import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class clase13may {
    public static void main(String[] args) {
        System.out.println("  /\\_/\\  \n" +
                " ( o.o ) \n" +
                "  > ^ <  \n" +
                "  /   \\  \n" +
                " /     \\ \n" +
                "/_______\\ \nTaller 2 - Clase 13 de Mayo - Transparencia\n");

        File file = new File("src/main/resources/image/fondo.jpg");
        File file2 = new File("src/main/resources/image/superior.jpg");
        File file3 = new File("src/main/resources/image/medio.jpg");

        int height, width;
        float alpha = 0.5f;
        float invAlpha = 1.0f - alpha;

        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage image2 = ImageIO.read(file2);
            BufferedImage image3 = ImageIO.read(file3);

            height = image.getHeight();
            width = image.getWidth();

            if (image.getWidth() != width || image.getHeight() != height) {
                Image temporal = image.getScaledInstance(width, height, Image.SCALE_FAST);
                BufferedImage buffertmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2dtmp = buffertmp.createGraphics();
                g2dtmp.drawImage(temporal, 0, 0, null);
                g2dtmp.dispose();
                image = buffertmp;
            }

            if (image2.getWidth() != width || image2.getHeight() != height) {
                Image temporal2 = image2.getScaledInstance(width, height, Image.SCALE_FAST);
                BufferedImage buffertmp2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2dtmp2 = buffertmp2.createGraphics();
                g2dtmp2.drawImage(temporal2, 0, 0, null);
                g2dtmp2.dispose();
                image2 = buffertmp2;
            }

            if (image3.getWidth() != width || image3.getHeight() != height) {
                Image temporal3 = image3.getScaledInstance(width, height, Image.SCALE_FAST);
                BufferedImage buffertmp3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2dtmp3 = buffertmp3.createGraphics();
                g2dtmp3.drawImage(temporal3, 0, 0, null);
                g2dtmp3.dispose();
                image3 = buffertmp3;
            }

            BufferedImage bufferBlend = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int[] pixels1 = image.getRGB(0, 0, width, height, null, 0, width);
            int[] pixels2 = image2.getRGB(0, 0, width, height, null, 0, width);
            int[] pixels3 = image3.getRGB(0, 0, width, height, null, 0, width);
            int[] result = new int[width * height];
            for (int i = 0; i < result.length; i++) {
                int pixel1 = pixels1[i];
                int pixel2 = pixels2[i];
                int pixel3 = pixels3[i];

                int red1 = (pixel1 >> 16) & 0xFF;
                int green1 = (pixel1 >> 8) & 0xFF;
                int blue1 = pixel1 & 0xFF;

                int red2 = (pixel2 >> 16) & 0xFF;
                int green2 = (pixel2 >> 8) & 0xFF;
                int blue2 = pixel2 & 0xFF;

                int red3 = (pixel3 >> 16) & 0xFF;
                int green3 = (pixel3 >> 8) & 0xFF;
                int blue3 = pixel3 & 0xFF;

                // Mezcla secuencial con transparencia
                int redTemp = (int) (invAlpha * red1 + alpha * red2);
                int greenTemp = (int) (invAlpha * green1 + alpha * green2);
                int blueTemp = (int) (invAlpha * blue1 + alpha * blue2);

                int redResultante = (int) (invAlpha * redTemp + alpha * red3);
                int greenResultante = (int) (invAlpha * greenTemp + alpha * green3);
                int blueResultante = (int) (invAlpha * blueTemp + alpha * blue3);

                result[i] = (redResultante << 16) | (greenResultante << 8) | blueResultante;
            }

            bufferBlend.setRGB(0, 0, width, height, result, 0, width);

            // Guardar la imagen resultante
            File outputFile = new File("src/main/resources/image/transparencia.png");
            ImageIO.write(bufferBlend, "png", outputFile);
            System.out.println("Imagen generada con transparencia.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}