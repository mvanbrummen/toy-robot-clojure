(ns toy-robot.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :refer [lower-case upper-case replace]]
            [toy-robot.config]
            [toy-robot.input-handling :as input]
            [toy-robot.events :as events])
  (:gen-class))

(def events (atom []))

(defn main-loop []
  (loop []
    (let [in (read-line)]
      (cond
        (input/validate-place in) (do (swap! events conj (events/place-event in)) (recur))
        (input/validate-turn in) (do (swap! events conj (events/turn-event in)) (recur))
        (input/validate-move in) (do (swap! events conj (events/move-event in)) (recur))

        (input/validate-quit in) (System/exit 0)
        (input/validate-report in) (do (println @events) (recur))
        :else (recur)))))

(defn -main
  [& _]
  (println "Booting up Toy Robot...beep...bop...")
  (main-loop))
