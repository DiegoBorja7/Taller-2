# Ejercicios - Efectos de Degradado y Generación de Imágenes

## Descripción

Este programa permite generar imágenes con diferentes tipos de degradados (verticales, horizontales y radiales) además de otras operaciones con imágenes como copiar y generar imágenes aleatorias.

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

Al ejecutar el programa, se mostrará un menú interactivo con las siguientes opciones:

```
╔══════════════════════════════════════════════════════════════╗
║    Ejercicios - Efectos de Degradado y Generación de Imágenes ║
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

#### 0. **Salir**

- Cierra el programa

## Estructura de Archivos

```
ejercicios 1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ec/edu/uce/efectos/
│   │   │       └── Imagen.java
│   │   └── resources/
│   │       └── image/
│   │           ├── trionda.jpg (imagen de entrada)
│   │           ├── trionda_copy.jpg
│   │           ├── imagen_aleatoria.png
│   │           ├── degradado_vertical.png
│   │           ├── degradado_vertical2.png
│   │           ├── degradado_horizontal_1.png
│   │           ├── degradado_horizontal_2.png
│   │           └── degradado_radial.png
│   └── test/
├── target/
│   ├── classes/
│   └── ... (archivos compilados)
├── pom.xml
└── README.md
```

## Archivos Generados

Todas las imágenes generadas se guardan en la carpeta `src/main/resources/image/` siguiendo las mejores prácticas de Maven. Esto permite que el programa maneje correctamente las rutas de recursos independientemente del directorio desde el que se ejecute.

## Interpolación LERP

El programa utiliza **interpolación lineal (LERP)** para crear los degradados suavemente. La fórmula utilizada es:

```
color_final = color_inicio * (1 - ratio) + color_fin * ratio
```

Donde:

- `ratio` va de 0 a 1 según la posición (vertical, horizontal o radial)
- En `ratio = 0` → se obtiene `color_inicio`
- En `ratio = 1` → se obtiene `color_fin`

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Maven**: Herramienta de compilación y gestión de dependencias
- **BufferedImage**: Para manipulación de imágenes
- **ImageIO**: Para lectura y escritura de archivos de imagen (PNG, JPG)

## Notas Importantes

- El programa requiere que exista la carpeta `src/main/resources/image/` con la imagen `trionda.jpg` para ejecutar correctamente el ejercicio 1.
- Las imágenes generadas se sobrescriben cada vez que se ejecutan los ejercicios.
- El menú es interactivo y permite ejecutar múltiples ejercicios sin cerrar el programa.
- El programa maneja excepciones y proporciona mensajes de error informativos.

---

## 👨‍💻 Autor

|        |                 |
| ----------- | -------------------------- |
| **Nombre**  | Diego Andrés Borja Simbaña |
| **Carrera** | Ingeniería en Computación  |
| **Correo**  | daborjas@uce.edu.ec        |

## 📚 Información del Curso

|            |                            |
| --------------- | ------------------------------------- |
| **Materia**     | Taller 2                              |
| **Docente**     | Ing. Wladimir Carrillo                |
| **Institución** | Universidad Central del Ecuador (UCE) |
| **Fecha de presentación**       | 15 de Abril 2026                            |

---
