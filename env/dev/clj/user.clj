(ns user
  (:require [mpesa-sender.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [mpesa-sender.core :refer [start-app]]
            [mpesa-sender.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'mpesa-sender.core/repl-server))

(defn stop []
  (mount/stop-except #'mpesa-sender.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'mpesa-sender.db.core/*db*)
  (mount/start #'mpesa-sender.db.core/*db*)
  (binding [*ns* 'mpesa-sender.db.core]
    (conman/bind-connection mpesa-sender.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


