# Foro Hub - Challenge Back-End

## Descripción
Este proyecto es una API REST desarrollada en Java como parte del Challenge ONE Back-End. Permite gestionar un foro en línea donde los usuarios pueden crear, consultar, actualizar y eliminar tópicos, así como autenticarse mediante tokens JWT. Utiliza Spring Boot y se conecta a una base de datos para la persistencia de datos.

## Uso
1. **Ejecutar la aplicación:**
   - Ejecuta la clase principal `ForoHubApplication.java` para iniciar el servidor.
2. **Endpoints disponibles:**
   - La API ofrece los siguientes endpoints para interactuar con los tópicos y usuarios:
     - `POST /auth/register`: Registra un nuevo usuario.
     - `POST /auth/login`: Autentica un usuario y devuelve un token JWT.
     - `POST /topicos`: Crea un nuevo tópico (requiere autenticación).
     - `GET /topicos`: Lista todos los tópicos registrados (requiere autenticación)..
     - `GET /topicos/{id}`: Obtiene los detalles de un tópico específico por ID (requiere autenticación)..
     - `PUT /topicos/{id}`: Actualiza un tópico existente (requiere autenticación).
     - `DELETE /topicos/{id}`: Elimina un tópico (requiere autenticación).
3. **Ejemplo de uso:**
   - **Registrar un usuario:**
     - **URL:** `http://localhost:8080/auth/login`
     - **Método:** POST
     - **Cuerpo (JSON):**
       ```json
       {
         "nombre": "tu_usuario",
         "email": "tu_email"
         "contrasena": "tu_contraseña"
       }
       ```
     - **Respuesta:**
       ```json
       {
         "id": 1,
         "username": "tu_usuario",
         "email": "tu_email"
       }
       ```
   - **Autenticación:**
     - **URL:** `http://localhost:8080/auth/login`
     - **Método:** POST
     - **Cuerpo (JSON):**
       ```json
       {
         "login": "tu_usuario",
         "contrasena": "tu_contraseña"
       }
       ```
     - **Respuesta:**
       ```json
       {
         "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
       }
       ```
   - **Crear un tópico:**
     - **URL:** `http://localhost:8080/topicos`
     - **Método:** POST
     - **Cabecera:** `Authorization: Bearer <token>`
     - **Cuerpo (JSON):**
       ```json
        {
          "titulo": "Problema con JPA",
          "mensaje": "No me funciona la relación en la entidad...",
          "curso": 1
        }
       ```
     - **Respuesta:**
       ```json
        {
            "id": 1,
            "titulo": "Problema con JPA",
            "mensaje": "No me funciona la relación en la entidad...",
            "fechaCreacion": "2025-08-19T04:56:51.8565286",
            "estado": "ACTIVO",
            "autor": "root",
            "curso": {
                "id": 1,
                "nombre": "Desarrollo Backend con Java Spring Boot",
                "categoria": "Backend"
            }
        }
       ```
   - **Listar tópicos:**
     - **URL:** `http://localhost:8080/topicos`
     - **Método:** GET
     - **Respuesta:**
       ```json
       {
        "content": [
            {
                "id": 1,
                "titulo": "Problema con JPA",
                "mensaje": "No me funciona la relación en la entidad...",
                "fechaCreacion": "2025-08-19T03:13:01",
                "estado": "ACTIVO",
                "autor": "root",
                "curso": {
                    "id": 1,
                    "nombre": "Desarrollo Backend con Java Spring Boot",
                    "categoria": "Backend"
                }
            },
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": {
                "empty": false,
                "unsorted": false,
                "sorted": true
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 3,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "numberOfElements": 3,
        "first": true,
        "empty": false
       }
       ```

**Nota:** Usa herramientas como Postman o Insomnia para probar los endpoints. Asegúrate de incluir el token JWT en la cabecera `Authorization` para las operaciones que requieren autenticación.

## Funcionalidades
- **Registro de usuarios:** Permite crear nuevos usuarios para acceder al foro.
- **Autenticación con JWT:** Los usuarios deben autenticarse para crear, actualizar o eliminar tópicos.
- **Gestión de tópicos:** Crear, listar, consultar, actualizar y eliminar tópicos en el foro.
- **Persistencia de datos:** Los tópicos y usuarios se almacenan en una base de datos relacional.
- **Validación de datos:** Implementa validaciones para garantizar la integridad de los datos enviados.
- **Paginación y ordenación:** La lista de tópicos soporta paginación y ordenación para mejorar la experiencia de consulta.

## Instalación
1. **Requisitos:**
   - Java JDK 17 (especificado en `pom.xml`).
   - Maven para la gestión de dependencias.
   - Base de datos MySQL instalada y configurada.
   - Dependencias especificadas en `pom.xml`:
     - Spring Boot Starter Data JPA (`spring-boot-starter-data-jpa`)
     - Spring Boot Starter Security (`spring-boot-starter-security`)
     - Spring Boot Starter Validation (`spring-boot-starter-validation`)
     - Spring Boot Starter Web (`spring-boot-starter-web`)
     - Flyway Core (`flyway-core`)
     - Flyway MySQL (`flyway-mysql`)
     - MySQL Connector/J (`mysql-connector-j`)
     - Project Lombok (`lombok`)
     - Spring Boot Starter Test (`spring-boot-starter-test`)
     - Spring Security Test (`spring-security-test`)
     - Java JWT (`java-jwt:4.5.0`)
2. **Configuración de la base de datos:**
   - Crea una base de datos en MySQL (ejemplo: `foro_hub_db`).
   - Configura las variables de entorno en el archivo `application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub_db
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.format-sql=true
     spring.flyway.enabled=true
     server.error.include-stacktrace=never
     api.security.token.secret=tu_secreto
     ```
3. **Pasos:**
   - Clona el repositorio desde [https://github.com/fera1991/Foro-Hub---Challenge---Back-End](https://github.com/fera1991/Foro-Hub---Challenge---Back-End).
   - Asegúrate de que las dependencias estén configuradas en tu entorno (vía Maven).
   - Configura la conexión a la base de datos en `application.properties`.
   - Compila y ejecuta `ForoHubApplication.java`.

## Challenge realizado por Fernando Jose Galdamez Mendoza
