module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS)
  where
import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS num 
      | num <= 0 = error "Capacidad menor o igual a 0"
      | otherwise = Sta [] num

freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets capacidad) = capacidad - length palets 

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets capacidad) palet 
            | (netS (Sta palets capacidad) + netP palet) > 10 = error "No se puede agregar el palet, supera el peso máximo" -- Si el peso del palet supera las 10 toneladas tiro excepción
            | length palets < capacidad = Sta (palet : palets) capacidad -- Si hay lugar para el palet lo agrego
            | otherwise = Sta palets capacidad -- Si se llenó la capacidad (length palets == capacidad) NO HAGO NADA

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] _) = 0  -- Si no hay palets en el Stack pesa 0 
netS (Sta (p:ps) capacidad) = netP p + netS (Sta ps capacidad) -- Sino extraigo un palet del stack, saco su peso y lo sumo con el peso del resto del Stack

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta palets capacidad) myPalet myRoute
      | freeCellsS (Sta palets capacidad) == 0 = False -- Si no hay lugar  NO LO ACEPTO
      | netS (Sta palets capacidad) == 10 = False -- Si no aguanta el peso NO LO ACEPTO
holdsS (Sta [] _) myPalet myRoute = True -- Si no hay nada en el stack no importa la ruta LO ACEPTO
holdsS (Sta (last_palet : palets) capacity) myPalet myRoute = inOrderR myRoute (destinationP myPalet) (destinationP last_palet) -- Si hay algo en el stack compruebo con lo ultimo que hay
                                                                                                                                -- Si lo que quiero meter viene antes en la ruta LO ACEPTO

popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta [] num) city = Sta [] num -- si el stack está vacío entonces devolvelo
popS (Sta (last_palet : palets) capacity) city -- si hay algo en el stack
    | destinationP last_palet == city = popS (Sta palets capacity) city -- si el ultimo palet coincide con la ciudad obj entonces sigo iterando con lo restante
    | otherwise = Sta  (last_palet : palets) capacity -- si lo que está al tope no coincide con ciudad obj devuelvo el stack tal cual está








