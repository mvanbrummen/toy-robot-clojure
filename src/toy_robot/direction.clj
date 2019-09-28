(ns toy-robot.direction
  (:require [clojure.string :refer [lower-case upper-case]]))

(def direction [:north :east :south :west])

(defn direction-left [dir]
  (case dir
    :north :west
    :east :north
    :south :east
    :west :south
    :else dir))

(defn direction-right [dir]
  (case dir
    :north :east
    :east :south
    :south :east
    :west :north
    :else dir))

(defn string->direction [str]
  (-> str
      (lower-case)
      (keyword)))

(defn direction->string [dir]
  (-> dir
      (str)
      (upper-case)
      (replace #":" "")))
