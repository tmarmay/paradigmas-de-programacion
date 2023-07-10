# Laboratorio 2: Orientación a Objetos

#### Paradigmas de Programacion, Famaf 2023

## Integrantes:
- Cuevas Ignacio
- Guglieri Juan Cruz
- Marmay Tomas

## Introducción
Este proyecto se basa en el uso del paradigma de orientación a objetos, el cual fue estudiado en las clases teóricas de la materia.

[consignas](https://docs.google.com/document/d/1ijVLPpQLn0ErsYYiWT72qjnrAUwswzXE5DnRY-yAgpw/edit?usp=sharing)

## Objetivo del proyecto
El principal objetivo de este laboratorio es aprender el manejo de las clases, subclases y la buena practica de orientación a objetos.
El programa obtiene un feed de una serie de paginas dadas en `subscriptions.json` en `config/` y los imprime por pantalla. Ademas tiene la posibilidad de detectar entidades nombradas, es decir, analiza las palabras y, si son entidades nombradas, las instancia en un objeto con ciertos atributos que brindan mas informacion de la entidad en cuestion. A traves de las heuristicas:
  - Quick Heuristic
  - Random Heuristic

## Empezando el proyecto
Arrancar el proyecto fue duro, nadie habia trabajado con Java ni mucho menos con objetos. Entonces el primer paso fue entender las bases del lenguaje y el paradigma.

## Desarollo de trabajo
El enunciado nos brindaba el camino para la realización del proyecto y nos ayudo bastante en nuestra orientación. Primero nos centramos en obtener las url's de los distintos feeds. Para esto estudiamos como se componían las subscripciones, por la url, los parametros que pueden ir en la url, y el formato de datos. Entoces en el modulo `SubscriptionParser` pusimos el foco, allí parseamos el archivo JSON usando tambien funcionalidades del paquete `subscription`, modificando la url para que tome los parámetros que se pidió, tome el formato y pueda abrir ese archivo para la extracción de datos, los cuales después de filtraciones o ajustamientos mostraremos por pantalla como resultado. El parser antes mencionado tiene como clase "padre" a `GeneralParser`, el cual cumple la función de la principal interfaz de las funcionalidades de parseo, ya que  dependiendo del tipo de url, puede llamar a `RssParser` o `RedditParser`.  
  
Luego definimos un modulo `httpRequester` el cual es imprescindible para realizar el pedido del feed al servidor de noticias, esta toma una url y devuelve el contenido del feed, usando modulos de java.net para la conexión de http, siendo cuidadosos a la hora de que ocurra un error (`MalformedURLException` ó `IOException`).  
  
Luego de conseguir la lista de articulos de un feed, lo dividimos en secciones del artículo que nos interesa, como el titulo, el texto o contenido, la fecha de publicación y el link a esta, de esto de encarga el paquete `feed` el cual se divide en dos clases `Feed`, el cual modela la lista de articulos de un feed, y `Article` el cual modela el contenido de un articulo, obteniendo el titulo, texto y todo lo especificado mas arriba. Estas clases cuentan con funciones que muestran por pantalla lo obtenido en cada una y de forma tal que sea facil la lectura de estas.   
  
Finalmente implementamos la funcionalidad extra, el cual se ejecuta mediante un parametro (-ne) seguido de el metodo de obtención de heuristica (Quick o Random). Cada palabra se busca en un diccionario donde aparece (o no) junto con su respectiva categoria. Luego se instancia con una clase particular con atributos que describen al objeto.

## Conclusión 
Aspectos a mejorar:
- Heuristica: Estaria bueno implementar una heuristica mas "inteligente" que detecte la entidad de acuerdo al contexto, una buena idea seria utilizar herramientas de procesamiento de texto.
- Diccionario: Con un diccionario mas completo podriamos tener mas matching y mas informacion en cada subclase de cada entidad.
- Herencia multiple: el problema con la herencia multiple es que la combinatoria entre `Topics` y `Clases` crece demasiado rapido y se hace inviable esta implementacion.

## Extras

### Como correr
- `configurar el entorno` :export CLASSPATH=../utils/json-simple-1.1.1.jar:/../utils/json-20230227.jar:.
- `compilar los archivos`: javac FeedReaderMain.java 
- `correr ejecutable`:
    - java FeedReaderMain
    - java FeedReaderMain -ne (Quick/Random)


### Aclaraciones
- Debido a las heuristicas implementadas, hay campos de subclases que o bien tienen valores `hardcodeados` o bien no fueron seteados. 
- Para simular la herencia multiple se debe crear una subclase de entidad nombrada y luego usar una interfaz para el tema. Debido a esto se debe hacer una sub clase por por cada convinacion posible, lo que no se hizo pues no era el fin de este laboratorio.

### Paradigma OOP
Este paradigma tiene varias caracterisicas o bases, las cuales construyen una forma organizada y estructurada de programar. Se basa en el encapsulamiento de datos y/o metodos relacionados, la cual llamamos clase, para su uso mediante una interfaz. Esto tambien incluye la abstracción de cada clase, la cual es otra base de este paradigma. Este proceso identifica las caracteristicas esenciales de cada objeto, de este modo, podemos representar entidades del mundo real en forma de objetos de software.

Otro gran pilar de este paradigma es la herencia, la cual fue mencionada antes, este es un mecanismo la cual permite crear la jerarquia de clases para la organización lógica y estructurada de un programa. Compartir y reutilizar código, extendiendo las funcionalidades de este objeto. 