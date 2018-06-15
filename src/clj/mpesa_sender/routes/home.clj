(ns mpesa-sender.routes.home
  (:use mpesa-sender.routes.services)
  (:require [mpesa-sender.layout :as layout]
            [mpesa-sender.db.core :as db]
            [mpesa-sender.util :refer [upload-file read-csv-file]]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [ring.util.response :refer [redirect response]]
            [clojure.java.io :as io]
            [mpesa-sender.db.db-helper :refer [save-payment get-payments]]))

(def resource-path "/tmp/")

(defn home-page [{:keys [flash]}]
  (layout/render
    "home.html"
    (merge {:display-upload-table false} (select-keys flash [:name :message :errors]))))

(defn report-page [{:keys [flash params]}]
  (layout/render
    "reports.html"
    (merge {:uploads              (get-payments)
            :display-upload-table true} (select-keys flash [:name :message :errors]))))


(defn process-transaction-response [request]
  )

(defn process-transaction-timeout [request]
  )

(defroutes home-routes
           (GET "/" request (home-page request))

           (GET "/reports" request (report-page request))

           (POST "/MPESAResponse" request (process-transaction-response request))

           (POST "/MPESATimeout" request (process-transaction-timeout request))

           (POST "/upload" [file]
             (upload-file resource-path file)
             (redirect "/")))

