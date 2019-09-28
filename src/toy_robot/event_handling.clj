(ns toy-robot.event-handling)


(defn handle-move [prev] )


(defn handle-turn [prev event] )


(defn handle-place [event] )


(defn next-state [state event]] 
    (let [type (:type event)] 
        (case type 
        :turn (handle-turn state event)
        :place (handle-place event)
        :move (handle-move state)
        )
        )
)