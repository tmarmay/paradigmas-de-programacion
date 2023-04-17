module Interp (
    interp,
    Conf(..),
    interpConf,
    initial
) where

import Graphics.Gloss(Picture, Display(InWindow) ,makeColorI, color, pictures, translate, white, display)
import Dibujo (Dibujo, foldDib)
import FloatingPic (FloatingPic, Output, grid,half,zero)

import qualified Graphics.Gloss.Data.Point.Arithmetic as V

rotar :: FloatingPic -> FloatingPic
rotar f x w h = f (x V.+ w) (h) (zero V.- w)

rot45 :: FloatingPic -> FloatingPic
rot45 f x w h = f (x V.+ (half (w V.+ h)))  (half (w V.+ h)) (half(h V.- w))

espejar :: FloatingPic -> FloatingPic
espejar f x w h = f (x V.+ w) (zero V.- w) (h)

encimar :: FloatingPic -> FloatingPic -> FloatingPic 
encimar f g x w h = pictures [(f x w h),(g x w h)]

juntar :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
juntar n m f g x w h = pictures [(f x w' h),(g (x V.+ w') (r' V.* w) h)]
    where 
        r = m/(m+n)
        r'= n/(m+n)
        w' = r V.* w

apilar :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
apilar n m f g x w h = pictures [(f (x V.+ h') w (r V.* h)),(g x w h')]
    where 
        r = m/(m+n)
        r'= n/(m+n)
        h' = r' V.* h
        
-- Interpretación de un dibujo
-- formulas sacadas del enunciado
{-  
    type FloatingPic = Vector -> Vector -> Vector -> Picture
    type Output a = a -> FloatingPic

    interp :: (a -> FloatingPic -> Dibujo a) -> FloatingPic
-}
interp :: Output a -> Output (Dibujo a)
interp f d = foldDib f rotar espejar rot45 apilar juntar encimar d


-- Configuración de la interpretación
data Conf = Conf {
        name :: String,
        pic :: FloatingPic
    }

interpConf :: Conf -> Float -> Float -> Picture 
interpConf (Conf _ p) x y = p (0, 0) (x,0) (0,y)

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial cfg size = do
    let n = name cfg
        win = InWindow n (ceiling size, ceiling size) (0, 0)
    display win white $ withGrid (interpConf cfg size size) size size
  where withGrid p x y = translate (-size/2) (-size/2) $ pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
        grey = makeColorI 120 120 120 120