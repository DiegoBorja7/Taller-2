package ec.edu.uce.clases.ejerciciospropuestos;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

public class G2Capas3D_Java2D extends JFrame {
    private double[] zValues = {1.5, 2.5, 3.5};
    private int[] alphas = {255, 255, 255};
    private Color[] colors = {Color.WHITE, Color.WHITE, Color.WHITE};
    // Posiciones "3D" simuladas
    private double[] xOffsets = {-90, 0, 90};
    private double[] yOffsets = {-30, 0, 30};
    private BufferedImage[] textures = new BufferedImage[3];

    private JComboBox<String> layerCombo;
    private JSlider zSlider;
    private JSlider alphaSlider;
    private JSlider rSlider, gSlider, bSlider;
    
    private JCheckBox depthTestCheck;
    private JCheckBox depthMapCheck;
    private JCheckBox texturesCheck;
    
    private RenderPanel renderPanel;
    private boolean isUpdating = false;

    private boolean depthTestEnabled = true;
    private boolean depthMapEnabled = false;
    private boolean texturesEnabled = true;

    public G2Capas3D_Java2D() {
        setTitle("Pure Java 2D - Capas 3D Simulado");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initTextures();
        initUI();
    }

    private void initTextures() {
        try {
            textures[0] = ImageIO.read(new File("src\\main\\resources\\image\\ejemplo.jpg"));
            textures[1] = ImageIO.read(new File("src\\main\\resources\\image\\perfil.jpg"));
            textures[2] = ImageIO.read(new File("src\\main\\resources\\image\\universo.jpg"));
        } catch (Exception e) {
            // Texturas de respaldo si no existen los archivos
            for (int i = 0; i < 3; i++) {
                textures[i] = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = textures[i].createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (i == 0) {
                    g.setColor(Color.RED);
                    g.fillOval(10, 10, 108, 108);
                } else if (i == 1) {
                    g.setColor(Color.GREEN);
                    g.fillRect(10, 10, 108, 108);
                } else {
                    g.setColor(Color.BLUE);
                    int[] x = {64, 10, 118};
                    int[] y = {10, 118, 118};
                    g.fillPolygon(x, y, 3);
                }
                g.dispose();
            }
        }
    }

    private void initUI() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(680);

        renderPanel = new RenderPanel();
        renderPanel.setPreferredSize(new Dimension(680, 560));

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        sidebar.add(new JLabel("Seleccionar Capa:"));
        layerCombo = new JComboBox<>(new String[]{"Capa 1 - Roja", "Capa 2 - Verde", "Capa 3 - Azul"});
        layerCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        layerCombo.addActionListener(e -> updateSelectedLayerUI());
        sidebar.add(layerCombo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        sidebar.add(new JLabel("Profundidad Z:"));
        zSlider = new JSlider(50, 500, 150);
        zSlider.addChangeListener(e -> {
            if (!isUpdating) {
                zValues[layerCombo.getSelectedIndex()] = zSlider.getValue() / 100.0;
                renderPanel.repaint();
            }
        });
        sidebar.add(zSlider);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(new JLabel("Transparencia (Alpha):"));
        alphaSlider = new JSlider(0, 255, 255);
        alphaSlider.addChangeListener(e -> {
            if (!isUpdating) {
                alphas[layerCombo.getSelectedIndex()] = alphaSlider.getValue();
                renderPanel.repaint();
            }
        });
        sidebar.add(alphaSlider);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        sidebar.add(new JLabel("Tinte Rojo (R):"));
        rSlider = new JSlider(0, 255, 255);
        rSlider.addChangeListener(e -> updateTintColor());
        sidebar.add(rSlider);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        sidebar.add(new JLabel("Tinte Verde (G):"));
        gSlider = new JSlider(0, 255, 255);
        gSlider.addChangeListener(e -> updateTintColor());
        sidebar.add(gSlider);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        sidebar.add(new JLabel("Tinte Azul (B):"));
        bSlider = new JSlider(0, 255, 255);
        bSlider.addChangeListener(e -> updateTintColor());
        sidebar.add(bSlider);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        depthTestCheck = new JCheckBox("Habilitar GL_DEPTH_TEST (Simulado)", true);
        depthTestCheck.addActionListener(e -> {
            depthTestEnabled = depthTestCheck.isSelected();
            renderPanel.repaint();
        });
        sidebar.add(depthTestCheck);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        texturesCheck = new JCheckBox("Habilitar Texturas", true);
        texturesCheck.addActionListener(e -> {
            texturesEnabled = texturesCheck.isSelected();
            renderPanel.repaint();
        });
        sidebar.add(texturesCheck);
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        depthMapCheck = new JCheckBox("Ver Mapa de Profundidad", false);
        depthMapCheck.addActionListener(e -> {
            depthMapEnabled = depthMapCheck.isSelected();
            renderPanel.repaint();
        });
        sidebar.add(depthMapCheck);

        split.setLeftComponent(renderPanel);
        split.setRightComponent(sidebar);
        add(split);

        updateSelectedLayerUI();
    }

    private void updateSelectedLayerUI() {
        int idx = layerCombo.getSelectedIndex();
        isUpdating = true;
        zSlider.setValue((int) (zValues[idx] * 100.0));
        alphaSlider.setValue(alphas[idx]);
        rSlider.setValue(colors[idx].getRed());
        gSlider.setValue(colors[idx].getGreen());
        bSlider.setValue(colors[idx].getBlue());
        isUpdating = false;
    }

    private void updateTintColor() {
        if (!isUpdating) {
            int idx = layerCombo.getSelectedIndex();
            colors[idx] = new Color(rSlider.getValue(), gSlider.getValue(), bSlider.getValue());
            renderPanel.repaint();
        }
    }

    class RenderPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics gRaw) {
            super.paintComponent(gRaw);
            Graphics2D g = (Graphics2D) gRaw;

            // Anti-aliasing para bordes suaves
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int width = getWidth();
            int height = getHeight();

            // Fondo
            if (depthMapEnabled) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(new Color(21, 21, 21)); // 0.082f gris oscuro
            }
            g.fillRect(0, 0, width, height);

            // Origin al centro de la pantalla
            g.translate(width / 2, height / 2);

            // Ordenamiento (Painter's Algorithm simulando Z-Test)
            Integer[] orden = {0, 1, 2};
            if (depthTestEnabled) {
                // Si el Depth Test está activado, dibujamos desde la capa más lejana a la más cercana
                Arrays.sort(orden, (a, b) -> Double.compare(zValues[b], zValues[a]));
            } else {
                // Si está desactivado, dibujamos en orden aleatorio/fijo, lo que causará errores visuales
                // (una capa lejana podría dibujarse encima de una cercana)
            }

            for (int i : orden) {
                double layerZ = zValues[i];
                int alpha = alphas[i];
                Color tint = colors[i];

                // Factor de escala basado en Z (Simulando proyección perspectiva 'glFrustum')
                // A mayor Z (más lejos), más pequeño se ve.
                double scale = 3.0 / layerZ; 

                // Tamaño base del cuadrado
                int baseSize = 150;
                int scaledSize = (int) (baseSize * scale);

                // Posición calculada simulando perspectiva
                int drawX = (int) (xOffsets[i] * scale) - (scaledSize / 2);
                int drawY = (int) (yOffsets[i] * scale) - (scaledSize / 2);

                if (depthMapEnabled) {
                    // Mapa de Profundidad (Blanco cerca, Negro lejos)
                    double minZ = 0.5;
                    double maxZ = 5.0;
                    float intensity = (float) (1.0 - (layerZ - minZ) / (maxZ - minZ));
                    intensity = Math.max(0.0f, Math.min(1.0f, intensity));
                    
                    g.setColor(new Color(intensity, intensity, intensity));
                    g.fillRect(drawX, drawY, scaledSize, scaledSize);

                } else {
                    // Configurar Transparencia (Alpha Blending)
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255.0f));

                    if (texturesEnabled && textures[i] != null) {
                        // Aplicar Tinte de Color a la Textura
                        BufferedImage tintedImage = applyTint(textures[i], tint);
                        g.drawImage(tintedImage, drawX, drawY, scaledSize, scaledSize, null);
                    } else {
                        // Dibujar cuadrado de color sólido si no hay texturas
                        g.setColor(tint);
                        g.fillRect(drawX, drawY, scaledSize, scaledSize);
                    }
                }
            }
        }
        
        private BufferedImage applyTint(BufferedImage img, Color tint) {
            BufferedImage tinted = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = tinted.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();
            
            float rScale = tint.getRed() / 255.0f;
            float gScale = tint.getGreen() / 255.0f;
            float bScale = tint.getBlue() / 255.0f;
            
            RescaleOp rescaleOp = new RescaleOp(
                new float[]{rScale, gScale, bScale, 1.0f}, // Factores de multiplicación (RGBA)
                new float[]{0, 0, 0, 0},                 // Offsets
                null);
            
            rescaleOp.filter(tinted, tinted);
            return tinted;
        }
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            new G2Capas3D_Java2D().setVisible(true);
        });
    }
}
