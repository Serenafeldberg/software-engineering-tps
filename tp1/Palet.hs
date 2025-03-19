module Palet ( Palet, newP, destinationP, netP )
  where

import Control.Exception (Exception, throw)

data PaletException = InvalidWeightException Int
                    deriving (Show)
instance Exception PaletException

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas

newP destination weight 
                  | weight <= 0 = throw (InvalidWeightException weight)  -- Si el peso es menor o igual a 0 tiro excepción
                  | otherwise = Pal destination weight 

destinationP :: Palet -> String  -- responde la ciudad destino del palet
destinationP (Pal destination _) = destination

netP :: Palet -> Int             -- responde el peso en toneladas del palet
netP (Pal _ weight) = weight

