{-# LANGUAGE LambdaCase #-}
module Dibujo (
    Dibujo(..),
    figura, rotar, espejar, rot45, apilar, juntar, encimar,
    r180, r270,
    (.-.), (///), (^^^),
    cuarteto, encimar4, ciclar,
    foldDib, mapDib,
    figuras
) where

{-
Gramática de las figuras:
<Fig> ::= Figura <Bas> | Rotar <Fig> | Espejar <Fig> | Rot45 <Fig>
    | Apilar <Float> <Float> <Fig> <Fig> 
    | Juntar <Float> <Float> <Fig> <Fig> 
    | Encimar <Fig> <Fig>
-}

-- Agreguen los tipos y definan estas funciones

data Dibujo a = Figura a | Rotar (Dibujo a) | Espejar (Dibujo a) | Rot45 (Dibujo a)
        | Apilar Float Float (Dibujo a) (Dibujo a)
        | Juntar Float Float (Dibujo a) (Dibujo a)
        | Encimar (Dibujo a) (Dibujo a)
    deriving (Eq, Show)

-- Construcción de dibujo. Abstraen los constructores.

figura :: a -> Dibujo a
figura a = Figura a

rotar :: Dibujo a -> Dibujo a
rotar a = Rotar a

espejar :: Dibujo a -> Dibujo a
espejar a = Espejar a

rot45 :: Dibujo a -> Dibujo a
rot45 a = Rot45 a

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar x y d1 d2 = Apilar x y d1 d2

juntar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar

-- Composicion n veces
comp :: (a -> a) -> Int -> a -> a
comp f 0 x = x
comp f n x | (n > 0) = f (comp f (n-1) x)
           | otherwise = error "no se puede componer negativamente"

-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 d = comp rotar 2 d

r270 :: Dibujo a -> Dibujo a
r270 d = comp rotar 3 d

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) d1 d2 = apilar 1 1 d1 d2

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) d1 d2 = juntar 1 1 d1 d2

-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) d1 d2 = encimar d1 d2

-- Dadas cuatro figuras las ubica en los cuatro cuadrantes.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a 
cuarteto d1 d2 d3 d4 = (.-.) ((///) d1 d2) ((///) d3 d4)

{- Suponemos cuarteto como:
    d1  d2
    d3  d4
-}

-- Una figura repetida con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 d = encimar d (encimar (rotar d) (encimar (r180 d) (r270 d)))

-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar d = cuarteto d (rotar d) (r180 d) (r270 d)

-- Estructura general para la semántica (a no asustarse). Ayuda: 
-- pensar en foldr y las definiciones de Intro a la lógica
foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) -> 
       (Float -> Float -> b -> b -> b) -> 
       (b -> b -> b) ->
       Dibujo a -> b
foldDib fi ro es r45 ap ju en (Figura x)  = fi x
foldDib fi ro es r45 ap ju en (Rotar x)   = ro (foldDib fi ro es r45 ap ju en x)
foldDib fi ro es r45 ap ju en (Espejar x) = es (foldDib fi ro es r45 ap ju en x)
foldDib fi ro es r45 ap ju en (Rot45 x)   = r45 (foldDib fi ro es r45 ap ju en x)
foldDib fi ro es r45 ap ju en (Apilar f1 f2 x y) = ap f1 f2 (foldDib fi ro es r45 ap ju en x) (foldDib fi ro es r45 ap ju en y)
foldDib fi ro es r45 ap ju en (Juntar f1 f2 x y) = ju f1 f2 (foldDib fi ro es r45 ap ju en x) (foldDib fi ro es r45 ap ju en y)
foldDib fi ro es r45 ap ju en (Encimar x y)      = en (foldDib fi ro es r45 ap ju en x) (foldDib fi ro es r45 ap ju en y)

-- Demostrar que `mapDib figura = id`

mapDib :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
mapDib f (Figura d) = f d
mapDib f (Rotar d) = rotar (mapDib f d)
mapDib f (Espejar d) = espejar (mapDib f d)
mapDib f (Rot45 d) = rot45 (mapDib f d)
mapDib f (Apilar x y d1 d2) = apilar x y (mapDib f d1) (mapDib f d2)
mapDib f (Juntar x y d1 d2) = Juntar x y (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = encimar (mapDib f d1) (mapDib f d2)

-- Junta todas las figuras básicas de un dibujo.
figuras :: Dibujo a -> [a]
figuras (Figura d) = [d]
figuras (Rotar d) = figuras d
figuras (Espejar d) = figuras d
figuras (Rot45 d) = figuras d
figuras (Apilar x y d1 d2) = figuras d1 ++ figuras d2
figuras (Juntar x y d1 d2) = figuras d1 ++ figuras d2
figuras (Encimar d1 d2) = figuras d1 ++ figuras d2