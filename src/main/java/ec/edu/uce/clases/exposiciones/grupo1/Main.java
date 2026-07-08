package ec.edu.uce.clases.exposiciones.grupo1;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class Main {

    private long window;

    public void run() {

        if (!glfwInit()) {
            throw new IllegalStateException(
                    "No se pudo iniciar GLFW");
        }

        window = glfwCreateWindow(
                800,
                600,
                "Rasterizacion - ZBuffer - Cubos 3D",
                0,
                0);

        if (window == 0) {
            throw new RuntimeException(
                    "No se pudo crear la ventana");
        }

        glfwMakeContextCurrent(window);

        // Callback para ajustar el viewport cuando se cambia el tamaño de la ventana
        glfwSetFramebufferSizeCallback(window, (win, width, height) -> {
            glViewport(0, 0, width, height);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            float aspectRatio = (float) width / (float) height;
            if (width >= height) {
                glFrustum(-aspectRatio, aspectRatio, -1, 1, 1, 100);
            } else {
                glFrustum(-1, 1, -1 / aspectRatio, 1 / aspectRatio, 1, 100);
            }
            glMatrixMode(GL_MODELVIEW);
        });

        glfwSwapInterval(1);

        GL.createCapabilities();

        init();

        loop();

        glfwDestroyWindow(window);

        glfwTerminate();
    }

    private void init() {

        // Configuración inicial del viewport
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetFramebufferSize(window, width, height);
        glViewport(0, 0, width[0], height[0]);

        // Configuración de la proyección 3D
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        float aspectRatio = (float) width[0] / (float) height[0];
        if (width[0] >= height[0]) {
            glFrustum(-aspectRatio, aspectRatio, -1, 1, 1, 100);
        } else {
            glFrustum(-1, 1, -1 / aspectRatio, 1 / aspectRatio, 1, 100);
        }

        glMatrixMode(GL_MODELVIEW);

        // ── Z-Buffer ──────────────────────────────────────────────────────────
        // ACTIVADO: el cubo más cercano tapa correctamente al más lejano y la
        // intersección se ve limpia.
        // Para ver el efecto SIN Z-Buffer, comenta la siguiente línea:
        //glEnable(GL_DEPTH_TEST);
        // ─────────────────────────────────────────────────────────────────────

        glDepthFunc(GL_LESS);

        glClearColor(0.15f, 0.15f, 0.15f, 1f); // fondo gris oscuro
    }

    private void loop() {

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            dibujar();

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }

    // ── Escena ────────────────────────────────────────────────────────────────
    private void dibujar() {

        // Rotación global para ver la escena en perspectiva 3D
        glLoadIdentity();
        glTranslatef(0.0f, 0.0f, -5.0f);
        glRotatef(30.0f, 1.0f, 0.0f, 0.0f); // inclinar hacia abajo
        glRotatef(35.0f, 0.0f, 1.0f, 0.0f); // girar horizontalmente

        // Cubo ROJO: desplazado a la izquierda y un poco al frente
        dibujarCubo(-0.8f, 0.0f, 0.3f, 1.0f, 1.0f, 0.0f, 0.0f);

        // Cubo AZUL: desplazado a la derecha y un poco al fondo
        // Su posición se superpone parcialmente con el cubo rojo
        dibujarCubo(0.5f, 0.0f, -0.3f, 1.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Dibuja un cubo sólido centrado en (cx, cy, cz) con semilado = mitad,
     * usando el color (r, g, b).
     *
     * Las 6 caras se definen como GL_QUADS.
     * Cada cara tiene su propio color ligeramente sombreado para dar sensación 3D.
     */
    private void dibujarCubo(float cx, float cy, float cz,
                              float mitad,
                              float r, float g, float b) {

        glPushMatrix();
        glTranslatef(cx, cy, cz);

        float d = mitad; // semilado

        glBegin(GL_QUADS);

        // ── Cara FRONTAL (z = +d) ─────────────────────────────────────────
        glColor3f(r, g, b);
        glVertex3f(-d, -d,  d);
        glVertex3f( d, -d,  d);
        glVertex3f( d,  d,  d);
        glVertex3f(-d,  d,  d);

        // ── Cara TRASERA (z = -d) ─────────────────────────────────────────
        glColor3f(r * 0.6f, g * 0.6f, b * 0.6f);
        glVertex3f( d, -d, -d);
        glVertex3f(-d, -d, -d);
        glVertex3f(-d,  d, -d);
        glVertex3f( d,  d, -d);

        // ── Cara SUPERIOR (y = +d) ────────────────────────────────────────
        glColor3f(r * 0.9f, g * 0.9f, b * 0.9f);
        glVertex3f(-d,  d,  d);
        glVertex3f( d,  d,  d);
        glVertex3f( d,  d, -d);
        glVertex3f(-d,  d, -d);

        // ── Cara INFERIOR (y = -d) ────────────────────────────────────────
        glColor3f(r * 0.4f, g * 0.4f, b * 0.4f);
        glVertex3f(-d, -d, -d);
        glVertex3f( d, -d, -d);
        glVertex3f( d, -d,  d);
        glVertex3f(-d, -d,  d);

        // ── Cara DERECHA (x = +d) ─────────────────────────────────────────
        glColor3f(r * 0.75f, g * 0.75f, b * 0.75f);
        glVertex3f( d, -d,  d);
        glVertex3f( d, -d, -d);
        glVertex3f( d,  d, -d);
        glVertex3f( d,  d,  d);

        // ── Cara IZQUIERDA (x = -d) ───────────────────────────────────────
        glColor3f(r * 0.55f, g * 0.55f, b * 0.55f);
        glVertex3f(-d, -d, -d);
        glVertex3f(-d, -d,  d);
        glVertex3f(-d,  d,  d);
        glVertex3f(-d,  d, -d);

        glEnd();

        glPopMatrix();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
