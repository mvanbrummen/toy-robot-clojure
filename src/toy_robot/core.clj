(ns toy-robot.core
  (:require [toy-robot.input-handling :as input]
            [toy-robot.event-handling :as event-handling]
            [toy-robot.events :as events]
            [toy-robot.reporting :as reporting])
  (:gen-class))

(def events (atom []))

(defn add-event [event] (swap! events conj event))
(defn print-usage [] (println "Valid commands are PLACE, MOVE, LEFT, RIGHT, REPORT and QUIT"))

(defn process-input [in]
  (cond
    (input/validate-place in) (add-event (events/place in))
    (input/validate-turn in) (add-event (events/turn in))
    (input/validate-move in)  (add-event (events/move in))

    (input/validate-quit in) (System/exit 0)
    (input/validate-report in)  (reporting/report @events)
    :else (print-usage)))

(defn main-loop
  ([]
   (loop []
     (let [in (read-line)]
       (process-input in)
       (recur))))

  ([lines] (dorun (map process-input lines))))

(defn get-lines [file]
  (-> file
      slurp
      (clojure.string/split-lines)))

(defn -main
  [& args]
  (println "Booting up Toy Robot...beep...bop...")

  (if-not (empty? args)
    (main-loop (get-lines (first args)))
    (main-loop)))
