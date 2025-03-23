import Control.Exception
import System.IO.Unsafe
import Palet
import Route
import Stack
import Truck

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


rutaCorta = newR ["roma"]
rutaLarga = newR ["roma", "paris", "mdq" , "berna"]

s = stackS (stackS (stackS (newS 5) (newP "Rosario" 2)) (newP "Cordoba" 3)) (newP "Cordoba" 1)
truck = loadT (newT 2 2 (newR ["Cordoba"])) (newP "Cordoba" 3)
truck2 = newT 2 3 (newR ["Cordoba", "Rosario"])
truck3 = foldl loadT (newT 2 2 (newR ["Cordoba", "Rosario", "Buenos Aires"])) [newP "Cordoba" 3, newP "Cordoba" 3, newP "Rosario" 2, newP "Buenos Aires" 1]
t = [ testF(newR []), 
      testF(inOrderR rutaCorta "roma" "paris"), 
      inOrderR rutaLarga "roma" "mdq",
      testF(newS 0),
      testF(newS (-1)),
      testF(newP "roma" 0),
      testF(newP "roma" (-1)),
      freeCellsS (newS 10) == 10,
      freeCellsS (stackS (stackS (newS 2) (newP "Cordoba" 1)) (newP "Rosario" 1)) == 0,
      freeCellsS (stackS (newS 3) (newP "Cordoba" 2)) == 2,
      netS (newS 5) == 0,
      netS (stackS (newS 3) (newP "Cordoba" 4)) == 4,
      netS (stackS (stackS (newS 3) (newP "Cordoba" 4)) (newP "Rosario" 3)) == 7,
      netS (stackS (stackS (stackS (newS 2) (newP "Cordoba" 2)) (newP "Rosario" 1)) (newP "Buenos Aires" 3)) == netS (stackS (stackS (newS 2) (newP "Cordoba" 2)) (newP "Rosario" 1)),
      testF (stackS (stackS (newS 3) (newP "Cordoba" 4)) (newP "Rosario" 7)),
      holdsS (newS 5) (newP "Cordoba" 3) (newR ["Cordoba", "Rosario"]),
      not(holdsS (stackS (stackS (newS 2) (newP "Rosario" 3)) (newP "Cordoba" 3)) (newP "Cordoba" 2) (newR ["Cordoba", "Rosario", "Buenos Aires"])),
      not(holdsS (stackS (newS 3) (newP "Cordoba" 2)) (newP "Rosario" 3) (newR ["Cordoba", "Rosario"])),
      freeCellsS(popS (newS 5) "Cordoba") == freeCellsS (newS 5),
      freeCellsS (popS (stackS (newS 3) (newP "Cordoba" 2)) "Cordoba") == 3,
      freeCellsS (popS s "Cordoba") == 4,
      freeCellsS (popS (stackS (newS 3) (newP "Rosario" 2)) "Cordoba") == 2,
      testF (newT 0 1 rutaCorta),
      testF (newT 1 0 rutaCorta),
      testF (newT (-1) 1 rutaCorta),
      testF (newT 1 (-1) rutaCorta),
      testF (newT 5 6 (newR [])),
      freeCellsT (newT 2 3 (newR ["Cordoba", "Rosario"])) == 6,
      freeCellsT (foldl loadT (newT 1 2 (newR ["Cordoba"])) [newP "Cordoba" 3, newP "Cordoba" 3]) == 0,
      freeCellsT truck == 3,
      testF (loadT (newT 1 1 (newR ["Cordoba"])) (newP "Rosario" 3)),
      testF (foldl loadT (newT 1 1 (newR ["Cordoba"])) [newP "Cordoba" 3, newP "Cordoba" 3]),
      testF (foldl loadT (newT 1 2 (newR ["Cordoba"])) [newP "Cordoba" 10, newP "Cordoba" 3]),
      testF (foldl loadT (newT 2 2 (newR ["Cordoba", "Rosario", "Buenos Aires"])) [newP "Cordoba" 3, newP "Cordoba" 3, newP "Rosario" 2, newP "Buenos Aires" 1]), 
      freeCellsT (unloadT (newT 2 3 (newR ["Cordoba", "Rosario"])) "Rosario") == 6,
      freeCellsT (unloadT (loadT (loadT (newT 2 3 (newR ["Cordoba", "Rosario"])) (newP "Cordoba" 3)) (newP "Cordoba" 2)) "Cordoba") == 6,
      freeCellsT (unloadT (loadT (loadT (newT 2 3 (newR ["Cordoba", "Rosario"])) (newP "Rosario" 3)) (newP "Cordoba" 2)) "Rosario") == 4,
      netT (newT 2 3 (newR ["Cordoba", "Rosario"])) == 0,
      netT (loadT (newT 2 3 (newR ["Cordoba"])) (newP "Cordoba" 4)) == 4,
      netT (foldl loadT (newT 1 2 (newR ["Cordoba"])) [newP "Cordoba" 5, newP "Cordoba" 5]) == 10
    ]

