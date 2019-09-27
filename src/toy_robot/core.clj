(ns toy-robot.core
(:require [clojure.core.match :refer [match]])
  (:gen-class))

(def direction [:north :east :south :west])

(def app-state (atom {:direction nil :coords nil}))

(defn main-loop [] 
  (loop [in (read-line)] 
    (match [in]
      ["exit"] (System/exit 0)
      ["loop"] (recur (read-line))
    )
  ))

(defn -main
  [& _]
  (main-loop))
