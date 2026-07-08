package ec.edu.uce.clases.may4MatrizFiltro;

public class Matrices {
    static float[][] coloresEscalaDeGrises = {
            { 0.299f, 0.587f, 0.114f, 0.0f },
            { 0.299f, 0.587f, 0.114f, 0.0f },
            { 0.299f, 0.587f, 0.114f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    static float[][] coloresFiltroSepia = {
            { 0.393f, 0.769f, 0.189f, 0.0f },
            { 0.349f, 0.686f, 0.168f, 0.0f },
            { 0.272f, 0.534f, 0.131f, 0.0f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    static float[][] brillo = {
            { 1.0f, 0.0f, 0.0f, 0.3f },
            { 0.0f, 1.0f, 0.0f, 0.3f },
            { 0.0f, 0.0f, 1.0f, 0.3f },
            { 0.0f, 0.0f, 0.0f, 1.0f }
    };

    // 1) Neon Cyberpunk (magenta + cyan, súper vibrante)
    static float[][] coloresFiltroNeon = {
            { 1.35f, -0.10f, 0.45f, 0.0f },
            { 0.00f, 1.15f, 0.20f, 0.0f },
            { 0.20f, 0.25f, 1.45f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

    // 2) Cinemático Teal & Orange (look de película)
    static float[][] coloresFiltroCine = {
            { 1.25f, 0.15f, -0.10f, 0.0f },
            { -0.05f, 1.00f, 0.20f, 0.0f },
            { -0.15f, 0.30f, 1.20f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

    // 3) Matrix Verde (oscuro + dominante green)
    static float[][] coloresFiltroMatrix = {
            { 0.10f, 0.30f, 0.05f, 0.0f },
            { 0.05f, 1.30f, 0.05f, 0.0f },
            { 0.05f, 0.35f, 0.10f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

    // 4) Retro VHS (lavado cálido con leve dominante roja)
    static float[][] coloresFiltroVHS = {
            { 1.15f, 0.10f, 0.05f, 0.0f },
            { 0.05f, 1.00f, 0.10f, 0.0f },
            { 0.02f, 0.08f, 0.90f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

    // 5) Vaporwave (rosado + celeste, contraste suave)
    static float[][] coloresFiltroVaporwave = {
            { 1.25f, 0.05f, 0.35f, 0.0f },
            { 0.00f, 0.95f, 0.25f, 0.0f },
            { 0.20f, 0.20f, 1.25f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

    // 6) Hielo Ártico (frío azul/cyan)
    static float[][] coloresFiltroArtico = {
            { 0.85f, 0.05f, 0.20f, 0.0f },
            { 0.00f, 1.05f, 0.25f, 0.0f },
            { 0.10f, 0.25f, 1.40f, 0.0f },
            { 0.00f, 0.00f, 0.00f, 1.0f }
    };

}
