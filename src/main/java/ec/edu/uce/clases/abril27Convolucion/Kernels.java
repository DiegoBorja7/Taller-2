package ec.edu.uce.clases.abril27Convolucion;

public class Kernels {
    // No modifica imagen
    public static final float[] kernelNormal = {
            0f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 0f
    };

    // Enfoque (sharpen)
    public static final float[] kernelEnfoque = {
            0f, -1f, 0f,
            -1f, 5f, -1f,
            0f, -1f, 0f
    };

    // Desenfoque (blur)
    public static final float[] kernelDesenfoque = {
            1f / 9, 1f / 9, 1f / 9,
            1f / 9, 1f / 9, 1f / 9,
            1f / 9, 1f / 9, 1f / 9,
    };

    // Detección de bordes
    public static final float[] kernelBordes = {
            -0.5f, -0.5f, -0.5f,
            -0.5f, 4f, -0.5f,
            -0.5f, -0.5f, -0.5f
    };

    // Aclarar
    public static final float[] kernelAclaracion = {
            0.1f, 0.1f, 0.1f,
            0.1f, 1.0f, 0.1f,
            0.1f, 0.1f, 0.1f
    };

    // Oscurecer
    public static final float[] kernelOscurecer = {
            0.01f, 0.01f, 0.01f,
            0.01f, 0.5f, 0.01f,
            0.01f, 0.01f, 0.01f
    };

    // Gaussian 3x3 (más natural que la caja 3x3)
    public static final float[] kernelGaussian3 = {
            1f / 16f, 2f / 16f, 1f / 16f,
            2f / 16f, 4f / 16f, 2f / 16f,
            1f / 16f, 2f / 16f, 1f / 16f
    };

    // Laplaciano fuerte para detección de bordes (suma=0)
    public static final float[] kernelLaplacian = {
            -1f, -1f, -1f,
            -1f, 8f, -1f,
            -1f, -1f, -1f
    };

    /**
     * Normaliza un kernel para que su suma sea 1. Si la suma es 0
     * (por ejemplo kernels de detección de bordes), devuelve una copia.
     */
    public static float[] normalize(float[] k) {
        float sum = 0f;
        for (float v : k)
            sum += v;
        float[] out = k.clone();
        if (Math.abs(sum) < 1e-9f)
            return out;
        for (int i = 0; i < out.length; i++)
            out[i] = out[i] / sum;
        return out;
    }

    /**
     * Escala (multiplica) todos los valores del kernel por un factor.
     */
    public static float[] scale(float[] k, float factor) {
        float[] out = k.clone();
        for (int i = 0; i < out.length; i++)
            out[i] = out[i] * factor;
        return out;
    }
}
