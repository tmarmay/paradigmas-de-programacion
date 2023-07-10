# Laboratorio de programación funcional

  

## Integrantes:

+ Cuevas Ignacio

+ Guglieri Juan Cruz

+ Marmay Tomas

### Nuestra experiencia 

- Antes de empezar nos resultó complicado volver a usar un lenguaje funcional, ya que lo usamos poco (a comparación de su potencial) y lo dejamos de usar al usar lenguajes imperativos.  
Comenzamos divagando como funcionaba Dibujo.hs el cual no entendimos bien hasta que nos dimos cuenta que tenia la forma de gramaticas, la cual vimos hace poco en Intro a Lógica, y con eso pudimos arrancar a implementar las funciones simples de este módulo, y con estas simples seguir con las compuestas, las funcionalidades que tuvimos que recurrir a fuentes para saber que y como hacen, es `foldDib` y el `mapDib`.

## Preguntas y Respuestas


1. ¿Por qué están separadas las funcionalidades en los módulos indicados? Explicar detalladamente la responsabilidad de cada módulo.

  

- Las funcionalidades estan separadas para generar encapsulamiento y poder usarlas posteriormente (en otros modulos) llamando a su uso de forma mas simple
- Funcionalidad : 

	-  `Dibujo.hs` : Es el modulo de menor nivel ya que traduce los comandos en gramaticas y los desconpone segun el tipo de función que se llame. Además exporta las funcionalidades mas importantes sobre el manejo de los dibujos.

  

	-  `Pred.hs` : Se encarga de que ciertos predicados se cumplan en figuras basicas.

  

	-  `Interp.hs` : Este modulo es el "puente" entre nuestras funciones y Gloss, el cual muestra en pantalla. La función principal es interpretar en el lenguaje de Gloss lo que implementamos en Dibujo.hs

  

	-  `Dibujos/..`: Función en prueba de nuestra implementación, Además esta la figura de Escher para mostrarla en pantalla (obviamente usando nuestras funciones) la cual es el principal objetivo del laboratorio.

  

	-  `Main.hs` : Modulo donde hace el llamado principal para correr el programa, alli se pasan parametros para mostrar por pantalla los dibujos especificados en la carpeta Dibujos.

  

	-  `Tests/TestDibujo` : Unidad de testeo para Dibujo.hs, la usamos para chequear que las funciones que implementamos en Dibujo estan haciendo lo deseado. Los testeos se hacen utilizando el modulo HUnit que importa assertEqual.

  

	-  `Tests/TestPred` : Unidad de testeo para Pred.hs, se implemento de la misma forma que TestDibujo. Alli se probo el buen funcionamiento de las funciones de Pred.hs generando pruebas para los casos posibles.

  
  
  

2. ¿Por qué las figuras básicas no están incluidas en la definición del lenguaje, y en vez es un parámetro del tipo?

- Esto se debe a que si queremos usar otras figuras como basicas podemos ya que todas las funciones de Dibujo.hs estan definidas polimorficamente y despues solo tendriamos que cambiar cual es la interpretacion grafica que le damos en Gloss. De estar forma no limitamos lo que podemos graficar con nuestro lenguaje

  

3. ¿Qué ventaja tiene utilizar una función de `fold` sobre hacer pattern-matching directo?

- Es conveniente usar `fold` en vez de hacer pattern-matching directo ya que de no hacerlo significaria que en Inter.hs deberiamos conocer como funciona nuestro lenguaje de Dibujo.hs lo que romperia la abstraccion.
