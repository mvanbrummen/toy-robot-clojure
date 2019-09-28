(ns toy-robot.reporting
  (:require [toy-robot.event-handling :as event-handling]
            [toy-robot.direction :refer [direction->string]]))

(defn report [events]
  (let [event (event-handling/reduce-events events)
        {:keys [direction x y]} event]
    (when (not (= event event-handling/default-state)) (println (str (direction->string direction) "," x "," y)))))

