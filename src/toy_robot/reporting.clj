(ns toy-robot.reporting
  (:require [toy-robot.event-handling :as event-handling]
            [toy-robot.direction :as direction]))

(defn report [events]
  (let [state (event-handling/apply-events events)]
    (str  (:direction state) "," (:x state) "," (:y state))))


