(ns mpesa-sender.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [mpesa-sender.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[mpesa-sender started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[mpesa-sender has shut down successfully]=-"))
   :middleware wrap-dev})
