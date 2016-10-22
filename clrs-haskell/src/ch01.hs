
module Ch01 where

import Data.List
import Data.Foldable
import Test.QuickCheck
import Test.QuickCheck.All

insertionSort :: Ord a => [a] -> [a]
insertionSort = foldr insert []

-- quick check props :
-- sorting should not change a list length
prop_sortPreservesLength :: Ord a => [a] -> Bool
prop_sortPreservesLength xs = length xs == length (insertionSort xs)

