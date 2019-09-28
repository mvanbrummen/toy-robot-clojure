(ns toy-robot.reporting
  (:require [toy-robot.event-handling :as event-handling]
            [toy-robot.direction :refer [direction->string]]))

(defn report [events]
  (let [state (event-handling/reduce-events events)]
    (str (direction->string (:direction state)) "," (:x state) "," (:y state))))


