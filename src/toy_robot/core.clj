(ns toy-robot.core
  (:require [toy-robot.input-handling :as input]
            [toy-robot.event-handling :as event-handling]
            [toy-robot.events :as events]
            [toy-robot.reporting :as reporting])
  (:gen-class))

(def events (atom []))

(defn add-event [event] (swap! events conj event))

(defn main-loop []
  (loop []
    (let [in (read-line)]
      (cond
        (input/validate-place in) (do (add-event (events/place in)) (recur))
        (input/validate-turn in) (do (add-event (events/turn in)) (recur))
        (input/validate-move in) (do (add-event (events/move in)) (recur))

        (input/validate-quit in) (System/exit 0)
        (input/validate-report in) (do (reporting/report @events) (recur))
        :else (recur)))))

(defn -main
  [& _]
  (println "Booting up Toy Robot...beep...bop...")
  (main-loop))
