(ns toy-robot.event-handling
  (:require [toy-robot.config :as config]))

(defn handle-move [prev])

(defn handle-turn [prev event])

(defn handle-place [event] {:direction (get-in event [:value :direction]) :x (get-in event [:value :x]) :y (get-in event [:value :y])})

(defn next-state [state event]
  (let [type (:type event)]
    (case type
      :turn (handle-turn state event)
      :place (handle-place event)
      :move (handle-move state))))

(defn valid-state? [state]
  (and
   (> (:x state) 0)
   (> (:y state) 0)
   (< (:x state) config/board-size)
   (< (:y state) config/board-size)))

(defn event-source [prev-state event]
  (let [next-state (next-state prev-state event)]
    (if (valid-state? next-state) next-state prev-state)))

(defn reduce-events [events]
  (reduce event-source {:direction nil :x nil :y nil} events))