# Ejercicio Sistema Solar

##General

Se planteó una entidad **WheaterPredictor** (que implementa **Predictor**) que para un determinado día devuelve una colección de eventos que suceden dicho día. A **WheaterPredictor** es posible agregarle una colección de eventos del tipo clima, **WheaterEventType** que serán consulados para comprobar su ocurrencia en un día específico. **WheaterEventType** calcula su propia ocurrencia a partir de la posición de los planetas modelados en un conjunto de orbitas circulares **CircularOrbit**. La posición se calcula primero obteniendo un patrón **OrbitRelatedUniformEventPattern** al que luego se le consulta para saber si ocurre en el día de interés. El patrón se calcula siempre una sola vez y luego sólo se consulta (este mecanismo es bastante simple y en este caso particular de órbitas circulares con velocidad constante permite evitar utlizar mecanismos de cacheo - podrían implementarse con @Cacheable , pero no me pareción necesario para el scope del ejercicio).
Los cálculos geométricos se basan en tres interfaces Helpers, una orientado a cálculo angular *AngularHelper*, otra en geometría de triángulos *TriangleHelp* y otra en cálculos sobre rectas *RectHelper*. Las tres se implementaron luego con algoritmos básicos, pero al componer los **WheaterEventType** a través de las interfaces permiten un fácil reemplazo por algoritmos mejorados.
Todos los helpers entiende de coordenadas (polares o rectangulares) **PolarCoord** y **RectangularCoord**

Se disponibilizó el **WheaterPredictor** a través de un servicio que implementa una interfaz (en un proyecto aparte *wheater-predictor-api* para ofrecer la posibilidad de crear clientes http por proxy). La implementación se hizo utilizando Spring (ver contextos - **applicationContext.xml** - donde se configuran los eventos, orbitas, y predictors) y Jersey.

##### Coverage

Se dejó un coverage del 100% a nivel instrucciones según EclEmma (lo que se utilizó para medir). Y se instaló el plugin de jacoco para maven para chequear cobertura al compilar el proyecto.


##### Pendientes
- Diagrama de clases
- Subir a Heroku


##### Docker
Se incluye un Dockerfile para poder correr el servicio REST. El container está basado en una imagen de Tomcat a la cual se le agrega Swagger-ui para facilitar el acceso al servicio.

##Run
Descargar el código o clonar este repo, luego para correr el servicio REST sobre un container debe ejecutarse:

```
mvn clean install
docker-compose build
docker-compose up
```
Luego puede accederse a **http://localhost:8083/swagger-ui/**