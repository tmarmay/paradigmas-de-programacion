module Dibujos.Grilla (
    grillaConf,
    grilla
) where

import Graphics.Gloss (Picture,Vector, blue, color, line, pictures, red, white, text,translate,scale)

import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo (Dibujo, figura, juntar, apilar, rot45, rotar, encimar, espejar,cuarteto)
import FloatingPic (Output, half, zero,FloatingPic)
import Interp (Conf(..), interp)


type Par = (Int, Int)

parToString :: Par -> String
parToString (x,y) = "(" ++ (show x) ++ "," ++ (show y) ++ ")"

stringToText :: Output Par
stringToText (p1,p2) x y z =  scale 0.2 0.2 
                                (translate ((fromIntegral $ p2) * 600) ((7-(fromIntegral p1)) * 600) 
                                (text (parToString (p1,p2))))

row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar (fromIntegral $ length ds) 5 d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar (fromIntegral $ length ds) 5 d (column ds)

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

testAll :: Dibujo Par
testAll = grilla [[row [figura (0,0), figura (0,1),figura (0,2),figura (0,3),figura (0,4),figura (0,5),figura (0,6),figura (0,7)]],
                  [row [figura (1,0),figura (1,1),figura (1,2),figura (1,3),figura (1,4),figura (1,5),figura (1,6),figura (1,7)]],
                  [row [figura (2,0),figura (2,1),figura (2,2),figura (2,3),figura (2,4),figura (2,5),figura (2,6),figura (2,7)]],
                  [row [figura (3,0),figura (3,1),figura (3,2),figura (3,3),figura (3,4),figura (3,5),figura (3,6),figura (3,7)]],
                  [row [figura (4,0),figura (4,1),figura (4,2),figura (4,3),figura (4,4),figura (4,5),figura (4,6),figura (4,7)]],
                  [row [figura (5,0),figura (5,1),figura (5,2),figura (5,3),figura (5,4),figura (5,5),figura (5,6),figura (5,7)]],
                  [row [figura (6,0),figura (6,1),figura (6,2),figura (6,3),figura (6,4),figura (6,5),figura (6,6),figura (6,7)]],
                  [row [figura (7,0),figura (7,1),figura (7,2),figura (7,3),figura (7,4),figura (7,5),figura (7,6),figura (7,7)]]]  

grillaConf :: Conf
grillaConf = Conf {
    name = "Grilla",
    pic = interp stringToText testAll
}
