(ns toy-robot.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :refer [lower-case upper-case replace]])
  (:gen-class))

;; board size ie 5x5
(def board-size 5)

;; directions

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

;; app state

(def app-state (atom {:direction nil :coords {:x nil :y nil}}))

(defn app-state-nil? []
  (and
   (nil? (:direction @app-state))
   (nil? (get-in @app-state [:coords :x]))
   (nil? (get-in @app-state [:coords :y]))))

(defn re-match-place [str]  (re-matches #"PLACE (\d),(\d),(NORTH|EAST|WEST|SOUTH)" str))

;; commands 

(defn report [] (str (direction->string (:direction @app-state)) "," (get-in @app-state [:coords :x]) "," (get-in @app-state [:coords :y])))

(defn place [args]
  (let [[str x y direction] args]
    (swap! app-state merge {:direction (string->direction direction) :coords {:x  (Integer. x) :y (Integer. y)}})))

(defn left [] (swap! app-state update-in [:direction] direction-left))
(defn right [] (swap! app-state update-in [:direction] direction-right))

(defn update-y [y] (swap! app-state assoc-in [:coords :y] y))
(defn update-x [x] (swap! app-state assoc-in [:coords :x] x))

(defn valid-coords? [x y]
  (and
   (>= x 0)
   (>= y 0)
   (< x board-size)
   (< y board-size)))

(defn move []
  (let [direction (:direction @app-state)
        x (get-in @app-state [:coords :x])
        y (get-in @app-state [:coords :y])]

  (case direction
    :north (do (when (valid-coords? x (inc y))  (update-y (inc y))))
    :east (do (when (valid-coords? (inc x) y)  (update-x (inc x))))
    :south (do (when (valid-coords? x (dec y))  (update-y (dec y))))
    :west (do (when (valid-coords? (dec x) y)  (update-x (dec x)))))))

;; validate commands


(defn validate-report [str] (= str "REPORT"))
(defn validate-quit [str] (= str "QUIT"))
(defn validate-move [str] (= str "MOVE"))
(defn validate-left [str] (= str "LEFT"))
(defn validate-right [str] (= str "RIGHT"))
(defn validate-place [str] (some? (re-match-place str)))

;; loop

(defn main-loop []
  (loop [in (read-line)]
    (cond
      (validate-place in) (do (place (re-match-place in)) (recur (read-line)))
      (validate-left in) (do (left) (recur (read-line)))
      (validate-right in) (do (right) (recur (read-line)))
      (validate-move in) (do (move) (recur (read-line)))
      (validate-quit in) (System/exit 0)
      (validate-report in) (if (app-state-nil?) (recur (read-line)) (do (println (report)) (recur (read-line))))
      :else (recur (read-line)))))

;; main func

(defn -main
  [& _]
  (println "Booting up Toy Robot...beep...bop...")
  (main-loop))
