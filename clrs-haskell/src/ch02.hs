module Ch01 where

import Data.List
import Data.Foldable
import Test.QuickCheck
import Test.QuickCheck.All
import Data.Monoid

insertionSort :: Ord a => [a] -> [a]
insertionSort = foldr insert []

-- quick check props :

-- sorting should not change a list length
prop_sortPreservesLength xs = length xs == length (insertionSort xs)

-- sort is idempotent
prop_sortIdempotent xs = insertionSort (insertionSort xs) == insertionSort xs
-- to run 500 tests ->  quickCheckWith stdArgs { maxSuccess = 500 } prop_sortIdempotent

prop_sort_nn_min xs = not (null xs) ==> head (insertionSort xs) == minimum xs
prop_sort_nn_max xs = not (null xs) ==> last (insertionSort xs) == maximum xs

-- Execice 2.1.2
reverseInsertionSort :: Ord a => [a] -> [a]
reverseInsertionSort = foldr reverseInsert []

reverseInsert :: Ord a => a -> [a] -> [a]
reverseInsert x xs = insertBy (flip compare) x xs

-- another way to write reverseInsert
reverseInsert' :: Ord a => a -> [a] -> [a]
reverseInsert' x [] = [x]
reverseInsert' x ls = if x >= head ls
                        then x:ls
                        else head ls : reverseInsert' x (tail ls)

prop_reverseInsertionSort xs = reverseInsertionSort xs == reverse (insertionSort xs)

-- Exercice 2.1.3: search if an element is in a list and return its index
search :: Ord a => a -> [a] -> Maybe a
search x ls = find (==x) ls

search' :: Ord a => a -> [a] -> Maybe a
search' v ls = getFirst . foldMap (\x -> First (if v == x then Just x else Nothing))




