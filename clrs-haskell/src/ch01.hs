
module Ch01 where

import Data.List
import Data.Foldable

insertionSort :: Ord a => [a] -> [a]
insertionSort = foldr insert []

