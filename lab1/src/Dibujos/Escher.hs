module Dibujos.Escher (
    feoConf,
    Escher(..),
    interpEscher,
    grilla
) where

import Graphics.Gloss (Picture, blue, color, line, pictures, red, white, polygon, blank, circle)

import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo (Dibujo, figura, juntar, apilar, rot45, rotar, encimar, espejar, r270, r180, ciclar, cuarteto)
import FloatingPic (Output, half, zero)
import Interp (Conf(..), interp)

type Escher = Bool

-- Las coordenadas que usamos son:
--
--  x + y
--  |
--  x --- x + w
--
-- por ahi deban ajustarlas
interpEscher :: Output Escher
interpEscher False x w h = blank
interpEscher True x w h = pictures [line $ triangulo x w h, cara x w h]
  where
      triangulo x w h = map (x V.+) [zero, h, w, zero]
      cara x w h = polygon $ triangulo (x V.+ half h) (half w) (half h)

dd :: Dibujo Escher
dd = figura True

small :: Dibujo Escher
small = espejar $ rot45 $ dd

-- El dibujoU.
dibujoU :: Dibujo Escher -> Dibujo Escher
dibujoU p = encimar 
                (encimar (encimar small (rotar small)) (rotar (rotar small))) 
                (rotar (rotar (rotar small)))

-- El dibujo t.
dibujoT :: Dibujo Escher -> Dibujo Escher
dibujoT p = encimar p
                    (encimar small (rotar (rotar (rotar small))))


-- Esquina con nivel de detalle en base a la figura p.
esquina :: Int -> Dibujo Escher -> Dibujo Escher
esquina n p | n == 0 = cuarteto (figura False) (figura False) (figura False) (dibujoU p)
            | n > 0 = cuarteto (esquina (n-1) p) (lado (n-1) p) (rotar (lado (n-1) p)) (dibujoU p)

-- Lado con nivel de detalle.
lado :: Int -> Dibujo Escher -> Dibujo Escher
lado n p | n == 0 = cuarteto  (figura False) (figura False) (rotar (dibujoT p)) (dibujoT p)
         | n > 0 = cuarteto (lado (n-1) p) (lado (n-1) p) (rotar (dibujoT p)) (dibujoT p)

noneto p q r s t u v w x = apilar 2 1
                            (juntar 2 1 p (juntar 1 1 q r))
                            (apilar 1 1
                                (juntar 2 1 s (juntar 1 1 t u))
                                (juntar 2 1 v (juntar 1 1 w x)))

escher :: Int -> Dibujo Escher -> Dibujo Escher
escher n p = noneto (esquina n p)
                    (lado n p)
                    (r270 $ esquina n p)
                    (rotar $ lado n p)
                    (dibujoU p)
                    (r270 $ lado n p)
                    (rotar $ esquina n p)
                    (r180 $ lado n p)
                    (r180 $ esquina n p)

row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar (fromIntegral $ length ds) 1 d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar (fromIntegral $ length ds) 1 d (column ds)

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

testAll :: Dibujo Escher
testAll = grilla [[escher 6 dd]]

feoConf :: Conf
feoConf = Conf {
    name = "Escher",
    pic = interp interpEscher testAll
}
