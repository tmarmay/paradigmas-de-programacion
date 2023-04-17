# Paradigmas de Programacion 
**Famaf 2023**

Breve explicacion sobre cada laboratorio de la materia


### Lab1: Programacion Funcional (Haskell)

- La idea de este laboratorio es hacer un lenguaje y luego un interprete, para que con la ayuda de Gloss poder graficar las formas basicas mas las posibles convinaciones de las mismas usando algunas funciones basicas.
Las funcionalidades estan separadas para generar encapsulamiento, Una breve explicacion de los modulos:

    -  `Dibujo.hs` : Es el modulo de menor nivel ya que traduce los comandos en gramaticas y los desconpone segun el tipo de función que se llame. Además exporta las funcionalidades mas importantes sobre el manejo de los dibujos.

    -  `Pred.hs` : Se encarga de que ciertos predicados se cumplan en figuras basicas.

    -  `Interp.hs` : Este modulo es el "puente" entre nuestras funciones y Gloss, el cual muestra en pantalla. La función principal es interpretar en el lenguaje de Gloss lo que implementamos en Dibujo.hs

    -  `Dibujos/..`: Función en prueba de nuestra implementación, Además esta la figura de Escher para mostrarla en pantalla (obviamente usando nuestras funciones) la cual es el principal objetivo del laboratorio.

    -  `Main.hs` : Modulo donde hace el llamado principal para correr el programa, alli se pasan parametros para mostrar por pantalla los dibujos especificados en la carpeta Dibujos.

    -  `Tests/TestDibujo` : Unidad de testeo para Dibujo.hs, la usamos para chequear que las funciones que implementamos en Dibujo estan haciendo lo deseado. Los testeos se hacen utilizando el modulo HUnit que importa assertEqual.

    -  `Tests/TestPred` : Unidad de testeo para Pred.hs, se implemento de la misma forma que TestDibujo. Alli se probo el buen funcionamiento de las funciones de Pred.hs generando pruebas para los casos posibles.

> Puede consultar [Consginas](https://docs.google.com/document/d/1Wh_kRkKQwa1_M5X-dr5MlzLA4NwwTxXDYIYTnIFaedE/edit?usp=sharing) para obtener mas informacion detallada. 

**Como correr:**
> runhaskell Main.hs Feo
> runhaskell Main.hs Escher

### Lab2: Programacion Orientada a Objetos (Java)