(ns toy-robot.core
  (:require [clojure.core.match :refer [match]])
  (:gen-class))

(def direction [:north :east :south :west])

(def app-state (atom {:direction nil :coords {:x nil :y nil}}))

(defn app-state-nil? []
  (and
   (nil? (:direction @app-state))
   (nil? (get-in @app-state [:coords :x]))
   (nil? (get-in @app-state [:coords :y]))))

(defn report [] (str (:direction @app-state) "," (get-in @app-state [:coords :x]) "," (get-in @app-state [:coords :y])))

(defn re-match-place [str]  (re-matches #"PLACE (\d),(\d),(NORTH|EAST|WEST|SOUTH)" str))

(defn validate-report [str] (= str "REPORT"))
(defn validate-quit [str] (= str "QUIT"))
(defn validate-move [str] (= str "MOVE"))
(defn validate-left [str] (= str "LEFT"))
(defn validate-right [str] (= str "RIGHT"))
(defn validate-place [str] (some? (re-match-place str)))

(defn place [args]
  (let [[str x y direction] args]
    (swap! app-state merge {:direction direction :coords {:x  (Integer. x) :y (Integer. y)}})))

(defn main-loop []
  (loop [in (read-line)]
    (cond
      (validate-place in) (do (place (re-match-place in)) (recur (read-line)))
      (validate-left in) (recur (read-line))
      (validate-right in) (recur (read-line))
      (validate-move in) (recur (read-line))
      (validate-quit in) (System/exit 0)
      (validate-report in) (if (app-state-nil?) (recur (read-line)) (println (report)))
      :else (recur (read-line)))))

(defn -main
  [& _]
  (println "Booting up Toy Robot...beep...bop...")
  (main-loop))
