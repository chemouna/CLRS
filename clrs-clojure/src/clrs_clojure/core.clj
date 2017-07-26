(ns clrs-clojure.core
  (:gen-class))

(defn find-max-crossing-subarray [A low mid high]
  (if (or (= low mid) (= mid high)) nil
    (let [coll (take (inc high) (drop low A))
          left (take (inc (- mid low)) coll)
          right (take-last (- high mid) coll)]
      (#(list (first %) (second %) (apply + (map (partial nth %) [2 3])))
         (apply interleave
                (map (partial apply (partial max-key second))
                     (list (for [i (range (inc (- mid low)))]
                             (list (+ low i) (apply + (drop i left))))
                           (for [i (range (- high mid))]
                             (list (- high i) (apply + (drop-last i right)))))))))))

(defn find-maximum-subarray [A low high]
  (if (= high low)
    (list low high (nth A low))
    (let [mid (int (/ (+ low high) 2))]
      (apply (partial max-key #(nth % 2))
             (remove nil?
                     (list (find-maximum-subarray A low mid)
                           (find-maximum-subarray A (inc mid) high)
                           (find-max-crossing-subarray A low mid high)))))))

;; Written in a more lipsy way
(defn find-max-crossing-subseq [left right]
  (apply concat
         (map (partial apply (partial max-key (partial apply +)))
              (list (for [left-i (range (count left))]
                      (drop left-i left))
                    (for [right-i (range (count right))]
                      (drop-last right-i right))))))

(defn find-maximum-subseq [coll]
  (if (= 1 (count coll))
    coll
    (let [splitted (split-at (/ (count coll) 2) coll)]
      (max-key (partial apply +)
               (find-maximum-subseq (first splitted))
               (find-maximum-subseq (last splitted))
               (find-max-crossing-subseq (first splitted) (last splitted))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
