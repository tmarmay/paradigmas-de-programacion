module Pred (
  Pred,
  cambiar, anyDib, allDib, orP, andP
) where

import Dibujo(Dibujo,mapDib,foldDib,figura,espejar,rotar,rot45,apilar,juntar,encimar,figuras)

type Pred a = a -> Bool

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por la figura básica indicada por el segundo argumento.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar p f d = mapDib (\x -> if p x then f x else (figura x)) d 
 
-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib p d = foldl (||) False (map p $ figuras d) 

-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib p d = foldl (&&) True (map p $ figuras d) 

-- La idea para entenderlo es que arma un nuevo predicado 
-- preg:No me queda claro porque aparece x si no esta en la delcaracion pero esta bien asi (ya lo probe)
      -- supongo que es porque explicitamente le va a llegar un x 
-- Los dos predicados se cumplen para el elemento recibido.
andP :: Pred a -> Pred a -> Pred a
andP p1 p2 x  = ((p1 x) && (p2 x)) 

-- Algún predicado se cumple para el elemento recibido.
orP :: Pred a -> Pred a -> Pred a
orP p1 p2 x = ((p1 x) || (p2 x)) 