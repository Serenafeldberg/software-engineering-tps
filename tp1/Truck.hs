module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT nStack hStack route = Tru [newS hStack | x <- [1..nStack]] route

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stacks route) = sum [freeCellsS s | s <- stacks]

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru [] route) palet = Tru [] route -- Si no hay más stacks con los que probar / si mi lista de stacks está vacía 
loadT (Tru stacks route) palet 
      | not (inRouteR route (destinationP palet)) = Tru stacks route  -- Si el destino del palet no esta en la ruta no entra
      | netT (Tru stacks route) == length stacks * 10 = Tru stacks route -- Si todos sus stacks están en peso maximo no entran mas palets
      | freeCellsT (Tru stacks route) == 0 = Tru stacks route -- Si todos sus stacks están llenos no entran más palets
loadT (Tru (headStack : stacks) route) palet 
      | holdsS headStack palet route = Tru (stackS headStack palet : stacks) route -- Si entra en el primero genial, metelo en ese stack
      | otherwise = let (Tru newStacks newRoute) = loadT (Tru stacks route) palet in Tru (headStack: newStacks) newRoute -- Sino busca en los demás stacks sin olvidar los que ya recorriste

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargar se en la ciudad
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru stacks route) = sum [netS s | s <- stacks]

