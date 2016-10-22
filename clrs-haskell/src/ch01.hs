module Ch01 where

import Data.List
import Data.Foldable
import Test.QuickCheck
import Test.QuickCheck.All

insertionSort :: Ord a => [a] -> [a]
insertionSort = foldr insert []

-- quick check props :

-- sorting should not change a list length
prop_sortPreservesLength xs = length xs == length (insertionSort xs)

-- sort is idempotent
prop_sortIdempotent xs = insertionSort (insertionSort xs) == insertionSort xs

-- sorted first is less than last
prop_sortFirstLessThanLast xs = not (null xs) ==> head (insertionSort xs) < last (insertionSort xs)
