(ns mpesa-sender.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[mpesa-sender started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[mpesa-sender has shut down successfully]=-"))
   :middleware identity})
