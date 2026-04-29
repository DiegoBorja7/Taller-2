# Ejercicios - Efectos de Degradado, Generación de Imágenes y Filtros de Colores

## 📋 Descripción

Este proyecto implementa dos paquetes principales:

1. **ec.edu.uce.efectos**: Generación de degradados (vertical, horizontal y radial) e imágenes aleatorias
2. **ec.edu.uce.FiltrosPorColores**: 6 filtros avanzados de procesamiento de imágenes con análisis multi-valor

### ✨ Características Principales

- ✅ Menú interactivo para seleccionar efectos y filtros
- ✅ Generación automática de múltiples variantes (N valores para cada parámetro)
- ✅ Análisis comparativo de resultados visuales
- ✅ Fórmulas matemáticas optimizadas (LERP, luminancia ITU-R, cuantización)
- ✅ Total de **40 imágenes** generadas automáticamente

## Requisitos

- **Java JDK 17 o superior**
- **Maven 3.9.10 o superior**

## Instalación y Configuración

### 1. Clonar o descargar el proyecto

```bash
cd "ruta/del/proyecto"
```

### 2. Compilar el proyecto

```bash
mvn clean compile
```

### 3. Ejecutar el programa

```bash
java -cp target/classes ec.edu.uce.efectos.Imagen
```

## Uso del Programa

El proyecto contiene **dos menús independientes**:

### Opción 1: Ejecutar Efectos de Degradado

```bash
java -cp target/classes ec.edu.uce.efectos.Imagen
```

Se mostrará un menú interactivo con 7 opciones:

```
╔══════════════════════════════════════════════════════════════╗
║         Generador de Efectos de Degradado                    ║
╚══════════════════════════════════════════════════════════════╝

Elige un ejercicio para ejecutar:

  1. Copiar imagen
  2. Generar imagen aleatoria
  3. Degradado vertical 1
  4. Degradado vertical 2 (invertido)
  5. Degradado horizontal 1
  6. Degradado horizontal 2 (invertido)
  7. Degradado radial
  0. Salir
```

### Opción 2: Ejecutar Filtros de Colores

```bash
java -cp target/classes ec.edu.uce.FiltrosPorColores.Filtros
```

Se mostrará un menú interactivo con 6 opciones:

```
╔══════════════════════════════════════════════════════════════╗
║       Filtros de Procesamiento de Colores                    ║
╚══════════════════════════════════════════════════════════════╝

Selecciona un filtro:

  1. Desvanecimiento Circular
  2. Efecto Blanco y Negro
  3. Efecto Escalas de Grises
  4. Efecto Retro 1 (RGB)
  5. Efecto Retro 2 (2 Canales)
  6. Vidrio Esmerilado
  0. Salir
```

### Descripción de cada ejercicio

#### 1. **Copiar Imagen**

- Lee la imagen `trionda.jpg` de la carpeta de recursos
- Copia pixel por pixel a una nueva imagen
- Guarda el resultado en `trionda_copy.jpg`
- Muestra las dimensiones y el color del pixel (200, 200)

#### 2. **Generar Imagen Aleatoria**

- Crea una imagen de 900x600 píxeles
- Genera colores RGB aleatorios para cada píxel
- Guarda el resultado en `imagen_aleatoria.png`

#### 3. **Degradado Vertical 1 (Rojo a Blanco)**

- Crea un degradado vertical de **Rojo** (superior) a **Blanco** (inferior)
- Dimensiones: 900x600 píxeles
- Guarda el resultado en `degradado_vertical.png`

#### 4. **Degradado Vertical 2 (Blanco a Rojo)**

- Crea un degradado vertical invertido de **Blanco** (superior) a **Rojo** (inferior)
- Dimensiones: 900x600 píxeles
- Guarda el resultado en `degradado_vertical2.png`

#### 5. **Degradado Horizontal 1 (Amarillo → Azul → Rojo)**

- Crea un degradado horizontal con 3 colores:
  - **Izquierda**: Amarillo
  - **Centro**: Azul
  - **Derecha**: Rojo
- Dimensiones: 900x600 píxeles
- Guarda el resultado en `degradado_horizontal_1.png`

#### 6. **Degradado Horizontal 2 (Rojo → Azul → Amarillo)**

- Crea un degradado horizontal invertido con 3 colores:
  - **Izquierda**: Rojo
  - **Centro**: Azul
  - **Derecha**: Amarillo
- Dimensiones: 900x600 píxeles
- Guarda el resultado en `degradado_horizontal_2.png`

#### 7. **Degradado Radial (Morado a Rosado)**

- Crea un degradado radial desde el centro hacia afuera:
  - **Centro**: Morado (147, 112, 219)
  - **Bordes**: Rosado Fuerte (255, 105, 180)
- Dimensiones: 900x600 píxeles
- El degradado parte del centro y se expande hacia las esquinas
- Guarda el resultado en `degradado_radial.png`

---

## 🎨 Filtros de Colores (Paquete: FiltrosPorColores)

El segundo menú permite aplicar 6 filtros avanzados a la imagen `Deber2.jpg`. Cada filtro genera múltiples variantes automáticamente para análisis comparativo.

### 1. **Desvanecimiento Circular**

- **Descripción**: Crea un efecto de desvanecimiento desde el centro (opaco) hacia las esquinas (transparente)
- **Algoritmo**: `alpha = 255 × (1 - distancia/distancia_máxima)`
- **Fórmula**: Usa distancia euclidiana desde el centro
- **Salida**: 1 imagen - `DesvanecimientoCircular.png`

#### Análisis:

- Centro de la imagen: completamente opaco (alpha = 255)
- Esquinas: completamente transparentes (alpha = 0)
- Transición suave y simétrica

---

### 2. **Efecto Blanco y Negro**

- **Descripción**: Convierte la imagen a binaria (solo blanco y negro)
- **Algoritmo**:
  - Calcula luminancia: `gris = 0.2126×R + 0.7152×G + 0.0722×B`
  - Aplica umbral: si `gris ≥ 128` → blanco (255), si no → negro (0)
- **Salida**: 1 imagen - `BlancoNegro.png`

#### Análisis:

- Pérdida total de información de color
- Basado en sensibilidad del ojo humano (más verde, menos azul)
- Ideal para detectar bordes y contraste

---

### 3. **Efecto Escalas de Grises - Análisis Multi-N**

- **Descripción**: Reduce los colores a N niveles de gris
- **Valores de N**: 2, 4, 8, 64, 128, 255
- **Algoritmo**:
  - Paso = 255 / (N - 1)
  - Nivel = round(gris / paso)
  - Gris cuantizado = round(nivel × paso)
- **Salida**: 6 imágenes automáticas

| N   | Niveles               | Salida                   |
| --- | --------------------- | ------------------------ |
| 2   | 2 grises              | `EscalasGrises_N2.png`   |
| 4   | 4 grises              | `EscalasGrises_N4.png`   |
| 8   | 8 grises              | `EscalasGrises_N8.png`   |
| 64  | 64 grises             | `EscalasGrises_N64.png`  |
| 128 | 128 grises            | `EscalasGrises_N128.png` |
| 255 | 255 grises (completo) | `EscalasGrises_N255.png` |

#### Análisis:

- **N=2**: Casi idéntico a blanco y negro
- **N=4, 8**: Compresión visual notable
- **N=64, 128**: Calidad aceptable para diferenciación
- **N=255**: Imagen original en escala de grises

---

### 4. **Efecto Retro 1 - Análisis Multi-N**

- **Descripción**: Reduce cada canal RGB independientemente a N colores
- **Valores de N**: 2, 4, 8, 64, 128, 255
- **Algoritmo**: Cuantización por canal (R, G, B)
  - Para cada canal: `paso = 255 / (N - 1)`
  - Nivel = round(canal / paso)
  - Canal cuantizado = round(nivel × paso)
- **Salida**: 6 imágenes automáticas

| N   | Colores                   | Archivo                 |
| --- | ------------------------- | ----------------------- |
| 2   | 2×2×2 = 8 colores         | `EfectoRetro1_N2.png`   |
| 4   | 4×4×4 = 64 colores        | `EfectoRetro1_N4.png`   |
| 8   | 8×8×8 = 512 colores       | `EfectoRetro1_N8.png`   |
| 64  | 64×64×64 = 262K colores   | `EfectoRetro1_N64.png`  |
| 128 | 128×128×128 = 2M colores  | `EfectoRetro1_N128.png` |
| 255 | 255×255×255 = 16M colores | `EfectoRetro1_N255.png` |

#### Análisis:

- **N=2**: Efecto extremo, solo 8 colores (muy pixelado)
- **N=4**: 64 colores, efecto retro muy marcado
- **N=8**: 512 colores, comenzamos a ver detalle
- **N=64 y mayores**: Degradación imperceptible al ojo

---

### 5. **Efecto Retro 2 - Análisis Multi-Modo y Multi-N**

- **Descripción**: Reduce solo 2 canales RGB a N colores, el tercero se anula
- **Modos**: RG (rojo-verde), RB (rojo-azul), GB (verde-azul)
- **Valores de N**: 2, 4, 8, 64, 128, 255
- **Algoritmo**: Similar a Retro 1 pero solo para 2 canales
- **Salida**: 18 imágenes automáticas (3 modos × 6 N)

| Modo   | N=2                      | N=4 | N=8 | N=64 | N=128 | N=255                      |
| ------ | ------------------------ | --- | --- | ---- | ----- | -------------------------- |
| **RG** | `EfectoRetro2_RG_N2.png` | ... | ... | ...  | ...   | `EfectoRetro2_RG_N255.png` |
| **RB** | `EfectoRetro2_RB_N2.png` | ... | ... | ...  | ...   | `EfectoRetro2_RB_N255.png` |
| **GB** | `EfectoRetro2_GB_N2.png` | ... | ... | ...  | ...   | `EfectoRetro2_GB_N255.png` |

#### Análisis por Modo:

- **RG**: Tonalidades amarillas (R+G combinadas)
- **RB**: Tonalidades magentas (R+B combinadas)
- **GB**: Tonalidades cíans (G+B combinadas)
- El canal anulado siempre es 0, eliminando ese color base

#### Análisis por N:

- **N=2**: 2×2 = 4 colores posibles por modo
- **N=4**: 4×4 = 16 colores posibles por modo
- **N=8**: 8×8 = 64 colores posibles por modo
- Mayor N = más detalle pero menos efecto retro

---

### 6. **Vidrio Esmerilado**

- **Descripción**: Crea un efecto de transparencia variable basada en el brillo del píxel
- **Algoritmo**: `alpha = 50 + (luminancia × 205/255)`
- **Comportamiento**:
  - Píxeles oscuros: más transparentes (alpha mínimo ≈ 50)
  - Píxeles claros: más opacos (alpha máximo ≈ 255)
- **Salida**: 1 imagen - `VidrioEsmerilado.png`

#### Análisis:

- El canal alpha varía de 50 a 255
- Crea efecto de textura cristalina
- Preserva estructura pero con transparencia variable

---

#### 0. **Salir**

- Cierra el programa

## Estructura de Archivos

```
ejercicios 1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ec/edu/uce/
│   │   │       ├── efectos/
│   │   │       │   └── Imagen.java (7 efectos de degradado)
│   │   │       └── FiltrosPorColores/
│   │   │           ├── Filtros.java (menú principal)
│   │   │           ├── DesvanecimientoCircular.java
│   │   │           ├── EfectoBlancoNegro.java
│   │   │           ├── EfectoEscalasGrises.java
│   │   │           ├── EfectoRetro1.java
│   │   │           ├── EfectoRetro2.java
│   │   │           └── VidrioEsmerilado.java
│   │   └── resources/
│   │       └── image/
│   │           ├── Filtros por Colores/
│   │           │   └── Deber2.jpg (imagen de entrada)
│   │           ├── degradado_vertical.png
│   │           ├── degradado_vertical2.png
│   │           ├── degradado_horizontal_1.png
│   │           ├── degradado_horizontal_2.png
│   │           ├── degradado_radial.png
│   │           ├── imagen_aleatoria.png
│   │           ├── DesvanecimientoCircular.png
│   │           ├── BlancoNegro.png
│   │           ├── EscalasGrises_N2.png ... N255.png (6 imágenes)
│   │           ├── EfectoRetro1_N2.png ... N255.png (6 imágenes)
│   │           ├── EfectoRetro2_RG_N2.png ... GB_N255.png (18 imágenes)
│   │           └── VidrioEsmerilado.png
│   └── test/
├── target/
│   ├── classes/
│   └── ... (archivos compilados)
├── pom.xml
└── README.md
```

## 📊 Archivos Generados

### Resumen General

| Categoría                    | Efectos | Variantes          | Total Imágenes  |
| ---------------------------- | ------- | ------------------ | --------------- |
| **Imagen (Degradados)**      | 7       | 1 c/u              | 7               |
| **Desvanecimiento Circular** | 1       | 1                  | 1               |
| **Blanco y Negro**           | 1       | 1                  | 1               |
| **Escalas de Grises**        | 1       | 6 (N valores)      | 6               |
| **Efecto Retro 1**           | 1       | 6 (N valores)      | 6               |
| **Efecto Retro 2**           | 1       | 18 (3 modos × 6 N) | 18              |
| **Vidrio Esmerilado**        | 1       | 1                  | 1               |
| **TOTAL**                    | **13**  |                    | **40 imágenes** |

### Detalles de Generación

#### Paquete Imagen (7 imágenes):

- `trionda_copy.png` - Copia de la imagen original
- `imagen_aleatoria.png` - Imagen con colores aleatorios
- `degradado_vertical.png` - Degradado vertical 1
- `degradado_vertical2.png` - Degradado vertical 2
- `degradado_horizontal_1.png` - Degradado horizontal 1
- `degradado_horizontal_2.png` - Degradado horizontal 2
- `degradado_radial.png` - Degradado radial

#### Paquete FiltrosPorColores (33 imágenes):

**Filtros simples (3 imágenes):**

- `DesvanecimientoCircular.png`
- `BlancoNegro.png`
- `VidrioEsmerilado.png`

**Escalas de Grises - 6 variantes (1 por cada N):**

- `EscalasGrises_N2.png`
- `EscalasGrises_N4.png`
- `EscalasGrises_N8.png`
- `EscalasGrises_N64.png`
- `EscalasGrises_N128.png`
- `EscalasGrises_N255.png`

**Efecto Retro 1 - 6 variantes (1 por cada N):**

- `EfectoRetro1_N2.png`
- `EfectoRetro1_N4.png`
- `EfectoRetro1_N8.png`
- `EfectoRetro1_N64.png`
- `EfectoRetro1_N128.png`
- `EfectoRetro1_N255.png`

**Efecto Retro 2 - 18 variantes (3 modos × 6 N):**

- Modo RG: `EfectoRetro2_RG_N2.png` ... `EfectoRetro2_RG_N255.png`
- Modo RB: `EfectoRetro2_RB_N2.png` ... `EfectoRetro2_RB_N255.png`
- Modo GB: `EfectoRetro2_GB_N2.png` ... `EfectoRetro2_GB_N255.png`

---

Todas las imágenes se guardan en `src/main/resources/image/` siguiendo las mejores prácticas de Maven.

## 🧮 Fórmulas Matemáticas Utilizadas

### 1. Interpolación Lineal (LERP)

```
color_final = color_inicio × (1 - ratio) + color_fin × ratio
```

Utilizada en todos los degradados para transiciones suaves.

### 2. Luminancia (Estándar ITU-R BT.601)

```
gris = 0.2126 × R + 0.7152 × G + 0.0722 × B
```

Conversión perceptual de RGB a escala de grises (el ojo ve más verde que azul).

### 3. Cuantización de N Niveles

```
paso = 255 / (N - 1)
nivel = round(valor / paso)
valor_cuantizado = round(nivel × paso)
```

Utilizada en EfectoEscalasGrises, EfectoRetro1 y EfectoRetro2.

### 4. Distancia Euclidiana (para Desvanecimiento Circular)

```
distancia = √((x - center_x)² + (y - center_y)²)
alpha = 255 × (1 - distancia / distancia_máxima)
```

### 5. Transparencia por Luminancia (Vidrio Esmerilado)

```
alpha = 50 + (luminancia × 205 / 255)
```

Rango de 50 (píxeles oscuros) a 255 (píxeles claros).

---

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal
- **Maven 3.9.10**: Herramienta de compilación y gestión de dependencias
- **BufferedImage**: Manipulación de imágenes en memoria
- **ImageIO**: Lectura y escritura de formatos PNG y JPG
- **Math**: Operaciones matemáticas (round, sqrt, etc.)
- **java.io.File**: Gestión de rutas de archivos
- **java.util.Scanner**: Entrada interactiva del usuario

## ⚠️ Notas Importantes

- El proyecto contiene **dos menús independientes**: uno para Efectos de Degradado y otro para Filtros de Colores
- La carpeta `src/main/resources/image/` debe existir y contener `Deber2.jpg` para ejecutar los filtros
- Los filtros que aceptan múltiples valores de **N** generan automáticamente todas las variantes en una sola ejecución:
  - **EfectoEscalasGrises**: 6 imágenes (N = 2, 4, 8, 64, 128, 255)
  - **EfectoRetro1**: 6 imágenes (N = 2, 4, 8, 64, 128, 255)
  - **EfectoRetro2**: 18 imágenes (3 modos × 6 valores de N)
- Las imágenes generadas se sobrescriben cada vez que se ejecutan los efectos/filtros
- El menú es completamente interactivo y permite ejecutar múltiples efectos sin cerrar el programa
- El programa maneja excepciones y proporciona mensajes informativos de error
- **Total de 40 imágenes** pueden ser generadas: 7 (Imagen) + 33 (Filtros)

---

## 👨‍💻 Autor

|             |                            |
| ----------- | -------------------------- |
| **Nombre**  | Diego Andrés Borja Simbaña |
| **Carrera** | Ingeniería en Computación  |
| **Correo**  | daborjas@uce.edu.ec        |

## 📚 Información del Curso

|                           |                                       |
| ------------------------- | ------------------------------------- |
| **Materia**               | Taller 2                              |
| **Docente**               | Ing. Wladimir Carrillo                |
| **Institución**           | Universidad Central del Ecuador (UCE) |
| **Fecha de presentación** | 19 de Abril 2026                      |

---
