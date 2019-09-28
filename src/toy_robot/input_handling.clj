(ns toy-robot.input-handling
  (:require [clojure.string :refer [lower-case]]
            [toy-robot.direction :as direction]))

(defn re-match-place [str]  (re-matches #"PLACE (\d),(\d),(NORTH|EAST|WEST|SOUTH)" str))
(defn re-match-turn [str]  (re-matches #"(LEFT|RIGHT)" str))

(defn validate-report [str] (= str "REPORT"))
(defn validate-quit [str] (= str "QUIT"))
(defn validate-move [str] (= str "MOVE"))
(defn validate-turn [str] (some? (re-match-turn str)))
(defn validate-place [str] (some? (re-match-place str)))

