module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Control.Exception (Exception, throw)
import Palet
import Stack
import Route

data TruckException = InvalidStacksException Int
                    | InvalidHeightException Int
                    | InvalidRouteException Route
                    | InvalidDestinationException Palet
                    | InvalidWeightException Int
                    | InvalidPaletException Palet
                    deriving (Show)
instance Exception TruckException

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT nStack hStack route 
                  | nStack <= 0 = throw (InvalidStacksException nStack) -- Si la cantidad de stacks es menor o igual a 0 tiro excepción
                  | hStack <= 0 = throw (InvalidHeightException hStack) -- Si la altura de los stacks es menor o igual a 0 tiro excepción
                  | otherwise = route `seq` Tru [newS hStack | _ <- [1..nStack]] route  -- `seq` evalúa `route` de inmediato

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stacks route) = sum [freeCellsS s | s <- stacks]

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru [] route) palet = throw (InvalidPaletException palet) -- Si no hay stacks tiro excepción
loadT (Tru stacks route) palet 
      | not (inRouteR route (destinationP palet)) = throw (InvalidDestinationException palet) -- Si la ciudad de destino del palet no está en la ruta tiro excepción
      | netT (Tru stacks route) == length stacks * 10 = throw (InvalidWeightException (netT (Tru stacks route))) -- Si el peso del camión supera las 10 toneladas tiro excepción
      | freeCellsT (Tru stacks route) == 0 = throw (InvalidStacksException (0)) -- Si no hay lugar en el camión tiro excepción
loadT (Tru (headStack : stacks) route) palet 
      | holdsS headStack palet route = Tru (stackS headStack palet : stacks) route -- Si entra en el primero genial, metelo en ese stack
      | otherwise = let (Tru newStacks newRoute) = loadT (Tru stacks route) palet in Tru (headStack: newStacks) newRoute -- Sino busca en los demás stacks sin olvidar los que ya recorriste

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargar se en la ciudad
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru stacks route) = sum [netS s | s <- stacks]

