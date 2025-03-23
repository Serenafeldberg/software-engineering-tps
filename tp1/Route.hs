module Route ( Route, newR, inOrderR, inRouteR)
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route   
newR [] = error "No hay ciudades en la ruta" -- Si no hay ciudades en la lista tiro excepción
newR list = Rou list        -- construye una ruta segun una lista de ciudades

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta
inRouteR (Rou cities) cityObj = cityObj `elem` cities

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou[]) _ _ = False -- Si no encontré ninguna de las dos ciudades entonces es falso, ninguna ciudad está antes que otra
inOrderR (Rou (c:cs)) city1 city2
        | not (inRouteR (Rou (c:cs)) city2 ) = error "La segunda ciudad ingresada no está en la ruta"  -- Si no encuentro city2 en la lista de ciudades, tiro excepción
        | c == city1 = True -- Si encuentro primero city1 es True
        | c == city2 = False -- Si encuentro primero city2 es False
        | otherwise = inOrderR (Rou cs) city1 city2 -- Si no encontre ninguna, sigo iterando manteniendo city1 y city2







