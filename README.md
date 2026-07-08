# Taller 2 - Procesamiento de Imagenes en Java

Proyecto Maven con ejercicios de generacion de imagenes, filtros por color y practicas de convolucion.

Actualizado al estado del codigo del 4 de mayo de 2026.

## Requisitos

- Java JDK 17+
- Maven 3.9+

## Compilacion

```bash
mvn clean compile
```

## Ejecucion

### 1) Menu principal de ejercicios (13 opciones)

```bash
java -cp target/classes ec.edu.uce.clases.Imagen
```

Opciones disponibles en el menu:

1. Copiar imagen
2. Generar imagen aleatoria
3. Degradado vertical 1
4. Degradado vertical 2
5. Degradado horizontal 1
6. Degradado horizontal 2
7. Degradado radial
8. Escala de grises
9. Filtro negativo
10. Brillo por canal
11. Escala de grises HSV
12. Filtros HSV
13. Canal alpha

### 2) Menu de filtros por colores

```bash
java -cp target/classes ec.edu.uce.FiltrosPorColores.Filtros
```

Opciones disponibles en el menu:

1. Vidrio Esmerilado
2. Desvanecimiento Circular
3. Efecto Retro 1 (RGB)
4. Efecto Retro 2 (2 canales)
5. Blanco y negro
6. Escala de grises

### 3) Practica de amanecer por convolucion (Prueba 1)

```bash
java -cp target/classes ec.edu.uce.clases.prueba1
```

Esta clase genera 10 imagenes progresivas en:

- `src/main/resources/image/Prueba/ImagenPrueba_1.png`
- ...
- `src/main/resources/image/Prueba/ImagenPrueba_10.png`

Entrada:

- `src/main/resources/image/Prueba/mundial.jpg`

Logica aplicada:

- Kernel 3x3 con vecinos fijos en `0.01`.
- El centro del kernel aumenta por paso de `0.27` a `0.97`.
- Suma del kernel por paso: aprox. `0.35` a `1.05`.
- Resultado: primeras imagenes mas oscuras y ultimas cercanas a original o ligeramente mas claras.

## Otras clases de practica incluidas

- `ec.edu.uce.clases.clase20Abril`
  - Recorte de bits, vectorizacion, HSV y transparencia.
- `ec.edu.uce.clases.clase24Abril`
  - Convolucion manual (kernel 9x9).
- `ec.edu.uce.clases.abril27Convolucion.clase27Abril`
  - Convolucion con `ConvolveOp` y kernels reutilizables.
- `ec.edu.uce.clases.clase27AbrilFiltros`
  - Filtros por canal RGB sobre imagen de prueba.

## Estructura actual del proyecto

```text
src/main/java/ec/edu/uce/
  clases/
    Imagen.java
    prueba1.java
    clase20Abril.java
    clase24Abril.java
    clase27AbrilFiltros.java
    abril27Convolucion/
      Kernels.java
      clase27Abril.java
  FiltrosPorColores/
    Filtros.java
    VidrioEsmerilado.java
    DesvanecimientoCircular.java
    EfectoRetro1.java
    EfectoRetro2.java
    EfectoBlancoNegro.java
    EfectoEscalasGrises.java
```

## Recursos de imagen

- Carpeta base: `src/main/resources/image/`
- Subcarpeta de filtros: `src/main/resources/image/Filtros por Colores/`
- Subcarpeta de prueba amanecer: `src/main/resources/image/Prueba/`

## Notas

- El proyecto usa rutas relativas a `src/main/resources/image/...` para entradas y salidas.
- Si una imagen de entrada no existe, la clase mostrara error en consola.
- El repositorio incluye `.gitignore` para no versionar `target/` y mantener versionadas las imagenes de `resources`.

## Autor

- Diego Andres Borja Simbana
- Universidad Central del Ecuador
- Carrera: Ingenieria en Computacion
