(ns toy-robot.event-handling
  (:require [toy-robot.config :as config]))

(defn handle-move [prev])

(defn handle-turn [prev event])

(defn handle-place [event]
  (let [{{:keys [direction x y]} :value} event]
    {:direction direction :x x :y y}))

(defn next-state [state event]
  (let [type (:type event)]
    (case type
      :turn (handle-turn state event)
      :place (handle-place event)
      :move (handle-move state))))

(defn valid-state? [state]
  (let [{:keys [x y]} state]
    (and
     (> x 0)
     (> y 0)
     (< x config/board-size)
     (< y config/board-size))))

(defn event-source [prev-state event]
  (let [next-state (next-state prev-state event)]
    (if (valid-state? next-state) next-state prev-state)))

(def default-state {:direction nil :x nil :y nil})

(defn reduce-events [events]
  (reduce event-source default-state events))