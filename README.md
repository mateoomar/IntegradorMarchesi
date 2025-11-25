# Mutant Detector API: Integrador Marchesi

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Coverage](https://img.shields.io/badge/Coverage-80%25%2B-green.svg)]()
[![Build Tool](https://img.shields.io/badge/Build%20Tool-Gradle-blue.svg)](https://gradle.org/)

Link Repositorio GitHub: [https://github.com/mateoomar/IntegradorMarchesi.git](https://github.com/mateoomar/IntegradorMarchesi.git)
Link API Desplegada (Render): [**REEMPLAZAR_AQUÍ** con la URL de tu API en Render]

[cite_start]API REST que detecta si un humano es mutante basándose en su secuencia de ADN[cite: 3].

---

## 📄 Descripción y Requisitos (Nivel 1, 2, y 3)

[cite_start]Este proyecto fue desarrollado para Magneto con el fin de detectar mutantes basándose en la secuencia de ADN[cite: 3]. La solución cumple con los siguientes niveles:

### Nivel 1: Algoritmo de Detección
* Un humano es **mutante** si se encuentran **más de una secuencia de cuatro letras iguales** de forma horizontal, vertical u oblicua (diagonales)[cite: 13, 44].
* [cite_start]El algoritmo está optimizado con **terminación anticipada**: se detiene y devuelve `true` tan pronto como encuentra la segunda secuencia[cite: 98, 186].

### Nivel 2: API REST
* [cite_start]Se expone el servicio `/mutant/` mediante `HTTP POST`[cite: 22, 51].
* Respuesta: **200 OK** si es mutante, **403 Forbidden** si es humano[cite: 26, 54, 55].
* [cite_start]**Despliegue:** La API está hosteada en **Render** (Cloud Computing Libre)[cite: 22].

### Nivel 3: Persistencia y Estadísticas
* [cite_start]**Persistencia:** Se usa **H2 Database** para guardar cada secuencia de ADN verificada[cite: 28, 61].
* Se garantiza que solo haya **un registro por ADN** (evitando duplicados mediante el cálculo de un hash)[cite: 29, 63].
* [cite_start]**Endpoint `/stats`:** Expone las estadísticas de las verificaciones[cite: 30, 64].

---

## Tecnologías Utilizadas

| Componente | Descripción |
| :--- | :--- |
| **Java 17** | Lenguaje de programación. |
| **Spring Boot 3.2.0** | Framework para el desarrollo rápido de API REST. |
| **Gradle** | Herramienta para la gestión de dependencias y compilación. |
| **H2 Database** | [cite_start]Base de datos embebida para persistencia de los registros de ADN[cite: 61]. |
| **Spring Data JPA** | Facilita la interacción con la base de datos. |
| **JUnit 5 & Mockito** | Frameworks para tests unitarios y de integración. |
| **JaCoCo** | [cite_start]Generación de reporte de cobertura de código (Code Coverage > 80% requerido). |

---

## Instrucciones de Ejecución (Local)

Para ejecutar el proyecto en su máquina local, asegúrese de tener **Java 17** y el wrapper de **Gradle** disponibles.

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/mateoomar/IntegradorMarchesi.git](https://github.com/mateoomar/IntegradorMarchesi.git)
    cd IntegradorMarchesi
    ```

2.  **Compilar y Ejecutar la aplicación:**
    * **En Mac/Linux:**
        ```bash
        ./gradlew bootRun
        ```
    * **En Windows:**
        ```powershell
        .\gradlew.bat bootRun
        ```

3.  **Verificar:** La aplicación iniciará en el puerto `8080`.

---

## Documentación y Herramientas

Una vez iniciada la aplicación, se puede acceder a las siguientes herramientas:

* **Swagger UI (Documentación interactiva):**
    > `http://localhost:8080/swagger-ui.html`
    > *Permite probar los endpoints `/mutant` y `/stats` directamente.*

* **Consola H2 (Base de Datos):**
    > `http://localhost:8080/h2-console`
    > *Credenciales por defecto (configuradas en `application.properties`):*
    > [cite_start]* **JDBC URL:** `jdbc:h2:mem:testdb` [cite: 118]
    > * **User Name:** `sa` [cite: 119]
    > [cite_start]* **Password:** (dejar vacío) [cite: 120]

---

## Endpoints de la API

### 1. POST /mutant/
Verifica si una secuencia de ADN pertenece a un mutante.

* **URL:** `/mutant/`
* [cite_start]**Método:** `POST` [cite: 51]
* **Body (JSON):**
    ```json
    {
        "dna": [
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        ]
    }
    ```
* **Respuestas:**
    * `200 OK`: El ADN corresponde a un **Mutante**[cite: 54].
    * [cite_start]`403 Forbidden`: El ADN corresponde a un **Humano**[cite: 55].
    * `400 Bad Request`: ADN inválido (no es matriz NxN, caracteres no permitidos, etc.).

### 2. GET /stats
Devuelve las estadísticas de todas las verificaciones de ADN procesadas.

* [cite_start]**URL:** `/stats` [cite: 30, 64]
* **Método:** `GET` [cite: 65]
* [cite_start]**Respuesta (JSON 200 OK):** [cite: 66]
    ```json
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }
    ```
* [cite_start]**Cálculo del Ratio:** $ratio = \frac{count\_mutant\_dna}{count\_human\_dna}$[cite: 69, 161].

---

## 🧪 Pruebas y Cobertura

Para ejecutar todas las pruebas unitarias y de integración y generar el reporte de cobertura:

```bash
./gradlew clean test
