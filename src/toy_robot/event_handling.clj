(ns toy-robot.event-handling
  (:require [toy-robot.config :as config]
            [toy-robot.direction :refer [direction-left direction-right]]))

(defn handle-move [prev]
  (let [{:keys [direction]} prev]
    (case direction
      :north (update prev :x inc)
      :east (update prev :y inc)
      :south (update prev :x dec)
      :west (update prev :y dec))))

(defn handle-turn [prev event]
  (let [{:keys [value]} event]
    (case value
      :left (update prev :direction direction-left)
      :right (update prev :direction direction-right))))

(defn handle-place [event]
  (let [{{:keys [direction x y]} :value} event]
    {:direction direction :x x :y y}))

(defn next-state [state event]
  (let [{:keys [type]} event]
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