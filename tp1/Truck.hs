module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where
import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT nStack hStack route 
                  | nStack <= 0 = error "Cantidad de bahias menor o igual a 0" -- Si la cantidad de bahias es menor o igual a 0 tiro excepción
                  | hStack <= 0 = error "Altura de las bahias menor o igual a 0" -- Si la altura de las bahias es menor o igual a 0 tiro excepción
                  | otherwise   = let !r = route in Tru [newS hStack | _ <- [1..nStack]] r -- Con !r forzamos la evaluación para que testF no falle, sin usar seq ni case. 

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stacks route) = sum [freeCellsS s | s <- stacks]

loadT :: Truck -> Palet -> Truck
loadT (Tru [] route) _ = error "No hay stacks disponibles para el palet"
loadT (Tru stacks route) palet
    | not (inRouteR route (destinationP palet)) = error "La ciudad de destino del palet no está en la ruta"
    | netT (Tru stacks route) == length stacks * 10 = error "Todas las bahías del camión están en su peso máximo"
    | freeCellsT (Tru stacks route) == 0 = error "No hay celdas disponibles en el camión"
    | not (holdT (Tru stacks route) palet) = error "No hay stacks disponibles para el palet"
loadT (Tru (headStack : stacks) route) palet
    | holdsS headStack palet route = Tru (stackS headStack palet : stacks) route
    | otherwise = let (Tru newStacks newRoute) = loadT (Tru stacks route) palet in Tru (headStack: newStacks) newRoute -- Sino busca en los demás stacks sin olvidar los que ya recorriste

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargar se en la ciudad
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru stacks route) = sum [netS s | s <- stacks]

holdT :: Truck -> Palet -> Bool
holdT (Tru [] route) _ = False
holdT (Tru (headStack : stacks) route) palet 
    | holdsS headStack palet route = True
    | otherwise = holdT (Tru stacks route) palet 