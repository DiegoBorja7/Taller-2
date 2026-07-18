package ec.edu.uce.EcualizadorHistograma;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FiltroHistogramaApp extends JFrame {

    // --- PALETA DE COLORES (Inspirada en el degradado azul/violeta oscuro) ---
    private final Color COLOR_BG_PANEL = new Color(24, 26, 56); // Azul profundo para los contenedores
    private final Color COLOR_TEXT_MUTED = new Color(150, 155, 190); // Texto secundario
    // -------------------------------------------------------------------------

    private BufferedImage originalImageFullRes;
    private BufferedImage originalImagePreview;

    private ImageZoomPanel panelOriginal;
    private ImageZoomPanel panelProcessed;
    private JLabel lblHistograma;
    private JSlider sliderBrillo;
    private JCheckBox chkGrayscale;
    private JCheckBox chkCDF;
    private JLabel lblBrilloTxt;
    private JButton btnGuardar;

    private JScrollPane scrollOriginal;
    private JScrollPane scrollProcessed;

    private SwingWorker<Void, Void> currentWorker;

    public FiltroHistogramaApp() {
        setTitle("StudioPro7");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. ICONO PERSONALIZADO (Adios al vaso de Java) ---
        BufferedImage icon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gIcon = icon.createGraphics();
        gIcon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gIcon.setColor(new Color(212, 60, 182)); // Rosa Cibernético
        gIcon.fillRoundRect(4, 4, 56, 56, 20, 20);
        gIcon.setColor(Color.WHITE);
        gIcon.setFont(new Font("Segoe UI", Font.BOLD, 36));
        gIcon.drawString("S", 21, 46); // "S" de StudioPro
        gIcon.dispose();
        setIconImage(icon);

        // --- TOP PANEL (Equilibrado) ---
        JPanel topPanel = new JPanel(new BorderLayout(20, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JButton btnCargar = new JButton("Cargar Imagen");
        btnCargar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCargar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGuardar = new JButton("Guardar Resultado");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setBackground(new Color(212, 60, 182)); // Rosa Cibernético / Morado brillante
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setEnabled(false);

        sliderBrillo = new JSlider(-255, 255, 0);
        sliderBrillo.setMajorTickSpacing(50);
        sliderBrillo.setPaintTicks(true);
        sliderBrillo.setPaintLabels(true);
        sliderBrillo.setEnabled(false);
        sliderBrillo.setPreferredSize(new Dimension(600, 60));
        sliderBrillo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sliderBrillo.setForeground(new Color(210, 215, 255));

        // --- 2. DOBLE CLIC PARA RESETEAR EL BRILLO ---
        sliderBrillo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && sliderBrillo.isEnabled()) {
                    sliderBrillo.setValue(0);
                }
            }
        });

        chkGrayscale = new JCheckBox("Escala de Grises");
        chkGrayscale.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chkGrayscale.setEnabled(false);

        chkCDF = new JCheckBox("Ecualizar CDF");
        chkCDF.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chkCDF.setEnabled(false);
        // Tooltip opcional
        chkCDF.setToolTipText("Aplica ecualización de histograma CDF y deshabilita el ajuste lineal");

        lblBrilloTxt = new JLabel("Ajuste de Brillo: 0");
        lblBrilloTxt.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        leftPanel.add(btnCargar);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 0));
        centerPanel.add(lblBrilloTxt);
        centerPanel.add(sliderBrillo);
        centerPanel.add(chkGrayscale);
        centerPanel.add(chkCDF);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        rightPanel.add(btnGuardar);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // --- PANEL CENTRAL ---
        JPanel centerPanelWrapper = new JPanel(new BorderLayout());
        centerPanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelOriginal = new ImageZoomPanel("Esperando imagen...");
        panelProcessed = new ImageZoomPanel("Esperando imagen...");
        lblHistograma = new JLabel("Esperando datos...", SwingConstants.CENTER);
        lblHistograma.setForeground(COLOR_TEXT_MUTED);
        lblHistograma.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblHistograma.setOpaque(true);
        lblHistograma.setBackground(COLOR_BG_PANEL);

        scrollOriginal = new JScrollPane(panelOriginal);
        scrollProcessed = new JScrollPane(panelProcessed);
        JScrollPane scrollHistograma = new JScrollPane(lblHistograma);

        scrollOriginal.setMinimumSize(new Dimension(100, 100));
        scrollProcessed.setMinimumSize(new Dimension(100, 100));
        scrollHistograma.setMinimumSize(new Dimension(100, 100));

        scrollOriginal.setBorder(BorderFactory.createTitledBorder("Vista Original"));
        scrollProcessed.setBorder(BorderFactory.createTitledBorder("Vista Procesada"));
        scrollHistograma.setBorder(BorderFactory.createTitledBorder("Histograma RGB"));

        // Sincronizar el Zoom
        MouseWheelListener zoomListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (panelOriginal.getImage() == null)
                    return;
                double currentScale = panelOriginal.getScale();
                if (e.getWheelRotation() < 0) {
                    currentScale *= 1.15;
                } else {
                    currentScale /= 1.15;
                }
                panelOriginal.setScale(currentScale);
                panelProcessed.setScale(currentScale);
                e.consume();
            }
        };

        scrollOriginal.setWheelScrollingEnabled(false);
        scrollProcessed.setWheelScrollingEnabled(false);
        scrollOriginal.addMouseWheelListener(zoomListener);
        scrollProcessed.addMouseWheelListener(zoomListener);

        final JSplitPane splitLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollOriginal, scrollProcessed);
        splitLeft.setContinuousLayout(true);
        splitLeft.setResizeWeight(0.5);

        final JSplitPane splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeft, scrollHistograma);
        splitMain.setContinuousLayout(true);
        splitMain.setResizeWeight(0.666);

        centerPanelWrapper.add(splitMain, BorderLayout.CENTER);
        add(centerPanelWrapper, BorderLayout.CENTER);

        // Eventos
        btnCargar.addActionListener(e -> cargarImagen());
        btnGuardar.addActionListener(e -> guardarImagen());
        sliderBrillo.addChangeListener(e -> actualizarVistasAsincrono());
        chkGrayscale.addActionListener(e -> actualizarVistasAsincrono());
        chkCDF.addActionListener(e -> {
            boolean useCDF = chkCDF.isSelected();
            sliderBrillo.setEnabled(!useCDF);
            lblBrilloTxt.setForeground(useCDF ? COLOR_TEXT_MUTED : new Color(230, 232, 255));
            actualizarVistasAsincrono();
        });

        // --- 3. ATAJOS DE TECLADO PROFESIONALES (Ctrl+O y Ctrl+S) ---
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control O"), "cargar");
        getRootPane().getActionMap().put("cargar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                btnCargar.doClick();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control S"),
                "guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                btnGuardar.doClick();
            }
        });

        // Maximizar ventana al iniciar
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Forzar proporciones al maximizar
        addComponentListener(new java.awt.event.ComponentAdapter() {
            private boolean isFirst = true;

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (isFirst) {
                    splitMain.setDividerLocation(0.6666);
                    splitLeft.setDividerLocation(0.5);
                    isFirst = false;
                }
            }
        });
    }

    private void cargarImagen() {
        JFileChooser fileChooser = new JFileChooser("src/main/resources/image");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                if (img != null) {
                    originalImageFullRes = img;
                    lblHistograma.setText("");

                    originalImagePreview = generarPreview(originalImageFullRes, 1200, 1200);
                    panelOriginal.setImage(originalImagePreview);

                    SwingUtilities.invokeLater(() -> {
                        int w = scrollOriginal.getViewport().getWidth();
                        int h = scrollOriginal.getViewport().getHeight();
                        panelOriginal.autoFit(w, h);
                        panelProcessed.setScale(panelOriginal.getScale());
                    });

                    sliderBrillo.setValue(0);
                    sliderBrillo.setEnabled(true);
                    chkGrayscale.setEnabled(true);
                    chkCDF.setEnabled(true);
                    btnGuardar.setEnabled(true);

                    actualizarVistasAsincrono();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + ex.getMessage());
            }
        }
    }

    private void guardarImagen() {
        if (originalImageFullRes == null)
            return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Imagen Procesada");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("resultado.png"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".png");
            }

            final File finalFile = fileToSave;
            final int brillo = sliderBrillo.getValue();
            final boolean grayscale = chkGrayscale.isSelected();
            final boolean useCDF = chkCDF.isSelected();

            // Guardar en hilo de fondo para no congelar la UI si la imagen es 4K
            btnGuardar.setText("Guardando...");
            btnGuardar.setEnabled(false);

            SwingWorker<Void, Void> saveWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    BufferedImage processedFullRes;
                    if (useCDF) {
                        processedFullRes = ProcesadorImagen.ecualizarCDF(originalImageFullRes, grayscale);
                    } else {
                        processedFullRes = ProcesadorImagen.ajustarBrillo(originalImageFullRes, brillo, grayscale);
                    }
                    ImageIO.write(processedFullRes, "png", finalFile);
                    return null;
                }

                @Override
                protected void done() {
                    btnGuardar.setText("Guardar Resultado");
                    btnGuardar.setEnabled(true);
                    try {
                        get(); // Lanza excepcion si fallo algo
                        JOptionPane.showMessageDialog(FiltroHistogramaApp.this,
                                "¡Imagen guardada exitosamente en alta resolución!", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(FiltroHistogramaApp.this, "Error al guardar: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            saveWorker.execute();
        }
    }

    private BufferedImage generarPreview(BufferedImage img, int maxWidth, int maxHeight) {
        int w = img.getWidth();
        int h = img.getHeight();
        if (w <= maxWidth && h <= maxHeight) {
            return img;
        }
        double ratio = Math.min((double) maxWidth / w, (double) maxHeight / h);
        int newW = (int) (w * ratio);
        int newH = (int) (h * ratio);

        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage preview = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = preview.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        return preview;
    }

    private void actualizarVistasAsincrono() {
        if (originalImagePreview == null)
            return;

        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
        }

        final int brillo = sliderBrillo.getValue();
        final boolean grayscale = chkGrayscale.isSelected();
        final boolean useCDF = chkCDF.isSelected();

        lblBrilloTxt.setText("Ajuste de Brillo: " + (brillo > 0 ? "+" + brillo : brillo));

        currentWorker = new SwingWorker<Void, Void>() {
            private BufferedImage imagenProcesada;
            private BufferedImage graficoHistograma;

            @Override
            protected Void doInBackground() throws Exception {
                if (useCDF) {
                    imagenProcesada = ProcesadorImagen.ecualizarCDF(originalImagePreview, grayscale);
                } else {
                    imagenProcesada = ProcesadorImagen.ajustarBrillo(originalImagePreview, brillo, grayscale);
                }

                if (isCancelled())
                    return null;
                graficoHistograma = ProcesadorImagen.generarGraficoHistograma(imagenProcesada, grayscale);
                return null;
            }

            @Override
            protected void done() {
                try {
                    if (isCancelled())
                        return;
                    if (imagenProcesada != null && graficoHistograma != null) {
                        panelProcessed.setImage(imagenProcesada);

                        // Darle fondo oscuro al histograma para que encaje en el tema
                        BufferedImage darkHistogram = new BufferedImage(graficoHistograma.getWidth(),
                                graficoHistograma.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = darkHistogram.createGraphics();
                        g.setColor(COLOR_BG_PANEL);
                        g.fillRect(0, 0, darkHistogram.getWidth(), darkHistogram.getHeight());
                        g.drawImage(graficoHistograma, 0, 0, null);
                        g.dispose();

                        lblHistograma.setIcon(new ImageIcon(darkHistogram));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        currentWorker.execute();
    }

    public static void main(String[] args) {
        // --- INICIALIZAR FLATLAF (La clave para el diseño 2026) ---
        try {
            // Aplicamos redondeos de esquinas al estilo macOS (Jony Ive)
            UIManager.put("Button.arc", 15);
            UIManager.put("Component.arc", 15);
            UIManager.put("ProgressBar.arc", 15);
            UIManager.put("TextComponent.arc", 15);

            // Inyectamos la paleta de colores del archivo .png
            UIManager.put("Component.accentColor", "#5560EB");
            UIManager.put("Panel.background", new Color(13, 14, 33));
            UIManager.put("RootPane.background", new Color(13, 14, 33));
            UIManager.put("Button.background", new Color(85, 96, 235));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Label.foreground", new Color(230, 232, 255));
            UIManager.put("CheckBox.foreground", new Color(230, 232, 255));
            UIManager.put("TitledBorder.titleColor", new Color(230, 232, 255));
            UIManager.put("SplitPane.background", new Color(13, 14, 33));

            // --- SLIDER "MASTER" STYLE (Para que resalte como protagonista) ---
            UIManager.put("Slider.trackWidth", 8);
            UIManager.put("Slider.thumbWidth", 26);
            UIManager.put("Slider.thumbHeight", 26);
            UIManager.put("Slider.thumbColor", new Color(210, 215, 255)); // Blanco/Lavanda extremo de la paleta
            UIManager.put("Slider.hoverThumbColor", Color.WHITE);
            UIManager.put("Slider.trackValueColor", new Color(115, 126, 255));
            UIManager.put("Slider.trackColor", new Color(40, 43, 85));
            UIManager.put("Slider.tickColor", new Color(210, 215, 255)); // Ticks super brillantes
            // -------------------------------------------------------------------

            // Activar el tema base
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new FiltroHistogramaApp().setVisible(true);
        });
    }

    class ImageZoomPanel extends JPanel {
        private BufferedImage image;
        private double scale = 1.0;
        private String placeholderText;

        public ImageZoomPanel(String placeholder) {
            this.placeholderText = placeholder;
            setBackground(COLOR_BG_PANEL);
        }

        public void setImage(BufferedImage img) {
            this.image = img;
            revalidate();
            repaint();
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setScale(double scale) {
            this.scale = Math.max(0.05, Math.min(scale, 10.0));
            revalidate();
            repaint();
        }

        public double getScale() {
            return scale;
        }

        public void autoFit(int viewWidth, int viewHeight) {
            if (image == null || viewWidth <= 0 || viewHeight <= 0)
                return;
            double scaleX = (double) viewWidth / image.getWidth();
            double scaleY = (double) viewHeight / image.getHeight();
            this.scale = Math.min(scaleX, scaleY) * 0.95;
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            if (image == null)
                return new Dimension(400, 400);
            return new Dimension((int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int drawX = (getWidth() - (int) (image.getWidth() * scale)) / 2;
                int drawY = (getHeight() - (int) (image.getHeight() * scale)) / 2;
                if (drawX < 0)
                    drawX = 0;
                if (drawY < 0)
                    drawY = 0;

                g2d.translate(drawX, drawY);
                g2d.scale(scale, scale);
                g2d.drawImage(image, 0, 0, null);
                g2d.dispose();
            } else {
                g.setColor(COLOR_TEXT_MUTED);
                g.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(placeholderText)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g.drawString(placeholderText, x, y);
            }
        }
    }
}
