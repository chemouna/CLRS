
module Ch01 where

import Data.List
import Data.Foldable

insertionSort :: Ord a => [a] -> [a]
insertionSort = foldr insert []

-- quick check props :
-- sorting should not change a list length
sortPreservesLength :: Ord a => [a] -> Bool
sortPreservesLength xs = length xs == length (insertionSort xs)
