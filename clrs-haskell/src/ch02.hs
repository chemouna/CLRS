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
search :: Eq a => a -> [a] -> Maybe a
search x ls = find (==x) ls

-- search' :: Eq a => a -> [a] -> Maybe a
-- search' v ls = getFirst . foldMap (\ x -> First (if v == x then Just x else Nothing))

-- simple linear search
searchL :: Eq a => a -> [a] -> Maybe a
searchL _ [] = Nothing
searchL v (x:xs) = if v == x then return x else searchL v xs

-- Exercice 2.2-2
selectionSort :: Ord a => [a] -> [a]
selectionSort [] = []
selectionSort xs = let x = maximum xs in selectionSort (remove x xs) ++ [x]
  where remove _ [] = []
        remove a (x:xs)
          | x == a = xs
          | otherwise = x : remove a xs

selectionSort2 :: Ord a => [a] -> [a]
selectionSort2 [] = []
selectionSort2 xs = let x = minimum xs in [x] ++ selectionSort2 (remove x xs)
  where remove _ [] = []
        remove a (x:xs)
          | x == a = xs
          | otherwise = x : remove a xs

prop_selectionSort xs = insertionSort xs == selectionSort xs
prop_selectionSort2 xs = insertionSort xs == selectionSort2 xs
