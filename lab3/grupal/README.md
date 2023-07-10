## Laboratorio 4: Índice invertido
###### Paradigmas: FaMAF 2023

###### Integrantes
 - Ignacio Cuevas  
 - Juan Cruz Guglieri  
 - Tomas Marmay  
  
### Introducción
Para este laboratorio tuvimos que implementar un `índice invertido` tal que sea capaz de, a partir de una palabra dada, que sea capaz de devolvernos una lista ordenada por cantidad de apariciones de los links de los que se extrajo la misma. Todo esto por encima del laboratorio anterior que usando *Spark* hace cálculo distribuído, para el procesamiento de entidades nombradas.
>Para no repetir las especificaciones del laboratorio utilizados ver los informes en el directorio de cada alumno. [Ver más](https://bitbucket.org/paradigmas-programacion-famaf/grupo32_lab03_2023/src/master/)

#### Ventajas y desventajas de cada laboratorio individual
La diferencia entre el laboratorio de Guglieri con los de Marmay y Cuevas es el punto estrella.
Y la diferencia entre los laboratorios de Marmay y Cuevas fue el orden en el que se desarrollaron las partes donde paralelizamos, pero al final ambos llegaron al mismo resultado. El procesamiento en paralelo se dio primero en la obtención de la lista de `Article` para luego procesar cada uno, en paralelo también.

#### Ventajas de cada sitio
En este laboratorio tuvimos que comparar las herramientas que utilizó cada uno para ayudarse a completar el laboratorio anterior y llegamos a estas conclusiones:

- StackOverflow: no hay demasiados ejemplos, y si los hay no son de mucha confianza (pocos upvotes).

- ChatGPT: esta IA es muy buena para obtener ejemplos de cómo usar la documentación. Es posible solicitar ejemplos bastante concretos de lo que se desea.

- Documentación de Spark: la página oficial nos sirvió mucho para entender la estructura de cómo tendría que ser el programa y proporciona cómo correr algunos ejemplos. También nos sirvió para entender y conocer los distintos métodos que podemos utilizar.

>Una fórmula que nos funcionó bastante bien fue el uso de ChatGPT en complemento con la página oficial de Spark.

#### Índice invertido
Para poder generar el índice invertido tuvimos que cambiar la implementación de `NamedEntity` agregándole dos listas, una con los links en donde apareció dicha entidad nombrada y otra para contar la cantidad de ocurrencias de la palabra en cada página.

*Aclaración* : Sea $A$ la lista de links y $B$ la lista de ocurrencias, $B[i]$ denota la cantidad de veces que apareció la entidad nombrada en $A[i]$.

- `Qué hace`: Cuando el usuario llama al programa con la opción -word "palabra que se desea buscar", lo que se hace es obtener el objeto de dicha entidad nombrada y devolver (o imprimir) los links en los que apareció con la cantidad de ocurrencias.

- `Cómo lo hace`: Cuando se ejecuta la heurística para encontrar las entidades nombradas, y efectivamente encuentra una, esta se instancia y además se agrega a $A$ el link donde fue detectada la entidad. Si el url ya fue agregado, lo que se hace es incrementar el contador de ocurrencias en esa página.

  *Aclaración* : Al momento de realizar el `reduce` que junta los map<String, NamedEntity> se tuvo que editar la función para que cuando se encuentre una entidad ya agregada, se haga un cauteloso `merge` de las listas $A$ y $B$, de modo que quede una sola con los datos necesarios.

##### Compilación
  - `Configurar el entorno`: export CLASSPATH=../utils/json-simple-1.1.1.jar:../utils/json-20230227.jar:.:/mnt/spark/jars/*
  - `Compilar`: javac FeedReaderMain.java
  - `Correr ejecutable`:
    - java FeedReaderMain
    - java FeedReaderMain -ne (Quick/Random)
    - java FeedReaderMain -word (word)

##### Filtrar output de Spark
  - `java FeedReaderMain -word "value" | awk '/^Esta|^([1-9]|[1-9][0-9]{1,2}) |^No/ {print}'`
  
>Estos comandos se deben ejecutar dentro de `../grupal/src`

##### Configuraciones de Spark
  - `local[*]` 	Run Spark locally with as many worker threads as logical cores on your machine ~ [master urls](https://spark.apache.org/docs/latest/submitting-applications.html).


##### Borrar archivos .class de manera rápida 
  - `find src/ -type f -name "*.class" -exec rm {} + `

>Este comando se debe ejecutar dentro de `../grupal`
