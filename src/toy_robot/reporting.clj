(ns toy-robot.reporting
  (:require [toy-robot.event-handling :as event-handling]
            [toy-robot.direction :refer [direction->string]]))

(defn report [events]
  (let [{:keys [direction x y]} (event-handling/reduce-events events)]
    (str (direction->string direction) "," x "," y)))


