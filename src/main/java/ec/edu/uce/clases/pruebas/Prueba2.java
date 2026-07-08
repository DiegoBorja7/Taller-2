package ec.edu.uce.clases.pruebas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Prueba2 {
    public static void main(String[] args) {
        String ruta = "src/main/resources/image/Prueba/";
        File file = new File(ruta+"imagen.jpg");

        int alto, ancho, pixel, pixelAux;
        int r,g,b;

        float[] kernel = {
                0, -1, 0,
                -1, 5, -1,
                0, -1, 0
        };


        try {
            BufferedImage image = ImageIO.read(file);

            alto=image.getHeight();
            ancho=image.getWidth();

            BufferedImage resultado = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);

            for (int y=1;y<alto-1;y++){
                for (int x=1;x<ancho-1;x++){
                    pixel=image.getRGB(x,y);

                    r=(pixel>>16)&0xFF;
                    b=(pixel)&0xFF;

                    int sumaG=0;
                    int indice = 0;

                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            pixel = image.getRGB(x + i, y + j);

                            g = (pixel >>  8) & 0xFF;
                            sumaG += g * kernel[indice];

                            indice++;
                        }
                    }

                    r = 0;
                    g = Math.max(0, Math.min(255, (int) sumaG));
                    b = 0;

                    pixelAux = (r<<16)|(g<<8)|b;
                    resultado.setRGB(x,y,pixelAux);
                }
            }

            File result = new File(ruta+"resultado.png");
            ImageIO.write(resultado,"png",result);
            System.out.println("Imagen generada con matriz de convolucion");

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
