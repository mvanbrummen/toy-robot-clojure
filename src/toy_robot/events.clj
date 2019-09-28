(ns toy-robot.events
  (:require [clojure.string :refer [lower-case]]
             [toy-robot.input-handling :refer [re-match-place]]
             [toy-robot.direction :refer [string->direction]]
             ))

(defn turn [input] {:type :turn :value (keyword (lower-case input))})

(defn place [input]
  (let [[str x y direction] (re-match-place input)]
    {:type :place
     :value {:x (Integer. x)
             :y (Integer. y)
             :direction (string->direction direction)}}))

(defn move [input] {:type :move :value nil})