# Ecommerce API

Este es un servicio de Ecommerce construido con Spring Boot que permite gestionar productos y consultar una API externa de la NASA para obtener fotos astronómicas del día.

## Requisitos

- Java 21 o superior
- Docker y Docker Compose (para la ejecución en contenedores)
- MongoDB versión 8.0.4

---
## Instrucciones

### 1. Ejecutar la Aplicación Localmente

#### a) Clonar el repositorio

```bash
  git clone <URL_DEL_REPOSITORIO>
  cd <nombre_del_directorio>
```
#### b) Compilar la aplicación
Compila la aplicación usando Gradle:
```bash
  ./gradlew build
```
#### c) Ejecutar la aplicación localmente
Una vez compilada la aplicación, ejecútala usando:
```bash
  ./gradlew bootRun
```
Esto iniciará la aplicación en `http://localhost:8080`.

### 2. Configurar las Claves de Acceso para la API Externa
La aplicación utiliza la [NASA API](https://api.nasa.gov/) para obtener fotos astronómicas del 
día. Para ello, necesitas generar una clave de API y configurarla en el archivo 
`application.yml` o `application-docker.yml`.
- Visita [NASA API](https://api.nasa.gov/) y genera tu clave de API.
- Añade la clave en el archivo `application.yml` o `application-docker.yml` en la sección `nasa.api-key`:
```yaml
nasa:
  api-key: <TU_API_KEY_AQUI>
```
### 3. Probar la Integración
#### a) Crear un Producto
Envía una solicitud POST a `http://localhost:8080/api/v1/product` con el siguiente cuerpo JSON 
para crear un nuevo producto:
```json
{
  "productId": "P00001",
  "name": "headphones",
  "description": "Over-ear headphones with noise cancellation.",
  "price": 199.99,
  "stock": 50
}
```
#### b) Consultar Productos
Envía una solicitud GET a `http://localhost:8080/api/v1/products` para obtener una lista 
de todos los productos.
#### c) Consultar un Producto por ID
Envía una solicitud GET a `http://localhost:8080/api/v1/product/{productId}`, reemplazando {productId} 
por el ID del producto.
#### d) Actualizar un Producto
Envía una solicitud PUT a `http://localhost:8080/api/v1/product/{productId}` para actualizar un producto, 
pasando los datos actualizados en el cuerpo de la solicitud.
#### e) Eliminar un Producto
Envía una solicitud DELETE a `http://localhost:8080/api/v1/product/{productId}` para eliminar un producto.
#### f) Consultar Fotos Astronómicas del Día
Envía una solicitud GET a `http://localhost:8080/api/v1/nasa/apod?date={AAAA-MM-DD}`, reemplazando {AAAA-MM-DD} 
con una fecha en formato AAAA-MM-DD para obtener la foto astronómica del día.

### 4. Construir y Ejecutar la Aplicación con Docker
#### a) Construir la aplicación
Para construir la imagen Docker de la aplicación, primero asegúrate de haber ejecutado el siguiente comando 
para generar el JAR de la aplicación:
```bash
  ./gradlew build
```
#### b) Ejecutar la aplicación con Docker Compose
Utiliza Docker Compose para construir y ejecutar los contenedores:
```bash
  docker-compose up --build
```
Debes ejecutar el comando desde la ubicación raíz de tu proyecto, esto construirá la imagen 
de la API y el contenedor de MongoDB, y expondrá los puertos de la siguiente manera:
- API: `http://localhost:8081`
- MongoDB: `mongodb://localhost:27017/ecommerce_db`
  
Si necesitas cambiar los puertos o la configuración de MongoDB o la API, puedes hacerlo en los archivos 
`docker-compose.yml`, `application.yml` o `application-docker.yml`.
#### c) Detener la aplicación
Para detener la aplicación y los contenedores:
```bash
  docker-compose down
```
---

## Licencia

Este proyecto está bajo la licencia ISC.

